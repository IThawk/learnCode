package com.gupaoedu.security.config;

import com.gupaoedu.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 让每一个人的职业生涯不留遗憾
 *
 * @author 波波老师【咕泡学院】
 */
@Configuration
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private PersistentTokenRepository tokenRepository;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.inMemoryAuthentication() .withUser("lisi") .password("{noop}123")
		 * .authorities("ADMIN");
		 */
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		http.authorizeRequests() // 设置哪些页面可以直接访问，哪些需要验证
				.antMatchers("/login.html", "/error.html").permitAll() // 放过
				// .antMatchers("/query/*").hasRole("ROOT")
				// .antMatchers("/save").hasRole("test")
				// .antMatchers("").access("")
				.anyRequest().authenticated() // 剩下的所有的地址都是需要在认证状态下才可以访问
				.and().formLogin().loginPage("/login.html") // 指定指定要的登录页面
				.loginProcessingUrl("/login.do") // 处理认证路径的请求
				.defaultSuccessUrl("/home.html").failureForwardUrl("/error.html").and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").and()
				.rememberMe().tokenRepository(tokenRepository) // 覆盖掉默认的基于内存存储的方式
				.and().csrf().disable();
	}

	@Bean
	public PersistentTokenRepository tokenRepository(DataSource dataSource) {
		JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
		repository.setDataSource(dataSource);
		// 第一次帮我们创建表结构
		// repository.setCreateTableOnStartup(true);
		return repository;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
