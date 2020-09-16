package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.security.jwt.JWTFilter;
import com.emard.sigrhp.security.jwt.TokenProvider;
import com.emard.sigrhp.service.ControllerService;
import com.emard.sigrhp.web.rest.errors.BadRequestAlertException;
import com.emard.sigrhp.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.micrometer.core.annotation.Timed;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@Timed
public class UserJWTController {

    private final TokenProvider tokenProvider;

    // private final AuthenticationManager authenticationManager;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final ControllerService controllerService;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
    /*AuthenticationManager authenticationManager,*/ ControllerService controllerService) {
        this.tokenProvider = tokenProvider;
        //this.authenticationManager = authenticationManager;
        this.controllerService = controllerService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(authentication.isAuthenticated()){
            Boolean result = controllerService.test(authentication.getName());
            if (!result) {
                /* HttpHeaders httpHeaders2 = new HttpHeaders();
                httpHeaders2.add(JWTFilter.AUTHORIZATION_HEADER, "Probleme de licence, contactez le numéro 77.605.56.41! ");
                return new ResponseEntity<>(null, httpHeaders2, ("Probleme de licence, contactez le numéro 77.605.56.41!", 401));
                */
                throw new BadRequestAlertException("Probleme de licence, contactez le numéro 77.605.56.41 ou par mail tijdrame@gmail.com!", "", "401");
                //return null;
            }

        }

        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
