drop table if exists customer;
drop table if exists Rooms;
create table customer(customer_id int,address varchar(225), email_address varchar(225),first_name varchar(225),last_name varchar(225),password varchar(225),phone_number varchar(225) );
create table Rooms(id int,image varchar(225),booking_status boolean,check_in date ,check_out date ,room_id int,room_Type varchar(225));


--creating customer data
--insert into customer values (1, 'Puri', 'Gyananjaya@gmail.com', 'Gyananjaya', 'Rath', '1234', '9348696233');
--insert into customer values(2,'mumbai','ram@gmail.com','ram','sita','1253','1234567895');


--INSERT INTO customer (customer_id, address, email_address, first_name, last_name, password, phone_number) 
--VALUES (1, 'Puri', 'Gyananjaya@gmail.com', 'Gyananjaya', 'Rath', '1234', '9348696233');
--insert into customer values(2,'mumbai','ram@gmail.com','ram','sita','1253','1234567895');

--insert into Rooms(id,booking_Status,check_In,check_Out,room_Id,room_Type)values(1,0,'2023-01-02','2024-01-12',121,'single');
insert into Rooms values(2,'../images/room4.jpg',0,'2023-01-02','2024-04-06',122,'single');
insert into Rooms values(3,'../images/room5.jpg',1,'2023-01-02','2024-04-06',123,'double');
insert into Rooms values(4,'../images/room3.jpg',0,'2023-01-02','2024-01-10',124,'delux');
insert into Rooms values(5,'../images/room2.jpg',1,NULL,NULL,125,'single');
insert into Rooms values(6,'../images/room1.jpg',0,'2023-01-02','2024-04-06',126,'delux');
insert into Rooms values(7,'../images/room6.jpg',0,'2023-01-02','2024-04-06',127,'double');
insert into Rooms values(8,'../images/room7.jpg',0,'2023-01-02','2024-04-06',128,'delux');
insert into Rooms values(9,'../images/room8.jpg',0,'2023-01-02','2024-04-06',129,'double');
insert into Rooms values(10,'../images/room2.jpg',0,'2023-01-02','2024-04-06',130,'delux');
insert into Rooms values(11,'../images/room4.jpg',0,'2023-01-02','2024-04-06',131,'single');
insert into Rooms values(12,'../images/room6.jpg',0,'2023-01-02','2024-04-06',132,'single');
insert into Rooms values(13,'../images/room1.jpg',0,'2023-01-02','2024-04-06',133,'delux');
insert into Rooms values(14,'../images/room3.jpg',0,'2023-01-02','2024-04-06',134,'double');


--insert into Reservation(ReservationId,CustomerId,RoomId,FromDate,ToDate,CheckInDone)
--values(1,1,3,'12/2/2022','15/2/2022',1);
