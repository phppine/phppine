package org.example.animalsheltersecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.exception.ConflictException;
import org.example.animalsheltersecurity.exception.ResourceNotFoundException;
import org.example.animalsheltersecurity.model.User;
import org.example.animalsheltersecurity.repository.AdoptionApplicationRepository;
import org.example.animalsheltersecurity.repository.AnimalHandoverRecordRepository;
import org.example.animalsheltersecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final AnimalHandoverRecordRepository handoverRecordRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException("User with username '" + user.getUsername() + "' already exists");
        }

        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new LinkedHashSet<>(Set.of(DEFAULT_ROLE)));
        } else {
            user.setRoles(new LinkedHashSet<>(user.getRoles()));
        }
        if (user.getEnabled() == null) {
            user.setEnabled(true);
        }
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User changes) {
        User user = findById(id);

        if (StringUtils.hasText(changes.getUsername())
                && !changes.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsernameAndIdNot(changes.getUsername(), id)) {
                throw new ConflictException("User with username '" + changes.getUsername() + "' already exists");
            }
            user.setUsername(changes.getUsername());
        }
        if (StringUtils.hasText(changes.getPassword())) {
            user.setPassword(passwordEncoder.encode(changes.getPassword()));
        }
        if (changes.getRoles() != null) {
            user.setRoles(new LinkedHashSet<>(changes.getRoles()));
        }
        if (changes.getEnabled() != null) {
            user.setEnabled(changes.getEnabled());
        }
        return userRepository.save(user);
    }

    /**
     * Deletes the user together with their adoption applications and the
     * handover records of those applications.
     */
    @Transactional
    public void delete(Long id) {
        findById(id);

        handoverRecordRepository.deleteByApplicationApplicantId(id);
        adoptionApplicationRepository.deleteByApplicantId(id);
        userRepository.deleteById(id);
    }
}
