INSERT INTO users (id, name, email) VALUES
(1, 'John Doe', 'john.doe@example.com'),
(2, 'Sarah Connor', 'sarah.connor@example.com'),
(3, 'Michael Scott', 'michael.scott@example.com')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO exam_registration (id, user_id, name, exam_status, is_active) VALUES
(1, 1, 'Java SE 21 Developer Certification (1Z0-830)', 1, TRUE),
(2, 1, 'AWS Certified Solutions Architect Associate', 1, TRUE),
(3, 2, 'Spring Certified Professional 2024', 1, TRUE),
(4, 3, 'Scrum Master Professional (PSM I)', 1, TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name);
