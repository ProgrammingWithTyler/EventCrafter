use eventcrafter;

drop table if exists events;

CREATE TABLE events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT,
    date DATE NOT NULL,
    organizer VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    attendees INT DEFAULT 0,
    category ENUM('Conference', 'Meetup', 'Workshop', 'Other') NOT NULL DEFAULT 'Other',
    FOREIGN KEY (organizer) REFERENCES users(username)
);

INSERT INTO events (title, location, description, date, organizer, attendees, category) VALUES
('Tech Conference 2025', 'San Francisco Convention Center', 'Annual tech conference with keynote speakers and workshops.', '2025-11-15', 'john_doe', 500, 'Conference'),
('Web Development Meetup', 'Local Co-Working Space', 'Monthly meetup for web developers to share knowledge.', '2025-06-20', 'jane_doe', 50, 'Meetup'),
('Advanced Java Workshop', 'Online - Zoom', 'Hands-on workshop covering advanced Java topics.', '2025-08-05', 'alice_smith', 30, 'Workshop'),
('Community Art Fair', 'City Park', 'Local art fair showcasing community artwork.', '2025-07-10', 'bob_jones', 100, 'Other'),
('Data Science Symposium', 'New York University', 'Symposium on the latest trends in data science.', '2025-09-22', 'charlie_b', 200, 'Conference'),
('React.js Beginners Workshop', 'Online - Google Meet', 'Introduction to React.js for beginners.', '2025-07-01', 'david_lee', 25, 'Workshop'),
('Open Source Project Night', 'Tech Hub', 'Collaborative coding night for open source projects.', '2025-06-12', 'eve_wilson', 40, 'Meetup'),
('Summer Music Festival', 'Outdoor Venue', 'Three-day music festival with various artists.', '2025-08-18', 'frank_m', 300, 'Other'),
('AI and Machine Learning Conference', 'London Expo Center', 'International conference on AI and machine learning.', '2025-10-28', 'grace_w', 400, 'Conference'),
('Python for Data Analysis Workshop', 'Online - Teams', 'Practical workshop on using Python for data analysis.', '2025-07-28', 'henry_c', 20, 'Workshop'),
('Local Business Networking', 'Downtown Hotel', 'Networking event for local business owners.', '2025-06-28', 'irene_r', 75, 'Meetup'),
('Annual Charity Gala', 'Grand Ballroom', 'Charity gala to raise funds for local causes.', '2025-09-05', 'jack_l', 150, 'Other'),
('Cloud Computing Summit', 'Seattle Convention Center', 'Summit on the latest cloud computing technologies.', '2025-12-08', 'kelly_y', 550, 'Conference'),
('Mobile App Development Workshop', 'Online - YouTube Live', 'Workshop on developing mobile apps with Flutter.', '2025-08-20', 'larry_h', 35, 'Workshop'),
('Startup Founders Meetup', 'Co-Working Space', 'Meetup for startup founders to share experiences.', '2025-07-05', 'mary_k', 60, 'Meetup');

select * from events;