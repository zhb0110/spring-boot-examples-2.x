package com.example.springcloudloadbalancersayhello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 启动类也可以作为controller
 */
@RestController
@SpringBootApplication
public class SpringCloudLoadbalancerSayHelloApplication {

    private static Logger log = LoggerFactory.getLogger(SpringCloudLoadbalancerSayHelloApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudLoadbalancerSayHelloApplication.class, args);
    }

    @GetMapping("/greeting")
    public String greet() {
        log.info("Access /greeting");

        List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
        Random rand = new Random();

        int randomNum = rand.nextInt(greetings.size());
        return greetings.get(randomNum);
    }

    @GetMapping("/")
    public String home() {
        log.info("Access /");
        return "Hi!";
    }
}
