package com.safar.service;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.safar.exception.AdminException;
import com.safar.exception.BusException;
import com.safar.model.Bus;
import com.safar.model.CurrentAdminSession;
import com.safar.model.Route;
import com.safar.repository.BusRepository;
import com.safar.repository.CurrentAdminSessionRepository;
import com.safar.repository.RouteRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BusServiceImpl implements BusService{
    private final BusRepository busRepo;
    private final RouteRepository routeRepo;
    private final CurrentAdminSessionRepository currAdminRepo;
    private final IMap<Long, Bus> busCache;

    // Construtor foi simplificado para remover dependências não mais necessárias
    public BusServiceImpl(BusRepository busRepo, RouteRepository routeRepo,
                          CurrentAdminSessionRepository currAdminRepo, HazelcastInstance hazelcastInstance) {
        this.busRepo = busRepo;
        this.routeRepo = routeRepo;
        this.currAdminRepo = currAdminRepo;
        this.busCache = hazelcastInstance.getMap("busCache");
    }

    @Override
    @Transactional
    public Bus addBus(Bus bus, String key) throws BusException, AdminException {
        CurrentAdminSession admin = currAdminRepo.findByaid(key);
        if (admin == null) {
            throw new AdminException("Key is not valid! Please provide a valid key.");
        }

        // 1. Busca ou cria a Rota associada
        Route route = routeRepo.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());
        if (route == null) {
            if (bus.getRoute() == null || bus.getRoute().getDistance() == null) {
                throw new BusException("Route details (including distance) must be provided for a new route.");
            }
            // Cria uma nova instância de Rota se ela não existir
            route = new Route(bus.getRouteFrom(), bus.getRouteTo(), bus.getRoute().getDistance());
        }

        // 2. Associa a rota ao ônibus
        bus.setRoute(route);

        // 3. Salva o ônibus. O JPA cuidará de persistir a nova rota, se for o caso, devido à cascata.
        Bus savedBus = busRepo.save(bus);

        // 4. Atualiza o cache diretamente após salvar no banco
        busCache.put(savedBus.getBusId(), savedBus);

        return savedBus;
    }

    @Override
    @Transactional
    public Bus updateBus(Bus busUpdates, String key) throws BusException, AdminException {
        CurrentAdminSession admin = currAdminRepo.findByaid(key);
        if (admin == null) {
            throw new AdminException("Key is not valid! Please provide a valid key.");
        }

        
        Bus existingBus = busRepo.findById(busUpdates.getBusId())
                .orElseThrow(() -> new BusException("Bus doesn't exist with busId: " + busUpdates.getBusId()));

        
        existingBus.setBusName(busUpdates.getBusName());
        existingBus.setDriverName(busUpdates.getDriverName());
        existingBus.setBusType(busUpdates.getBusType());
        existingBus.setRouteFrom(busUpdates.getRouteFrom());
        existingBus.setRouteTo(busUpdates.getRouteTo());
        existingBus.setBusJourneyDate(busUpdates.getBusJourneyDate());
        existingBus.setArrivalTime(busUpdates.getArrivalTime());
        existingBus.setDepartureTime(busUpdates.getDepartureTime());
        existingBus.setSeats(busUpdates.getSeats());
        existingBus.setAvailableSeats(busUpdates.getAvailableSeats());
        existingBus.setFare(busUpdates.getFare());

        
        Route route = routeRepo.findByRouteFromAndRouteTo(busUpdates.getRouteFrom(), busUpdates.getRouteTo());
        if (route == null) {
            if (busUpdates.getRoute() == null || busUpdates.getRoute().getDistance() == null) {
                throw new BusException("Route details (including distance) must be provided for a new route.");
            }
            route = new Route(busUpdates.getRouteFrom(), busUpdates.getRouteTo(), busUpdates.getRoute().getDistance());
        }

        
        existingBus.setRoute(route);

        
        Bus updatedBus = busRepo.save(existingBus);

        
        busCache.put(updatedBus.getBusId(), updatedBus);

        return updatedBus;
    }

    @Override
    @Transactional
    public Bus deleteBus(Long busId, String key) throws BusException, AdminException {
        CurrentAdminSession admin = currAdminRepo.findByaid(key);
        if (admin == null) {
            throw new AdminException("Key is not valid! Please provide a valid key.");
        }

        Bus existingBus = busRepo.findById(busId)
                .orElseThrow(() -> new BusException("Bus not found with busId: " + busId));

        if (LocalDate.now().isBefore(existingBus.getBusJourneyDate()) && !existingBus.getAvailableSeats().equals(existingBus.getSeats())) {
            throw new BusException("Can't delete scheduled and reserved bus.");
        }

        busRepo.delete(existingBus);
        busCache.remove(busId); 
        return existingBus;
    }

    @Override
    public List<Bus> viewAllBuses() throws BusException {
        List<Bus> busList = busRepo.findAll();
        if (busList.isEmpty()) {
            throw new BusException("No bus found at this moment. Try again later!");
        }
        return busList;
    }

    @Override
    public Bus viewBus(Long busId) throws BusException {
        
        return Optional.ofNullable(busCache.get(busId)) 
                .or(() -> busRepo.findById(busId).map(bus -> { 
                    busCache.put(busId, bus); 
                    return bus;
                }))
                .orElseThrow(() -> new BusException("No bus exists with this busId: " + busId));
    }

    @Override
    public List<Bus> viewBusByBusType(String busType) throws BusException {
        List<Bus> busListType = busRepo.findByBusType(busType);
        if (busListType.isEmpty()) {
            throw new BusException("There are no buses with bus type: " + busType);
        }
        return busListType;
    }

}
