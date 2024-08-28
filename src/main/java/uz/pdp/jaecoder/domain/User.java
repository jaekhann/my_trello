package uz.pdp.jaecoder.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.jaecoder.enums.AuthRole;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private boolean isDeleted;
    private AuthRole role;
}
