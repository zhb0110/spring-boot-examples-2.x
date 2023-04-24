package com.example.mybatismysql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MybatisMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisMysqlApplication.class, args);
    }


    private final CityMapper cityMapper;

    public MybatisMysqlApplication(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    /**
     * 写法类似项目 accessing-data-jpa，直接在main方法中写
     *
     * @return
     * @Bean public CommandLineRunner demo(CustomerRepository repository) {
     */
    @Bean
    CommandLineRunner sampleCommandLineRunner() {
        System.out.println("哈哈1");
        return args -> {
            City city = new City();
            city.setId(2L);
            city.setName("San Francisco1");
            city.setState("CA1");
            city.setCountry("US1");
            cityMapper.insert(city);
            // 这个后加载，所以查的是第二个
            System.out.println(this.cityMapper.findById(city.getId()));
            // 这个查的第一个是data.sql中加的数据
            System.out.println(this.cityMapper.findById(1L));
        };
    }

//    public static void main(String[] args) {
//        SpringApplication.run(SampleAnnotationApplication.class, args);
//    }

//    private final CityMapper cityMapper;

//    public SampleAnnotationApplication(CityMapper cityMapper) {
//        this.cityMapper = cityMapper;
//    }

//    @Override
//    @SuppressWarnings("squid:S106")
//    public void run(String... args) {
//        System.out.println("哈哈");
//        System.out.println(this.cityMapper.findByState("CA"));
//    }
}
