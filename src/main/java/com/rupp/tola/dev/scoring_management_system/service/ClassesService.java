package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.Class;

public interface ClassesService {
	Class createClasses(Class aClass);

	List<Class> getClasses(Boolean status);

	Class getById(UUID id);

	Class editclasses(UUID id, Class aClass);
}
