package com.situ.shoplook2025.category.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.situ.shoplook2025.core.utils.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@TableName("category")
@Getter
@Setter
public class Category extends AuditEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    @TableField(condition = SqlCondition.LIKE,whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
    private String icon;
    private Integer sort;
    private String description;

    //父分类，不进行序列化
    @TableField(exist = false)
    @JsonBackReference
    private Category parent;

    //所有子类别
    @TableField(exist = false)
    @JsonManagedReference
    private List<Category> children;
}
