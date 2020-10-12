package com.sdjzu.graduation.project.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "plat_token")
public class PlatToken implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "token")
    private String token;

    private static final long serialVersionUID = 1L;
}