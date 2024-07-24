package com.example.easyexceltest2.read;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @Author Zhb
 * @create 2024/4/10 下午2:43
 */
@Getter
@Setter
@EqualsAndHashCode
public class DemoData {

    private String string;
    private Date date;
    private Double doubleData;

}
