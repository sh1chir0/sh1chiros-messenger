package ua.sh1chiro.messenger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.sh1chiro.messenger.services.CustomUserDetailService;

/**
 * @author sh1chiro 20.04.2023
 */
@Configuration
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{
    private final CustomUserDetailService customUserDetailService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/registration", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll();
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/img/**", "/error/**");
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}
