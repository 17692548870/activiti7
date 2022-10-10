package com.hpe.ctrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 17692
 */
@Configuration
public class PasswordConfig {
    /**
     * 密码配置
     * @return 明文密码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        明文密码
     return NoOpPasswordEncoder.getInstance();
//     Bcrypt加密
       // return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
