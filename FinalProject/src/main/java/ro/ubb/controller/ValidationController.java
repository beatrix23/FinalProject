package ro.ubb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.ubb.dto.FileValidationRequestDTO;
import ro.ubb.dto.TextValidationRequestDTO;
import ro.ubb.dto.ValidationDTO;
import ro.ubb.dto.ValidationRequestDTO;
import ro.ubb.service.ValidationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class ValidationController {

    @Autowired
    private ValidationService validationService;

    @PostMapping(value = "/validate-link", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValidationDTO> validate(@RequestBody @Valid ValidationRequestDTO requestDTO) throws Exception {
        return validationService.validate(requestDTO);
    }

    @PostMapping(value = "/validate-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValidationDTO> validate(@ModelAttribute @Valid FileValidationRequestDTO requestDTO) throws Exception {
        return validationService.validate(requestDTO);
    }

    @PostMapping(value = "/validate-text", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValidationDTO> validate(@RequestBody @Valid TextValidationRequestDTO requestDTO) throws Exception {
        return validationService.validate(requestDTO);
    }

    @GetMapping(value = "/validations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValidationDTO> find(@RequestParam String location, @RequestParam Map<String, String> params) {
        return validationService.find(location);
    }
}
