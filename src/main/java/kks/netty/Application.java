package kks.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        log.info("========================================================================");
        log.info("Active profile : {}", env.getActiveProfiles());
        log.info("=======================================================================");
    }

    /**
     * main 메소드 실행
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
