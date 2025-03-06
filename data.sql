-- Заполнение таблицы parent
INSERT INTO parent (email, first_name, last_name, password) VALUES
('parent1@mail.com', 'Иван', 'Петров', 'password123'),
('parent2@mail.com', 'Мария', 'Сидорова', 'password124'),
('parent3@mail.com', 'Алексей', 'Иванов', 'password125'),
('parent4@mail.com', 'Елена', 'Смирнова', 'password126'),
('parent5@mail.com', 'Дмитрий', 'Козлов', 'password127'),
('parent6@mail.com', 'Ольга', 'Морозова', 'password128'),
('parent7@mail.com', 'Сергей', 'Волков', 'password129'),
('parent8@mail.com', 'Татьяна', 'Лебедева', 'password130'),
('parent9@mail.com', 'Андрей', 'Соколов', 'password131'),
('parent10@mail.com', 'Наталья', 'Павлова', 'password132');

-- Заполнение таблицы child
INSERT INTO child (first_name, last_name, age, parent_id) VALUES
('Артём', 'Петров', 7, 1),
('София', 'Сидорова', 8, 2),
('Максим', 'Иванов', 6, 3),
('Анна', 'Смирнова', 9, 4),
('Даниил', 'Козлов', 7, 5),
('Виктория', 'Морозова', 8, 6),
('Александр', 'Волков', 6, 7),
('Полина', 'Лебедева', 9, 8),
('Михаил', 'Соколов', 7, 9),
('Екатерина', 'Павлова', 8, 10);

-- Заполнение таблицы teacher
INSERT INTO teacher (email, first_name, last_name, password) VALUES
('teacher1@school.com', 'Анна', 'Михайлова', 'teacher123'),
('teacher2@school.com', 'Владимир', 'Кузнецов', 'teacher124'),
('teacher3@school.com', 'Екатерина', 'Васильева', 'teacher125'),
('teacher4@school.com', 'Игорь', 'Николаев', 'teacher126'),
('teacher5@school.com', 'Марина', 'Федорова', 'teacher127'),
('teacher6@school.com', 'Павел', 'Семенов', 'teacher128'),
('teacher7@school.com', 'Светлана', 'Попова', 'teacher129'),
('teacher8@school.com', 'Денис', 'Захаров', 'teacher130'),
('teacher9@school.com', 'Юлия', 'Андреева', 'teacher131'),
('teacher10@school.com', 'Роман', 'Степанов', 'teacher132');

-- Заполнение таблицы lesson
INSERT INTO lesson (subject, start_time, duration_minutes, teacher_id) VALUES
('Математика', '2024-03-20 09:00:00', 45, 1),
('Русский язык', '2024-03-20 10:00:00', 45, 2),
('Английский язык', '2024-03-20 11:00:00', 45, 3),
('Физика', '2024-03-20 12:00:00', 45, 4),
('Химия', '2024-03-20 13:00:00', 45, 5),
('История', '2024-03-20 14:00:00', 45, 6),
('География', '2024-03-20 15:00:00', 45, 7),
('Биология', '2024-03-21 09:00:00', 45, 8),
('Литература', '2024-03-21 10:00:00', 45, 9),
('Информатика', '2024-03-21 11:00:00', 45, 10);

-- Заполнение таблицы lesson_child
-- Заполнение таблицы lesson_child
INSERT INTO lesson_child (lesson_id, child_id) VALUES
(11, 1), (11, 2), (11, 3),
(12, 4), (12, 5), (12, 6),
(13, 7), (13, 8),
(14, 9), (14, 10),
(15, 1), (15, 3), (15, 5),
(16, 2), (16, 4), (16, 6),
(17, 7), (17, 9),
(18, 8), (18, 10),
(19, 1), (19, 4), (19, 7),
(20, 2), (20, 5), (20, 8);