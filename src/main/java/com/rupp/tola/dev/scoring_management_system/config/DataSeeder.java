package com.rupp.tola.dev.scoring_management_system.config;

import com.rupp.tola.dev.scoring_management_system.entity.Permission;
import com.rupp.tola.dev.scoring_management_system.entity.RefreshToken;
import com.rupp.tola.dev.scoring_management_system.entity.Role;
import com.rupp.tola.dev.scoring_management_system.entity.User;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import com.rupp.tola.dev.scoring_management_system.repository.PermissionRepository;
import com.rupp.tola.dev.scoring_management_system.repository.RolesRepository;
import com.rupp.tola.dev.scoring_management_system.repository.UsersRepository;
import com.rupp.tola.dev.scoring_management_system.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Seeding data...");

        Permission adminRead = perm("admin:read" , "Can access GET method." , "ADMIN_MANAGEMENT");
        Permission adminWrite = perm("admin:write" , "Can access POST and PUT method." , "ADMIN_MANAGEMENT");
        Permission adminDelete = perm("admin:delete" , "Can access DELETE method." , "ADMIN");

        Role roleAdmin = createRole("ROLE_ADMIN" , "Only admin can use this role." ,
                "ACTIVE" , Set.of(adminRead, adminWrite, adminDelete));

        createRole("ROLE_STAFF" , "Only staff can use this role." , "ACTIVE" , Set.of(adminRead, adminWrite, adminDelete));

        if(!usersRepository.existsByEmail("admin@gmail.com")) {
            User admin = new User();
            admin.setFullName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setVerified(true);
            admin.setRole(roleAdmin);
            admin.setVerificationToken(jwtService.generateToken("admin"));
            RefreshToken refreshToken = refreshTokenService.create();
            refreshToken.setUser(admin);
            admin.setRefreshToken(refreshToken);
            usersRepository.save(admin);
        }

    }

    public Permission perm(String name, String description, String module) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> {
                    Permission permission = new Permission();
                    permission.setName(name);
                    permission.setDescription(description);
                    permission.setModule(module);
                    return permissionRepository.save(permission);
                });
    }

    public Role createRole(String name , String description , String status , Set<Permission> permissions) {
        return rolesRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    role.setDescription(description);
                    role.setStatus(status);
                    role.setPermissions(permissions);
                    return rolesRepository.save(role);
                });
    }
}
