//package com.rupp.tola.dev.scoring_management_system.repository;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//
//import com.rupp.tola.dev.scoring_management_system.entity.Roles;
//
//@DataJpaTest
//public class RoleRepositoryTest {
//	@Autowired
//	private RolesRepository rolesRepository;
//
//	@Test
//	void testFindByStatus() {
//		Roles role = new Roles();
////		role.setName("ADMIN");
////		role.setStatus(false);
//
//		rolesRepository.save(role);
//
//		List<Roles> result = rolesRepository.findByStatus(false);
//
//		assertFalse(result.isEmpty());
//	}
//}
