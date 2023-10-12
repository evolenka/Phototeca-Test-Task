package by.evlashkina.crypto.service;

import by.evlashkina.crypto.entity.User;

import java.util.List;

public interface UserService {

    void registerNewUser(Long chatId, String userName);
    List<User> findAllUsers ();
}
