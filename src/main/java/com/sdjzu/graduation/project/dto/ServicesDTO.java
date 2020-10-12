package com.sdjzu.graduation.project.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServicesDTO implements Serializable {

    private Long delayTime;
    private String msg;
    private Long orderId;

    private static final long serialVersionUID = -5853637080839402423L;
}
