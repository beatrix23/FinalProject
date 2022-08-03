package ro.ubb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ro.ubb.dto.ValidationDTO;
import ro.ubb.dto.ValidationRequestDTO;
import ro.ubb.service.ValidationService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class ValidationController {

	@Autowired
	private ValidationService validationService;

	@PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ValidationDTO> validate(@RequestBody ValidationRequestDTO requestDTO) throws Exception {
		return validationService.validate(requestDTO);
	}
	
	@GetMapping(value = "/validations", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ValidationDTO> find(@RequestParam String href, @RequestParam Map<String, String> params){
		return validationService.find(href);
	}
}
