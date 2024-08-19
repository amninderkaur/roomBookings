CREATE TABLE users (
    userId LONG NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(75) NOT NULL UNIQUE,
    encryptedPassword VARCHAR(128) NOT NULL,
    enabled BIT NOT NULL 
);

CREATE TABLE roles (
    roleId LONG NOT NULL PRIMARY KEY AUTO_INCREMENT,
    roleName VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    id LONG NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    roleId BIGINT NOT NULL
);

ALTER TABLE user_roles
    ADD CONSTRAINT user_roles_uk UNIQUE (userId, roleId);

ALTER TABLE user_roles
    ADD CONSTRAINT user_roles_fk1 FOREIGN KEY (userId) REFERENCES users(userId);

ALTER TABLE user_roles
    ADD CONSTRAINT user_roles_fk2 FOREIGN KEY (roleId) REFERENCES roles(roleId);

CREATE TABLE room (
    roomId LONG NOT NULL PRIMARY KEY AUTO_INCREMENT,
    roomType VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    availability BIT NOT NULL DEFAULT true
);

CREATE TABLE booking (
    bookingId LONG NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    roomId BIGINT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL
);

ALTER TABLE booking
    ADD CONSTRAINT bookings_uk UNIQUE (userId, roomId, checkInDate, checkOutDate);
