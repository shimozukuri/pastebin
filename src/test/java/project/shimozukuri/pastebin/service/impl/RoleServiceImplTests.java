package project.shimozukuri.pastebin.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shimozukuri.pastebin.config.TestConfig;
import project.shimozukuri.pastebin.entities.Role;
import project.shimozukuri.pastebin.repositories.RoleRepository;
import project.shimozukuri.pastebin.services.impls.RoleServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTests {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Test
    public void RoleServiceImpl_GetUserRole_ReturnRole() {
        String name = "ROLE_USER";
        Role testRole = new Role();
        testRole.setName(name);

        when(roleRepository.findByName(name)).thenReturn(Optional.of(testRole));

        Role role = roleService.getUserRole();

        verify(roleRepository).findByName(name);

        assertEquals(role.getName(), testRole.getName());
    }

    @Test
    public void RoleServiceImpl_GetAdminRole_ReturnRole() {
        String name = "ROLE_ADMIN";
        Role testRole = new Role();
        testRole.setName(name);

        when(roleRepository.findByName(name)).thenReturn(Optional.of(testRole));

        Role role = roleService.getAdminRole();

        verify(roleRepository).findByName(name);

        assertEquals(role.getName(), testRole.getName());
    }
}
