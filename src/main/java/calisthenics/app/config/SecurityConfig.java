package calisthenics.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import calisthenics.app.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    /*
     * autowired annotation mean that spring will automatically
     * search for a bean of type UserDetailsService and inject it into this field.
     * in this case, it will be MyUserDetailsService, which is a custom
     * implementation of UserDetailsService.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    // kürze schreibweise für
    /*
     * Spring security sucht am anfang nach Beans vom typ SecurityFilterChain und
     * wendet diese auf die HttpSecurity an.
     * falls ja -> wende deine conif und falls nicht -> default config
     * http.csrf erstelle einen Objekt vom typ CsrfConfigurer<HttpSecurity> und rufe
     * darauf die Methode disable() auf.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                // spring security bietet eine default login, session management, logout, etc.
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /*
     * 
     * This Bean takes care of the authentication process.
     * it is connected to the UserDetailsService and the PasswordEncoder.
     * 
     * 
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
    // {

    // UserDetails user = User
    // .withUsername("user")
    // .password(passwordEncoder.encode("u123"))
    // .roles("USER")
    // .build();

    // return new InMemoryUserDetailsManager(user);
    // }
}
