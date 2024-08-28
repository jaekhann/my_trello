package uz.pdp.jaecoder.dto;


import uz.pdp.jaecoder.enums.AuthRole;

public record UserUpdateDto(String username, String email, AuthRole role) implements Dto {
}
