package project.shimozukuri.pastebin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shimozukuri.pastebin.entities.Role;
import project.shimozukuri.pastebin.repositories.RoleRepository;
import project.shimozukuri.pastebin.services.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
