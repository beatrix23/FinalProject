package ro.ubb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.ubb.config.jwt.JwtTokenProvider;
import ro.ubb.dto.UserDTO;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;

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

	@Transactional
	public String login(UserDTO userDTO) throws Exception {
		User user = userRepository.findByUsername(userDTO.getUsername());
		if (user != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
			return jwtTokenProvider.createToken(userDTO.getUsername());
		}
		throw new Exception("Invalid username/token supplied");
	}

	@Transactional
	public String add(UserDTO userDTO) {
		User user = new User();
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setUsername(userDTO.getUsername());
		userRepository.save(user);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
		return jwtTokenProvider.createToken(userDTO.getUsername());
	}

}
