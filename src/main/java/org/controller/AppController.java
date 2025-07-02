package org.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api")
@Validated
public class AppController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndpoint() {
        return "This is a USER endpoint.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public String adminEndpoint(@Valid @RequestBody RequestData request) {
        return "Hello Admin, you posted: " + request.getContent();
    }

    @Autowired
    private org.security.AuthBean customBean;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure-bean")
    public String beanSecured() {
        return customBean.secureMethod();
    }


    public static class RequestData {
        @NotBlank
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
