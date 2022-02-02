INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('Jaime', 'jaime@gmail.com', '25091825010', '011987463529', '22/01/1997','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('John', 'john@gmail.com', '84440157092', '011982346218', '15/02/1998','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('Robert', 'robert@gmail.com', '06032858057', '01239876212', '20/04/1990','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_NUTRITIONIST');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);