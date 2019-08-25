package hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService; // Это мой объект
    private AuthenticationSuccessHandler authenticationSuccessHandler; // Объект для редиректа пользователей после авторизации на разные страницы
    private AuthenticationFailureHandler authenticationFailureHandler; // Объект для обработки ошибок так как я хочу

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    } // Шифруем пароль

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // Передаю в объект auth своего юзера и декодер пароля
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") // Страница admin доступна только пользователям с ролью admin
                .anyRequest().authenticated() // Гарантирует, что любой запрос к нашему приложению требует аутентификации пользователя
                .and().formLogin() // Пользователей аутетифицируем через форму логина
                .permitAll()// Разрешить все запросы на форму логина
                .failureHandler(authenticationFailureHandler) // Используй мой обработчик ошибок
                .successHandler(authenticationSuccessHandler).usernameParameter("username").passwordParameter("password") // Разрешаю пользователю с login и password проходить аутентификацию
                .and().logout().permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID") // При выходе удалить cookie и сбросить сессию
                .and().csrf().disable(); // Отключаем CSRF-это защита от кросс-доменных запросов
    }
}
