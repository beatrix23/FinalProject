package ro.ubb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.ubb.dto.TokenDTO;
import ro.ubb.dto.UserDTO;
import ro.ubb.service.UserService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDTO) throws Exception {
        String token = userService.login(userDTO);
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> signup(@RequestBody UserDTO userDTO) {
        String token = userService.add(userDTO);
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }

}
