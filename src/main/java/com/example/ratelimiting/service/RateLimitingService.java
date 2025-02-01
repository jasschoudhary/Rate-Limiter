package com.example.ratelimiting.service;
/*
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.util.Map;

*/

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.example.ratelimiting.config.RateLimitConfig;
@Service
public class RateLimitingService {

	/**
	// Logic for token-bucket algorithm 
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    public boolean allowRequest(String apiKey) {
        Bucket bucket = buckets.computeIfAbsent(apiKey, this::createNewBucket);
        return bucket.tryConsume(1);
    }
    private Bucket createNewBucket(String apiKey) {
        Bandwidth limit = Bandwidth.classic(10, Refill.smooth(10, Duration.ofMinutes(1)));
        return Bucket4j.builder().addLimit(limit).build();
    }
    */
	
	
	// Logic for Fixed window Counter algorithm 
	
	private final RateLimitConfig rateLimitConfig ;
	
	private final ConcurrentHashMap<String,RequestCounter> requestCounters = new ConcurrentHashMap<>() ;

	public RateLimitingService(RateLimitConfig rateLimitConfig) {
		this.rateLimitConfig = rateLimitConfig;
	}
    
    public boolean allowRequest(String apiKey) {
    	
    	int maxRequests = rateLimitConfig.getKeys().getOrDefault(apiKey, rateLimitConfig.getDefaults().getMaxRequests());
    	int windowDuration = rateLimitConfig.getDefaults().getWindowDurationSeconds() ;
    	
    	RequestCounter counter = requestCounters.compute(apiKey, (key,existingCounter) ->{
    		long currentTime  = System.currentTimeMillis() ;
	    		if(existingCounter == null || currentTime - existingCounter.getWindowStart() > TimeUnit.SECONDS.toMillis(windowDuration)) {
	    				// New window 
	    			System.out.println("New window for "+apiKey+" at "+currentTime);	
	    			return new RequestCounter(currentTime, 0) ;
	    				
	    		}else if (existingCounter.getCount() >= maxRequests){
	    			return existingCounter ;
	    			
	    		}
	    		else {
	    			// Increment count in current window 
	    			System.out.println("Increamenting "+ apiKey + " to " + existingCounter.getCount()+1);
	    			return new RequestCounter(existingCounter.getWindowStart(), existingCounter.getCount()+1) ;
	    		}
    	}) ;
    	System.out.println("current count for "+apiKey +" : " +counter.getCount());
    	return counter.getCount() < maxRequests ;  
    }
    
    
    private static class RequestCounter {
    	
    	private final long windowStart ;
    	private final int count ;
    	
		public RequestCounter(long windowStart, int count) {
			this.windowStart = windowStart;
			this.count = count;
		}

		public long getWindowStart() {
			return windowStart;
		}

		public int getCount() {
			return count;
		} 
	
    
}
    }



