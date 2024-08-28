package uz.pdp.jaecoder.dto;


import uz.pdp.jaecoder.enums.AuthRole;

public record UserCreateDto(String username, String email, String password, AuthRole role) implements Dto {
}
