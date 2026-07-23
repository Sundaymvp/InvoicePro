package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.RegisterRequest;
import com.sundaymvp.invoice_api.dto.response.UserResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Role;
import com.sundaymvp.invoice_api.entity.User;
import com.sundaymvp.invoice_api.exception.EmailAlreadyExistsException;
import com.sundaymvp.invoice_api.exception.RoleNotFoundException;
import com.sundaymvp.invoice_api.exception.UserNotFoundException;
import com.sundaymvp.invoice_api.mapper.UserMapper;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.RoleRepository;
import com.sundaymvp.invoice_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SuppressWarnings("null")
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       CompanyRepository companyRepository,
                       PasswordEncoder passwordEncoder,
                       FileStorageService fileStorageService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    public UserResponse createUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RoleNotFoundException("Role not found."));

        Company company = companyRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() ->
                        new RuntimeException("Default company not found."));

        User user = UserMapper.toEntity(request);

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(role);
        user.setCompany(company);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        return UserMapper.toResponse(user);
    }

    /**
 * Upload user profile image.
 */
public UserResponse uploadProfileImage(
        Long userId,
        MultipartFile file) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found."));

    // Delete old profile image
    if (user.getProfileImage() != null
            && !user.getProfileImage().isBlank()) {

        fileStorageService.deleteFile(
                "profile-images",
                user.getProfileImage());
    }

    // Upload new profile image
    String fileName = fileStorageService.uploadFile(
            file,
            "profile-images");

    user.setProfileImage(fileName);

    User updatedUser = userRepository.save(user);

    return UserMapper.toResponse(updatedUser);
}

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        userRepository.delete(user);
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));
    }
}