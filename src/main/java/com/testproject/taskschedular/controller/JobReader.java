package com.testproject.taskschedular.controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.testproject.taskschedular.pojo.Job;
import lombok.Data;

@Data
public class JobReader {
	@JsonProperty("jobs")
	Map<String, List<Job>> jobMap;

}

