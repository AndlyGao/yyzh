package com.qc.yyzh.mapper;

import com.qc.yyzh.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qc
 * @date 2019/4/26
 */
@Mapper
public interface MusicMapper {


    List<Music> querybyType(int type);

    List<Music> querybyName(String name);

}
