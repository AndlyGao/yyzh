package com.qc.yyzh.qc.mapper;

import com.qc.yyzh.qc.entity.Up;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author qc
 * @date 2019/4/28
 */
@Mapper
public interface UpMapper {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into up values(0,#{text})")
    void insert(Up up);
}
