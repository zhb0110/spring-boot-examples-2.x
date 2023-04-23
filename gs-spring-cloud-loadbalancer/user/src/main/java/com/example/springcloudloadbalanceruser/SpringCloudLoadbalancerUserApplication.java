package com.example.springcloudloadbalanceruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
@SpringBootApplication
public class SpringCloudLoadbalancerUserApplication {

    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public SpringCloudLoadbalancerUserApplication(WebClient.Builder webClientBuilder,
                                                  ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedWebClientBuilder = webClientBuilder;
        this.lbFunction = lbFunction;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudLoadbalancerUserApplication.class, args);
    }


    @RequestMapping("/hi")
    public Mono<String> hi(@RequestParam(value = "name", defaultValue = "Mary") String name) {
        return loadBalancedWebClientBuilder.build().get().uri("http://say-hello/greeting")
                .retrieve().bodyToMono(String.class)
                .map(greeting -> String.format("%s, %s!", greeting, name));
    }

    @RequestMapping("/hello")
    public Mono<String> hello(@RequestParam(value = "name", defaultValue = "John") String name) {
        return WebClient.builder()
                .filter(lbFunction)
                .build().get().uri("http://say-hello/greeting")
                .retrieve().bodyToMono(String.class)
                .map(greeting -> String.format("%s, %s!", greeting, name));
    }
}
