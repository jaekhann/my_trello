package uz.pdp.jaecoder;

import com.sun.net.httpserver.HttpServer;
import uz.pdp.jaecoder.controller.UserController;
import uz.pdp.jaecoder.service.impl.RestUserService;
import uz.pdp.jaecoder.dao.impl.PostgresUserDao;
import uz.pdp.jaecoder.properties.DatabaseProperties;

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
    }
}