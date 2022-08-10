package se.lexicon.mvcthymeleaf.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService
{
    int customUserDetailsSize();
}
