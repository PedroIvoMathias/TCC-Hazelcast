package com.safar.service;

import com.safar.exception.AdminException;
import com.safar.exception.UserException;
import com.safar.model.User;

import java.util.List;

public interface UserService {
    public User createUser(User user) throws UserException;
    public User updateUser(User user, String key) throws UserException;
    public User deleteUser(Long userId, String key) throws UserException, AdminException;
    public User viewUserById(Long userId, String key) throws UserException, AdminException;
    public List<User> viewAllUser(String key) throws UserException, AdminException;
}
