package com.toad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toad.entities.Rental;
import com.toad.repositories.RentalRepository;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/toad/rental") // This means URL's start with /film (after Application path)
public class RentalController {
    @Autowired
    private RentalRepository RentalRepository; // Assuming you have a FilmRepository for Film entity

    @PutMapping(path = "/update/{id}") // Map ONLY PUT Requests for updating a film
    public @ResponseBody String updateRental(
            @PathVariable Integer id,
            @RequestParam String rental_date,
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id,
            @RequestParam String return_date,
            @RequestParam Integer staff_id,
            @RequestParam String last_update) {

        String message_retour;
        Rental Rental = RentalRepository.findById(id).orElse(null);
        if (Rental == null) {
            message_retour = "Rental not found";
        } else {
            Rental.setRentalId(id);
            Rental.setRentalDate(rental_date);
            Rental.setInventoryId(inventory_id);
            Rental.setCustomerId(customer_id);
            Rental.setReturnDate(return_date);
            Rental.setStaffId(staff_id);
            Rental.setLastUpdate(last_update);
            RentalRepository.save(Rental);
            
            message_retour = "Rental Updated";
        }
        return message_retour;
    }

    @PostMapping(path = "/add")
    public @ResponseBody String createRental(
            @RequestParam String rental_date,
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id,
            @RequestParam String return_date,
            @RequestParam Integer staff_id,
            @RequestParam String last_update) {
    
        Rental newRental = new Rental();
        newRental.setRentalDate(rental_date);
        newRental.setInventoryId(inventory_id);
        newRental.setCustomerId(customer_id);
        newRental.setReturnDate(return_date);
        newRental.setStaffId(staff_id);
        newRental.setLastUpdate(last_update);
    
        RentalRepository.save(newRental);
    
        return "Location créée avec succès !";
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteFilm(@PathVariable Integer id) {
        RentalRepository.deleteById(id);
        return "Location Supprimée";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Rental> getAllUsers() {
        return RentalRepository.findAll();
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Rental getRentalById(@RequestParam Integer id) {
        Rental rental = RentalRepository.findById(id).orElse(null);
        if (rental != null) {
            Rental filteredRental = new Rental();
            filteredRental.setRentalId(rental.getRentalId());
            filteredRental.setRentalDate(rental.getRentalDate());
            filteredRental.setInventoryId(rental.getInventoryId());
            filteredRental.setCustomerId(rental.getCustomerId());
            filteredRental.setReturnDate(rental.getReturnDate());
            filteredRental.setStaffId(rental.getStaffId());
            filteredRental.setLastUpdate(rental.getLastUpdate());
            return filteredRental;
        }
        return null;
    }
}
