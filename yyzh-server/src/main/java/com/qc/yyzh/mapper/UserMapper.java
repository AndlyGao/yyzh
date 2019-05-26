package com.qc.yyzh.mapper;

import com.qc.yyzh.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author qc
 * @date 2019/4/26
 */
@Mapper
public interface UserMapper {
    @Options(useGeneratedKeys = true,keyProperty = "userId")
    @Insert("insert into User values(0,#{userName},#{userPassword},#{userEmail},#{userPhone})")
    public void register(User user);
    @Select("select * from User where userName=#{name}")
    public User  login(String name);

}
