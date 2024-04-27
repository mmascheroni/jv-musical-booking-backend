package com.musicalbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.dto.UserDto;
import com.musicalbooking.entity.User;
import com.musicalbooking.exceptions.BadRequestException;
import com.musicalbooking.exceptions.ResourceNotFoundException;
import com.musicalbooking.repository.UserRepository;
import com.musicalbooking.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public UserDto getUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        UserDto userDto = null;

        if ( user != null ) {
            userDto = objectMapper.convertValue(user, UserDto.class);
            log.info("The user with id {} has been found: {}", id, userDto);

            return userDto;
        }

        log.error("The user with id {} was not found", id);
        throw new ResourceNotFoundException("Not found the user with id: " + id);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = null;

        if ( users.size() > 0 ) {
            usersDto = users.stream()
                    .map(user -> objectMapper.convertValue(user, UserDto.class))
                    .collect(Collectors.toList());
            log.info("All these users were found: {}", usersDto);
        }

        log.warn("No registered users found");
        return usersDto;
    }

    @Override
    public UserDto saveUser(User user) {
        User userToSave = userRepository.save(user);
        UserDto userDto = objectMapper.convertValue(userToSave, UserDto.class);

        log.info("User registered successfully: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(User user) {
        return null;
    }

    @Override
    public String deleteUser(Long id) throws ResourceNotFoundException {
        if ( getUserById(id) != null ) {
            userRepository.deleteById(id);
            log.warn("The user with id {} has been delete", id);
        }

        return "The image has been removed successfully";
    }
}
