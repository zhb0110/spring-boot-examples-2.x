package com.example.springbooteasycode.demos.web.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbooteasycode.demos.web.entity.TestChargeDischargeExecutionRecord;
import com.example.springbooteasycode.demos.web.service.TestChargeDischargeExecutionRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (TestChargeDischargeExecutionRecord)表控制层
 *
 * @author makejava
 * @since 2024-03-07 09:36:28
 */
@RestController
@RequestMapping("testChargeDischargeExecutionRecord")
public class TestChargeDischargeExecutionRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private TestChargeDischargeExecutionRecordService testChargeDischargeExecutionRecordService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param testChargeDischargeExecutionRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<TestChargeDischargeExecutionRecord> page, TestChargeDischargeExecutionRecord testChargeDischargeExecutionRecord) {
        return success(this.testChargeDischargeExecutionRecordService.page(page, new QueryWrapper<>(testChargeDischargeExecutionRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.testChargeDischargeExecutionRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param testChargeDischargeExecutionRecord 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody TestChargeDischargeExecutionRecord testChargeDischargeExecutionRecord) {
        return success(this.testChargeDischargeExecutionRecordService.save(testChargeDischargeExecutionRecord));
    }

    /**
     * 修改数据
     *
     * @param testChargeDischargeExecutionRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody TestChargeDischargeExecutionRecord testChargeDischargeExecutionRecord) {
        return success(this.testChargeDischargeExecutionRecordService.updateById(testChargeDischargeExecutionRecord));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.testChargeDischargeExecutionRecordService.removeByIds(idList));
    }
}

