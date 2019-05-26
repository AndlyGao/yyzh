package com.qc.yyzh.qc.mapper;


import com.qc.yyzh.qc.entity.Music;
import org.apache.ibatis.annotations.Mapper;

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
