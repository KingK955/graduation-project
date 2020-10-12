package com.sdjzu.graduation.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderDTO implements Serializable {

    private Long id;

    private String orderNo;

    private String projectName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date installTime;

    /**
     * 最迟交货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadlines;

    private String componentNo;

    private Long componentId;

    private String componentName;

    private Long componentNumber;

    private Double componentVolume;

    private Long buildingId;

    private Long factoryId;

    private Long status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creatDate;

    private static final long serialVersionUID = 1L;
}
