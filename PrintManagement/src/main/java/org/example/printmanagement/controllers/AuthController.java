package org.example.printmanagement.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.printmanagement.model.dtos.request.ResetPasswordRequest;
import org.example.printmanagement.model.dtos.request.SignInRequest;
import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.dtos.response.JwtResponse;
import org.example.printmanagement.model.entities.ConfirmEmail;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.jwt.JwtTokenProvider;
import org.example.printmanagement.model.services.impl.MailService;
import org.example.printmanagement.model.services.impl.TokenService;
import org.example.printmanagement.model.services.impl.UserService;
import org.example.printmanagement.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager _authenticationManager;
    @Autowired
    private JwtTokenProvider _jwtTokenProvider;
    @Autowired
    private TokenService _tokenService;
    @Autowired
    private UserService _userService;
    @Autowired
    private MailService _mailService;

    //Login method
    /**
     * @method POST
     * @path /auth/login
     * @param req sign in request
     * @return JwtResponse - user info and token
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody SignInRequest req) {
        try {
            Authentication authentication = _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            //Generate token
            String jwt = _jwtTokenProvider.generateToken(customUserDetail);
            _tokenService.createToken(customUserDetail.getId(), jwt);
            //Take role
            Set<String> setRoles = customUserDetail.getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toSet());

            return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getIsActive(), setRoles));
        } catch (AuthenticationException e) {
            // Xử lý khi không tìm thấy người dùng hoặc sai thông tin đăng nhập
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    //Login via Google

    //Forgot password
    /**
     * @method POST
     * @path /auth/reset-password
     * @param email email of account
     * @return message to the email
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        User user = _userService.findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("The email does not exist");
        }
        //Generate random code
        String confirmCode = user.getId() + "_" + RandomStringUtils.randomAlphanumeric(8);
        byte[] bytesData = confirmCode.getBytes();
        String encodedData = Base64.getEncoder().encodeToString(bytesData);

        _mailService.sendEmailWithHTML(email, "Validate Code Print Management", "<h1>" + encodedData + "</h1>");
        _mailService.createConfirmMail(user.getId(), confirmCode);
        return ResponseEntity.ok("Please check your email");
    }

    /**
     * @method POST
     * @path /auth/update-password
     * @param req new password and confirm code
     * @return String - result message
     */
    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody ResetPasswordRequest req) {
        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Please re-enter password");
        }
        try {
            ConfirmEmail confirmEmail = _mailService.updateConfirmMail(req.getConfirmCode());
            _userService.updatePassword(confirmEmail.getUserId(), req.getNewPassword());
            return ResponseEntity.ok("Update successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Sign up
    /**
     * @method POST
     * @path /auth/register
     * @param request sign up request
     * @return result message to the email
     */
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        try {
            _userService.createUser(request);
            _mailService.sendEmailWithHTML(request.getEmail(), "Welcome to Print Manager Community", "<h1>Hello there,</h1>\"<h4>Have fun and give it your all ^^!</h4>");
            return ResponseEntity.ok("Sign up successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
