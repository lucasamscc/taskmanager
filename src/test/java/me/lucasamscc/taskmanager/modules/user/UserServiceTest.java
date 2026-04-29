package me.lucasamscc.taskmanager.modules.user;

import me.lucasamscc.taskmanager.core.exception.BusinessRuleException;
import me.lucasamscc.taskmanager.modules.user.dto.CreateUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should throw an exception if the email is already registered.")
    void shouldThrowExceptionWhenEmailExists() {
        CreateUserDTO dto = new CreateUserDTO("Lucas", "lucas@email.com");
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> userService.create(dto));
        verify(userRepository, never()).save(any());
    }
}