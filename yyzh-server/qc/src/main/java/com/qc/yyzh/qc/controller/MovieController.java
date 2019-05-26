package com.qc.yyzh.qc.controller;


import com.qc.yyzh.qc.entity.Movie;
import com.qc.yyzh.qc.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieController {
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/movie/getall")
	public Map<String,Object> getAll(){
		Map<String,Object> map=new HashMap<>();
		List<Movie> abc= movieService.getall();
		map.put("movies",abc);
		return map;
	}
}
