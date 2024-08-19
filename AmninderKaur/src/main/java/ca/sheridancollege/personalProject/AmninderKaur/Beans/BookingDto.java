package ca.sheridancollege.personalProject.AmninderKaur.Beans;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
	private Long bookingId;
    private String roomType;
    private String userName;
    private Double price;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
