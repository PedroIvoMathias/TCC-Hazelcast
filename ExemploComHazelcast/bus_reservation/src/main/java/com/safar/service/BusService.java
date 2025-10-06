package com.safar.service;

import com.safar.exception.AdminException;
import com.safar.exception.BusException;
import com.safar.model.Bus;

import java.util.List;

public interface BusService {
    public Bus addBus(Bus bus, String key) throws BusException , AdminException;
    public Bus updateBus(Bus bus, String key) throws BusException, AdminException;
    public Bus deleteBus(Long busId, String key) throws BusException, AdminException;

    public Bus viewBus(Long busId) throws BusException;
    public List<Bus> viewBusByBusType(String busType) throws BusException;
    public List<Bus> viewAllBuses() throws BusException;
}
