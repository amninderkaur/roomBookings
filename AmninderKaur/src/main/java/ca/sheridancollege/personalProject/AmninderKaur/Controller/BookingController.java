package ca.sheridancollege.personalProject.AmninderKaur.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.sheridancollege.personalProject.AmninderKaur.Beans.Booking;
import ca.sheridancollege.personalProject.AmninderKaur.Database.DatabaseAccess;
import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private DatabaseAccess da;
    
    @GetMapping
	public List<Booking> getBookingCollection() {
	return da.findAll();
	}
    
    @GetMapping(value="/{id}")
    public Booking getIndividualBooking(@PathVariable Long id) {
    	return da.findById(id);
    	}
    
    @PostMapping(consumes = "application/json")
	public String postStudent(@RequestBody Booking Booking) {
	return "http://localhost:8080/api/v1/bookings/" + da.save(Booking);
	}
    
    @PutMapping(consumes = "application/json")
	public String putStudentCollection(@RequestBody List<Booking> bookingList) {
	da.deleteAll();
	da.saveAll(bookingList);
	return "Total Records: " + da.count();
	}
    
    @DeleteMapping
	public void deletedBookingCollection() {
	da.deleteAll();
	}
    
    @DeleteMapping(value = "/{id}")
	public void deletedBookingById(@PathVariable Long id) {
	da.deleteBooking(id);
	}
    
}
