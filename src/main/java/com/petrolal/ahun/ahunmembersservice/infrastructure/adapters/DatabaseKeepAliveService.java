package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters;

import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.repository.MemberSpringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DatabaseKeepAliveService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseKeepAliveService.class);
    private final MemberSpringRepository repository;

    public DatabaseKeepAliveService(MemberSpringRepository repository) {
        this.repository = repository;
    }

    // Ping the database every 12 hours (43200000 milliseconds) to prevent Supabase auto-pause
    @Scheduled(fixedRate = 43200000)
    public void pingDatabase() {
        logger.info("Pinging database to prevent Supabase auto-pause...");
        try {
            long count = repository.count();
            logger.info("Database ping successful. Total members found: {}", count);
        } catch (Exception e) {
            logger.error("Failed to ping database: ", e);
        }
    }
}
