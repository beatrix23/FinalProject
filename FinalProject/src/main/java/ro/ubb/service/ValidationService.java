package ro.ubb.service;

import nu.validator.htmlparser.io.Encoding;
import nu.validator.messages.MessageEmitter;
import nu.validator.messages.MessageEmitterAdapter;
import nu.validator.messages.TextMessageEmitter;
import nu.validator.servlet.imagereview.ImageCollector;
import nu.validator.source.SourceCode;
import nu.validator.validation.SimpleDocumentValidator;
import nu.validator.xml.SystemErrErrorHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import ro.ubb.config.SecurityContext;
import ro.ubb.dto.*;
import ro.ubb.model.User;
import ro.ubb.model.Validation;
import ro.ubb.model.ValidationMessage;
import ro.ubb.repository.ValidationRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    @Autowired
    private SecurityContext securityContext;

    @Autowired
    private ValidationRepository validationRepository;

    public List<ValidationDTO> validate(ValidationRequestDTO requestDTO) {
        Set<String> hrefs = new HashSet<>();
        if (requestDTO.isEntireWebsite()) {
            findHrefs(requestDTO.getLocation(), requestDTO.getLocation(), hrefs);
        } else {
            hrefs.add(requestDTO.getLocation());
        }
        List<ValidationDTO> validationDTOs = new ArrayList<>();
        for (String href : hrefs) {
            ValidationDTO validationDTO = new ValidationDTO();
            Document doc = null;
            try {
                doc = Jsoup.connect(href).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (doc != null) {
                try {
                    validationDTO.setMessages(convertMessages(validateHtml(doc.toString())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            validationDTO.setLocation(href);
            validationDTOs.add(validationDTO);
        }
        if (securityContext.getCurrentUser() != null) {
            User user = securityContext.getCurrentUser();
            for (ValidationDTO validationDTO : validationDTOs) {
                saveValidation(user, validationDTO);
            }
        }
        return validationDTOs;
    }

    public List<ValidationDTO> validate(TextValidationRequestDTO requestDTO) throws Exception {
        List<ValidationDTO> validationDTOs = new ArrayList<>();
        ValidationDTO validationDTO = new ValidationDTO();
        validationDTO.setMessages(convertMessages(validateHtml(requestDTO.getText())));
        validationDTO.setLocation("text");
        if (securityContext.getCurrentUser() != null) {
            User user = securityContext.getCurrentUser();
            saveValidation(user, validationDTO);
        }
        validationDTOs.add(validationDTO);
        return validationDTOs;
    }

    public List<ValidationDTO> validate(FileValidationRequestDTO requestDTO) throws Exception {
        List<ValidationDTO> validationDTOs = new ArrayList<>();
        ValidationDTO validationDTO = new ValidationDTO();
        if (requestDTO != null && requestDTO.getFile() != null && requestDTO.getFile().getContentType() != null && requestDTO.getFile().getContentType().equals("text/html")) {
            String text = new BufferedReader(new InputStreamReader(requestDTO.getFile().getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            validationDTO.setMessages(convertMessages(validateHtml(text)));
            validationDTO.setLocation(requestDTO.getFile().getOriginalFilename());
            if (securityContext.getCurrentUser() != null) {
                User user = securityContext.getCurrentUser();
                saveValidation(user, validationDTO);
            }
        }
        validationDTOs.add(validationDTO);
        return validationDTOs;
    }

    private void findHrefs(String base, String href, Set<String> hrefs) {
        if (href.contains("#")) {
            href = href.substring(0, href.lastIndexOf("#"));
        }
        if (!hrefs.contains(href) && href.startsWith(base)) {
            hrefs.add(href);
            try {
                Document doc = Jsoup.connect(href).get();
                if (doc != null) {
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {
                        findHrefs(base, link.absUrl("href"), hrefs);
                    }
                }
            } catch (Exception e) {
                hrefs.remove(href);
            }
        }
    }

    private String validateHtml(String htmlContent) throws Exception {
        InputStream in = new ByteArrayInputStream(htmlContent.getBytes(Encoding.UTF8.getCanonName()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        SourceCode sourceCode = new SourceCode();
        ImageCollector imageCollector = new ImageCollector(sourceCode);
        boolean showSource = false;
        MessageEmitter emitter = new TextMessageEmitter(out, false);
        MessageEmitterAdapter errorHandler = new MessageEmitterAdapter(null, sourceCode, showSource, imageCollector, 0, false, emitter);
        errorHandler.setErrorsOnly(false);

        SimpleDocumentValidator validator = new SimpleDocumentValidator();
        validator.setUpMainSchema("http://s.validator.nu/html5-rdfalite.rnc", new SystemErrErrorHandler());
        validator.setUpValidatorAndParsers(errorHandler, true, false);
        validator.checkHtmlInputSource(new InputSource(in));
        errorHandler.end("", "", "");
        return out.toString();
    }

    private List<MessageDTO> convertMessages(String errors) {
        if (errors != null) {
            String[] messages = errors.split("\n");
            List<MessageDTO> list = new ArrayList<>();
            int i = 0;
            int j = i + 1;
            while (j < messages.length) {
                if (!messages[j].matches("On line .*") && !messages[j].matches("At line .*") && !messages[j].matches("From line .*")) {
                    list.add(new MessageDTO(messages[i], ""));
                    i--;
                    j--;
                } else {
                    list.add(new MessageDTO(messages[i], messages[j]));
                }
                i = i + 2;
                j = j + 2;
            }
            return list;
        }
        return new ArrayList<>();
    }

    public List<ValidationDTO> find(String href) {
        if (securityContext.getCurrentUser() != null) {
            User user = securityContext.getCurrentUser();
            Set<Validation> validations = validationRepository.findByHrefAndUserOrderByDateCreatedDesc(href, user);
            List<ValidationDTO> validationDTOs = new ArrayList<>();
            for (Validation validation : validations) {
                ValidationDTO validationDTO = new ValidationDTO();
                validationDTO.setLocation(validation.getHref());
                validationDTO.setMessages(new ArrayList<>());
                for (ValidationMessage validationMessage : validation.getValidationMessages()) {
                    validationDTO.getMessages().add(new MessageDTO(validationMessage.getMessage(), validationMessage.getLocation()));
                }
                validationDTOs.add(validationDTO);
            }
            return validationDTOs;
        }
        return new ArrayList<>();
    }

    private void saveValidation(User user, ValidationDTO validationDTO) {
        Validation validation = new Validation();
        validation.setDateCreated(LocalDateTime.now());
        validation.setUser(user);
        validation.setHref(validationDTO.getLocation());
        validation.setValidationMessages(new ArrayList<>());
        for (MessageDTO messageDTO : validationDTO.getMessages()) {
            ValidationMessage validationMessage = new ValidationMessage();
            validationMessage.setValidation(validation);
            validationMessage.setLocation(messageDTO.getLocation());
            validationMessage.setMessage(messageDTO.getMessage());
            validation.getValidationMessages().add(validationMessage);
        }
        validationRepository.save(validation);
    }

}
