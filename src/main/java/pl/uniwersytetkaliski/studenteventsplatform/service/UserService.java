package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.exception.UserNotFoundException;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id); // Optional lepiej obsługuje błędy typu

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setFullName(userDetails.getFullName());
            existingUser.setUserRole(userDetails.getUserRole());
            existingUser.setEnabled(userDetails.isEnabled());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Funkcja najpierw sprawdza, czy w bazie istnieje użytkownik o zadanym id
     * Jeśli takiego nie ma - rzuca wyjątkiem.
     * W kolejnym kroku próbuje wykonać operacje usunięcia - jeśli mu się uda to sukces
     * w przeciwnym wypadku - obsługuje wyjątek.
     *
     * @param id szukane id użytkownika
     */
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas usuwania użytkownika o ID " + id, e);
        }
    }
}
