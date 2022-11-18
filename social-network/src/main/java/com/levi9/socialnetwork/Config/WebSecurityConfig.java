package com.levi9.socialnetwork.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.levi9.socialnetwork.Security.authority.JWToken;
import com.levi9.socialnetwork.Security.authority.RestAuthenticationEntryPoint;
import com.levi9.socialnetwork.Security.authority.TokenAuthenticationFilter;
import com.levi9.socialnetwork.Service.UserService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	

	@Autowired
	private JWToken jWToken;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserService userService)
	        throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	    		.userDetailsService(userService)
				.passwordEncoder(passwordEncoder)
	            .and()
	            .build();
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				// .antMatchers("/admin").hasRole("ADMIN") ili
				// .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
				.authorizeRequests().antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/users/**").permitAll()
				
				.anyRequest().authenticated().and()

				.cors().and()

				.addFilterBefore(new TokenAuthenticationFilter(jWToken, userService), BasicAuthenticationFilter.class);

		http.csrf().disable();
		
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> {
			web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
			web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
					"/**/*.css", "/**/*.js");
		};
	}

}