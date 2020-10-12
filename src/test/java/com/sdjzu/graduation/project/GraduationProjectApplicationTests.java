package com.sdjzu.graduation.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootTest
class GraduationProjectApplicationTests {

    @Test
    void contextLoads() {
    }

}
