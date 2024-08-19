INSERT INTO room (roomType, description, price, availability) 
VALUES 
('Single Room', 'A cozy room perfect for one person. Includes a single bed, desk, and bathroom.', 75.00, true),
('Double Room', 'A comfortable room suitable for two people. Includes a double bed, desk, and bathroom.', 120.00, true),
('Deluxe Room', 'A spacious room with luxurious amenities. Includes a king-size bed, seating area, and large bathroom.', 200.00, true),
('Suite', 'An expansive suite with separate living and sleeping areas. Includes a king-size bed, sofa, and deluxe bathroom.', 350.00, true),
('Family Room', 'A large room suitable for a family. Includes two double beds, a seating area, and a bathroom.', 180.00, true),
('Twin Room', 'A room with two single beds. Includes a desk and a bathroom. Ideal for friends or colleagues.', 130.00, true),
('Executive Room', 'A premium room with business amenities. Includes a king-size bed, workspace, and bathroom.', 220.00, true),
('Penthouse Suite', 'The most luxurious suite with top-floor views. Includes a king-size bed, living area, and deluxe bathroom.', 500.00, true),
('Standard Room', 'A basic room with essential amenities. Includes a double bed and a bathroom.', 100.00, true),
('Accessible Room', 'A room designed for accessibility. Includes a queen-size bed, accessible bathroom, and necessary amenities.', 110.00, true);

INSERT INTO users (name, username, email, encryptedPassword, enabled) 
VALUES ('Admin User', 'adminuser', 'admin@example.com', '$2y$10$V6xxR2oyzoYGZ3D8kPmaN.k81/MEmwz/82TCtfWfbRgMMmYSj6ade', 1),
       ('User One', 'user1', 'user1@example.com', '$2y$10$FzfWyFEVDwB/IRK58uuxwOKyL4192SCngzYVhIa/IWDUK5Fez9r8G', 1),
       ('User Two', 'user2', 'user2@example.com', '$2y$10$FzfWyFEVDwB/IRK58uuxwOKyL4192SCngzYVhIa/IWDUK5Fez9r8G', 1),
       ('User Three', 'user3', 'user3@example.com', '$2y$10$FzfWyFEVDwB/IRK58uuxwOKyL4192SCngzYVhIa/IWDUK5Fez9r8G', 1),
       ('Guest User', 'guestuser', 'guest@example.com', '$2y$10$yBbxydv2pTx/uaidq/8grOhtsyRl4mArhol2TyorJ1NonSoVSAW5i', 1);
       
/* admin: 12345, user1,2,3: 123456, guest: 1234*/
       
INSERT INTO roles (roleName)
VALUES ('ROLE_USER');
 
INSERT INTO roles (roleName)
VALUES ('ROLE_GUEST');

INSERT INTO roles (roleName)
VALUES ('ROLE_ADMIN');
 
INSERT INTO user_roles (userId, roleId) 
VALUES 
(1, 3),(2,1),(3,1),(4,1),(5,2);



INSERT INTO booking (roomId, userId, price, checkInDate, checkOutDate)
VALUES 
(1, 2, 75.00, '2024-07-01', '2024-07-05'),
(2, 3, 120.00, '2024-07-10', '2024-07-15'),
(3, 4, 200.00, '2024-07-20', '2024-07-25');

