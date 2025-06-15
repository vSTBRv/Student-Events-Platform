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
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            var userDetails = user.get();

            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(userDetails.getEmail()) // tutaj również email jako username
                    .password(userDetails.getPassword())
                    .roles(userDetails.getUserRole().name())
                    .build();
        } else {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o adresie email: " + email);
        }
    }
}

