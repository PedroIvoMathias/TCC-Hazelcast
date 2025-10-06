package com.safar.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.safar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.safar.exception.BusException;
import com.safar.exception.UserException;
import com.safar.model.Bus;
import com.safar.model.CurrentUserSession;
import com.safar.model.User;

import com.safar.repository.BusRepository;
import com.safar.repository.CurrentUserSessionRepository;
import com.safar.repository.UserRepository;

import com.safar.exception.FeedBackException;
import com.safar.model.Feedback;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	 private final FeedbackRepository fdao;
	    private final UserRepository userDao;
	    private final BusRepository busDao;
	    private final CurrentUserSessionRepository userSessionDao;
	    private final IMap<Long, Feedback> feedbackCache;

	    @Autowired
	    public FeedbackServiceImpl(FeedbackRepository fdao,
	                               UserRepository userDao,
	                               BusRepository busDao,
	                               CurrentUserSessionRepository userSessionDao,
	                               HazelcastInstance hazelcastInstance) {
	        this.fdao = fdao;
	        this.userDao = userDao;
	        this.busDao = busDao;
	        this.userSessionDao = userSessionDao;
	        this.feedbackCache = hazelcastInstance.getMap("feedbackCache");
	    }

	@Override
	public Feedback addFeedBack(Feedback feedBack, Long busId, String key) throws BusException, UserException {
		
		CurrentUserSession loggedInUser= userSessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new UserException("Please provide a valid key to give Feedback!");
		}
		
		User user = userDao.findById(loggedInUser.getUserID()).orElseThrow(()-> new UserException("User not found!"));

		Optional<Bus> busOptional = busDao.findById(busId);
		if (busOptional.isEmpty()) {
			throw new BusException("Bus is not present with Id: "+ busId);
		}

		feedBack.setBus(busOptional.get());
		feedBack.setUser(user);
		feedBack.setFeedbackDateTime(LocalDateTime.now());
		Feedback savedFeedback = fdao.save(feedBack);
		feedbackCache.put(savedFeedback.getFeedBackId(), savedFeedback);

		return savedFeedback;
	}

	@Override
	public Feedback updateFeedBack(Feedback feedback,String key) throws FeedBackException, UserException {
		
		CurrentUserSession loggedInUser= userSessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new UserException("Please provide a valid key to update Feedback!");
		}
		
		User user = userDao.findById(loggedInUser.getUserID()).orElseThrow(()-> new UserException("User not found!"));

		Feedback existingFeedback = fdao.findById(feedback.getFeedBackId())
                .orElseThrow(() -> new FeedBackException("No feedback found!"));

        Bus bus = busDao.findById(existingFeedback.getBus().getBusId())
                .orElseThrow(() -> new FeedBackException("Invalid bus details!"));

        feedback.setBus(bus);
        feedback.setUser(user);
        user.getFeedbackList().add(feedback);
        feedback.setFeedbackDateTime(LocalDateTime.now());

        Feedback updatedFeedback = fdao.save(feedback);
        feedbackCache.put(updatedFeedback.getFeedBackId(), updatedFeedback);

        return updatedFeedback;
	}

	@Override
	public Feedback viewFeedback(Long id) throws FeedBackException {
		Feedback cachedFeedback = feedbackCache.get(id);
	    	if (cachedFeedback != null) {
	            return cachedFeedback;
	        }
		Feedback feedback = fdao.findById(id).orElseThrow(() -> new FeedBackException("No feedback found!"));
		feedbackCache.put(id, feedback);
		return feedback;
	}

	@Override
	public List<Feedback> viewFeedbackAll() throws FeedBackException {
		List<Feedback> allFeedbacks = fdao.findAll();
        if (allFeedbacks.isEmpty()) throw new FeedBackException("No feedbacks found!");

        allFeedbacks.forEach(f -> feedbackCache.put(f.getFeedBackId(), f));

        return allFeedbacks;
	}

	@Override
	public Feedback deleteFeedBack(Long feedbackId, String key) throws FeedBackException, UserException {
        CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);
        if (loggedInUser == null) throw new UserException("Please provide a valid key to update Feedback!");

        userDao.findById(loggedInUser.getUserID()).orElseThrow(() -> new UserException("User not found!"));

        Feedback feedback = fdao.findById(feedbackId).orElseThrow(() -> new FeedBackException("No feedback found!"));

        fdao.delete(feedback);
        feedbackCache.remove(feedbackId);

        return feedback;
	}
}
