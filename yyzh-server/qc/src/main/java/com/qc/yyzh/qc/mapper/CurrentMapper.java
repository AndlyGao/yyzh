package com.qc.yyzh.qc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author qc
 * @date 2019/4/26
 */
@Mapper
public interface CurrentMapper {
    @Update("update  current set dish=dish+1 where users=1")
    void login();
    @Update("update  current set dish=dish-1 where users=1")
    void logout();
    @Select("select dish from current where users=1")
    int getCurrent();
}
