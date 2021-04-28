package com.ithawk.learn.springboot.mapper;

import com.ithawk.learn.springboot.entity.ShareEmailDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ShareEmailDetailMapper {
    @Delete({
        "delete from share_email_detail",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into share_email_detail (id, name, ",
        "creator, create_time, ",
        "email_data, receivers, ",
        "content, subject, ",
        "status, last_update_time, ",
        "send_type)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{creator,jdbcType=VARCHAR}, #{createTime,jdbcType=DATE}, ",
        "#{emailData,jdbcType=VARCHAR}, #{receivers,jdbcType=VARCHAR}, ",
        "#{content,jdbcType=VARCHAR}, #{subject,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=SMALLINT}, #{lastUpdateTime,jdbcType=DATE}, ",
        "#{sendType,jdbcType=VARCHAR})"
    })
    int insert(ShareEmailDetail record);

    int insertSelective(ShareEmailDetail record);

    @Select({
        "select",
        "id, name, creator, create_time, email_data, receivers, content, subject, status, ",
        "last_update_time, send_type",
        "from share_email_detail",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.ithawk.learn.springboot.mapper.ShareEmailDetailMapper.BaseResultMap")
    ShareEmailDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareEmailDetail record);

    @Update({
        "update share_email_detail",
        "set name = #{name,jdbcType=VARCHAR},",
          "creator = #{creator,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=DATE},",
          "email_data = #{emailData,jdbcType=VARCHAR},",
          "receivers = #{receivers,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "subject = #{subject,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=SMALLINT},",
          "last_update_time = #{lastUpdateTime,jdbcType=DATE},",
          "send_type = #{sendType,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ShareEmailDetail record);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM share_email_detail")
    List<ShareEmailDetail> selectAllShareEmailDetail();
}
