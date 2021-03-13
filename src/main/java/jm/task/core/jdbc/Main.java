package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Donald", "Trump", (byte) 70);
        userService.saveUser("John", "3Volta", (byte) 50);
        userService.saveUser("Bruce", "Lee", (byte) 30);
        userService.saveUser("Donald", "Duck", (byte) 40);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
