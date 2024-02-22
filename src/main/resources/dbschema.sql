create table if not exists operators (
    operator_id bigint auto_increment primary key,
    name varchar(255) not null,
    creation_timestamp timestamp not null default current_timestamp,
    last_accessed_timestamp timestamp not null default current_timestamp
);

create table if not exists appointments (
    appointment_id bigint auto_increment primary key,
    operator_id bigint,
    start_time timestamp not null,
    end_time timestamp not null,
    description varchar(255),
    creation_timestamp timestamp not null default current_timestamp,
    last_accessed_timestamp timestamp not null default current_timestamp,
    foreign key (operator_id) references Operators(operator_id)
);
