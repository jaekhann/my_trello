package uz.pdp.jaecoder.dao;

import lombok.NonNull;
import uz.pdp.jaecoder.domain.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User, Long> {
    Optional<User> findByUsername(@NonNull String username);

    Optional<User> findByEmail(@NonNull String email);
}
