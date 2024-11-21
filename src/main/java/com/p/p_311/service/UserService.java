package com.p.p_311.service;

import com.p.p_311.model.User;
import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getAllUsers();

    void removeUserById(Integer userId);

    User getUserById(Integer userId);

    void updateUser(User user);
}