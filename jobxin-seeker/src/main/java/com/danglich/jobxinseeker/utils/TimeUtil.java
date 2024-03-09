package com.danglich.jobxinseeker.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component("timeUtil")
public class TimeUtil {

	public long daysOfExpired(LocalDateTime expiredAt) {
		
		
		Duration duration = Duration.between(LocalDateTime.now(), expiredAt);
		
		return duration.toDays();
	}
}
