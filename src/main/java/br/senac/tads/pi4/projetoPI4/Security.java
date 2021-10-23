package br.senac.tads.pi4.projetoPI4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email as username, senha as password, 1 as enable from usuarios where email=? ")
                .authoritiesByUsernameQuery("select email as username, tipo as authority from usuarios where email=?")
                .passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/cliente/**", "/carrinho/cliente/**").hasAnyAuthority("cliente")
                .antMatchers("/gerenciar/**").hasAnyAuthority("Administrador", "Suporte").and()
                .formLogin().loginPage("/login").failureUrl("/login").loginProcessingUrl("/login").defaultSuccessUrl("/cliente/conta")
                .usernameParameter("username").passwordParameter("password").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID").and().exceptionHandling().accessDeniedPage("/negado").and().csrf().disable();
    }
}
