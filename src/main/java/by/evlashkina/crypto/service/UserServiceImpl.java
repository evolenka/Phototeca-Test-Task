package by.evlashkina.crypto.service;

import by.evlashkina.crypto.entity.User;
import by.evlashkina.crypto.exception.UserException;
import by.evlashkina.crypto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${bot.limit}")
    private int userLimit;

    @Override
    public void registerNewUser(Long chatId, String userName) {

        if (userRepository.count() < userLimit) {
            User user = User.builder().chatId(chatId).userName(userName).build();
            userRepository.save(user);
        } else {
            throw new UserException("User limit has been achieved");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
