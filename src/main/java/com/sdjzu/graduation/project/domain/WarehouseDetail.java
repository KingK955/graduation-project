package com.sdjzu.graduation.project.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@Table(name = "warehouse_detail")
public class WarehouseDetail implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "`date`")
    private Date date;

    /**
     * 相关信息
     */
    @Column(name = "`no`")
    private String no;

    @Column(name = "volume")
    private Double volume;

    /**
     * 动作
     */
    @Column(name = "`action`")
    private Integer action;

    /**
     * 剩余容量
     */
    @Column(name = "left_capacity")
    private Double leftCapacity;

    /**
     * 所属仓库
     */
    @Column(name = "warehouse_id")
    private Long warehouseId;

    private static final long serialVersionUID = 1L;
}