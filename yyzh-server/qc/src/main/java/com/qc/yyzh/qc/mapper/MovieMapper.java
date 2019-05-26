package com.qc.yyzh.qc.mapper;


import com.qc.yyzh.qc.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MovieMapper {

    List<Movie> queryAll();
}