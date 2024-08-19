package ca.sheridancollege.personalProject.AmninderKaur.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ca.sheridancollege.personalProject.AmninderKaur.Database.*;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
@Autowired
@Lazy
private DatabaseAccess da;
@Override
public UserDetails loadUserByUsername (String username) throws
UsernameNotFoundException {
ca.sheridancollege.personalProject.AmninderKaur.Beans.User user = da.findUserAccount(username);
if (user == null) {
System.out.println("User not found:" + username);
throw new UsernameNotFoundException("User " + username + "was not found in the database");
}
List<String> roleNameList = da.getRolesById(user.getUserId());
List<GrantedAuthority> grantList = new ArrayList<>();
if (roleNameList != null) {
for (String role : roleNameList) {
	grantList.add(new SimpleGrantedAuthority(role));
}
}
UserDetails userDetails = new
org.springframework.security.core.userdetails.User(user.getUsername(), user.getEncryptedPassword(),grantList);
return userDetails;
}
}
