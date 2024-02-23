create table if not exists Operators (
    operator_id bigint auto_increment primary key,
    agentName varchar(255) unique not null,
    creation_timestamp timestamp not null default current_timestamp,
    last_accessed_timestamp timestamp not null default current_timestamp
);

create table if not exists Appointments (
    appointment_id uuid not null primary key,
    operator_id bigint,
    start_time timestamp not null,
    end_time timestamp not null,
    creation_timestamp timestamp not null default current_timestamp,
    last_accessed_timestamp timestamp not null default current_timestamp,
    foreign key (operator_id) references Operators(operator_id)
);

insert into Operators(name) values("ServiceOperator0")
insert into Operators(name) values("ServiceOperator1")
insert into Operators(name) values("ServiceOperator2")
