package com.musicalbooking.service;

import com.musicalbooking.dto.UserDto;
import com.musicalbooking.entity.User;
import com.musicalbooking.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUserService {

    UserDto getUserById(Long id) throws ResourceNotFoundException;

    List<UserDto> getUsers();

    UserDto saveUser(User user);

    UserDto updateUser(User user);

    String deleteUser(Long id) throws ResourceNotFoundException;
}
