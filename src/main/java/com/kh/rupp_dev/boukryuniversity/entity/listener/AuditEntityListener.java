package com.kh.rupp_dev.boukryuniversity.entity.listener;

import com.kh.rupp_dev.boukryuniversity.entity.AuditLog;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditEntityListener {

    @PrePersist
    public void beforeCreate(Object entity) {
        createAuditEntry(entity , "CREATE");
    }

    @PreUpdate
    public void beforeUpdate(Object entity) {
        createAuditEntry(entity , "UPDATE");
    }

    @PreRemove
    public void beforeDelete(Object entity) {
        createAuditEntry(entity , "DELETE");
    }

    private void createAuditEntry(Object entity , String action) {
        String entityName = entity.getClass().getSimpleName();
        String entityId = getIdValue(entity);
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String username = (auth != null && auth.isAuthenticated()) ? auth.getName() : "Anonymous";

        AuditLog auditLog = new AuditLog();
        auditLog.setUsername(username);
        auditLog.setAction(action);
        auditLog.setEntityName(entityName);
        auditLog.setEntityId(entityId);

//        AuditContextHolder.
    }

    private String getIdValue(Object entity) {
         String idField = entity.getClass().getSimpleName();
         return null;
    }

}
