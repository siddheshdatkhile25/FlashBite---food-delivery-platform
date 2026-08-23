package com.flashbite.user.controller;

import com.flashbite.user.dto.UserProfileRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.flashbite.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flashbite.common.api.ApiResponse;
import com.flashbite.user.dto.UserProfileReponse;

import java.security.Principal;
import java.util.UUID;

import static com.flashbite.common.api.ApiConstants.API_PREFIX;

@RestController
@RequestMapping(API_PREFIX + "/users")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileReponse>> getUserProfile(Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return userService.getUserProfile(userId)
                .<ResponseEntity<ApiResponse<UserProfileReponse>>>map(profile -> ResponseEntity.<ApiResponse<UserProfileReponse>>ok(ApiResponse.<UserProfileReponse>success(profile)))
                .orElseGet(() -> ResponseEntity.notFound().<ApiResponse<UserProfileReponse>>build());
    }


    @PatchMapping("/profile/update")
    public ResponseEntity<ApiResponse<UserProfileReponse>> updateUserProfile(@Valid @RequestBody UserProfileRequest userProfileRequest , Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return userService.updateUserProfile(userId , userProfileRequest)
                .<ResponseEntity<ApiResponse<UserProfileReponse>>>map(profile -> ResponseEntity.<ApiResponse<UserProfileReponse>>ok(ApiResponse.<UserProfileReponse>success(profile , "User Updated Successfully")))
                .orElseGet(() -> ResponseEntity.notFound().<ApiResponse<UserProfileReponse>>build());

    }




}
















