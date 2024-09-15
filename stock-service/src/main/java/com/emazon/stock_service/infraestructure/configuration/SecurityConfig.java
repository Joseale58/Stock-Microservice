package com.emazon.stock_service.infraestructure.configuration;


import com.emazon.stock_service.infraestructure.output.security.utils.JwtTokenValidatorFilter;
import com.emazon.stock_service.infraestructure.output.security.utils.JwtUtils;
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
    //Para definir la cadena de seguridad
    //Http security es un obj que se pasa por todos los filtros
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())       //Sin estado significa que no se guardara la sesion
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http  ->{
                    //Configurar endpoints públicos
                    http.requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/products/**").hasAuthority("admin");
                    http.requestMatchers(HttpMethod.POST,"/categories/**").hasAuthority("admin");
                    http.requestMatchers(HttpMethod.POST,"/brands/**").hasAuthority("admin");
                    //Configurar cualquier otro endpoint (Zero trust)
                    http.anyRequest().denyAll(); //Niega todos los otros request a endpoints no configurados
                    //http.anyRequest().authenticated(); // Niegas los otros request a endpoints solo si no estan autenticados
                })
                .addFilterBefore(new JwtTokenValidatorFilter(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }




}
