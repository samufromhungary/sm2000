create table if not exists accounts(
    id SERIAL primary key,
    username text not null,
    email text unique not null,
    password text not null
);
 create table if not exists schedules(
    id SERIAL primary key,
    title text unique not null,
    days numeric (1),
    accounts_id int REFERENCES accounts(id)
);
create table if not exists tasks(
    id serial primary key,
    title text unique not null,
    owner int REFERENCES accounts(id),
    content text unique not null
);
create table if not exists coordinated(
    tasks_id int REFERENCES tasks(id),
    schedules_id int REFERENCES schedules(id),
	day numeric(1), --trigger ellen.
	start_date numeric(2),
	end_date numeric(2)
);
