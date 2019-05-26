package com.qc.yyzh.mapper;

import org.apache.ibatis.annotations.Mapper;
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
}
