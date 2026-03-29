package com.rupp.tola.dev.scoring_management_system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions-testing")
@PreAuthorize("hasRole('admin')")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:write')")
    public String admin() {
        return "admin";
    }

}
