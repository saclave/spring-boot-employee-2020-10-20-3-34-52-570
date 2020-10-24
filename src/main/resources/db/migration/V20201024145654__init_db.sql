create table company (
     id bigint not null primary key auto_increment,
     company_name varchar(30) not null
);

create table employee (
     id bigint not null primary key auto_increment,
     name varchar(30) not null,
     age bigint,
     gender varchar(10),
     salary bigint,
     company_id bigint,
     foreign key (company_id) references company (id)
);