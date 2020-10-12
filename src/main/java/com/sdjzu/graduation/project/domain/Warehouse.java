package com.sdjzu.graduation.project.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "warehouse")
public class Warehouse implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 仓库名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 总容量
     */
    @Column(name = "total_capacity")
    private Double totalCapacity;

    /**
     * 剩余容量
     */
    @Column(name = "left_capacity")
    private Double leftCapacity;

    /**
     * 所属用户
     */
    @Column(name = "user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;
}