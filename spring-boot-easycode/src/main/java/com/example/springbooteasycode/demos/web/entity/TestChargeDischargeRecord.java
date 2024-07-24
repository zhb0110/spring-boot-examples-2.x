package com.example.springbooteasycode.demos.web.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (TestChargeDischargeRecord)表实体类
 *
 * @author makejava
 * @since 2024-03-07 09:34:35
 */
@SuppressWarnings("serial")
//public class TestChargeDischargeRecord extends Model<TestChargeDischargeRecord> {
public class TestChargeDischargeRecord {
    //试验记录表id，自增
    private Integer id;
    //储能设备表设备表储能柜真实 sn
    private String energyStorageCabinetSn;
    //业务类型：
//1. 出厂试验前验证执行记录
//2. 试充放电记录
    private Integer serviceType;
    //试验策略
//1. 数据和试充放电验证
    private String strategyType;
    //试验通道：通道sn，通道表和这边关联
    private String monitorChannelSn;
    //是否进行动力柜数据验证：
//1. 是
//2. 否
    private Integer isPowerCabinetVerify;
    //试验状态：
//1. 未开始
//2. 执行中
//3. 暂停--不可手动暂停，只是数据异常时自动暂停
//4. 已终止
//5. 已完成
    private Integer testStatus;
    //试验结果（自动，也可手动修改）
//1. 未完成
//2. 数据异常--检修后，可重新开始
//3. 数据正常
//4. 试充放电异常
//5. 试充放电正常
//6. 试验不通过
//7. 试验通过
    private Integer testResults;
    //试验记录：json格式，总体实时数据，包含过程中关键数据记录。由于采集的存储周期为5min，为避免核心数据统计误差，需要额外记录实时数据
    private String testRecords;
    //试验开始时间
    private Date startTime;
    //试验结束时间
    private Date endTime;
    //负责人，manager中的用户id
    private Integer userId;
    //备注
    private String remarks;
    //删除标识，默认值'0'
    private String delFlag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnergyStorageCabinetSn() {
        return energyStorageCabinetSn;
    }

    public void setEnergyStorageCabinetSn(String energyStorageCabinetSn) {
        this.energyStorageCabinetSn = energyStorageCabinetSn;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public String getMonitorChannelSn() {
        return monitorChannelSn;
    }

    public void setMonitorChannelSn(String monitorChannelSn) {
        this.monitorChannelSn = monitorChannelSn;
    }

    public Integer getIsPowerCabinetVerify() {
        return isPowerCabinetVerify;
    }

    public void setIsPowerCabinetVerify(Integer isPowerCabinetVerify) {
        this.isPowerCabinetVerify = isPowerCabinetVerify;
    }

    public Integer getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(Integer testStatus) {
        this.testStatus = testStatus;
    }

    public Integer getTestResults() {
        return testResults;
    }

    public void setTestResults(Integer testResults) {
        this.testResults = testResults;
    }

    public String getTestRecords() {
        return testRecords;
    }

    public void setTestRecords(String testRecords) {
        this.testRecords = testRecords;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

