package ca.sheridancollege.personalProject.AmninderKaur.Database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.personalProject.AmninderKaur.Beans.*;

@Repository
public class DatabaseAccess {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // User
    public void addUser(String email, String name, String username, String password) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO users (email, name, username, encryptedPassword, enabled) " +
                       "VALUES (:email, :name, :username, :encryptedPassword, 1)";
        namedParameters.addValue("email", email);
        namedParameters.addValue("name", name);
        namedParameters.addValue("username", username);
        namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));
        jdbc.update(query, namedParameters);
    }
    
    public List<String> getRolesById(long userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT roles.roleName " +
                       "FROM user_roles " +
                       "JOIN roles ON user_roles.roleId = roles.roleId " +
                       "WHERE user_roles.userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters, String.class);
    }


    public void addRole(long userId, long roleId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_roles (userId, roleId) " +
                       "VALUES (:userId, :roleId)";
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("roleId", roleId);
        jdbc.update(query, namedParameters);
    }

    public User findUserAccount(String username) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM users WHERE username = :username";
        namedParameters.addValue("username", username);
        try {
            return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    public List<String> getRolesByUserId(long userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT roles.roleName " +
                       "FROM user_roles " +
                       "JOIN roles ON user_roles.roleId = roles.roleId " +
                       "WHERE user_roles.userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters, String.class);
    }
    
    public List<User> getUserList() {
        String query = "SELECT userId, name, username, email FROM users";
        return jdbc.query(query, new BeanPropertyRowMapper<>(User.class));
    }
    
    public long getUserIdByUsername(String username) {
        String sql = "SELECT userId FROM users WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        
        return jdbc.queryForObject(sql, params, long.class);
    }

    
    // Room
    public void addRoom(Room room) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("roomType", room.getRoomType());
        namedParameters.addValue("description", room.getDescription());
        namedParameters.addValue("price", room.getPrice());
        namedParameters.addValue("availability", room.isAvailability());
        String query = "INSERT INTO room(roomType, description, price, availability) " +
                       "VALUES (:roomType, :description, :price, :availability)";
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0)
            System.out.println("Room inserted into database");
    }
    
    public List<Room> getAllRooms() {
        String query = "SELECT * FROM room";
        return jdbc.query(query, new BeanPropertyRowMapper<>(Room.class));
    }

    public void updateRoom(Room room) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE room SET roomType = :roomType, description = :description, price = :price, availability = :availability " +
                       "WHERE roomId = :roomId";
        namedParameters.addValue("roomId", room.getRoomId());
        namedParameters.addValue("roomType", room.getRoomType());
        namedParameters.addValue("description", room.getDescription());
        namedParameters.addValue("price", room.getPrice());
        namedParameters.addValue("availability", room.isAvailability());

        jdbc.update(query, namedParameters);
    }

    public void deleteRoom(long roomId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM room WHERE roomId = :roomId";
        namedParameters.addValue("roomId", roomId);
        jdbc.update(query, namedParameters);
    }
    
    
    public List<Room> getRoomListByRoomId(long roomId){
    	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("roomId", roomId);
        String query = "SELECT * FROM room WHERE roomId = :roomId";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Room>(Room.class));
    }
    

    public Double getRoomPrice(long roomId) {
        String sql = "SELECT price FROM room WHERE roomId = :roomId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roomId", roomId);
        return jdbc.queryForObject(sql, params, Double.class);
    }

    public String getRoomType(Long roomId) {
        String sql = "SELECT roomType FROM room WHERE roomId = :roomId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roomId", roomId);
        return jdbc.queryForObject(sql, params, String.class);
    }
    
    // Booking
    public void addBooking(Long userId, Long roomId, Double roomPrice, LocalDate checkInDate, LocalDate checkOutDate) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO booking (userId, roomId, price, checkInDate, checkOutDate) " +
                       "VALUES (:userId, :roomId, :price, :checkInDate, :checkOutDate)";
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("roomId", roomId);
        namedParameters.addValue("price", roomPrice);
        namedParameters.addValue("checkInDate", checkInDate);
        namedParameters.addValue("checkOutDate", checkOutDate);
        jdbc.update(query, namedParameters);
    }


    public Double getTotalPriceOfBookings() {
        String query = "SELECT SUM(price) FROM booking";
        try {
            return jdbc.queryForObject(query, new MapSqlParameterSource(), Double.class);
        } catch (EmptyResultDataAccessException e) {
            return 0.0;
        }
    }

    public List<BookingDto> getAllBookings() {
        String query = "SELECT b.bookingId, r.roomType, u.name, b.price, b.checkInDate, b.checkOutDate "
                     + "FROM booking b "
                     + "LEFT JOIN room r ON r.roomId = b.roomId "
                     + "JOIN users u ON u.userId = b.userId";
        List<BookingDto> bookings = jdbc.query(query, (rs, rowNum) -> {
            BookingDto dto = new BookingDto(
                rs.getLong("bookingId"),
                rs.getString("roomType"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getObject("checkInDate", LocalDate.class),
                rs.getObject("checkOutDate", LocalDate.class)
            );
            System.out.println("Booking: " + dto); 
            return dto;
        });

        System.out.println("Bookings size: " + bookings.size()); 
        return bookings;
    }
    
    public List<BookingDto> getAllBookingsByUserId(long userId) {
    	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("userId", userId);
    	String query = "SELECT b.bookingId, r.roomType, u.name, b.price, b.checkInDate, b.checkOutDate "
                     + "FROM booking b "
                     + "LEFT JOIN room r ON r.roomId = b.roomId "
                     + "JOIN users u ON u.userId = b.userId "
                     + "WHERE b.userId = :userId";
        List<BookingDto> bookings = jdbc.query(query,namedParameters, (rs, rowNum) -> {
            BookingDto dto = new BookingDto(
                rs.getLong("bookingId"),
                rs.getString("roomType"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getObject("checkInDate", LocalDate.class),
                rs.getObject("checkOutDate", LocalDate.class)
            );
            System.out.println("Booking: " + dto); 
            return dto;
        });

        System.out.println("Bookings size: " + bookings.size()); 
        return bookings;
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("userId", userId);
        String query = "SELECT * FROM booking WHERE userId = :userId";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Booking.class));
    }
    
    public List<Booking> getBookingsByBookingrId(Long bookingId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("bookingId", bookingId);
        String query = "SELECT * FROM booking WHERE bookingId = :bookingId";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Booking.class));
    }

    public void deleteBooking(long bookingId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM booking WHERE bookingId = :bookingId";
        namedParameters.addValue("bookingId", bookingId);
        jdbc.update(query, namedParameters);
    }

	public void editBookingById(long bookingId,long userId, long roomId,double roomPrice, LocalDate checkInDate,
			LocalDate checkOutDate) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE booking SET userId = :userId, roomId = :roomId, price = :price, checkInDate = :checkInDate, checkOutDate = :checkOutDate " +
                       "WHERE bookingId = :bookingId";
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("bookingId", bookingId);
        namedParameters.addValue("roomId", roomId);
        namedParameters.addValue("price", roomPrice);
        namedParameters.addValue("checkInDate", checkInDate);
        namedParameters.addValue("checkOutDate", checkOutDate);
        jdbc.update(query, namedParameters);   
	}

	public List<Booking> findAll(){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
				String query = "SELECT * FROM booking";
				return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Booking>(Booking.class));
	}
	
	// restful

	public Booking findById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM booking WHERE bookingId = :id";
		namedParameters.addValue("id", id);
		return jdbc.queryForObject(query, namedParameters, new
		BeanPropertyRowMapper<Booking>(Booking.class));
	}
	
	public Long save(Booking booking) {
		MapSqlParameterSource namedParameters = new
		MapSqlParameterSource();
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		 String query = "INSERT INTO booking (userId, roomId, price, checkInDate, checkOutDate) " +
                 "VALUES (:userId, :roomId, :price, :checkInDate, :checkOutDate)";
		  namedParameters.addValue("userId", booking.getUserId());
		  namedParameters.addValue("roomId",booking.getRoomId());
		  namedParameters.addValue("price", booking.getPrice());
		  namedParameters.addValue("checkInDate", booking.getCheckInDate());
		  namedParameters.addValue("checkOutDate", booking.getCheckOutDate());
		jdbc.update(query, namedParameters, generatedKeyHolder);
		return (Long) generatedKeyHolder.getKey();
	}
	
	public void deleteAll() {
		MapSqlParameterSource namedParameters = new
		MapSqlParameterSource();
		String query = "DELETE FROM booking";
		jdbc.update(query, namedParameters);
		}
	
	public Long count() {
		MapSqlParameterSource namedParameters = new
		MapSqlParameterSource();
		String query = "SELECT count(*) FROM booking";
		return jdbc.queryForObject(query, namedParameters, Long.TYPE);
		}
	
	public void saveAll(List<Booking> bookingList) {
		for (Booking b : bookingList) {
		save(b);
		}
	}
}
