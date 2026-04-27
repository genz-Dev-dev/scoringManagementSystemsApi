package com.rupp.tola.dev.scoring_management_system.audit;

import com.rupp.tola.dev.scoring_management_system.entity.AuditLog;
import com.rupp.tola.dev.scoring_management_system.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@Component
public class AuditListener {

    private static AuditLogRepository repository;

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // store previous state
    private static final Map<Object, String> CACHE = new WeakHashMap<>();

    @Autowired
    public void setRepository(AuditLogRepository repo) {
        AuditListener.repository = repo;
    }

    // capture original state AFTER loading from DB
    @PostLoad
    public void postLoad(Object entity) {
        try {
            CACHE.put(entity, toJson(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PrePersist
    public void prePersist(Object entity) {
        saveAudit(entity, "INSERT", null, toJson(entity));
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        String oldData = CACHE.get(entity);
        saveAudit(entity, "UPDATE", oldData, toJson(entity));
        CACHE.remove(entity);
    }

    @PreRemove
    public void preRemove(Object entity) {
        String oldData = CACHE.get(entity);
        saveAudit(entity, "DELETE", oldData, null);
        CACHE.remove(entity);
    }

    private void saveAudit(Object entity, String action, String oldJson, String newJson) {

        try {
            // prevent audit loop
            if (entity instanceof AuditLog) return;
            AuditLog log = new AuditLog();
            log.setTableName(entity.getClass().getSimpleName());
            log.setAction(action);
            log.setChangeAt(LocalDateTime.now());

            log.setRecordId(extractId(entity));

            log.setOldData(oldJson);
            log.setNewData(newJson);

            repository.save(log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SAFE JSON (NO recursion)
    private String toJson(Object obj) {
        try {
            if (obj == null) return null;

            Map<String, Object> safeMap = new HashMap<>();
            Class<?> currentClass = obj.getClass();

            while (currentClass != null && currentClass != Object.class) {
                for (Field field : currentClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    
                    // Skip static fields to avoid unnecessary data
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    // Skip if overridden by a subclass
                    if (safeMap.containsKey(field.getName())) {
                        continue;
                    }

                    Object value = field.get(obj);

                    if (value == null) {
                        safeMap.put(field.getName(), null);
                        continue;
                    }

                    Class<?> type = field.getType();
                    String pkgName = type.getPackageName();

                    // strict check for safe types to prevent Jackson recursion
                    boolean isSafeType = type.isPrimitive() || type.isEnum() ||
                            (type.isArray() && type.getComponentType().isPrimitive()) ||
                            (pkgName != null && (
                                    pkgName.startsWith("java.lang") ||
                                    pkgName.startsWith("java.time") ||
                                    pkgName.startsWith("java.math") ||
                                    type.getName().equals("java.util.UUID") ||
                                    type.getName().equals("java.util.Date")
                            ));

                    if (isSafeType) {
                        safeMap.put(field.getName(), value);
                    } else {
                        // avoid recursion (relationships, collections, etc)
                        safeMap.put(field.getName(), type.getSimpleName() + ":REF");
                    }
                }
                currentClass = currentClass.getSuperclass();
            }

            return mapper.writeValueAsString(safeMap);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // extract entity ID
    private UUID extractId(Object entity) {
        try {
            Class<?> currentClass = entity.getClass();
            while (currentClass != null && currentClass != Object.class) {
                for (Field field : currentClass.getDeclaredFields()) {

                    if (field.isAnnotationPresent(Id.class)) {
                        field.setAccessible(true);
                        Object value = field.get(entity);

                        if (value instanceof UUID) {
                            return (UUID) value;
                        }

                        // support other types (Long, Integer, etc.)
                        if (value != null) {
                            return UUID.fromString(value.toString());
                        }
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}