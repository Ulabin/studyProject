package com.springboot.studyproject.config.auth;

import com.springboot.studyproject.config.auth.dto.SessionUser;
import com.springboot.studyproject.config.auth.dto.UserSaveRequestDto;
import com.springboot.studyproject.domain.user.Role;
import com.springboot.studyproject.domain.user.User;
import com.springboot.studyproject.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndFromSocial(username, false)
                .orElseThrow(()->new UsernameNotFoundException("Check Email"));

        httpSession.setAttribute("user", new SessionUser(user));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    @Transactional
    public Long saveUser(UserSaveRequestDto userSaveRequestDto){
        User user = User.builder()
                .name(userSaveRequestDto.getName())
                .email(userSaveRequestDto.getEmail())
                .password(passwordEncoder.encode(userSaveRequestDto.getPassword()))
                .picture(userSaveRequestDto.getPicture())
                .fromSocial(false)
                .role(Role.USER)
                .build();
        return userRepository.save(user).getId();
    }
}
