package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.dto.RolesDTO;
import com.rupp.tola.dev.scoring_management_system.entity.Roles;
import com.rupp.tola.dev.scoring_management_system.mapper.RolesMapper;
import com.rupp.tola.dev.scoring_management_system.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RolesController {

	private final RoleService roleService;

	@PostMapping
	public ResponseEntity<?> createRole(@RequestBody @Valid RolesDTO rolesDTO) {
		Roles roles = RolesMapper.INSTANCE.toRoles(rolesDTO);
		roles = roleService.createRoles(roles);
		return ResponseEntity.ok(RolesMapper.INSTANCE.toRolesDTO(roles));

	}

	@GetMapping
	public ResponseEntity<?> getRoles() {
		return ResponseEntity.ok(roleService.roles());
	}

	@GetMapping("{roleId}")
	public ResponseEntity<?> getById(@PathVariable("roleId") UUID id) {
		Roles roles = roleService.getById(id);
		return ResponseEntity.ok(RolesMapper.INSTANCE.toRolesDTO(roles));
	}

	@GetMapping("/roleUpdate")
	public ResponseEntity<?> getAllRoles(@RequestParam(defaultValue = "false") Boolean status) {
		return ResponseEntity.ok(roleService.findByActive(status));
	}

	@PutMapping("{roleId}")
	public ResponseEntity<?> eDitRole(@PathVariable("roleId") UUID roleid, @RequestBody @Valid RolesDTO dto) {
		Roles roles = RolesMapper.INSTANCE.toRoles(dto);
		Roles editRole = roleService.editRole(roleid, roles);
		return ResponseEntity.ok(RolesMapper.INSTANCE.toRolesDTO(editRole));
	}

	@PatchMapping("{roleId}/status")
	public ResponseEntity<?> updateStatus(@PathVariable("roleId") UUID id, @RequestParam Boolean status) {
		Roles updateStatus = roleService.updateStatus(id, status);
		return ResponseEntity.ok(updateStatus);
	}
}
