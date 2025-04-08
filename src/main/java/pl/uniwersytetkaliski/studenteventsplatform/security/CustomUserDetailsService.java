package pl.uniwersytetkaliski.studenteventsplatform.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.service.UserService;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            var userDetails = user.get();
            return org.springframework.security.core.userdetails.User.builder().username(userDetails.getUsername()).password(userDetails.getPassword()).roles(userDetails.getUserRole().name()).build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
