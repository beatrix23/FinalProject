package ro.ubb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;

import java.util.Arrays;

@Component
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with " + email + " not found");
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword())
                .authorities(Arrays.asList(new SimpleGrantedAuthority("user"))).accountExpired(false).accountLocked(false).credentialsExpired(false)
                .disabled(!user.getActivated()).build();
    }

    public User loadUser(String email) {
        final User user = userRepository.findByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with " + email + " not found");
        }
        return user;
    }

    public UserDetails getUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword())
                .authorities(Arrays.asList(new SimpleGrantedAuthority("user"))).accountExpired(false).accountLocked(false).credentialsExpired(false)
                .disabled(!user.getActivated()).build();
    }

}