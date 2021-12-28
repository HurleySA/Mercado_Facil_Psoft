insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO)
values(10001,'Leite Integral', '87654321-B', 'Parmalat', FALSE, 'Mercearia', 4.5);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO)
values(10002,'Arroz Integral', '87654322-B', 'Tio Joao', FALSE, 'Perecíveis', 5.5);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO)
values(10003,'Sabao em Po', '87654323-B', 'OMO', FALSE, 'Limpeza', 120);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO)
values(10004,'Agua Sanitaria', '87654324-C', 'Dragao', FALSE, 'limpeza', 30);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO)
values(10005,'Creme Dental', '87654325-C', 'Colgate', FALSE, 'HIGIENE', 25);

insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS)
values(1, 10005, 5);

update produto set IS_DISPONIVEL = TRUE where ID = 10005;

insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS)
values(2, 10004, 10);

update produto set IS_DISPONIVEL = TRUE where ID = 10004;
