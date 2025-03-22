use eventcrafter;

drop table if exists users;

create table users (
	id int auto_increment primary key,
    full_name varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null,
    email varchar(255) not null unique,
    phone_number varchar(20),
    address varchar(255),
    city varchar(100),
    state varchar(100),
    zip_code varchar(20),
    country varchar(100),
    role enum('user', 'admin', 'organizer') not null default 'user',
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    last_login timestamp,
    is_active boolean default true -- Account status
);

INSERT INTO users (full_name, username, password, email, phone_number, address, city, state, zip_code, country, role) VALUES
('Administrator', 'admin', 'admin123', 'admin@example.com', '123-456-7890', '1 Admin St', 'Adminville', 'AdminState', '12345', 'AdminCountry', 'admin'),
('John Doe', 'john_doe', 'password123', 'john.doe@example.com', '987-654-3210', '123 Main St', 'Anytown', 'CA', '90210', 'USA', 'user'),
('Jane Doe', 'jane_doe', 'password456', 'jane.doe@example.com', '555-123-4567', '456 Oak Ave', 'Springfield', 'IL', '62704', 'USA', 'organizer'),
('Alice Smith', 'alice_smith', 'password789', 'alice.smith@example.com', '111-222-3333', '789 Pine Ln', 'Riverside', 'NY', '10001', 'USA', 'user'),
('Bob Jones', 'bob_jones', 'password321', 'bob.jones@example.com', '444-555-6666', '101 Elm Rd', 'Hilldale', 'TX', '75001', 'USA', 'organizer'),
('Charlie Brown', 'charlie_b', 'snoopy', 'charlie@example.com', '777-888-9999', '202 Maple Dr', 'Beach City', 'FL', '33101', 'USA', 'user'),
('David Lee', 'david_lee', 'passwordA1', 'david@example.com', '222-333-4444', '303 Cedar Ct', 'Mountain View', 'WA', '98001', 'USA', 'user'),
('Eve Wilson', 'eve_wilson', 'evepass', 'eve@example.com', '666-777-8888', '404 Birch Pl', 'Desert Hills', 'AZ', '85001', 'USA', 'organizer'),
('Frank Martin', 'frank_m', 'frank123', 'frank@example.com', '888-999-0000', '505 Willow St', 'Forest Grove', 'OR', '97001', 'USA', 'user'),
('Grace White', 'grace_w', 'grace456', 'grace@example.com', '333-444-5555', '606 Redwood Ave', 'Ocean View', 'ME', '04001', 'USA', 'user'),
('Henry Clark', 'henry_c', 'henry789', 'henry@example.com', '999-000-1111', '707 Spruce Ln', 'Valley Falls', 'CO', '80001', 'USA', 'organizer'),
('Irene Rodriguez', 'irene_r', 'irene321', 'irene@example.com', '555-666-7777', '808 Cherry Rd', 'Lake City', 'MN', '55001', 'USA', 'user'),
('Jack Lopez', 'jack_l', 'jackA1', 'jack@example.com', '111-222-3333', '909 Walnut Dr', 'Riverdale', 'GA', '30001', 'USA', 'user'),
('Kelly Young', 'kelly_y', 'kellypass', 'kelly@example.com', '444-555-6666', '1010 Pine Ct', 'Meadowland', 'NC', '27501', 'USA', 'organizer'),
('Larry Hall', 'larry_h', 'larry123', 'larry@example.com', '777-888-9999', '1111 Elm Pl', 'Summitville', 'NJ', '07001', 'USA', 'user'),
('Mary King', 'mary_k', 'mary456', 'mary@example.com', '222-333-4444', '1212 Oak St', 'Brookside', 'PA', '19001', 'USA', 'user'),
('Nancy Wright', 'nancy_w', 'nancy789', 'nancy@example.com', '666-777-8888', '1313 Cedar Ave', 'Greenfield', 'VA', '23001', 'USA', 'organizer'),
('Oliver Green', 'oliver_g', 'oliver321', 'oliver@example.com', '888-999-0000', '1414 Maple Ln', 'Woodland', 'WI', '53001', 'USA', 'user'),
('Patricia Adams', 'patricia_a', 'patriciaA1', 'patricia@example.com', '333-444-5555', '1515 Birch Rd', 'Highland', 'UT', '84001', 'USA', 'user'),
('Quentin Baker', 'quentin_b', 'quentinpass', 'quentin@example.com', '999-000-1111', '1616 Willow Dr', 'Lowland', 'NM', '87001', 'USA', 'organizer');

SELECT * FROM users;