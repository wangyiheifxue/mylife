package com.mylife.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wyh
 * @since 2020-08-20
 */
@Data
public class TUser extends Model<TUser> {

    private static final long serialVersionUID = 2899824301520106139L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
