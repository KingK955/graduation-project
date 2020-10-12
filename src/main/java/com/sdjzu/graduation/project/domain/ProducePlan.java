package com.sdjzu.graduation.project.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@Table(name = "produce_plan")
public class ProducePlan implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "produce_no")
    private String produceNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "produce_time")
    private Date produceTime;

    @Column(name = "component_id")
    private Long componentId;

    @Column(name = "component_number")
    private Long componentNumber;

    @Column(name = "`status`")
    private Long status;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "user_id")
    private Long userId;

    private Components component;
    private static final long serialVersionUID = -140552020828266734L;
}