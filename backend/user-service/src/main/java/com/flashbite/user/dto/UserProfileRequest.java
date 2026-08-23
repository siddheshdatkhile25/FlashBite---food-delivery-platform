package com.flashbite.user.dto;


import java.util.UUID;

public record UserProfileRequest(
        String firstname,
        String lastname,
        String avatarUrl,
        String email,
        String phone
) {

}
