package com.safar.controller;

import com.safar.exception.AdminException;
import com.safar.exception.BusException;
import com.safar.model.Bus;
import com.safar.service.BusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safar")
public class BusController {

    @Autowired
    private BusService busServ;


    @PostMapping("/admin/bus/add")
    public ResponseEntity<Bus> addBusHandler(@Valid @RequestBody Bus bus,@RequestParam(required = false) String key)throws BusException , AdminException {

        Bus newBus = busServ.addBus(bus,key);
        return new ResponseEntity<>(newBus, HttpStatus.CREATED);


    }

    @PutMapping("/admin/bus/update")
    public ResponseEntity<Bus> updateBusHandler(@Valid @RequestBody Bus bus,@RequestParam(required = false) String key) throws BusException, AdminException{
        Bus newBus = busServ.updateBus(bus,key);
        return new ResponseEntity<>(newBus,HttpStatus.OK);
    }



    @DeleteMapping("/admin/bus/delete/{busId}")
    public ResponseEntity<Bus> deleteBusByBusIdHandler(@PathVariable("busId") Long busId,@RequestParam(required = false) String key) throws BusException, AdminException{
        Bus deletedBus = busServ.deleteBus(busId,key);
        return new ResponseEntity<>(deletedBus,HttpStatus.OK);
    }

    @GetMapping("/bus/all")
    public ResponseEntity<List<Bus>> getAllBusesHandler()throws BusException{
        List<Bus> allBuses = busServ.viewAllBuses();
        return new ResponseEntity<>(allBuses,HttpStatus.OK);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<Bus> getBusByIdHandler(@PathVariable("busId") Long busId) throws BusException{
        Bus bus = busServ.viewBus(busId);
        return new ResponseEntity<>(bus,HttpStatus.OK);
    }

    @GetMapping("/bus/type/{busType}")
    public ResponseEntity<List<Bus>> getBusesByBusTypeHandler(@PathVariable("busType") String busType) throws BusException{
        List<Bus> busList = busServ.viewBusByBusType(busType);
        return new ResponseEntity<>(busList,HttpStatus.OK);
    }
}
