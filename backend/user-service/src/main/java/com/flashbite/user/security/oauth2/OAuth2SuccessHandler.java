package com.flashbite.user.security.oauth2;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.flashbite.user.persistence.RefreshTokenEntity;
import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.repository.RefreshTokenRepository;
import com.flashbite.user.repository.UserRepository;
import com.flashbite.user.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Google usually uses 'email', sometimes 'sub' or 'id'. Let's fetch the email we saved.
        String email = (String) oAuth2User.getAttributes().get("email");

        if(email == null){
            throw new IOException("Email not found from OAuth provider");
        }

        //Fetch the User we Just Created or Linked in CustomOAuth2UserService
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new IOException("User not found"));

        //Generate JWT Token
        String token = jwtService.generateAccessToken(user);

        //Generate Refresh Token
        String refreshToken = jwtService.generateRefreshToken();


        //Save Refresh Token
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUserId(user.getId());
        refreshTokenEntity.setTokenHash(hashToken(refreshToken));
        refreshTokenEntity.setTimeToLiveSeconds(jwtService.refreshTokenExpiresInSeconds());

        
        refreshTokenRepository.save(refreshTokenEntity);

        //Redirect to Frontend with Tokens
        String targetUrl = frontendUrl + "?token=" + token + "&refreshToken=" + refreshToken;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);


    }
    
    private String hashToken(String token) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
            return java.util.HexFormat.of().formatHex(messageDigest.digest(token.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (java.security.NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm is required", exception);
        }
    }
}
