package ro.ubb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;

@Component
public class SecurityContext {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            user = userRepository.findByUsername(auth.getName());
        }
        return user;
    }

}
