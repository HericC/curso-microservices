insert into category values (description) values ('Comic Books');
insert into category values (description) values ('Movies');
insert into category values (description) values ('Books');

insert into supplier values (name) values ('Panini Comics');
insert into supplier values (name) values ('Amazon');

insert into product values (name, fk_category, fk_supplier, quantity_available, created_at) values ('Crise nas Infinitas Terras', 1, 1, 10, current_timestamp);
insert into product values (name, fk_category, fk_supplier, quantity_available, created_at) values ('Interestelar', 2, 2, 5, current_timestamp);
insert into product values (name, fk_category, fk_supplier, quantity_available, created_at) values ('Harry Potter e a Pedra Filosofal', 3, 2, 3, current_timestamp);
