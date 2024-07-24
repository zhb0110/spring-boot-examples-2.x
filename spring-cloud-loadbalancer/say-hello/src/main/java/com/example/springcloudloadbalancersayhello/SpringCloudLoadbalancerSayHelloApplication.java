package com.example.springcloudloadbalancersayhello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类也可以作为controller
 */
@RestController
@SpringBootApplication
public class SpringCloudLoadbalancerSayHelloApplication {

    private static Logger log = LoggerFactory.getLogger(SayHelloApplication.class);

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
