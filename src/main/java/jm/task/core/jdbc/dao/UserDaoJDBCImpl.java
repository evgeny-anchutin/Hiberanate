package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql ="CREATE TABLE users\n" +
                "(\n" +
                "    id        BIGINT AUTO_INCREMENT\n" +
                "        PRIMARY KEY,\n" +
                "    name      VARCHAR(50) NULL,\n" +
                "    last_name VARCHAR(50) NULL,\n" +
                "    age       TINYINT(1)  NULL\n" +
                ")";
        try (Connection connection = Util.getConnection(); Statement cmd = connection.createStatement()) {
            cmd.execute(sql);
        } catch (SQLException ignored) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users";
        try (Connection connection = Util.getConnection(); Statement cmd = connection.createStatement()) {
            cmd.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ignored) {
        }
    }

    public User saveUser(String name, String lastName, byte age) {
        User user = null;
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
        String[] generatedColumns = {"id"};
        try (Connection connection = Util.getConnection();
                PreparedStatement cmd = connection.prepareStatement(sql, generatedColumns)) {
            cmd.setString(1, name);
            cmd.setString(2, lastName);
            cmd.setByte(3, age);
            int affectedRows = cmd.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = cmd.getGeneratedKeys()) {
                    if (rs.next()) {
                        user = new User(name, lastName, age);
                        user.setId(rs.getLong(1));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ignored) {
        }
        return user;
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE (id = ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement cmd = connection.prepareStatement(sql)) {
            cmd.setLong(1, id);
            cmd.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = Util.getConnection(); PreparedStatement cmd = connection.prepareStatement(sql)) {
            ResultSet result = cmd.executeQuery(sql);
            while (result.next()) {
                String name = result.getString("name");
                String lastName = result.getString("last_name");
                byte age = result.getByte("age");
                long id = result.getLong("id");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ignored) {
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = Util.getConnection(); Statement cmd = connection.prepareStatement(sql)) {
            cmd.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ignored) {
        }
    }
}
