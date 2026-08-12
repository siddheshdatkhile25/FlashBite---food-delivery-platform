package com.flashbite.user.security.oauth2;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.stereotype.Service;

import com.flashbite.common.domain.AuthProvider;
import com.flashbite.common.domain.UserRole;
import com.flashbite.common.domain.UserStatus;
import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        // 1. Load the user from the external provider (Google, Facebook, etc.)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. Extract the user info into our custom format
        String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo ;

        if(clientRegistrationId.equals("google")){
            userInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        }else{
            throw new OAuth2AuthenticationException("Unsupported OAuth2 provider: " + clientRegistrationId);
        }

        // 3. Register or Update the user in our local database
        // (We will implement this logic in the next step)

        if(userInfo.getEmail() == null){
            throw new OAuth2AuthenticationException("Email not found from OAuth provider");
        }

        //check if a user with this email already exists
        Optional<UserEntity> userOptional = userRepository.findByEmail(userInfo.getEmail());

        UserEntity user;
        if(userOptional.isPresent()){
            //Account Linking Case : user Already Exists!
            user = userOptional.get();

            //Optionally update their profile if they don't have an avatar yet
            if(user.getAvatarUrl() == null){

            }



        }else {
            //Auto-Registration case : Brand new user !
            user = UserEntity.builder()
                .email(userInfo.getEmail())
                .firstName(userInfo.getFirstname())
                .lastName(userInfo.getLastname())
                .avatarUrl(userInfo.getImageUrl())
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .emailVerified(true)
                .authProvider(AuthProvider.valueOf(clientRegistrationId.toUpperCase()))
                .oauthProviderId(userInfo.getId())
                .build();
            
            user = userRepository.save(user);
        }
        
        //4. Return the OAuth2User
        return oAuth2User;
    }

}
