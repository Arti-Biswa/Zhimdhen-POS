package com.java.Zhimdhen_POS.auth.controller;

import com.java.Zhimdhen_POS.auth.model.AuthRequest;
import com.java.Zhimdhen_POS.auth.service.AuthenticationServiceImpl;
import com.java.Zhimdhen_POS.email.dto.ForgotPasswordRequest;
import com.java.Zhimdhen_POS.email.dto.ResetPasswordRequest;
import com.java.Zhimdhen_POS.email.service.PasswordResetService;
import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.service.UserService;
import com.java.Zhimdhen_POS.utils.RestHelper;
import com.java.Zhimdhen_POS.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationServiceImpl loginService;

    @Autowired
    private PasswordResetService passwordResetService;

  @Autowired
  private UserService userService;
    /**
     * Handles the authentication for the user provided credentials.
     *
     * @param authRequest The authentication credentials containing object
     * @return The access keys and refresh keys for the associated authenticated user.
     */
    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@RequestBody AuthRequest authRequest) {
        HashMap<String, Object> listHashMap = new HashMap<>(loginService.authenticate(authRequest));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping("/register")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }
    /**
     * Handles token refresh using a valid refresh token
     *
     * @param authorizationHeader Headers with Authorization keyword
     * @return New access and refresh tokens
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<RestResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Extract token from Bearer authorization header
        String refreshToken = authorizationHeader.substring(7); // Remove "Bearer "
        Map<String, Object> tokenMap = new HashMap<>(loginService.refreshToken(refreshToken));
        return RestHelper.responseSuccess(tokenMap);
    }

    /**
     * Sends the password reset link to the concerned email.
     *
     * @param request The password request body containing the email of the user whose password is to be reset.
     * @return The confirmation that the password reset link has been sent.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<RestResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String message = passwordResetService.forgotPassword(request);
        return RestHelper.responseMessage(message);
    }

    /**
     * Resets the password from the provided token and the password.
     *
     * @param resetPasswordRequest The reset password request containing the jwt token and the password.
     * @return The message indicating that the password has been reset successfully.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<RestResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String message = passwordResetService.resetPassword(resetPasswordRequest);
        return RestHelper.responseMessage(message);
    }
}
