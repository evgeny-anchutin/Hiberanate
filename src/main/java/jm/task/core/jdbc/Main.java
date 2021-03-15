package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        final UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        User user;
        user = userService.saveUser("Donald", "Trump", (byte) 70);
        printUser(user);
        user = userService.saveUser("John", "3Volta", (byte) 50);
        printUser(user);
        user = userService.saveUser("Bruce", "Lee", (byte) 30);
        printUser(user);
        user = userService.saveUser("Donald", "Duck", (byte) 40);
        printUser(user);
        List<User> users = userService.getAllUsers();
        for (User usr : users) {
            System.out.println(usr);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    private static void printUser (User user) {
        System.out.printf("User с именем - %s %s добавлен в базу\n", user.getName(), user.getLastName());
    }

}
