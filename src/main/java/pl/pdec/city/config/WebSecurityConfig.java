package pl.pdec.city.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${server.servlet.session.cookie.name:JSESSIONID}")
    private String sessionCookie = null;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/logout/**").and()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .mvcMatchers("/", "/logout", "/ag/**").permitAll()
                        .mvcMatchers("/actuator/health", "/favicon.ico").permitAll()
                        .mvcMatchers("/testsave", "/testget", "/api/**").permitAll()
                        .anyRequest().authenticated())
                .logout().logoutUrl("/logout").logoutSuccessUrl("/ag/")
                .deleteCookies(sessionCookie).invalidateHttpSession(true);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("pass")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
