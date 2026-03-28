import { useEffect, useState } from "react";
import { KeyRound, Link2, Pencil, Plus, Power, ShieldCheck, Trash2, Users2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Modal } from "@/components/ui/modal";
import { Table } from "@/components/ui/table";
import {
  permissionsApi,
  rolesApi,
  usersApi,
  type PermissionInput,
  type RoleInput,
} from "@/services/smsApi";
import { getSessionUser } from "@/services/session";
import type { Permission, Role, User } from "@/types/models";

type ModalState = "role" | "permission" | "assignment" | null;

const selectClassName =
  "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2";

const textareaClassName =
  "min-h-28 w-full rounded-md border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2";

const emptyRole: RoleInput = {
  name: "",
  description: "",
  status: "ACTIVE",
  userIds: [],
};

const emptyPermission: PermissionInput = {
  name: "",
  description: "",
  module: "",
  roleIds: [],
};

export default function Access() {
  const sessionUser = getSessionUser();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const [allRoles, setAllRoles] = useState<Role[]>([]);
  const [allPermissions, setAllPermissions] = useState<Permission[]>([]);
  const [roles, setRoles] = useState<Role[]>([]);
  const [permissions, setPermissions] = useState<Permission[]>([]);
  const [users, setUsers] = useState<User[]>([]);

  const [roleFilter, setRoleFilter] = useState("ALL");
  const [moduleFilter, setModuleFilter] = useState("ALL");

  const [modalState, setModalState] = useState<ModalState>(null);
  const [editingRole, setEditingRole] = useState<Role | null>(null);
  const [editingPermission, setEditingPermission] = useState<Permission | null>(null);
  const [assigningRole, setAssigningRole] = useState<Role | null>(null);
  const [roleForm, setRoleForm] = useState<RoleInput>(emptyRole);
  const [permissionForm, setPermissionForm] = useState<PermissionInput>(emptyPermission);
  const [selectedPermissionIds, setSelectedPermissionIds] = useState<string[]>([]);

  const permissionModules = Array.from(new Set(allPermissions.map((permission) => permission.module))).sort();

  const loadAccessData = async (nextRoleFilter = roleFilter, nextModuleFilter = moduleFilter) => {
    setLoading(true);
    setError("");

    try {
      const [roleList, permissionList, userPage, completeRoleList, completePermissionList] = await Promise.all([
        nextRoleFilter === "ALL" ? rolesApi.list() : rolesApi.listByStatus(nextRoleFilter),
        nextModuleFilter === "ALL" ? permissionsApi.list() : permissionsApi.listByModule(nextModuleFilter),
        usersApi.list({ size: 100, sortBy: "fullName" }),
        rolesApi.list(),
        permissionsApi.list(),
      ]);

      setAllRoles(completeRoleList);
      setAllPermissions(completePermissionList);
      setRoles(roleList);
      setPermissions(permissionList);
      setUsers(userPage.items);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to load access control data.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void loadAccessData(roleFilter, moduleFilter);
  }, [roleFilter, moduleFilter]);

  const resetModalState = () => {
    setModalState(null);
    setEditingRole(null);
    setEditingPermission(null);
    setAssigningRole(null);
    setRoleForm(emptyRole);
    setPermissionForm(emptyPermission);
    setSelectedPermissionIds([]);
  };

  const openRoleModal = (role?: Role) => {
    setEditingRole(role || null);
    setRoleForm(
      role
        ? {
            name: role.name,
            description: role.description,
            status: role.status,
            userIds: role.userIds,
          }
        : emptyRole
    );
    setModalState("role");
  };

  const openPermissionModal = (permission?: Permission) => {
    setEditingPermission(permission || null);
    setPermissionForm(
      permission
        ? {
            name: permission.name,
            description: permission.description,
            module: permission.module,
            roleIds: permission.roleIds,
          }
        : emptyPermission
    );
    setModalState("permission");
  };

  const openAssignmentModal = (role: Role) => {
    setAssigningRole(role);
    setSelectedPermissionIds(
      allPermissions.filter((permission) => permission.roleIds.includes(role.id)).map((permission) => permission.id)
    );
    setModalState("assignment");
  };

  const handleRoleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);

    try {
      if (editingRole) {
        await rolesApi.update(editingRole.id, roleForm);
      } else {
        await rolesApi.create(roleForm);
      }
      resetModalState();
      await loadAccessData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to save role.");
    } finally {
      setSaving(false);
    }
  };

  const handlePermissionSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);

    try {
      if (editingPermission) {
        await permissionsApi.update(editingPermission.id, permissionForm);
      } else {
        await permissionsApi.create(permissionForm);
      }
      resetModalState();
      await loadAccessData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to save permission.");
    } finally {
      setSaving(false);
    }
  };

  const handleRoleStatus = async (role: Role) => {
    const nextStatus = role.status === "ACTIVE" ? "INACTIVE" : "ACTIVE";
    if (!window.confirm(`Change ${role.name} to ${nextStatus}?`)) return;

    try {
      await rolesApi.updateStatus(role.id, nextStatus);
      await loadAccessData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to update role status.");
    }
  };

  const handlePermissionDelete = async (permission: Permission) => {
    if (!window.confirm(`Delete permission ${permission.name}?`)) return;

    try {
      await permissionsApi.remove(permission.id);
      await loadAccessData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to delete permission.");
    }
  };

  const handleAssignmentSave = async (mode: "append" | "replace") => {
    if (!assigningRole) return;

    setSaving(true);
    try {
      if (mode === "append") {
        await rolesApi.assignPermissions(assigningRole.id, selectedPermissionIds);
      } else {
        await rolesApi.replacePermissions(assigningRole.id, selectedPermissionIds);
      }
      resetModalState();
      await loadAccessData();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to update role permissions.");
    } finally {
      setSaving(false);
    }
  };

  const handleAssignedPermissionRemove = async (role: Role, permission: Permission) => {
    if (!window.confirm(`Remove ${permission.name} from ${role.name}?`)) return;

    try {
      await rolesApi.removePermission(role.id, permission.id);
      await loadAccessData();
      openAssignmentModal(role);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to remove permission from role.");
    }
  };

  const toggleRoleUser = (userId: string) => {
    setRoleForm((current) => ({
      ...current,
      userIds: current.userIds.includes(userId)
        ? current.userIds.filter((currentUserId) => currentUserId !== userId)
        : [...current.userIds, userId],
    }));
  };

  const togglePermissionRole = (roleId: string) => {
    setPermissionForm((current) => ({
      ...current,
      roleIds: current.roleIds.includes(roleId)
        ? current.roleIds.filter((currentRoleId) => currentRoleId !== roleId)
        : [...current.roleIds, roleId],
    }));
  };

  const toggleAssignedPermission = (permissionId: string) => {
    setSelectedPermissionIds((current) =>
      current.includes(permissionId)
        ? current.filter((currentPermissionId) => currentPermissionId !== permissionId)
        : [...current, permissionId]
    );
  };

  const getRolePermissionCount = (roleId: string) =>
    allPermissions.filter((permission) => permission.roleIds.includes(roleId)).length;

  const getPermissionRoles = (permission: Permission) =>
    allRoles.filter((role) => permission.roleIds.includes(role.id)).map((role) => role.name);

  const currentAssignments = assigningRole
    ? allPermissions.filter((permission) => permission.roleIds.includes(assigningRole.id))
    : [];

  const roleColumns = [
    {
      key: "name",
      label: "តួនាទី",
      render: (role: Role) => (
        <div>
          <p className="font-medium text-[13px] text-slate-900">{role.name}</p>
          <p className="mt-1 text-[12px] text-slate-500">{role.description}</p>
        </div>
      ),
    },
    {
      key: "status",
      label: "ស្ថានភាព",
      render: (role: Role) => (
        <span
          className={`rounded-full px-3 py-1 text-[11px] font-medium ${
            role.status === "ACTIVE"
              ? "bg-emerald-100 text-emerald-700"
              : role.status === "DELETE"
                ? "bg-rose-100 text-rose-700"
                : "bg-amber-100 text-amber-700"
          }`}
        >
          {role.status}
        </span>
      ),
    },
    {
      key: "userIds",
      label: "អ្នកប្រើប្រាស់",
      render: (role: Role) => <span className="text-[13px] text-slate-700">{role.userIds.length} គណនី</span>,
    },
    {
      key: "permissions",
      label: "សិទ្ធិ",
      render: (role: Role) => <span className="text-[13px] text-slate-700">{getRolePermissionCount(role.id)} សិទ្ធិ</span>,
    },
    {
      key: "actions",
      label: "សកម្មភាព",
      render: (role: Role) => (
        <div className="flex items-center gap-2">
          <Button variant="outline" size="sm" className="h-8" onClick={() => openAssignmentModal(role)}>
            <Link2 size={14} className="mr-2" />
            ផ្គូផ្គង
          </Button>
          <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => openRoleModal(role)}>
            <Pencil size={16} className="text-sky-600" />
          </Button>
          <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void handleRoleStatus(role)}>
            <Power size={16} className="text-amber-700" />
          </Button>
        </div>
      ),
    },
  ];

  const permissionColumns = [
    {
      key: "name",
      label: "សិទ្ធិ",
      render: (permission: Permission) => (
        <div>
          <p className="font-medium text-[13px] text-slate-900">{permission.name}</p>
          <p className="mt-1 text-[12px] text-slate-500">{permission.description}</p>
        </div>
      ),
    },
    { key: "module", label: "ម៉ូឌុល" },
    {
      key: "roleIds",
      label: "តួនាទី",
      render: (permission: Permission) => (
        <div className="flex flex-wrap gap-2">
          {getPermissionRoles(permission).length === 0 ? (
            <span className="text-[12px] text-slate-500">គ្មានការភ្ជាប់តួនាទី</span>
          ) : (
            getPermissionRoles(permission).map((roleName) => (
              <span key={`${permission.id}-${roleName}`} className="rounded-full bg-slate-50 border border-slate-200 px-3 py-1 text-[11px] font-medium text-slate-700">
                {roleName}
              </span>
            ))
          )}
        </div>
      ),
    },
    {
      key: "actions",
      label: "សកម្មភាព",
      render: (permission: Permission) => (
        <div className="flex items-center gap-2">
          <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => openPermissionModal(permission)}>
            <Pencil size={16} className="text-sky-600" />
          </Button>
          <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => void handlePermissionDelete(permission)}>
            <Trash2 size={16} className="text-rose-600" />
          </Button>
        </div>
      ),
    },
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">គ្រប់គ្រងការចូលប្រើប្រាស់</h1>

        <div className="rounded-md border border-emerald-200 bg-emerald-50 px-4 py-2 text-sm text-emerald-900 flex items-center gap-3">
          <ShieldCheck size={16} className="text-emerald-700" />
          <span className="font-medium">{sessionUser?.fullName || "អនាមិក"}</span>
          <span className="text-xs px-2 py-0.5 rounded bg-emerald-100 text-emerald-800 font-medium">{sessionUser?.role || "—"}</span>
        </div>
      </div>

      {error && <div className="rounded-md border border-rose-200 bg-rose-50 p-3 text-sm text-rose-700">{error}</div>}

      <section className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
        <article className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[11px] uppercase tracking-wider text-slate-500 font-medium">តួនាទី</p>
          <p className="mt-2 text-3xl font-bold text-slate-900">{allRoles.length}</p>
        </article>
        <article className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[11px] uppercase tracking-wider text-slate-500 font-medium">សិទ្ធិ</p>
          <p className="mt-2 text-3xl font-bold text-slate-900">{allPermissions.length}</p>
        </article>
        <article className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[11px] uppercase tracking-wider text-slate-500 font-medium">អ្នកប្រើដែលបានភ្ជាប់</p>
          <p className="mt-2 text-3xl font-bold text-slate-900">
            {allRoles.reduce((total, role) => total + role.userIds.length, 0)}
          </p>
        </article>
        <article className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <p className="text-[11px] uppercase tracking-wider text-slate-500 font-medium">ម៉ូឌុល</p>
          <p className="mt-2 text-3xl font-bold text-slate-900">{permissionModules.length}</p>
        </article>
      </section>

      <section className="grid gap-4 xl:grid-cols-[1.1fr_0.9fr]">
        <article className="rounded-lg border border-slate-200 bg-white shadow-sm overflow-hidden flex flex-col min-w-0">
          <div className="border-b border-slate-200 px-5 py-4 flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between bg-slate-50/50">
            <div>
              <h2 className="text-base font-semibold text-slate-900">តួនាទី</h2>
            </div>
            <div className="flex flex-col gap-3 sm:flex-row sm:items-center">
              <select className={`${selectClassName} text-[13px] border-slate-200 bg-slate-50`} value={roleFilter} onChange={(event) => setRoleFilter(event.target.value)}>
                <option value="ALL">គ្រប់ស្ថានភាព</option>
                <option value="ACTIVE">សកម្ម</option>
                <option value="INACTIVE">អសកម្ម</option>
                <option value="DELETE">បានលុប</option>
              </select>
              <Button className="whitespace-nowrap" onClick={() => openRoleModal()}>
                <Plus size={16} className="mr-2" />
                បន្ថែមតួនាទី
              </Button>
            </div>
          </div>
          <div className="flex-1 overflow-x-auto overflow-y-auto w-full">
            <Table columns={roleColumns} data={roles} isLoading={loading} />
          </div>
        </article>

        <article className="rounded-lg border border-slate-200 bg-white shadow-sm overflow-hidden flex flex-col min-w-0">
          <div className="border-b border-slate-200 px-5 py-4 flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between bg-slate-50/50">
            <div>
              <h2 className="text-base font-semibold text-slate-900">សិទ្ធិ</h2>
            </div>
            <div className="flex flex-col gap-3 sm:flex-row sm:items-center">
              <select
                className={`${selectClassName} text-[13px] border-slate-200 bg-slate-50 w-full lg:w-32 truncate`}
                value={moduleFilter}
                onChange={(event) => setModuleFilter(event.target.value)}
              >
                <option value="ALL">គ្រប់ម៉ូឌុល</option>
                {permissionModules.map((module) => (
                  <option key={module} value={module}>
                    {module}
                  </option>
                ))}
              </select>
              <Button className="whitespace-nowrap" onClick={() => openPermissionModal()}>
                <Plus size={16} className="mr-2" />
                បន្ថែមសិទ្ធិ
              </Button>
            </div>
          </div>

          <div className="flex-1 overflow-x-auto overflow-y-auto w-full">
            <Table columns={permissionColumns} data={permissions} isLoading={loading} />
          </div>
        </article>
      </section>

      <section className="grid gap-4 lg:grid-cols-3">
        <article className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <div className="inline-flex rounded-md bg-amber-50 p-2 text-amber-700 mb-3 block">
            <Users2 size={20} />
          </div>
          <h2 className="text-base font-semibold text-slate-900">សមាជិកភាពតួនាទី</h2>
          <p className="mt-1.5 text-sm text-slate-500">
            ទម្រង់តួនាទីអនុញ្ញាតឱ្យចាត់តាំងលេខសម្គាល់អ្នកប្រើប្រាស់ដោយផ្ទាល់ ដូច្នេះអ្នកអាចរក្សាសមាជិកភាពបុគ្គលិកស្របតាមនិយមន័យតួនាទីដោយមិនចាំបាច់ចាកចេញពីទំព័រនេះ។
          </p>
        </article>

        <article className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <div className="inline-flex rounded-md bg-teal-50 p-2 text-teal-700 mb-3 block">
            <KeyRound size={20} />
          </div>
          <h2 className="text-base font-semibold text-slate-900">ការភ្ជាប់សិទ្ធិ</h2>
          <p className="mt-1.5 text-sm text-slate-500">
            ប្រើប្រាស់សកម្មភាពផ្គូផ្គងនៅលើតួនាទីនីមួយៗ ដើម្បីបន្ថែមសិទ្ធិ ជំនួសសិទ្ធិទាំងអស់ ឬលុបសិទ្ធិមួយចេញម្តងមួយៗ។
          </p>
        </article>

        <article className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
          <div className="inline-flex rounded-md bg-slate-100 p-2 text-slate-700 mb-3 block">
            <Power size={20} />
          </div>
          <h2 className="text-base font-semibold text-slate-900">ចំណាំអំពីស្ថានភាព</h2>
          <p className="mt-1.5 text-sm text-slate-500">
            ចំណុចប្រទាក់សម្រាប់បិទ/បើកស្ថានភាពតួនាទីប្រើប្រាស់ទម្រង់ចុចផ្ទាល់។ ប្រសិនបើមានបញ្ហាសូមពិនិត្យរចនាសម្ព័ន្ធមុខងារនៅលើម៉ាស៊ីនមេ។
          </p>
        </article>
      </section>

      {modalState === "role" && (
        <Modal isOpen onClose={resetModalState} title={editingRole ? "កែប្រែតួនាទី" : "បង្កើតតួនាទី"}>
          <form onSubmit={handleRoleSubmit} className="space-y-4">
            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">ឈ្មោះតួនាទី</label>
              <Input
                required
                className=""
                value={roleForm.name}
                onChange={(event) => setRoleForm((current) => ({ ...current, name: event.target.value }))}
              />
            </div>

            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">ការពិពណ៌នា</label>
              <textarea
                required
                className={textareaClassName}
                value={roleForm.description}
                onChange={(event) => setRoleForm((current) => ({ ...current, description: event.target.value }))}
              />
            </div>

            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">ស្ថានភាព</label>
              <select
                className={selectClassName}
                value={roleForm.status}
                onChange={(event) => setRoleForm((current) => ({ ...current, status: event.target.value }))}
              >
                <option value="ACTIVE">សកម្ម (ACTIVE)</option>
                <option value="INACTIVE">អសកម្ម (INACTIVE)</option>
                <option value="DELETE">លុបចោល (DELETE)</option>
              </select>
            </div>

            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">អ្នកប្រើដែលបានភ្ជាប់</label>
              <div className="max-h-56 space-y-2 overflow-auto rounded-md border border-slate-200 bg-slate-50 p-3">
                {users.length === 0 ? (
                  <p className="text-[13px] text-slate-500">គ្មានអ្នកប្រើប្រាស់ទេ។</p>
                ) : (
                  users.map((user) => (
                    <label key={user.id} className="flex items-center justify-between gap-3 rounded-md bg-white px-3 py-3 shadow-sm border border-slate-200 hover:border-slate-300 transition-colors cursor-pointer">
                      <div>
                        <p className="text-[13px] font-medium text-slate-900">{user.fullName}</p>
                        <p className="text-[11px] text-slate-500 mt-0.5">{user.email}</p>
                      </div>
                      <input
                        type="checkbox"
                        checked={roleForm.userIds.includes(user.id)}
                        onChange={() => toggleRoleUser(user.id)}
                        className="h-4 w-4 accent-sky-600 rounded"
                      />
                    </label>
                  ))
                )}
              </div>
            </div>

            <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
              <Button className="" type="button" variant="outline" onClick={resetModalState}>
                បោះបង់
              </Button>
              <Button className="" type="submit" disabled={saving}>
                {saving ? "កំពុងរក្សាទុក..." : editingRole ? "រក្សាទុកតួនាទី" : "បង្កើតតួនាទី"}
              </Button>
            </div>
          </form>
        </Modal>
      )}

      {modalState === "permission" && (
        <Modal isOpen onClose={resetModalState} title={editingPermission ? "កែប្រែសិទ្ធិ" : "បង្កើតសិទ្ធិ"}>
          <form onSubmit={handlePermissionSubmit} className="space-y-4">
            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">ឈ្មោះសិទ្ធិ</label>
              <Input
                required
                className=""
                value={permissionForm.name}
                onChange={(event) => setPermissionForm((current) => ({ ...current, name: event.target.value }))}
              />
            </div>

            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">ម៉ូឌុល</label>
              <Input
                required
                className=""
                value={permissionForm.module}
                onChange={(event) => setPermissionForm((current) => ({ ...current, module: event.target.value }))}
              />
            </div>

            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">ការពិពណ៌នា</label>
              <textarea
                required
                className={textareaClassName}
                value={permissionForm.description}
                onChange={(event) => setPermissionForm((current) => ({ ...current, description: event.target.value }))}
              />
            </div>

            <div>
              <label className="mb-2 block text-[13px] font-medium text-slate-700">ភ្ជាប់ជាមួយតួនាទី</label>
              <div className="max-h-56 space-y-2 overflow-auto rounded-md border border-slate-200 bg-slate-50 p-3">
                {roles.length === 0 ? (
                  <p className="text-[13px] text-slate-500">គ្មានតួនាទីទេ។</p>
                ) : (
                  allRoles.map((role) => (
                    <label key={role.id} className="flex items-center justify-between gap-3 rounded-md bg-white px-3 py-3 shadow-sm border border-slate-200 hover:border-slate-300 transition-colors cursor-pointer">
                      <div>
                        <p className="text-[13px] font-medium text-slate-900">{role.name}</p>
                        <p className="text-[11px] text-slate-500 mt-0.5">{role.status}</p>
                      </div>
                      <input
                        type="checkbox"
                        checked={permissionForm.roleIds.includes(role.id)}
                        onChange={() => togglePermissionRole(role.id)}
                        className="h-4 w-4 accent-sky-600 rounded"
                      />
                    </label>
                  ))
                )}
              </div>
            </div>

            <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-6">
              <Button className="" type="button" variant="outline" onClick={resetModalState}>
                បោះបង់
              </Button>
              <Button className="" type="submit" disabled={saving}>
                {saving ? "កំពុងរក្សាទុក..." : editingPermission ? "រក្សាទុកសិទ្ធិ" : "បង្កើតសិទ្ធិ"}
              </Button>
            </div>
          </form>
        </Modal>
      )}

      {modalState === "assignment" && assigningRole && (
        <Modal isOpen onClose={resetModalState} title={`ផ្គូផ្គងសិទ្ធិទៅកាន់ ${assigningRole.name}`}>
          <div className="space-y-5">
            <div className="rounded-md border border-slate-200 bg-slate-50 px-4 py-4">
              <p className="text-[13px] font-medium text-slate-900">ការភ្ជាប់បច្ចុប្បន្ន</p>
              <div className="mt-3 flex flex-wrap gap-2">
                {currentAssignments.length === 0 ? (
                  <span className="text-[12px] text-slate-500">មិនទាន់មានសិទ្ធិភ្ជាប់នៅឡើយទេ។</span>
                ) : (
                  currentAssignments.map((permission) => (
                    <button
                      key={permission.id}
                      type="button"
                      onClick={() => void handleAssignedPermissionRemove(assigningRole, permission)}
                      className="rounded-full border border-slate-200 bg-white px-3 py-1 text-[11px] font-medium text-slate-700 transition hover:border-rose-200 hover:bg-rose-50 hover:text-rose-700"
                    >
                      {permission.name} ×
                    </button>
                  ))
                )}
              </div>
            </div>

            <div>
              <p className="text-[13px] font-medium text-slate-900">បណ្ណាល័យសិទ្ធិ</p>
              <div className="mt-3 max-h-72 space-y-2 overflow-auto rounded-md border border-slate-200 bg-slate-50 p-3">
                {allPermissions.length === 0 ? (
                  <p className="text-[13px] text-slate-500">គ្មានសិទ្ធិទេ។</p>
                ) : (
                  allPermissions.map((permission) => (
                    <label key={permission.id} className="flex items-center justify-between gap-3 rounded-md bg-white px-3 py-3 shadow-sm border border-slate-200 hover:border-slate-300 transition-colors cursor-pointer">
                      <div>
                        <p className="text-[13px] font-medium text-slate-900">{permission.name}</p>
                        <p className="text-[11px] text-slate-500 mt-0.5">
                          {permission.module} · {permission.description}
                        </p>
                      </div>
                      <input
                        type="checkbox"
                        checked={selectedPermissionIds.includes(permission.id)}
                        onChange={() => toggleAssignedPermission(permission.id)}
                        className="h-4 w-4 accent-sky-600 rounded"
                      />
                    </label>
                  ))
                )}
              </div>
            </div>

            <div className="rounded-md border border-sky-200 bg-sky-50 px-4 py-3 text-[12px] text-sky-800 leading-relaxed">
              `បន្ថែមលើមានស្រាប់` នឹងបញ្ចូលបន្ថែមសិទ្ធិថ្មីៗទៅតួនាទី។ `ជំនួសទាំងអស់` នឹងបោសសម្អាតសិទ្ធិចាស់ៗ និងប្រើតែសិទ្ធិដែលបានស្នើសុំថ្មីដោយផ្ទាល់ពីបញ្ជីជ្រើសរើស។
            </div>

            <div className="flex justify-end gap-3 pt-4 border-t border-slate-200 mt-2">
              <Button className="" type="button" variant="outline" onClick={resetModalState}>
                បោះបង់
              </Button>
              <Button className="" type="button" variant="secondary" disabled={saving} onClick={() => void handleAssignmentSave("append")}>
                {saving ? "កំពុងរក្សាទុក..." : "បន្ថែមលើមានស្រាប់"}
              </Button>
              <Button className="" type="button" disabled={saving} onClick={() => void handleAssignmentSave("replace")}>
                {saving ? "កំពុងជំនួស..." : "ជំនួសទាំងអស់"}
              </Button>
            </div>
          </div>
        </Modal>
      )}
    </div>
  );
}
