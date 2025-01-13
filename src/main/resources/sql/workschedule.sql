ALTER TABLE `work_schedule`
CHANGE `week_end` `week_end` DATE NOT NULL,
 CHANGE `week_start` `week_start` DATE NOT NULL;


 TRUNCATE TABLE day_types;
 -- Добавление данных в таблицу day_types
 INSERT INTO day_types (code, description) VALUES
 ('В', 'Выходной'),
 ('Б', 'Больничный'),
 ('ОТ', 'Отпуск'),
 ('Р', 'Рабочий день'),
 ('К', 'Командировка'),
 ('У', 'Учебный отпуск'),
 ('ДО', 'Дополнительный выходной'),
 ('Н', 'Нерабочий праздничный день');



 -- Заполняем таблицу данными
 INSERT INTO status_task (id, name) VALUES
 (1, 'Назначено'),
 (2, 'Принято к исполнению'),
 (3, 'В процессе решения'),
 (4, 'Выполнено');
