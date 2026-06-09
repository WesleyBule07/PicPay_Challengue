package com.picpay.dev_challengue.challengue.services;


import com.picpay.dev_challengue.challengue.mappers.UserMapper;
import com.picpay.dev_challengue.challengue.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public UserService(
            UserMapper userMapper,
            UserRepository userRepository
    ) {
        this.userMapper = userMapper;
        this.userRepository =  userRepository;
    }


}
