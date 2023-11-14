package com.example.javatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class JavaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaTestApplication.class, args);

        System.out.println("Hello world!");

        // "字符和字符串"练习：
        // 请将下面一组int值视为字符的Unicode码，把它们拼成一个字符串：
//        int a = 72;
//        int b = 105;
//        int c = 65281;
//        String s = String.valueOf(a) + b + c;
//        System.out.println(s);

//        String[] names = {"ABC", "XYZ", "zoo"};
//        String s = names[1]; // 变量s指向字符串"XYZ"
//        names[1] = "cat"; // 创建了字符串"cat"，names[1]变量指向"cat"
//        System.out.println(s); // s是"XYZ"还是"cat"?--当然是"XYZ"

        System.out.println("Hello, World");

        LocalDateTime now = LocalDateTime.of(2023, 11, 1, 0, 0, 0);
        System.out.println("计算两个时间的差：");
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(now, end);
        long days = duration.toDays(); //相差的天数
        long hours = duration.toHours();//相差的小时数
        long minutes = duration.toMinutes();//相差的分钟数
//        long seconds = duration.toSeconds(); // 相差的秒数
        long seconds = 0l;
        long millis = duration.toMillis();//相差毫秒数
        long nanos = duration.toNanos();//相差的纳秒数
        System.out.println(now);
        System.out.println(end);

        System.out.println("发送短信耗时【 " + days + "天，" + hours + " 小时，" + minutes + " 分钟，" + seconds + " 秒，" + millis + " 毫秒，" + nanos + " 纳秒】");


    }

}
