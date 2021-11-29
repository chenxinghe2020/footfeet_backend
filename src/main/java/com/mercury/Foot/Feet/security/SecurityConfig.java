package com.mercury.Foot.Feet.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mercury.Foot.Feet.security.handlers.AccessDeniedHandlerImpl;
import com.mercury.Foot.Feet.security.handlers.AuthenticationEntryPointImpl;
import com.mercury.Foot.Feet.security.handlers.AuthenticationFailureHandlerImpl;
import com.mercury.Foot.Feet.security.handlers.AuthenticationSuccessHandlerImpl;
import com.mercury.Foot.Feet.security.handlers.LogoutSuccessHandlerImpl;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsServiceImpl;
	
	@Autowired
	private AuthenticationEntryPointImpl authenticationEntryPointImpl;
	
	@Autowired
	private AccessDeniedHandlerImpl accessDeniedHandlerImpl;
	
	@Autowired
	private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;
	
	@Autowired
	private AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;
	
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandlerImpl;
	

	public void configure(HttpSecurity http) throws Exception {
		http
			.cors()		// by default, without configuring this line, no cors, then SOP apply
				.and()
			.csrf()		
				.disable()
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.and()
			.authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/products", "/index.html", "/checklogin").permitAll()
//				.antMatchers(HttpMethod.DELETE, "/orders/*").HAS
//				.anyRequest().authenticated()
				.anyRequest().permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandlerImpl)
				.authenticationEntryPoint(authenticationEntryPointImpl)
				.and()
			.formLogin()			// if not login yet, redirect to /login page
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(authenticationSuccessHandlerImpl)
				.failureHandler(authenticationFailureHandlerImpl)
				.and()
			.logout()
				.permitAll()	
				.logoutUrl("/logout")
				.logoutSuccessHandler(logoutSuccessHandlerImpl)
				.and()
			.httpBasic();
	}
	
	@Bean		// put the return object into spring container, as a bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@Autowired		// @Autowired on function will autowired the parameters
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // You should only set trusted site here. e.g. http://localhost:4200 means only this site can access.
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","HEAD","OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
