package ro.ubb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.config.jwt.JwtTokenProvider;
import ro.ubb.dto.UserActivationDTO;
import ro.ubb.dto.UserDTO;
import ro.ubb.email.EmailSender;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;
import ro.ubb.utils.Utils;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private Utils utils;

    @Autowired
    private EmailSender emailSender;

    @Transactional
    public String login(UserDTO userDTO) throws Exception {
        User user = userRepository.findByUsername(userDTO.getEmail());
        if (user != null) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
            return jwtTokenProvider.createToken(userDTO.getEmail());
        }
        throw new Exception("Invalid username/token supplied");
    }

    @Transactional
    public void add(UserDTO userDTO) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getEmail());
        user.setActivated(false);
        String code = utils.randomString();
        user.setActivationCode(passwordEncoder.encode(code));
        userRepository.save(user);
        emailSender.sendSendinblue(emailSender.signupTemplate, Arrays.asList(user.getUsername()), Collections.singletonMap(EmailSender.CODE, code));
    }

    @Transactional
    public String activate(@Valid UserActivationDTO userActivationDTO) {
        User user = userRepository.findByUsername(userActivationDTO.getEmail());
        if (user != null) {
            if (passwordEncoder.matches(userActivationDTO.getCode(), user.getActivationCode())) {
                user.setActivated(true);
                user.setActivationCode(null);
                userRepository.save(user);
                return jwtTokenProvider.createToken(user.getUsername());
            }
            throw new BadCredentialsException("");
        }
        throw new EntityNotFoundException();
    }

}
