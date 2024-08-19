package ca.sheridancollege.personalProject.AmninderKaur.Beans;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	
	private long bookingId;
    private long userId;  
    private long roomId;
    private double price; 
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
}
