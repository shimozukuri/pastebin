package project.shimozukuri.pastebin.configs.expressions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.shimozukuri.pastebin.entities.Role;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.exeptions.ResourceNotFoundException;
import project.shimozukuri.pastebin.services.RoleService;
import project.shimozukuri.pastebin.services.impls.UserServiceImpl;

@Service("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserServiceImpl userService;
    private final RoleService roleService;

    public boolean canAccessUser(Long id) {
        User user = getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(roleService.getAdminRole());
    }

    private boolean hasAnyRole(Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());

        return authentication.getAuthorities().contains(authority);
    }

    public boolean canAccessNote(Long noteId) {
        User user = getPrincipal();
        Long userId = user.getId();

        return userService.isNoteOwner(userId, noteId) || hasAnyRole(roleService.getAdminRole());
    }

    private User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return userService.getByUsername((String) authentication.getPrincipal()).orElseThrow(() ->
                new ResourceNotFoundException("Пользователь не найден"));
    }
}
