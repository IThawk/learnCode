package com.ithawk.orm.mybatis.entity;

import com.ithawk.orm.mybatis.constant.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEvent implements Serializable {
    private static final long serialVersionUID = -1840831686851699943L;

    /**
     * 主键
     */
    private int id;

    /**
     *
     */
    private String userId;

    /**
     * 事件描述
     */
    private String eventDes;

    /**
     * 事件类型
     */
    private EventType eventType;


    /**
     * 事件影响
     */
    private String  influence;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 事件开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
