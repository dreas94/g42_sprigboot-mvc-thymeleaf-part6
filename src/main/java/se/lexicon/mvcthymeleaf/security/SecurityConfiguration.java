package se.lexicon.mvcthymeleaf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.lexicon.mvcthymeleaf.service.CustomUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{
    @Autowired
    CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    /*@Bean
    public InMemoryUserDetailsManager userDetailsService()
    {
        UserDetails guest = User.withDefaultPasswordEncoder()
                .username("guest@test.se")
                .password("guest")
                .roles("GUEST")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("GUEST", "USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("GUEST", "USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(guest, user, admin);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {


        http
                .authorizeHttpRequests()
                .antMatchers("/webjars/**", "/login", "/images/**", "/js/**", "/authenticate")
                .permitAll()
                .antMatchers("/dashboard").hasRole("GUEST")
                .antMatchers("/category/list").hasRole("USER")
                .antMatchers("/category/view/**").hasRole("USER")
                .antMatchers("/category/form").hasRole("ADMIN")
                .antMatchers("/category/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/dashboard");

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customUserDetailsServiceImpl)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
