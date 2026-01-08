package com.ITSS.ITSS_NIHONGO.service;


import com.ITSS.ITSS_NIHONGO.dto.request.User.UpdatePassword;
import com.ITSS.ITSS_NIHONGO.dto.request.User.UpdateProfile;
import com.ITSS.ITSS_NIHONGO.dto.response.User.Profile;
import com.ITSS.ITSS_NIHONGO.model.Users;
import com.ITSS.ITSS_NIHONGO.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Users> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            Users user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.emptyList()
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public Profile getUserProfile(int userId) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return Profile.builder()
                    .name(user.getName())
                    .national(user.getNational())
                    .email(user.getUsername())
                    .avatar(user.getAvatar())
                    .build();
        }
        return null;
    }

    public boolean registerUser(String name, String password, String username, String national) {
       Optional<Users> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return false;
        }

        Users user = Users.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .username(username)
                .national(national)
                .build();
        userRepository.save(user);
        return true;
    }

    public boolean updatePassword(int userId, UpdatePassword updatePassword) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if(passwordEncoder.matches(updatePassword.oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(updatePassword.newPassword));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public boolean updateUserInfo(int userId, UpdateProfile updateProfile) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (updateProfile.fullname != null) {
                user.setName(updateProfile.fullname);
            }
            if (updateProfile.email != null) {
                user.setUsername(updateProfile.email);
            }
            if (updateProfile.avatar != null) {
                user.setAvatar(updateProfile.avatar);
            }
            if (updateProfile.nationality != null) {
                user.setNational(updateProfile.nationality);
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
