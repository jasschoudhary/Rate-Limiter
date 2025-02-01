package com.example.ratelimiting.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "rate-limit") 
public class RateLimitConfig {
	
	private Default defaults = new Default(); 
	private Map<String,Integer> keys = new HashMap<>() ;
	
	
	
	public static class  Default {
	
			private int maxRequests  ;
		    private int windowDurationSeconds ;
		    
		    
			public int getMaxRequests() {
				return maxRequests;
			}
			public void setMaxRequests(int maxRequests) {
				this.maxRequests = maxRequests;
			}
			public int getWindowDurationSeconds() {
				return windowDurationSeconds;
			}
			public void setWindowDurationSeconds(int windowDurationSeconds) {
				this.windowDurationSeconds = windowDurationSeconds;
			}
		    
		   
		   
		
		
	}
	
	
		// Getters and Setters 
	
		public Default getDefaults() {
			return defaults;
		}
		public void setDefaults(Default defaults) {
			 System.out.println("[DEBUG] Loaded defaults: maxRequests=" + defaults.getMaxRequests() 
             + ", windowDuration=" + defaults.getWindowDurationSeconds());
			this.defaults = defaults;
		}
		public Map<String, Integer> getKeys() {
			return keys;
		}
		public void setKeys(Map<String, Integer> keys) {
			System.out.println("[DEBUG] loaded custom keys : "+ keys);
			this.keys = keys;
		}
		
	
	

}
