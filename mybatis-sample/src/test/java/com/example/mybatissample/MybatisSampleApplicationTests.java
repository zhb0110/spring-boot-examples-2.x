package com.example.mybatissample;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
class MybatisSampleApplicationTests {

    /**
     * 捕获输出
     * @param output
     */
    @Test
    void contextLoads(CapturedOutput output) {
        // 没看出来执行了什么，但是主项目的所有都执行了，这个可能
        Assertions.assertThat(output.getOut()).contains("1,San Francisco,CA,US");
//        Assertions.assertThat(output.getOut()).contains("2,San Francisco,CA,US");
    }

}
