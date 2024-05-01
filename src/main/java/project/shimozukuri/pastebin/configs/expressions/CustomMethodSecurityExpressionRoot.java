package project.shimozukuri.pastebin.configs.expressions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import project.shimozukuri.pastebin.entities.Role;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.exeptions.ResourceNotFoundException;
import project.shimozukuri.pastebin.services.RoleService;
import project.shimozukuri.pastebin.services.impls.UserServiceImpl;

@Getter
@Setter
public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;

    private UserServiceImpl userService;
    private RoleService roleService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean canAccessUser(Long id) {
        User user = userService.getByUsername((String) this.getPrincipal()).orElseThrow(() ->
                new ResourceNotFoundException("Пользователь не найден"));
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(roleService.getAdminRole());
    }

    private boolean hasAnyRole(Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());

        return authentication.getAuthorities().contains(authority);
    }

    public boolean canAccessNote(Long noteId) {
        User user = userService.getByUsername((String) this.getPrincipal()).orElseThrow(() ->
                new ResourceNotFoundException("Пользователь не найден"));
        Long userId = user.getId();

        return userService.isNoteOwner(userId, noteId) || hasAnyRole(roleService.getAdminRole());
    }

    @Override
    public Object getThis() {
        return target;
    }
}
