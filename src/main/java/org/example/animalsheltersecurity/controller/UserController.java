package org.example.animalsheltersecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.dto.UserCreateRequest;
import org.example.animalsheltersecurity.dto.UserResponse;
import org.example.animalsheltersecurity.dto.UserUpdateRequest;
import org.example.animalsheltersecurity.model.User;
import org.example.animalsheltersecurity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> users = userService.findAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        User created = userService.create(toEntity(request));
        return ResponseEntity
                .created(URI.create("/api/users/" + created.getId()))
                .body(toResponse(created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        User updated = userService.update(id, toPatch(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private User toEntity(UserCreateRequest request) {
        return User.builder()
                .username(request.username())
                .password(request.password())
                .roles(request.roles() == null ? null : new LinkedHashSet<>(request.roles()))
                .enabled(request.enabled())
                .build();
    }

    private User toPatch(UserUpdateRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        if (request.roles() != null) {
            user.setRoles(new LinkedHashSet<>(request.roles()));
        }
        user.setEnabled(request.enabled());
        return user;
    }

    /**
     * The password is never part of the response.
     */
    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                user.getEnabled()
        );
    }
}
