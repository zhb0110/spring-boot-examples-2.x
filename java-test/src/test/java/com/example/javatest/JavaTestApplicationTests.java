package com.example.javatest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaTestApplicationTests {

    @Test
    void contextLoads() {

        // 数值比较
        Double a = 100d;
        Integer b = 100;
        if (a.compareTo(b.doubleValue()) == 0) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }
        if (a >= b) {
            System.out.println("相等1");
        } else {
            System.out.println("不相等1");
        }
    }

}
