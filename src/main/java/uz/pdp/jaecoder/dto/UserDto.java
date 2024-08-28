package uz.pdp.jaecoder.dto;

import uz.pdp.jaecoder.enums.AuthRole;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy,
        AuthRole role) implements Dto {
}
