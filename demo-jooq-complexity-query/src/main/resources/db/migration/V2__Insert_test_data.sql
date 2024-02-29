do 
$$ 
begin 

with pes as (
    insert into
        pessoa (nome)
    values
        ('Pessoa Teste') returning id
), 
usu as (
    insert into
    usuario (nome)
    values
    ('Usuario teste') returning id
),
prod as (
    insert into
    produto (nome)
    values
    ('Produto teste') returning id
),
compra as (
    insert into 
    operacao (data_hora, pessoa_id, usuario_id, tipo_operacao)
    values
    (now(), (select id from pes), (select id from usu), 'C') returning id
),
venda as (
    insert into 
    operacao (data_hora, pessoa_id, usuario_id, tipo_operacao)
    values
    (now(), (select id from pes), (select id from usu), 'V') returning id
),
compra_item as (
    insert into 
    operacao_item (operacao_id, produto_id, quantidade, valor_un)
    values
    ((select id from compra), (select id from prod), 5, 50) returning id
),
venda_item as (
    insert into 
    operacao_item (operacao_id, produto_id, quantidade, valor_un)
    values
    ((select id from venda), (select id from prod), 3, 90) returning id
),
aju as (
    insert into 
    ajuste (data_hora, usuario_id)
    values
    (now(), (select id from usu)) returning id
)
insert into 
    ajuste_item (ajuste_id, produto_id, quantidade, tipo_operacao)
    values
    ((select id from aju), (select id from prod), 1, 'E'), 
    ((select id from aju), (select id from prod), 7, 'S');

end $$