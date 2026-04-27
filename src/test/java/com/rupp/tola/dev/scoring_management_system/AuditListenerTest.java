package com.rupp.tola.dev.scoring_management_system;

import com.rupp.tola.dev.scoring_management_system.audit.AuditListener;
import com.rupp.tola.dev.scoring_management_system.entity.Permission;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class AuditListenerTest {
    @Test
    public void testToJson() throws Exception {
        AuditListener listener = new AuditListener();
        Permission p = new Permission();
        p.setId(UUID.randomUUID());
        p.setName("TEST_PERM");
        p.setCreatedAt(LocalDate.now());

        // Use reflection to call private toJson method
        java.lang.reflect.Method method = AuditListener.class.getDeclaredMethod("toJson", Object.class);
        method.setAccessible(true);
        String json = (String) method.invoke(listener, p);
        
        if (json == null) {
            System.err.println("JSON IS NULL!");
        } else {
            System.out.println("Resulting JSON: " + json);
        }
    }
}
