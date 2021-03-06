package com.mylife.bean.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Document(indexName = SUser.INDEX, replicas = 0)
public class SUser {
    public static final String INDEX = "user";

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    @Field(type = FieldType.Keyword)
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    @Field(type = FieldType.Text)
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobilePhone;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 是否可用：0：否；1：是；默认1；
     */
    private Integer enable;

    /**
     * 数据逻辑状态：0：逻辑删除；1：有效数据；默认1；
     */
    private Integer status;

    /**
     * 更新用户id
     */
    private Long updateUserId;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

}
