package com.petrolal.ahun.members;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "google.credentials={\"type\": \"service_account\", \"project_id\": \"test-project\"}")
@ActiveProfiles("test")
class AhunMembersServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
