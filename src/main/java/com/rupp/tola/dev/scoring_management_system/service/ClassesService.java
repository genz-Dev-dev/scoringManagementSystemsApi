package com.rupp.tola.dev.scoring_management_system.service;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.Classes;

public interface ClassesService {
	Classes createClasses(Classes classes);

	List<Classes> getClasses(Boolean status);

	Classes getById(UUID id);

	Classes editclasses(UUID id, Classes classes);
}
