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
        String sql ="create table users\n" +
                "(\n" +
                "    id        bigint auto_increment\n" +
                "        primary key,\n" +
                "    name      varchar(50) null,\n" +
                "    last_name varchar(50) null,\n" +
                "    age       tinyint(1)  null\n" +
                ")";

        try (Connection connection = Util.getConnection(); Statement cmd = connection.createStatement()) {
            cmd.execute(sql);
        } catch (SQLException e) {
            //e.printStackTrace();
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
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement cmd = connection.prepareStatement(sql)) {
            cmd.setString(1, name);
            cmd.setString(2, lastName);
            cmd.setByte(3, age);
            cmd.executeUpdate();
            System.out.printf("User с именем - %s %s добавлен в базу\n", name, lastName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE (id = ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement cmd = connection.prepareStatement(sql)) {
            cmd.setLong(1, id);
            cmd.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
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
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = Util.getConnection(); Statement cmd = connection.prepareStatement(sql)) {
            cmd.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }
}
