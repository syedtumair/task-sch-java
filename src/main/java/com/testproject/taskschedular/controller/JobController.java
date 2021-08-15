package com.testproject.taskschedular.controller;

import com.testproject.taskschedular.pojo.Job;
import com.testproject.taskschedular.pojo.JobRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@SuppressWarnings({ "unchecked", "rawtypes" })
public class JobController {

	@Autowired
	private TaskSchedularDriver schedular;

	@PostMapping("/add-job")
	public ResponseEntity<?> multiUploadFileModel(@ModelAttribute JobRequest request) {
		System.out.println("JobRequest: " + request);
		JobScheular.addJob(new Job(request.getTitle(), request.getPriority(), "user uploaded ", request.getDriver()));
		if(StringUtils.isEmpty(request.getScheduleAt()))
			JobScheular.runJar("C:\\Users\\ali\\Desktop\\TestJar1.jar",       // put any dummy path here.
					request.getDriver(), request.getFile());
		else
			schedular.scheduleTask(request.getFile(),request.getDriver(),getDate(request.getScheduleAt()));
		return new ResponseEntity("Successfully added!", HttpStatus.OK);
	}

	@GetMapping("/job-details")
	public ResponseEntity<?> getAllJobDetails() {
		return new ResponseEntity(JobScheular.getAllJob(), HttpStatus.OK);
	}


	private Date getDate(String strDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();
		try {
			date = format.parse(strDate);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
