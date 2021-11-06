package cn.edu.fudan.yummyuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class YummyUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(YummyUserApplication.class, args);
    }

}
