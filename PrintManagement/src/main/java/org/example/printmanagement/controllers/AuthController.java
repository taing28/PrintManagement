package org.example.printmanagement.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.printmanagement.model.dtos.request.ResetPasswordRequest;
import org.example.printmanagement.model.dtos.request.SignInRequest;
import org.example.printmanagement.model.dtos.response.JwtResponse;
import org.example.printmanagement.model.entities.ConfirmEmail;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.jwt.JwtTokenProvider;
import org.example.printmanagement.model.services.impl.MailService;
import org.example.printmanagement.model.services.impl.TokenService;
import org.example.printmanagement.model.services.impl.UserService;
import org.example.printmanagement.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/login")
    public ResponseEntity<?> testPort() {
        return ResponseEntity.ok("Ok");
    }

    //Login method
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody SignInRequest req) {
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
    }

    //Login via Google

    //Forgot password
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
}
