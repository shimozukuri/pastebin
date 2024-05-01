package project.shimozukuri.pastebin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shimozukuri.pastebin.entities.Role;
import project.shimozukuri.pastebin.exeptions.ResourceNotFoundException;
import project.shimozukuri.pastebin.repositories.RoleRepository;
import project.shimozukuri.pastebin.services.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(() ->
                new ResourceNotFoundException("Роль не найдена"));
    }

    @Override
    @Transactional
    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").orElseThrow(() ->
                new ResourceNotFoundException("Роль не найдена"));
    }


}
