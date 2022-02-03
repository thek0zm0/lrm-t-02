INSERT INTO tb_diet (name, description, start_Date, end_Date) VALUES ('Cetogenica','Dieta baseada na baixa ingestao de carboidratos','27/01/2021','27/01/2022');

INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('Jaime', 'jaime@gmail.com', '25091825010', '011987463529', '22/01/1997','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('John', 'john@gmail.com', '84440157092', '011982346218', '15/02/1998','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password, diet_id) VALUES ('Robert', 'robert@gmail.com', '06032858057', '01239876212', '20/04/1990','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);

INSERT INTO tb_meal (name, diet_id) VALUES ('Café da manhã 001', 1);
INSERT INTO tb_meal (name, diet_id) VALUES ('Café da manhã 002', 1);
INSERT INTO tb_meal (name, diet_id) VALUES ('Café da manhã 003', 1);

INSERT INTO tb_role (authority) VALUES ('ROLE_NUTRITIONIST');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);

INSERT INTO tb_information (height, weight, sex, activity_Status, user_id) VALUES ('1.8', '90.0', 'M', 'HIGH', 3);
INSERT INTO tb_information (height, weight, sex, activity_Status, user_id) VALUES ('1.8', '100.0', 'M', 'LOW', 3);
