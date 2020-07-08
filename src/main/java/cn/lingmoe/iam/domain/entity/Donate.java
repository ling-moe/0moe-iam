/*
 * @(#)Donate.java 2019年12月20日 下午2:04:15
 * Copyright 2019 yukdawn@gmail.com, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.lingmoe.iam.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

/**
 *
 * @author yukdawn@gmail.com
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class Donate {
    @Id
    private Integer id;

    private String nick;

    private Double amount;

    private Integer canal;

    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @TableField(exist = false)
    private String beginTime;

    @TableField(exist = false)
    private String endTime;
}
