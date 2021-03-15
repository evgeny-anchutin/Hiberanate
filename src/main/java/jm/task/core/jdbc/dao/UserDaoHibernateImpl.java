package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
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

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE users";
        try (Connection connection = Util.getConnection(); Statement cmd = connection.createStatement()) {
            cmd.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public User saveUser(String name, String lastName, byte age) {
        User user = null;
        SessionFactory factory = Util.getFactory();
        try {
            Session session = factory.getCurrentSession();
            user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } finally {
            factory.close();
        }
        return user;
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory factory = Util.getFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        } finally {
            factory.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SessionFactory factory = Util.getFactory();
        try {
            Session session = Util.getFactory().getCurrentSession();
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            session.close();
        } finally {
            factory.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        List<User> users = getAllUsers();
        SessionFactory factory = Util.getFactory();
        try {
            Session session = Util.getFactory().getCurrentSession();
            session.beginTransaction();
            users.stream().forEach(session::delete);
            session.getTransaction().commit();
            session.close();
        } finally {
            factory.close();
        }
    }
}
