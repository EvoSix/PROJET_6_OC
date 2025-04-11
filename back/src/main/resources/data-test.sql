
INSERT INTO users (id, username, email, password, created_at, updated_at)
VALUES (1, 'testuser', 'testuser@mail.com', 'password123', NOW(), NOW());

INSERT INTO topics (id, label, description)
VALUES (1, 'Java', 'Tout sur le langage Java');


INSERT INTO subscriptions (user_id, topic_id)
VALUES (1, 1);