package com.situ.shoplook2025.good.model;


import com.baomidou.mybatisplus.annotation.*;
import com.situ.shoplook2025.brand.model.Brand;
import com.situ.shoplook2025.category.model.Category;
import com.situ.shoplook2025.core.utils.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@TableName("good")
@Getter
@Setter
public class Good extends AuditEntity {
    //重写了父类属性id，是为了添加注解
    @TableId(type = IdType.AUTO)
    private Integer id;//主键
    @TableField(condition = SqlCondition.LIKE, whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;//商品名称
    private String alias;//别名
    private String summary;//摘要
    private BigDecimal markPrice;//标价
    private BigDecimal price;//实价
    private Integer qty;//库存
    private String pic;//主图
    private String pic2;//次图

    private String detailPics;//详情图，多幅图
    private String detail;//详情，富文本
    private Boolean isTakeDown;//是否下架
    private Boolean isHot;//是否热销
    private Boolean isDel;//是否删除
    private String description;//备注，纯文本
    private Integer categoryId;
    private Integer brandId;

    //关联模型，设置此两个字段不在表内，mybatis-plus在操作时会忽略这两个字段，这两个字段仅用于程序员手动逻辑处理
    //也可在@TableName这个注解内通过excludeProperty来声明
    @TableField(exist = false)
    private Category category;//商品类别
    @TableField(exist = false)
    private Brand brand;//商品品牌
}
