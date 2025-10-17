package com.waracle.cakemgr.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements  org.springframework.security.core.userdetails.UserDetailsService {

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        // For simplicity, we're using hardcoded users. In a real application, fetch users from a database.
        if ("massimo".equals(username)) {
            return User.builder()
                    .username("massimo")
                    .password("{noop}password") // {noop} disabilita l’encoding per test
                    .authorities(List.of(() -> "ROLE_USER"))
                    .build();
        } else if ("user".equals(username)) {
            return org.springframework.security.core.userdetails.User.withUsername("user")
                    .password("{noop}password") // {noop} indicates that no encoding is done
                    .roles("USER")
                    .build();
        } else if ("admin".equals(username)) {
            return org.springframework.security.core.userdetails.User.withUsername("admin")
                    .password("{noop}admin") // {noop} indicates that no encoding is done
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
