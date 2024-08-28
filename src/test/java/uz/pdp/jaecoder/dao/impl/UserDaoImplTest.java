package uz.pdp.jaecoder.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.pdp.jaecoder.dao.UserDao;
import uz.pdp.jaecoder.domain.User;
import uz.pdp.jaecoder.enums.AuthRole;
import uz.pdp.jaecoder.mapper.db.PostgresUserRowMapper;
import uz.pdp.jaecoder.properties.DatabaseProperties;
import uz.pdp.jaecoder.utils.PasswordUtil;
import uz.pdp.jaecoder.utils.UserSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class UserDaoImplTest {
    UserDao userDao;
    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                DatabaseProperties.url,
                DatabaseProperties.username,
                DatabaseProperties.password
        );
        userDao = new PostgresUserDao(connection, new PostgresUserRowMapper(), new UserSession());
    }

    @Test
    void insert() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(PasswordUtil.hash("admin123"));
        user.setEmail("admin@mail.ru");
        user.setRole(AuthRole.ADMIN);
        user.setCreatedBy((long) 1);
        userDao.save(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void findByUsername() {
        Optional<User> userOptional = userDao.findByUsername("admin");
        Assertions.assertNotNull(userOptional);
    }

    @Test
    void findByEmail() {
        Optional<User> userOptional = userDao.findByEmail("admin@mail.ru");
        Assertions.assertNotNull(userOptional);
    }

    @Test
    void update() {
        User user = new User();
        user.setId((long) 1);
        user.setUsername("jae");
        user.setPassword(PasswordUtil.hash("123"));
        user.setEmail("jae@gmail.com");
        user.setRole(AuthRole.USER);
        user.setUpdatedBy((long) 1);
        userDao.update(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void findById() {
        Long id = 1L;
        Optional<User> user = userDao.findById(id);
        Assertions.assertNotNull(user);
    }

    @Test
    void findAll() {
        List<User> all = userDao.findAll();
        Assertions.assertNotNull(all);
    }

    @Test
    void deleteById() {
        Long id = 1L;
        userDao.deleteById(id);
    }

    @Test
    void delete() {
    }

}