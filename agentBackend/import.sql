insert into agentlocalbase.accommodation_type(id, name) values (1, "Bed&breakfast");
insert into agentlocalbase.accommodation_type(id, name) values (2, "Hotel");
insert into agentlocalbase.accommodation_type(id, name) values (3, "Apartment");
insert into agentlocalbase.address(id, city, country, latitude, longitude, street, zip) values(1,"Cacak", "Srbija", 40.21, 21.23, "Nemanjina 15/12", 32000);
insert into agentlocalbase.users(dtype, id, email, password, username, first_name, last_name, status, brn, address_id) values (1, 1, 'agent@gmail.com', 'a', 'a', 'Borko', 'Borkic', 1, 1, 1);
insert into agentLocalBase.user_role(user_id, role_id) VALUES (1, 1);

insert into agentlocalbase.additional_services(id, name) values (1, "Pets");
insert into agentlocalbase.additional_services(id, name) values (2, "Wifi");
insert into agentlocalbase.additional_services(id, name) values (3, "Parking");
insert into agentlocalbase.additional_services(id, name) values (4, "TV");
insert into agentlocalbase.additional_services(id, name) values (5, "All inclusive");

select * from agentlocalbase.accommodation_category;
select * from agentlocalbase.accommodation_type;
select * from agentlocalbase.address;
select * from agentlocalbase.users;
select * from agentLocalBase.user_role;
select * from agentlocalbase.additional_services;
select * from agentlocalbase.accommodation;
select * from agentlocalbase.image_resource;
select * from agentlocalbase.accommodation_image;
