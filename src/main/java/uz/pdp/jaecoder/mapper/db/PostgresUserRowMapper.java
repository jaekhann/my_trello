package uz.pdp.jaecoder.mapper.db;

import uz.pdp.jaecoder.domain.User;
import uz.pdp.jaecoder.enums.AuthRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostgresUserRowMapper implements UserRowMapper {

    @Override
    public User mapTo(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        user.setCreatedBy(resultSet.getLong("created_by"));

        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        user.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);

        Object updatedBy = resultSet.getLong("updated_by");
        user.setUpdatedBy(updatedBy != null ? (Long) updatedBy : null);

        user.setDeleted(resultSet.getBoolean("is_deleted"));
        user.setRole(AuthRole.valueOf(resultSet.getString("role")));
        return user;
    }
}
