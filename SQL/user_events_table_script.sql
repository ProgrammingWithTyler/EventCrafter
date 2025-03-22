CREATE TABLE user_events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INT,
    user_id INT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Populate user_events
INSERT INTO user_events (event_id, user_id, registration_date) VALUES
(1, 1, NOW()),
(1, 2, NOW()),
(2, 3, NOW()),
(3, 1, NOW()),
(3, 4, NOW()),
(3, 2, NOW()),
(4, 5, NOW()),
(2, 5, NOW()),
(5, 1, NOW());

-- Update events table
UPDATE events
SET attendees = (
    SELECT COUNT(*)
    FROM user_events
    WHERE user_events.event_id = events.id
);

select * from user_events;