package uz.pdp.jaecoder.dao.impl;

import lombok.NonNull;
import lombok.extern.java.Log;
import uz.pdp.jaecoder.dao.UserDao;
import uz.pdp.jaecoder.domain.User;
import uz.pdp.jaecoder.exception.DataAccessException;
import uz.pdp.jaecoder.mapper.db.UserRowMapper;
import uz.pdp.jaecoder.utils.UserSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class PostgresUserDao implements UserDao {
    private static final String SELECT_QUERY = "select t.* from users t where t.is_deleted = false ";
    private static final String INSERT_QUERY = """
                    insert into users(username, password, email, created_by, role)
                    values (?, ?, ?, ?, ?) returning *;
            """;
    private static final String UPDATE_QUERY = """
            update users 
            set username = ?, 
                email = ?, 
                role = ?, 
                updated_by = ?,
                updated_at = current_timestamp
            where id = ?
            returning *;
            """;
    private static final String SOFT_DELETE_QUERY = """
            update users 
            set is_deleted = true,
                updated_at = current_timestamp,
                updated_by = ? 
            where id = ?
            returning *;
            """;
    private final Connection connection;
    private final UserRowMapper userRowMapper;
    private final UserSession userSession;

    public PostgresUserDao(Connection connection, UserRowMapper userRowMapper, UserSession userSession) {
        this.connection = connection;
        this.userRowMapper = userRowMapper;
        this.userSession = userSession;
    }

    @Override
    public Optional<User> findByUsername(@NonNull String username) throws DataAccessException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + "and t.username ilike ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = userRowMapper.mapTo(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.severe("error while getting user by username: " + username);
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(@NonNull String email) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + "and t.email ilike ?")) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = userRowMapper.mapTo(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.severe("error while getting user by email: " + email);
            throw new DataAccessException(e);
        }
    }

    @Override
    public User save(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, userSession.requireUserId()); // TODO this id must be taken from session
            preparedStatement.setString(5, user.getRole().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                user.setCreatedAt(createdAt.toLocalDateTime());
                return user;
            }
            throw new SQLException("error on insert");
        } catch (SQLException e) {
            throw new DataAccessException("Error inserting user", e);
        }
    }

    @Override
    public User update(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getRole().toString());
            preparedStatement.setLong(4, userSession.requireUserId());
            preparedStatement.setLong(5, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                Timestamp updated_at = resultSet.getTimestamp("updated_at");
                user.setUpdatedAt(updated_at.toLocalDateTime());
                return user;
            }
            throw new SQLException("Error on update user");
        } catch (SQLException e) {
            throw new DataAccessException("Error while updating user", e);
        }
    }

    @Override
    public void delete(User user) {
        deleteById(user.getId());
    }

    @Override
    public void deleteById(Long userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SOFT_DELETE_QUERY)) {
            preparedStatement.setLong(1, userSession.requireUserId());
            preparedStatement.setLong(2, userId);
            preparedStatement.execute();
            if (!preparedStatement.execute()) {
                throw new SQLException("No user found with id: " + userId);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting user", e);
        }
    }

    @Override
    public Optional<User> findById(Long userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + "and t.id = ?");
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = userRowMapper.mapTo(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException("Error while finding user by id", e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(userRowMapper.mapTo(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DataAccessException("Error while finding user", e);
        }
    }
}
