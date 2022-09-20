INSERT INTO tb_diet (name, description, start_Date, end_Date) VALUES ('Cetogenica','Dieta baseada na baixa ingestao de carboidratos','27/01/2021','27/01/2022');

INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('Jaime', 'jaime@gmail.com', '25091825010', '011987463529', '1997-01-27','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password) VALUES ('John', 'john@gmail.com', '84440157092', '011982346218', '1997-01-27','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, cpf, phone, birth_Date, password, diet_id) VALUES ('Robert', 'robert@gmail.com', '06032858057', '01239876212', '1997-01-27','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);

INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Banana','INNATURA','https://img.freepik.com/fotos-gratis/bando-de-banana-isolado_88281-1027.jpg?w=360','100','98.3','1.3','26.0','0.0', '0.0', '0.0', '0.0', '21.6', '0.4');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Maçã Fuji','INNATURA','https://media.soujusto.com.br/__sized__/products/207263-thumbnail-540x540-70.jpg','100','53.0','0.3','15.20','0.0', '0.0', '0.0', '0.0', '0.0', '0.0');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Azeite','INGREDIENTS','https://http2.mlstatic.com/D_NQ_NP_731025-MLU47590779855_092021-O.jpg','11','108.0','0.0','0.0','12.0', '0.0', '0.0', '0.0', '0.0', '0.0');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Sal','INGREDIENTS','https://www.ingredientesonline.com.br/media/catalog/product/cache/e928b84fd671f46d7960e6ef707820a9/f/l/flor_de_sal_6.jpg','5','0.0','0.0','0.0','0.0', '0.2', '0.0', '0.0', '0.0', '0.0');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Sardinha em Óleo','PROCESSED','https://static.paodeacucar.com/img/uploads/1/289/19919289.jpg','100','191.0','22.65','0.0','10.53', '0.46', '0.0', '0.001', '0.0', '0.004');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Queijo Ricota','PROCESSED','https://media.soujusto.com.br/products/Creme_De_Queijo_Ricota_Light_Président_Pote_200g.jpg','30','53.0','5','0.0','3.5', '0.1', '0.0', '0.0', '0.0', '0.0');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Sorvete','ULTRAPROCESSED','https://a-static.mlcdn.com.br/800x560/pote-de-sorvete-eskimo-grand-nevado-15-litros-eskimo-sorvetes/eskimosorvetes/6504903cb42d11eb82584201ac18500e/e911ccf0435a74051eb65d463cec3c1b.jpeg','100','201.0','3.52','24.4','10.72', '0.72', '22.3', '0.0', '0.0', '0.0');
INSERT INTO tb_food (name, food_Group, img_Url, quantity, calorie, protein, carbohydrate, fat, sodium, sugar, vitaminA, vitaminC, iron) VALUES ('Doritos','ULTRAPROCESSED','https://m.media-amazon.com/images/I/610trEtCQuS._AC_SX425_.jpg','100','500.0','5.0','55.0','25.0', '0.6', '0.0', '0.0', '0.0', '0.0');

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
