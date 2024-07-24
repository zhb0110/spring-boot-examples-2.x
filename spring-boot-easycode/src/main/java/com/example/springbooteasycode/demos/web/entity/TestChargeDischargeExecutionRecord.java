package com.example.springbooteasycode.demos.web.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (TestChargeDischargeExecutionRecord)表实体类
 *
 * @author makejava
 * @since 2024-03-07 09:36:28
 */
@SuppressWarnings("serial")
//public class TestChargeDischargeExecutionRecord extends Model<TestChargeDischargeExecutionRecord> {
public class TestChargeDischargeExecutionRecord {

    //试验执行记录表id，自增
    private Integer id;
    //试验记录表id：用于跟试验记录表关联
    private Integer testChargeDischargeRecordId;
    //试验步骤 id/key
//1. 数据验证：BCMU、空调、K 值
//2. 储能柜充电：。。。
//3. 静置 5min
//4. 储能柜放电：。。。
//5. 动力柜验证：。。。
    private Integer testStepsKey;
    //试验步骤名称，例如：100kW充电至3.58V，目前具体步骤和步骤名称都写固定在程序中
//1. 数据验证：BCMU、空调、K 值
//2. 储能柜充电：。。。
//3. 静置 5min
//4. 储能柜放电：。。。
//5. 动力柜验证：。。。
    private String testStepsName;
    //步骤记录：json格式，本步骤实时数据，包含该步骤中关键数据记录（eg：开始充电时正反向电度，结束充电时正反向电度等）以及充电电量、放电电量
    private String stepRecord;
    //步骤开始时间
    private Date startTime;
    //步骤结束时间
    private Date endTime;
    //执行状态
//1. 未开始
//2. 执行中
//3. 暂停--不可手动暂停，只是数据异常时自动暂停
//4. 已终止
//5. 已完成
    private Integer executionStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestChargeDischargeRecordId() {
        return testChargeDischargeRecordId;
    }

    public void setTestChargeDischargeRecordId(Integer testChargeDischargeRecordId) {
        this.testChargeDischargeRecordId = testChargeDischargeRecordId;
    }

    public Integer getTestStepsKey() {
        return testStepsKey;
    }

    public void setTestStepsKey(Integer testStepsKey) {
        this.testStepsKey = testStepsKey;
    }

    public String getTestStepsName() {
        return testStepsName;
    }

    public void setTestStepsName(String testStepsName) {
        this.testStepsName = testStepsName;
    }

    public String getStepRecord() {
        return stepRecord;
    }

    public void setStepRecord(String stepRecord) {
        this.stepRecord = stepRecord;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(Integer executionStatus) {
        this.executionStatus = executionStatus;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
//    @Override
//    protected Serializable pkVal() {
//        return this.id;
//    }
}

