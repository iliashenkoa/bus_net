package com.iliashenko.account.service;

import com.iliashenko.account.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
