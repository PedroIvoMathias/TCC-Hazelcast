package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.map.IMap;
import com.safar.model.Feedback;

@Service
public class FeedbackCache {

	
	 @Autowired
	 private IMap<Long, Feedback> busCache;
	 
	 public Feedback getFeedbackCache(Long id) {
	        return busCache.get(id);
	    }

	    public void addFeedbackCache(Feedback feedback) {
	        if (feedback != null && feedback.getFeedBackId() != null) {
	            busCache.put(feedback.getFeedBackId(), feedback);
	        }
	    }

	    public void removeFeedbackCache(Long id) {
	        if (id != null) {
	            busCache.remove(id);
	        }
	    }

	    public boolean hasFeedbackCache(Long id) {
	        return busCache.containsKey(id);
	    }
}
