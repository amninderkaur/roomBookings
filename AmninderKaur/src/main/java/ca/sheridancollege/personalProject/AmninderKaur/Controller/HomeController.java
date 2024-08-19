package ca.sheridancollege.personalProject.AmninderKaur.Controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.sheridancollege.personalProject.AmninderKaur.Beans.*;
import ca.sheridancollege.personalProject.AmninderKaur.Database.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    @Lazy
    private DatabaseAccess da;
    

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("rooms", da.getAllRooms());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/permission-denied")
	public String permissionDenied() {
	return "/error/permission-denied";
	}
    
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    

    @PostMapping("/signup")
    public String postRegister(@RequestParam String email, @RequestParam String name,
                               @RequestParam String username, @RequestParam String password,
                               Model model) {
        da.addUser(email, name, username, password);
        Long userId = da.findUserAccount(username).getUserId();
        da.addRole(userId, Long.valueOf(1)); 
        return "login";
    }


//user page 
    @GetMapping("/user")
    public String userIndex(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username"); 
        model.addAttribute("username", username);
        model.addAttribute("rooms", da.getAllRooms());
        return "user/index";
    }
    
    @GetMapping("/user/book/{id}")
    public String bookingForm(Model model, @PathVariable long id,HttpSession session) {
        String username = (String) session.getAttribute("username");
        long userId = da.getUserIdByUsername(username);
        String roomType = da.getRoomType(id);
        double roomPrice = da.getRoomPrice(id);
        model.addAttribute("roomId", id);
        model.addAttribute("username", username);
        model.addAttribute("userId", userId);
        model.addAttribute("roomType", roomType);
        model.addAttribute("roomPrice", roomPrice);
        System.out.println("Room ID: " + id);
        System.out.println("Room Type: " + roomType);
        System.out.println("Room Price: " + roomPrice);
        return "user/bookingForm";
    }

    @PostMapping("/user/bookRoom")
    public String bookRoom(@RequestParam Long roomId,
    		@RequestParam("checkInDate") LocalDate checkInDate, 
                           @RequestParam("checkOutDate") LocalDate checkOutDate, 
                           Model model, 
                           HttpSession session) {
        String username = (String) session.getAttribute("username");
        long userId = da.getUserIdByUsername(username);
        String roomType = da.getRoomType(roomId);
        double roomPrice = da.getRoomPrice(roomId);
        model.addAttribute("username", username);
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomType", roomType);
        model.addAttribute("roomPrice", roomPrice);
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        da.addBooking(userId, roomId, roomPrice, checkInDate, checkOutDate);
        model.addAttribute("rooms", da.getAllRooms());
        return "user/index";
    }

    
    @GetMapping("/user/bookingFormEdit/{id}")
    public String editBookingById(Model model, @PathVariable Long id, HttpSession session) {
    	Booking booking = da.getBookingsByBookingrId(id).get(0); 
        long userId = booking.getUserId();
        long roomId = booking.getRoomId();
        String roomType = da.getRoomType(roomId);
        Double price = booking.getPrice();
        String username = (String) session.getAttribute("username");
        model.addAttribute("userId", userId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomType", roomType);
        model.addAttribute("roomPrice", price);
        model.addAttribute("username", username);
        model.addAttribute("booking", booking);
        da.deleteBooking(id);
        return "user/bookingForm";
    }
    
    
    @GetMapping("/user/deleteBookingById/{id}")
    public String deleteBookingById(@PathVariable("id") long id, HttpSession session,Model model) {
        da.deleteBooking(id);
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("rooms", da.getAllRooms());
        return "user/index";
    }

    @GetMapping("/user/booking")
    public String bookingUser(Model model, HttpSession session) {
    	String username = (String) session.getAttribute("username");
    	List<BookingDto> bookings = da.getAllBookingsByUserId(da.getUserIdByUsername(username)); 
        model.addAttribute("username", username);
        model.addAttribute("bookings", bookings);
        return "user/userBooking"; 
    }
    //admin page 
    @GetMapping("/admin")
    public String adminIndex(Model model, HttpSession session) {
        model.addAttribute("rooms", da.getAllRooms());
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        
        return "admin/index";
    }

    @GetMapping("/admin/bookings")
    public String bookingAdmin(Model model, HttpSession session) {
    	List<BookingDto> bookings = da.getAllBookings();
         String username = (String) session.getAttribute("username");
         double totalPrice = da.getTotalPriceOfBookings();
         model.addAttribute("totalPrice", totalPrice);
         model.addAttribute("username", username);
         model.addAttribute("bookings", bookings);
        return "admin/booking";
    }

    @GetMapping("/admin/allUsers")
    public String allUsers(Model model, HttpSession session) {
        model.addAttribute("users", da.getUserList());
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "admin/userList";
    }

    @GetMapping("/admin/roomForm")
    public String showRoomForm(Model model, HttpSession session) {
        model.addAttribute("room", new Room());
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "admin/roomForm";
    }

    @PostMapping("/admin/addRoom")
    public String addRoom(Model model, @ModelAttribute Room room, HttpSession session ) {
    	String username = (String) session.getAttribute("username");
    	model.addAttribute("username", username);
    	List<Room> existingRoom = da.getRoomListByRoomId(room.getRoomId());
        if (existingRoom.isEmpty()) {
            da.addRoom(room);
        } else {
            da.updateRoom(room);
        }
        model.addAttribute("rooms", da.getAllRooms());
        model.addAttribute("room", new Room());
        return "admin/index";
    }
    
    @GetMapping("/admin/deleteRoomById/{roomId}")
    public String deleteRoomById(Model model, @PathVariable long roomId, HttpSession session) {
    	da.deleteRoom(roomId);
    	String username = (String) session.getAttribute("username");
    	model.addAttribute("username", username);
        model.addAttribute("rooms", da.getAllRooms());
        return "admin/index";
    }
    
    @GetMapping("/admin/editRoomById/{id}")
    public String editRoomById(Model model, @PathVariable("id") Long id, HttpSession session) {
    	String username = (String) session.getAttribute("username");
    	model.addAttribute("username", username);
    	Room room = da.getRoomListByRoomId(id).get(0); 
    	String roomType =room.getRoomType();
    	String description = room.getDescription();
    	Double price = room.getPrice();
    	boolean availability = room.isAvailability();
    	model.addAttribute("roomType", roomType);
    	model.addAttribute("description", description);
    	model.addAttribute("price", price);
    	model.addAttribute("availability", availability);
        model.addAttribute("room", room);  
        return "admin/roomForm"; 
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
