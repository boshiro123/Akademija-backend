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
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5), (2, 6),
(3, 7), (3, 8),
(4, 9), (4, 10),
(5, 1), (5, 3), (5, 5),
(6, 2), (6, 4), (6, 6),
(7, 7), (7, 9),
(8, 8), (8, 10),
(9, 1), (9, 4), (9, 7),
(10, 2), (10, 5), (10, 8);