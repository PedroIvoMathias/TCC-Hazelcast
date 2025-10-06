package com.safar.service;

import java.util.List;

import com.safar.exception.BusException;
import com.safar.exception.UserException;
import com.safar.exception.FeedBackException;
import com.safar.model.Feedback;


public interface FeedbackService {
	
	
	public Feedback addFeedBack(Feedback feedBack,Long busId,String key) throws BusException, UserException;
	
	public Feedback updateFeedBack(Feedback feedback,String key) throws FeedBackException, UserException;
	
	public Feedback deleteFeedBack(Long feedbackId, String key)throws FeedBackException,UserException;

	public Feedback viewFeedback(Long id) throws FeedBackException;

	public List<Feedback> viewFeedbackAll() throws FeedBackException;
	
}
