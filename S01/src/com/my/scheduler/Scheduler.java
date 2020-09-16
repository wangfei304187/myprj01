package com.my.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@EnableScheduling
@Component
public class Scheduler {

	@Scheduled(fixedRate = 10000)
//	@Scheduled(fixedDelay = 1000, initialDelay = 3000)
	public void fixedRateSch() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		System.out.println("Fixed reate scheduler:: " + sdf.format(new Date()));
	}
}
