create table if not exists pessoa  (
    id VARCHAR(36) not null,
    apelido varchar(32) not null unique,
    nascimento varchar(10) not null,
    nome varchar(100) not null,
    stack varchar(255) default null,
    primary key (id)
)