package com.gupaoedu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 让每一个人的职业生涯不留遗憾 SpringSecurity的配置文件
 *
 * @author 波波老师【咕泡学院】
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 将AuthenticationManager 注入IoC容器
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// .antMatchers("/gp/gp1").hasAnyAuthority("p1")
				// .antMatchers("/gp/gp2").hasAnyAuthority("p2")
				// .antMatchers("/login").permitAll()
				.anyRequest().authenticated().and().formLogin();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("zhang")
				.password(new BCryptPasswordEncoder().encode("123")).authorities("ROOT");
	}

}
