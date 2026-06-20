package com.petrolal.ahun.ahunbirthdayservice.infrastructure.config;

import com.petrolal.ahun.ahunbirthdayservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunbirthdayservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunbirthdayservice.application.usecases.MemberUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    MemberPort memberPort(MemberRepositoryPort memberRepositoryPort) {
        return new MemberUseCase(memberRepositoryPort);
    }

}
