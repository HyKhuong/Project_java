package com.example.quanlysach;

import com.example.quanlysach.Service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Đánh dấu lớp này là một lớp cấu hình cho Spring Context.
@EnableWebSecurity // Kích hoạt tính năng bảo mật web của Spring Security.
@EnableMethodSecurity // Lombok tự động tạo constructor có tham số cho tất cảcác trường final.
public class SecurityConfig {

    @Bean // Đánh dấu phương thức trả về một bean được quản lý bởi Spring Context.
    public UserDetailsService userDetailsService() {
        return  new CustomUserDetailService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean mã hóa mật khẩu sử dụng BCrypt.
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider(); // Tạo nhà cung cấp xác thực.
        auth.setUserDetailsService(userDetailsService()); // Thiết lập dịch vụ chi tiết người dùng.
        auth.setPasswordEncoder(passwordEncoder()); // Thiết lập cơ chế mã hóa mật khẩu.
        return auth; // Trả về nhà cung cấp xác thực.
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // Example: Disable CSRF protection for specific endpoints
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/",
                                "/register", "/error")
                        .permitAll() // Cho phép truy cập không cần xác thực.
                        .requestMatchers("/books/edit", "/books/delete")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers("/books","/books/add")
                        .hasAnyAuthority("ADMIN","USER")
                        .requestMatchers("/api/**")
                        .hasAnyAuthority("ADMIN","USER")
                        .anyRequest().authenticated() // Bất kỳ yêu cầu nào khác cần xác thực.
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // Trang chuyển hướng sau khi đăng xuất.
                        .deleteCookies("JSESSIONID") // Xóa cookie.
                        .invalidateHttpSession(true) // Hủy phiên làm việc.
                        .clearAuthentication(true) // Xóa xác thực.
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Trang đăng nhập.
                        .loginProcessingUrl("/login") // URL xử lý đăng nhập.
                        .defaultSuccessUrl("/books") // Trang sau đăng nhập thành công.
                        .failureUrl("/login?error") // Trang đăng nhập thất bại.
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.key("UniqueAndSecret")
                        .tokenValiditySeconds(86400) // Thời gian nhớ đăngnhập.
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403") // Trang báo lỗi khi truy cập không được phép.
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) // Giới hạn số phiên đăng nhập.
                        .expiredUrl("/login") // Trang khi phiên hết hạn.
                )
                .httpBasic(httpBasic -> httpBasic
                        .realmName("hutech") // Tên miền cho xác thực cơ bản.
                )
                .build(); // Xây dựng và trả về chuỗi lọc bảo mật.
    }
}