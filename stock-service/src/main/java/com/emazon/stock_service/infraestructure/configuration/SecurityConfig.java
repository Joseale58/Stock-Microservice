package com.emazon.stock_service.infraestructure.configuration;


import com.emazon.stock_service.infraestructure.output.security.utils.JwtTokenValidatorFilter;
import com.emazon.stock_service.infraestructure.output.security.utils.JwtUtils;
import com.emazon.stock_service.utils.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http  ->{
                    http.requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/products/**").hasAuthority(SecurityConstants.CLAIM_ROLE_ADMIN);
                    http.requestMatchers(HttpMethod.PATCH, "/products/**").hasAnyAuthority(SecurityConstants.CLAIM_ROLE_ADMIN, SecurityConstants.CLAIM_ROLE_AUX_BODEGA);
                    http.requestMatchers(HttpMethod.POST,"/categories/**").hasAuthority(SecurityConstants.CLAIM_ROLE_ADMIN);
                    http.requestMatchers(HttpMethod.POST,"/brands/**").hasAuthority(SecurityConstants.CLAIM_ROLE_ADMIN);
                    http.anyRequest().permitAll();
//                    http.anyRequest().denyAll(); //Niega todos los otros request a endpoints no configurados
                    //http.anyRequest().authenticated(); // Niegas los otros request a endpoints solo si no estan autenticados
                })
                .addFilterBefore(new JwtTokenValidatorFilter(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }




}
