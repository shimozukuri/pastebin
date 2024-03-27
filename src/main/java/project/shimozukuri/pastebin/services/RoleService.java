package project.shimozukuri.pastebin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shimozukuri.pastebin.entities.Role;
import project.shimozukuri.pastebin.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
