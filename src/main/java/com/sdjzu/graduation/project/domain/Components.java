package com.sdjzu.graduation.project.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "components")
public class Components implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 构件编号
     */
    @Column(name = "`no`")
    private String no;

    /**
     * 构件名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 构件数量
     */
    @Column(name = "`number`")
    private Long number;

    /**
     * 构件体积
     */
    @Column(name = "volume")
    private Double volume;

    /**
     * 所属用户
     */
    @Column(name = "user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;
}