package org.security.springsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.security.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("name") String name) {
        log.info("Request received to register the user");
        return ResponseEntity.ok(userService.createUser(username, password, name));
    }

    @PostMapping("/login")
    public ResponseEntity<String> basicLogin(@RequestParam("username") String username,
        @RequestParam("password") String password) {
        log.info("Request received for /users/login");
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        log.info("Authentication " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("User details: " + userDetails);
        return ResponseEntity.ok("User " + userDetails.getUsername() + " logged in successfully");
    }

}
