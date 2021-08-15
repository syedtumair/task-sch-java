package com.testproject.taskschedular.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.testproject.taskschedular.pojo.Job;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JobScheular {

	static List<Job> highList = new ArrayList<>();
	static List<Job> mediumList = new ArrayList<>();
	static List<Job> lowList = new ArrayList<>();

	public static List<Job> jobList = new ArrayList<>();

	@PostConstruct
	public void init() {
		Job j1 = new Job("job1", "low", "/Users/rizwan/JobSchedular/src/main/java/com/example/jobschedular/TestJar.jar",
				"TestClass");
		Job j2 = new Job("job2", "Medium",
				"/Users/rizwan/JobSchedular/src/main/java/com/example/jobschedular/TestJar1.jar", "TestClass1");
		Job j3 = new Job("job3", "high",
				"/Users/rizwan/JobSchedular/src/main/java/com/example/jobschedular/TestJar2.jar", "TestClass2");
		jobList.add(j1);
		jobList.add(j2);
		jobList.add(j3);

		for (Job job : jobList) {
			if (job.getPriority().equalsIgnoreCase("high")) {
				highList.add(job);
			} else if (job.getPriority().equalsIgnoreCase("medium")) {
				mediumList.add(job);
			} else if (job.getPriority().equalsIgnoreCase("low")) {
				lowList.add(job);
			} else {
				System.out.println("Invalid Priority");
			}
		}
	}

	public static void main(String args[]) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InterruptedException, MalformedURLException {
		// Run All Jobs
		Job j1 = new Job("job1", "low", "/Users/rizwan/JobSchedular/src/main/java/com/example/jobschedular/TestJar.jar",
				"TestClass");
		Job j2 = new Job("job2", "Medium",
				"/Users/rizwan/JobSchedular/src/main/java/com/example/jobschedular/TestJar1.jar", "TestClass1");
		Job j3 = new Job("job3", "high",
				"/Users/rizwan/JobSchedular/src/main/java/com/example/jobschedular/TestJar2.jar", "TestClass2");

		jobList.add(j1);
		jobList.add(j2);
		jobList.add(j3);

		for (Job job : jobList) {
			if (job.getPriority().equalsIgnoreCase("high")) {
				highList.add(job);
			} else if (job.getPriority().equalsIgnoreCase("medium")) {
				mediumList.add(job);
			} else if (job.getPriority().equalsIgnoreCase("low")) {
				lowList.add(job);
			} else {
				System.out.println("Invalid Priority");
			}
		}

		for (Job job : highList) {
			runJar(job.getJobActionPath(), job.getJobClassName(), null);
		}

		for (Job job : mediumList) {
			runJar(job.getJobActionPath(), job.getJobClassName(), null);
		}

		for (Job job : lowList) {
			runJar(job.getJobActionPath(), job.getJobClassName(), null);
		}

	}

	public static JobReader getAllJob() {
		JobReader jobReader = new JobReader();
		Map<String, List<Job>> priorityMap = new HashedMap();
		priorityMap.put("high", highList);
		priorityMap.put("low", lowList);
		priorityMap.put("medium", mediumList);
		jobReader.setJobMap(priorityMap);
		return jobReader;

	}

	public static void runJar(String path, String className, MultipartFile mpfile) {
		try {
			File f = new File(path);
			if (mpfile != null) {
				FileUtils.copyInputStreamToFile(mpfile.getInputStream(), f);
			}
			ClassLoader cl;
			cl = new URLClassLoader(new URL[] { f.toURI().toURL() }, null);
			final Class<?> claxx = cl.loadClass(className);
			final Method main = claxx.getMethod("main", String[].class);
			Thread th = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						main.invoke(claxx, new Object[] { new String[] { "Param1", "Param2" } });
					} catch (Throwable th) {
						th.printStackTrace();
					}
				}
			});
			th.setContextClassLoader(cl);
			th.start();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void addJob(Job job) {
		if (job.getPriority().equalsIgnoreCase("high")) {
			highList.add(job);
		} else if (job.getPriority().equalsIgnoreCase("medium")) {
			mediumList.add(job);
		} else if (job.getPriority().equalsIgnoreCase("low")) {
			lowList.add(job);
		} else {
			System.out.println("Invalid Priority");
		}
	}

}