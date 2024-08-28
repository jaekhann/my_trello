package uz.pdp.jaecoder.mapper.app;

import uz.pdp.jaecoder.domain.User;
import uz.pdp.jaecoder.dto.UserCreateDto;
import uz.pdp.jaecoder.dto.UserDto;
import uz.pdp.jaecoder.dto.UserUpdateDto;
import uz.pdp.jaecoder.enums.AuthRole;

import java.util.List;

public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy(),
                user.getRole()
        );
    }

    public List<UserDto> toDto(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }

    public User fromCreateDto(UserCreateDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setRole(dto.role());
        return user;
    }

    public User fromUpdateDto(User user, UserUpdateDto dto) {
        if (dto.username() != null) {
            user.setUsername(user.getUsername());
        }
        if (dto.email() != null) {
            user.setEmail(user.getEmail());
        }
        if (dto.role() != null) {
            user.setRole(user.getRole());
        }
        return user;
    }
}
