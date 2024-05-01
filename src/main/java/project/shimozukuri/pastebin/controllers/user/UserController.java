package project.shimozukuri.pastebin.controllers.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.shimozukuri.pastebin.dtos.user.UserDto;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.services.impls.UserServiceImpl;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "User API")
public class UserController {
    private final UserServiceImpl userService;

    @PutMapping("/{userId}")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public User update(
            @PathVariable(value = "userId") Long userId,
            @RequestBody UserDto userDto
    ) {
        return userService.update(userId, userDto);
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable(value = "userId") Long userId) {
        return userService.getById(userId);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public void deleteById(@PathVariable(value = "userId") Long userId) {
        userService.deleteById(userId);
    }
}
