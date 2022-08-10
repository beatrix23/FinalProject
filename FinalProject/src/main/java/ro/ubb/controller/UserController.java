package ro.ubb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.dto.TokenDTO;
import ro.ubb.dto.UserActivationDTO;
import ro.ubb.dto.UserDTO;
import ro.ubb.service.UserService;

import javax.validation.Valid;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserDTO userDTO) throws Exception {
        String token = userService.login(userDTO);
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> signup(@RequestBody @Valid UserDTO userDTO) {
        userService.add(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/activate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> activate(@RequestBody @Valid UserActivationDTO userActivationDTO) {
        String token = userService.activate(userActivationDTO);
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }

}
