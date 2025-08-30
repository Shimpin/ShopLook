package com.situ.shoplook2025.brand.model;
//品牌模型

import com.baomidou.mybatisplus.annotation.*;
import com.situ.shoplook2025.core.utils.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@TableName("brand")
@Getter
@Setter
public class Brand extends AuditEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(condition = SqlCondition.LIKE,whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
    private String company;
    private String logo;
    private String site;
    private String description;
}
