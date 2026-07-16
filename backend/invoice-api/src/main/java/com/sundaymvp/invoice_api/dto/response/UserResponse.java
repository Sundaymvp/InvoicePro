package com.sundaymvp.invoice_api.dto.response;

public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;

    public UserResponse() {
    }

    public UserResponse(Long id, String firstName, String lastName,
                        String email, String phone, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // Generate getters and setters
}