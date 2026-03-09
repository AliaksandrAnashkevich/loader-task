package by.alexander.backend.auth;

import by.alexander.backend.dao.UserRepository;
import by.alexander.backend.entity.User;
import by.alexander.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static by.alexander.backend.util.ConstantsUtil.LOGIN;
import static by.alexander.backend.util.ConstantsUtil.USERS;


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new ResourceNotFoundException(USERS, LOGIN, username));
        return new CustomUserDetails(user.getLogin(), user.getPassword(), user.getId(),
                Collections.emptyList());
    }

    public static CustomUserDetails getAuthentication() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
