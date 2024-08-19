package ca.sheridancollege.personalProject.AmninderKaur.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
	private long roomId; 
    private String roomType;
    private double price;
    private boolean availability;
    private String description;
}
