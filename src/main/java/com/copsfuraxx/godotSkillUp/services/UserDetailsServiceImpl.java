package com.copsfuraxx.godotSkillUp.services;

import com.copsfuraxx.godotSkillUp.models.AppUser;
import com.copsfuraxx.godotSkillUp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String authUsername) throws UsernameNotFoundException {
        final AppUser user = userRepository.findByUsername(authUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown User " + authUsername));
        return User.withUsername(authUsername)
                .password(user.getPassword())
                .roles(user.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
