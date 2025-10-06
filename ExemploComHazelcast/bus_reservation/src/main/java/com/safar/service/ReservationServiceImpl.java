
package com.safar.service;

import com.hazelcast.collection.IQueue;
import com.hazelcast.map.IMap;
import com.safar.exception.ReservationException;
import com.safar.model.*;
import com.safar.repository.*;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private BusRepository busRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private IMap<Long, Reservation> reservationCache;
    
    private final IQueue<Reservation> reservationQueue;
    
    @Autowired
    public ReservationServiceImpl(IQueue<Reservation> reservationQueue) {
        this.reservationQueue = reservationQueue;
    }
    

    @Override
    public synchronized Reservation addReservation(ReservationDTO dto, String key) throws ReservationException {
        Bus bus = busRepository.findByRouteFromAndRouteTo(dto.getSource(), dto.getDestination());
        if (bus == null) throw new ReservationException("Bus not found");

        if (bus.getAvailableSeats() < dto.getBookedSeat()) throw new ReservationException("Only " + bus.getAvailableSeats() + " seats are available");

        if (dto.getJourneyDate().isBefore(LocalDate.now())) {
            throw new ReservationException("Journey Date should be in the future");
        }

        
        bus.setAvailableSeats(bus.getAvailableSeats() - dto.getBookedSeat());
        busRepository.save(bus);

        Reservation reservation = new Reservation();
        reservation.setSource(dto.getSource());
        reservation.setDestination(dto.getDestination());
        reservation.setJourneyDate(dto.getJourneyDate());
        reservation.setBookedSeat(dto.getBookedSeat());
        reservation.setBus(bus);
        reservation.setFare(bus.getFare() * dto.getBookedSeat());
        
        return reservationRepository.save(reservation);
    }

    
    public void addToReservationQueue(Reservation reservation) {
    	reservationQueue.offer(reservation);
    }
    
    @Scheduled(fixedRate = 5000)
    public void processReservations() {
        while (!reservationQueue.isEmpty()) {
            try {
                Reservation reservation = reservationQueue.poll();
                if (reservation != null) {
                    System.out.println("Processando reserva: " + reservation.getReservationID());
                    reservationRepository.save(reservation);
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar reserva: " + e.getMessage());
            }
        }
    }


 
    @Override
    public Reservation viewReservation(Long rid, String key) throws ReservationException {
    	
    	CurrentAdminSession currentAdminSession = currentAdminSessionRepository.findByaid(key);
    	
    	
    	if(currentAdminSession == null) throw new ReservationException("Invalid admin login key");
    	
    	 Reservation cachedReservation = reservationCache.get(rid);
    	    if (cachedReservation != null) {
    	        return cachedReservation;
    	    }
    	
        Optional<Reservation> optional = reservationRepository.findById(rid);

        if(optional.isEmpty()) throw new ReservationException("Reservation with given id is not found");

        Reservation reservation = optional.get();
        
        reservationCache.put(rid, reservation);
        
        return reservation;
    }

    @Override
    public List<Reservation> getAllReservation(String key) throws ReservationException {

        CurrentAdminSession session = currentAdminSessionRepository.findByaid(key);

        if(session == null) throw new ReservationException("Please provide valid admin login key");

        List<Reservation> list = reservationRepository.findAll();

        if(list.isEmpty()) throw new ReservationException("Reservation Not found");

        return list;
    }

    @Override
    public List<Reservation> viewReservationByUserId(Long uid, String key) throws ReservationException {
    	
    	CurrentAdminSession currentAdminSession = currentAdminSessionRepository.findByaid(key);
    	
    	CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
    	
    	if(currentAdminSession == null && currentUserSession == null) throw new ReservationException("Invalid login key");
    	
    	
    	Optional<User> optional = userRepository.findById(uid);
    	
    	if(optional.isEmpty()) throw new ReservationException("User not find with given user id: " + uid);

    	User user = optional.get();
    	
    	List<Reservation> reservations = user.getReservationList();
    	
    	if(reservations.isEmpty()) throw new ReservationException("Reservation not found for this user");

        return reservations;
    }

    @Override
    public Reservation deleteReservation(Long rid, String key) throws ReservationException{
    	
    	CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
    	
    	if(currentUserSession == null) throw new ReservationException("Invalid session key"); 
    	
    	Optional<Reservation> optional =  reservationRepository.findById(rid);  
    	
    	if(optional.isEmpty()) throw new ReservationException("Reservation not found with the given id: " + rid);
    	
    	Reservation reservation = optional.get();
    	
    	if(reservation.getJourneyDate().isBefore(LocalDate.now())) throw new ReservationException("Reservation Already Expired");
    	
    	Integer n = reservation.getBus().getAvailableSeats();
    	
    	reservation.getBus().setAvailableSeats(n + reservation.getBookedSeat());
    	
    	Bus bus = reservation.getBus();
    	
    	busRepository.save(bus);    	
        reservationRepository.delete(reservation);
        
        return reservation;
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long rid, ReservationDTO dto, String key) throws ReservationException {
        CurrentUserSession userSession = currentUserSessionRepository.findByUuid(key);
        if (userSession == null) throw new ReservationException("Invalid Session key for user");

        Optional<Reservation> optional = reservationRepository.findById(rid);
        if (optional.isEmpty()) throw new ReservationException("Reservation not found with the given id: " + rid);

        Reservation reservation = optional.get();
        Bus oldBus = reservation.getBus();

        if (oldBus != null) {
            oldBus.setAvailableSeats(oldBus.getAvailableSeats() + reservation.getBookedSeat());
            busRepository.save(oldBus);
        }

        Bus newBus = busRepository.findByRouteFromAndRouteTo(dto.getSource(), dto.getDestination());
        if (newBus == null) throw new ReservationException("Bus not found for the given route");

        if (newBus.getAvailableSeats() < dto.getBookedSeat()) throw new ReservationException("Only " + newBus.getAvailableSeats() + " seats are available");

        newBus.setAvailableSeats(newBus.getAvailableSeats() - dto.getBookedSeat());
        busRepository.save(newBus);

        reservation.setSource(dto.getSource());
        reservation.setDestination(dto.getDestination());
        reservation.setJourneyDate(dto.getJourneyDate());
        reservation.setBookedSeat(dto.getBookedSeat());
        reservation.setBus(newBus);
        reservation.setFare(newBus.getFare() * dto.getBookedSeat());
        reservation.setTime(LocalTime.now());

        return reservationRepository.save(reservation);
    }



}
