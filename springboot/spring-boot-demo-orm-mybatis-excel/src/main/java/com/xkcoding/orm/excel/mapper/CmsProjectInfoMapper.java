package com.xkcoding.orm.excel.mapper;

import com.xkcoding.orm.excel.entity.CmsProjectInfo;
import com.xkcoding.orm.excel.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * User Mapper
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.mapper
 * @description: User Mapper
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 10:54
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Mapper
@Component
public interface CmsProjectInfoMapper {

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM CMS_PROJECT_INFO")
    List<CmsProjectInfo> selectAllCmsProjectInfo();

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM CMS_PROJECT_INFO")
    List<CmsProjectInfo> selectCmsProjectInfo();

    /**
     * 根据id查询用户
     *
     * @param projectcode 主键id
     * @return 当前id的用户，不存在则是 {@code null}
     */
    @Select("SELECT * FROM CMS_PROJECT_INFO WHERE projectcode = #{projectcode}")
    CmsProjectInfo selectCmsProjectInfoByProjectcode(@Param("projectcode") String projectcode);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 成功 - {@code 1} 失败 - {@code 0}
     */
    int saveCmsProjectInfo(@Param("user") CmsProjectInfo user);

    /**
     * 删除用户
     *
     * @param projectcode 主键id
     * @return 成功 - {@code 1} 失败 - {@code 0}
     */
    @Delete("DELETE * FROM FROM CMS_PROJECT_INFO WHERE projectcode = #{projectcode}")
    int deleteByProjectcode(@Param("projectcode") String projectcode);

}
