package uz.pdp.jaecoder;

import com.sun.net.httpserver.HttpServer;
import uz.pdp.jaecoder.controller.UserController;
import uz.pdp.jaecoder.dao.UserDao;
import uz.pdp.jaecoder.mapper.app.UserMapper;
import uz.pdp.jaecoder.mapper.db.PostgresUserRowMapper;
import uz.pdp.jaecoder.mapper.db.UserRowMapper;
import uz.pdp.jaecoder.service.UserService;
import uz.pdp.jaecoder.service.impl.RestUserService;
import uz.pdp.jaecoder.dao.impl.PostgresUserDao;
import uz.pdp.jaecoder.properties.DatabaseProperties;
import uz.pdp.jaecoder.utils.UserSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(
                DatabaseProperties.url,
                DatabaseProperties.username,
                DatabaseProperties.password
        );
        UserSession userSession = new UserSession();
        UserRowMapper userRowMapper = new PostgresUserRowMapper();
        UserMapper userMapper = new UserMapper();
        UserDao userDao = new PostgresUserDao(connection, userRowMapper, userSession);
        UserService userService = new RestUserService(userDao, userMapper);
        UserController userController = new UserController(userService);

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(9092), 0);
        httpServer.createContext("/users",userController);
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();
    }
}