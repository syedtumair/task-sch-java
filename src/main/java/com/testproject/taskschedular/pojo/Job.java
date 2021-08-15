package com.testproject.taskschedular.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Date;

@Getter
public class Job {

	String name;

	@JsonIgnore
	Date timeTrigger;

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
	Date timeCreation;

	@JsonIgnore
	String jobActionPath;

	String priority;

	@JsonIgnore
	String jobClassName;

	public Job(String name, String priority, String jobActionPath, String jobClassName) {
		timeCreation = new Date();
		this.name = name;
		this.priority = priority;
		this.jobActionPath = jobActionPath;
		this.jobClassName = jobClassName;
	}

}
