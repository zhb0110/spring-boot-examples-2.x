package com.example.springbooteasycode.demos.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbooteasycode.demos.web.dao.TestChargeDischargeRecordDao;
import com.example.springbooteasycode.demos.web.entity.TestChargeDischargeRecord;
import com.example.springbooteasycode.demos.web.service.TestChargeDischargeRecordService;
import org.springframework.stereotype.Service;

/**
 * (TestChargeDischargeRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-03-07 09:34:36
 */
@Service("testChargeDischargeRecordService")
public class TestChargeDischargeRecordServiceImpl extends ServiceImpl<TestChargeDischargeRecordDao, TestChargeDischargeRecord> implements TestChargeDischargeRecordService {

}

