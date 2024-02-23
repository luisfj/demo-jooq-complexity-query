create table pessoa (
    id serial primary key,
    nome varchar(255) not null
);

create table usuario (
    id serial primary key,
    nome varchar(255) not null
);

create table produto (
    id serial primary key,
    nome varchar(255) not null,
    descricao varchar(500)
);

create table operacao (
    id serial primary key,
    data_hora timestamp without time zone not null,
    pessoa_id integer not null references pessoa(id),
    usuario_id integer not null references usuario(id),
    tipo_operacao char not null CONSTRAINT tp_oper_compra_venda_chk check (tipo_operacao in ('C', 'V'))
);

create table operacao_item (
    id serial primary key,
    operacao_id integer not null references operacao(id),
    produto_id integer not null references produto(id),
    quantidade numeric(18,2) not null,
    valor_un numeric(18,2) not null 
);

create table ajuste (
    id serial primary key,
    data_hora timestamp without time zone not null,
    usuario_id integer not null references usuario(id)
);

create table ajuste_item (
    id serial primary key,
    ajuste_id integer not null references ajuste(id),
    produto_id integer not null references produto(id),
    quantidade numeric(18,2) not null,
    tipo_operacao char not null CONSTRAINT tp_oper_entrada_saida_chk check (tipo_operacao in ('E', 'S'))
);
