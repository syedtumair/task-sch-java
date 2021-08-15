package com.testproject.taskschedular.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JobRequest {
	private MultipartFile file;
	private String title;
	private String description;
	private String driver;
	private String priority;
	private String scheduleAt;
}