create table usuarios(
    id bigint not null auto_increment,
    username varchar(100) not null,
    password varchar(100) not null,
    roles varchar(100),

    primary key(id)
);