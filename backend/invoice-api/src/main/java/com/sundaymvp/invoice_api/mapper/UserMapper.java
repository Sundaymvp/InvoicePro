package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.request.RegisterRequest;
import com.sundaymvp.invoice_api.dto.response.UserResponse;
import com.sundaymvp.invoice_api.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        if (user.getRole() != null) {
            response.setRole(user.getRole().getName().name());
        }

        return response;
    }

    public static User toEntity(RegisterRequest request) {

        if (request == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());

        return user;
    }

}