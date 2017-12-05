package com.bailian.persistence;

import com.bailian.entity.User;
import com.bailian.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    User selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_xtgl_user
     *
     * @mbggenerated Thu Jun 01 20:45:49 CST 2017
     */
    int updateByPrimaryKey(User record);
}