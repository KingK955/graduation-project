package com.sdjzu.graduation.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.sdjzu.graduation.project.mapper")
public class GraduationProjectApplication {
    //测试
    public static void main(String[] args) {
        SpringApplication.run(GraduationProjectApplication.class, args);
    }

}
