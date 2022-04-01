INSERT INTO tb_diet (name, description, start_Date, end_Date) VALUES ('Cetogenica','Dieta baseada na baixa ingestao de carboidratos','27/01/2021','27/01/2022');

INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('Jaime', 'jaime@gmail.com', '25091825010', '011987463529', '1997-01-27','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('John', 'john@gmail.com', '84440157092', '011982346218', '1997-01-27','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password, diet_id) VALUES ('Robert', 'robert@gmail.com', '06032858057', '01239876212', '1997-01-27','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);

INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Banana','INNATURA','none','100','98.3','1.3','26.0','0.0', '0.0', '0.0', '0.0', '21.6', '0.4');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Maçã Fuji','INNATURA','none','100','53.0','0.3','15.20','0.0', '0.0', '0.0', '0.0', '0.0', '0.0');

INSERT INTO tb_meal (name, time_Hour, diet_id) VALUES ('Café da manhã', '06:30:00', 1);
INSERT INTO tb_meal (name, time_Hour, diet_id) VALUES ('Almoço', '12:30:00', 1);
INSERT INTO tb_meal (name, time_Hour, diet_id) VALUES ('Janta', '18:30:00', 1);

INSERT INTO tb_food_Item (meal_id, food_id, quantity, total_Calories) VALUES (1, 1, 2, 198.0);
INSERT INTO tb_food_Item (meal_id, food_id, quantity, total_Calories) VALUES (1, 2, 1, 53.0);

INSERT INTO tb_role (authority) VALUES ('ROLE_NUTRITIONIST');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);

INSERT INTO tb_information (height, weight, age, sex, activity_Status, created_Date, user_id) VALUES ('1.8', '90.0', 20, 'M', 'HIGH', '20/04/2021', 3);
INSERT INTO tb_information (height, weight, age, sex, activity_Status, created_Date, user_id) VALUES ('1.8', '100.0', 21, 'M', 'LOW', '21/06/2021', 3);
