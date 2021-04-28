package com.ithawk.learn.springboot.mapper;

import com.ithawk.learn.springboot.entity.OrmUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrmUserMapper {
    @Delete({
        "delete from orm_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into orm_user (id, name, ",
        "password, salt, ",
        "email, phone_number, ",
        "status, create_time, ",
        "last_login_time, last_update_time)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{salt,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{phoneNumber,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=SMALLINT}, #{createTime,jdbcType=DATE}, ",
        "#{lastLoginTime,jdbcType=DATE}, #{lastUpdateTime,jdbcType=DATE})"
    })
    int insert(OrmUser record);

    int insertSelective(OrmUser record);

    @Select({
        "select",
        "id, name, password, salt, email, phone_number, status, create_time, last_login_time, ",
        "last_update_time",
        "from orm_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.ithawk.learn.springboot.mapper.OrmUserMapper.BaseResultMap")
    OrmUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrmUser record);

    @Update({
        "update orm_user",
        "set name = #{name,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "salt = #{salt,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "phone_number = #{phoneNumber,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=SMALLINT},",
          "create_time = #{createTime,jdbcType=DATE},",
          "last_login_time = #{lastLoginTime,jdbcType=DATE},",
          "last_update_time = #{lastUpdateTime,jdbcType=DATE}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OrmUser record);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM orm_user")
    List<OrmUser> selectAllOrmUser();
}
