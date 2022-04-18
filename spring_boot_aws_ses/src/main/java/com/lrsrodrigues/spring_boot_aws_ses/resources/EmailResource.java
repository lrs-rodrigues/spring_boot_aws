package com.lrsrodrigues.spring_boot_aws_ses.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lrsrodrigues.spring_boot_aws_ses.model.Email;
import com.lrsrodrigues.spring_boot_aws_ses.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/email")
@Slf4j
public class EmailResource {

    @Autowired
    private EmailService emailService;

    @PostMapping
    private ResponseEntity<Void> sendEmail(@RequestBody Email email) throws JsonProcessingException {
        log.info(email.toString());
        emailService.sendEmail(email);
        return ResponseEntity.notFound().build();
    }

}
