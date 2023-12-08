INSERT INTO mydb.role (idrole, name)
VALUES (1, 'role_user');
INSERT INTO mydb.role (idrole, name)
VALUES (2, 'role_admin');

INSERT INTO mydb.meal_type (idmeal_type, type)
VALUES (1, 'Fit meal');
INSERT INTO mydb.meal_type (idmeal_type, type)
VALUES (2, 'Small meal');
INSERT INTO mydb.meal_type (idmeal_type, type)
VALUES (3, 'Big meal');

INSERT INTO mydb.meal (idmeal, name, price, day_before, idmeal_type_fk)
VALUES (1, 'Eggs with bacon', 350, 0, 2);
INSERT INTO mydb.meal (idmeal, name, price, day_before, idmeal_type_fk)
VALUES (2, 'Gyros', 450, 0, 3);
INSERT INTO mydb.meal (idmeal, name, price, day_before, idmeal_type_fk)
VALUES (3, 'Pizza', 380, 1, 3);
INSERT INTO mydb.meal (idmeal, name, price, day_before, idmeal_type_fk)
VALUES (4, 'Cezar salad', 350, 1, 1);
INSERT INTO mydb.meal (idmeal, name, price, day_before, idmeal_type_fk)
VALUES (5, 'Sandwich with ham', 220, 0, 2);
INSERT INTO mydb.meal (idmeal, name, price, day_before, idmeal_type_fk)
VALUES (6, 'Hamburger', 560, 0, 3);
INSERT INTO mydb.meal (idmeal, name, price, day_before, idmeal_type_fk)
VALUES (7, 'Burrito', 450, 0, 1);


INSERT INTO mydb.image (idimage, link, name)
VALUES (1,
        'https://cdn.discordapp.com/attachments/1084771724031176815/1088389593772085401/Screenshot_2023-03-23_101118.png',
        'image');
INSERT INTO mydb.image (idimage, link, name)
VALUES (2,
        'https://cdn.discordapp.com/attachments/1084771724031176815/1088389593772085401/Screenshot_2023-03-23_101118.png',
        'image');
INSERT INTO mydb.image (idimage, link, name)
VALUES (3,
        'https://cdn.discordapp.com/attachments/1084771724031176815/1088389593772085401/Screenshot_2023-03-23_101118.png',
        'image');
INSERT INTO mydb.image (idimage, link, name)
VALUES (4,
        'https://cdn.discordapp.com/attachments/1084771724031176815/1088389593772085401/Screenshot_2023-03-23_101118.png',
        'image');
INSERT INTO mydb.image (idimage, link, name)
VALUES (5,
        'https://cdn.discordapp.com/attachments/1084771724031176815/1088389593772085401/Screenshot_2023-03-23_101118.png',
        'image');
INSERT INTO mydb.image (idimage, link, name)
VALUES (6,
        'https://cdn.discordapp.com/attachments/1084771724031176815/1088389593772085401/Screenshot_2023-03-23_101118.png',
        'image');
INSERT INTO mydb.image (idimage, link, name)
VALUES (7,
        'https://cdn.discordapp.com/attachments/1084771724031176815/1088389593772085401/Screenshot_2023-03-23_101118.png',
        'image');


INSERT INTO mydb.address (idaddress, street_name, street_number)
VALUES (1, 'Bulevar Oslobodjenja', '127');

INSERT INTO mydb.user (iduser, first_name, last_name, email, password, number, idaddress_fk, the_choosen_one, idrole_fk)
VALUES (1, 'Admin', 'Admin', 'admin@gmail.com', '$2a$10$j2Vp1a3.YHSOJB.d.kuqCuJGnraodncgzVV3DcjJBjBFuZhOIuix.', '123',
        '1', 0, 2);
INSERT INTO mydb.user (iduser, first_name, last_name, email, password, number, idaddress_fk, the_choosen_one, idrole_fk)
VALUES (2, 'Uros', 'Zigic', 'uros.zigic@gmail.com', '$2a$10$j2Vp1a3.YHSOJB.d.kuqCuJGnraodncgzVV3DcjJBjBFuZhOIuix.',
        '123',
        '1', 0, 1);
INSERT INTO mydb.user (iduser, first_name, last_name, email, password, number, idaddress_fk, the_choosen_one, idrole_fk)
VALUES (3, 'Vladimir', 'Vuckovic', 'vidrak97@gmail.com', '$2a$10$j2Vp1a3.YHSOJB.d.kuqCuJGnraodncgzVV3DcjJBjBFuZhOIuix.',
        '123', '1', 0, 1);
INSERT INTO mydb.user (iduser, first_name, last_name, email, password, number, idaddress_fk, the_choosen_one, idrole_fk)
VALUES (4, 'Aleksandar', 'Antonic', 'aleksandar@gmail.com',
        '$2a$10$j2Vp1a3.YHSOJB.d.kuqCuJGnraodncgzVV3DcjJBjBFuZhOIuix.', '123', '1', 0, 1);

INSERT INTO mydb.menu (idmenu, start_date, description, idimage_fk, iduser_fk)
VALUES (1, '2023-04-10', 'Fifth week', 3, 1);


INSERT INTO mydb.menu_meal (idmenu_meal, idmeal_fk, idmenu_fk, meal_date)
VALUES (1, 1, 1, '2023-04-10');
INSERT INTO mydb.menu_meal (idmenu_meal, idmeal_fk, idmenu_fk, meal_date)
VALUES (2, 2, 1, '2023-04-11');
INSERT INTO mydb.menu_meal (idmenu_meal, idmeal_fk, idmenu_fk, meal_date)
VALUES (3, 3, 1, '2023-04-11');
INSERT INTO mydb.menu_meal (idmenu_meal, idmeal_fk, idmenu_fk, meal_date)
VALUES (4, 4, 1, '2023-04-11');
INSERT INTO mydb.menu_meal (idmenu_meal, idmeal_fk, idmenu_fk, meal_date)
VALUES (5, 5, 1, '2023-04-10');
INSERT INTO mydb.menu_meal (idmenu_meal, idmeal_fk, idmenu_fk, meal_date)
VALUES (6, 6, 1, '2023-04-12');
INSERT INTO mydb.menu_meal (idmenu_meal, idmeal_fk, idmenu_fk, meal_date)
VALUES (7, 7, 1, '2023-04-13');