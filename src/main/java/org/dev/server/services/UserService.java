package org.dev.server.services;

import org.dev.server.dao.UserDAO;
import org.dev.server.models.User;
import java.util.List;

public class UserService {
    private static UserService instance;
    private UserDAO userDAO;
    private AuditService auditService;

    private UserService() {
        this.userDAO = UserDAO.getInstance();
        this.auditService = AuditService.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    // CREATE
    public void registerUser(User user) {
        try {
            userDAO.create(user);
            auditService.logActionWithDetails("registerUser", "User ID: " + user.getId() + ", Name: " + user.getName() + ", Type: " + user.getClass().getSimpleName());
            System.out.println("User registered: " + user.getName() + " (" + user.getClass().getSimpleName() + ")");
        } catch (Exception e) {
            auditService.logActionWithDetails("registerUser_FAILED", "User ID: " + user.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to register user: " + e.getMessage());
            throw e;
        }
    }

    // READ
    public User findUserById(String userId) {
        try {
            User user = userDAO.read(userId);
            auditService.logActionWithDetails("findUserById", "User ID: " + userId + (user != null ? ", Found: " + user.getName() : ", Not found"));
            return user;
        } catch (Exception e) {
            auditService.logActionWithDetails("findUserById_FAILED", "User ID: " + userId + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<User> listUsers() {
        try {
            List<User> users = userDAO.findAll();
            auditService.logActionWithDetails("listUsers", "Found " + users.size() + " users");
            return users;
        } catch (Exception e) {
            auditService.logActionWithDetails("listUsers_FAILED", "Error: " + e.getMessage());
            throw e;
        }
    }

    public User findUserByEmail(String email) {
        try {
            User user = userDAO.findByEmail(email);
            auditService.logActionWithDetails("findUserByEmail", "Email: " + email + (user != null ? ", Found: " + user.getName() : ", Not found"));
            return user;
        } catch (Exception e) {
            auditService.logActionWithDetails("findUserByEmail_FAILED", "Email: " + email + ", Error: " + e.getMessage());
            throw e;
        }
    }

    // UPDATE
    public void updateUser(User user) {
        try {
            userDAO.update(user);
            auditService.logActionWithDetails("updateUser", "User ID: " + user.getId() + ", Name: " + user.getName());
            System.out.println("User updated: " + user.getName());
        } catch (Exception e) {
            auditService.logActionWithDetails("updateUser_FAILED", "User ID: " + user.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to update user: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void deleteUser(String userId) {
        try {
            userDAO.delete(userId);
            auditService.logActionWithDetails("deleteUser", "User ID: " + userId);
            System.out.println("User deleted: " + userId);
        } catch (Exception e) {
            auditService.logActionWithDetails("deleteUser_FAILED", "User ID: " + userId + ", Error: " + e.getMessage());
            System.err.println("Failed to delete user: " + e.getMessage());
            throw e;
        }
    }

}