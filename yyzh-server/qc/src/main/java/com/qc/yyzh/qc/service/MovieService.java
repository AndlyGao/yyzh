package com.qc.yyzh.qc.service;


import com.qc.yyzh.qc.entity.Movie;
import com.qc.yyzh.qc.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieMapper mapper;

    public List<Movie> getall() {
        return mapper.queryAll();
    }
}
