package com.example.easyexceltest2.read;

import com.alibaba.excel.EasyExcel;
import com.example.easyexceltest2.readEnergyStorage.EmsDeviceDataListener;
import com.example.easyexceltest2.readEnergyStorage.EmsDeviceVO;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @Author Zhb
 * @create 2024/4/10 下午2:38
 */
@SpringBootTest
public class ReadTest {


    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        // 此处读取的是target的文件路径
//        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 直接写固定路径
        String fileName = "D:\\WorkSoft\\IdeaTestProjects\\spring-boot-examples-2.x\\easyexcel-test2\\src\\main\\resources\\demo" + File.separator + "demo.xlsx";


        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }

    /**
     * 储能读取
     */
    @Test
    public void readEnergyStorageExcel() {
        // 此处读取的是target的文件路径
//        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = "D:\\WorkSoft\\IdeaTestProjects\\spring-boot-examples-2.x\\easyexcel-test2\\src\\main\\resources\\demo" + File.separator + "储能设备导入导出模板.xlsx";

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, EmsDeviceVO.class, new EmsDeviceDataListener()).sheet().doRead();
    }


}
