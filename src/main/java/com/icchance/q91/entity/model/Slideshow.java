package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Slideshow implements Serializable {

    private static final long serialVersionUID = -2492312259661171182L;

    @TableId("ID")
    private Integer id;

    private String title;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
