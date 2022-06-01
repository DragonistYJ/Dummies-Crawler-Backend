package scu.genius.dummiescrawler.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yujian
 * @since 2022/6/1 10:46
 */

@MapperScan("scu.genius.dummiescrawler.core.mapper")
@SpringBootApplication(scanBasePackages = "scu.genius.dummiescrawler")
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
