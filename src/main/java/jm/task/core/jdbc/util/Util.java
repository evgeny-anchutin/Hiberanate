package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String CONNECTION_STRING =
            "jdbc:mysql://localhost/jm?user=root&password=_123123_&serverTimezone=UTC";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_NAME);
        Connection connection = DriverManager.getConnection(CONNECTION_STRING);
        return connection;
    }

    public static SessionFactory getFactory() {
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jm?serverTimezone=UTC");
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.username", "root");
        prop.setProperty("hibernate.connection.password", "_123123_");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("show_sql", "true");
        prop.setProperty("hibernate.current_session_context_class", "thread");
        prop.setProperty("hibernate.show_sql", "false");
        prop.setProperty("log4j.logger.org.hibernate", "off");
        return new Configuration().addProperties(prop).addAnnotatedClass(User.class).buildSessionFactory();
    }
}