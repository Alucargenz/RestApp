package org.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class AuthBean {
    @PreAuthorize("hasRole('ADMIN')")
    public String secureMethod() {
        return "Access granted just for Daniel Santos.";
    }
}
