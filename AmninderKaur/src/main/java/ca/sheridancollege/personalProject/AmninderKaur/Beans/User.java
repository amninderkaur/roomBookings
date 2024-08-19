package ca.sheridancollege.personalProject.AmninderKaur.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private long userId;
	private String username;
    private String name;
    private String email;
    private String encryptedPassword;
    private boolean enabled;
    
}
