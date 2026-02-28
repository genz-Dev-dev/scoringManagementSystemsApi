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

import com.rupp.tola.dev.scoring_management_system.dto.PermissionDTO;
import com.rupp.tola.dev.scoring_management_system.entity.Permissions;
import com.rupp.tola.dev.scoring_management_system.mapper.PermissionMapper;
import com.rupp.tola.dev.scoring_management_system.security.PermissionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/permissions")
public class PermissionController {

	private final PermissionService permissionService;

	@PostMapping
	public ResponseEntity<?> createPermissions(@RequestBody @Valid PermissionDTO permissionDTO) {
		Permissions permissions = PermissionMapper.INSTANCE.toPermissions(permissionDTO);
		permissions = permissionService.createPermissions(permissions);
		return ResponseEntity.ok(PermissionMapper.INSTANCE.tPermissionDTO(permissions));
	}

	@GetMapping("{permissionId}")
	public ResponseEntity<?> getPermissionById(@PathVariable("permissionId") UUID id) {
		Permissions permissionId = permissionService.getById(id);
		return ResponseEntity.ok(PermissionMapper.INSTANCE.tPermissionDTO(permissionId));
	}

	@PutMapping("{permissionId}")
	public ResponseEntity<?> updatePermissionById(@PathVariable("permissionId") UUID id,
			@RequestBody @Valid PermissionDTO permissionDTO) {
		Permissions permissions = PermissionMapper.INSTANCE.toPermissions(permissionDTO);
		permissions = permissionService.updatePermissionById(id, permissions);
		return ResponseEntity.ok(PermissionMapper.INSTANCE.tPermissionDTO(permissions));
	}

	@PatchMapping("{permissionId}/status")
	public ResponseEntity<?> updateStatus(@PathVariable("permissionId") UUID id, @RequestParam Boolean status) {
		Permissions updated = permissionService.updatePermissionsByStatus(id, status);
		return ResponseEntity.ok(updated);
	}

	@GetMapping
	public ResponseEntity<?> getBystatus(@RequestParam(defaultValue = "false") Boolean status) {
		return ResponseEntity.ok(permissionService.getByStatus(status));
	}
}
