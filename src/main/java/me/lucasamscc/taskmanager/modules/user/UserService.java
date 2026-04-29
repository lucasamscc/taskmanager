package me.lucasamscc.taskmanager.modules.user;

import lombok.RequiredArgsConstructor;
import me.lucasamscc.taskmanager.core.exception.BusinessRuleException;
import me.lucasamscc.taskmanager.core.exception.ResourceNotFoundException;
import me.lucasamscc.taskmanager.modules.user.dto.CreateUserDTO;
import me.lucasamscc.taskmanager.modules.user.dto.UserResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO create(CreateUserDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new BusinessRuleException("Email already registered");
        }

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .build();

        return userMapper.toDTO(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(UUID id) {
        return userMapper.toDTO(getUserEntityById(id));
    }

    @Transactional(readOnly = true)
    public User getUserEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}