create table if not exists accounts(
    id SERIAL primary key,
    username text not null,
    email text unique not null,
    password text not null,
    permission text not null
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
    accounts_id int REFERENCES accounts(id),
    description text not null
);
create table if not exists coordinated(
    tasks_id int REFERENCES tasks(id),
    schedules_id int REFERENCES schedules(id),
	day numeric(1), --trigger ellen.
	start_date numeric(2),
	end_date numeric(2)
);

--CREATE OR REPLACE FUNCTION check_account_uniqueness()
--RETURN TRIGGER AS
--    'BEGIN
--        IF (SELECT EXISTS(SELECT 1 FROM accounts WHERE email = NEW.email) = true) THEN
--            RAISE EXCEPTION ''Email already in use'';
--        ELSE
--            RETURN NEW;
--        END IF;
--    END;
--'LANGUAGE plpgsql;
--
--CREATE OR REPLACE FUNCTION check_schedule_uniqueness()
--RETURN TRIGGER AS
--    'BEGIN
--        IF(SELECT EXISTS(SELECT 1 FROM schedules WHERE title = NEW.title AND accounts_id = NEW.accounts_id) = true) THEN
--            RAISE EXCEPTION ''Schedule already exists'';
--        ELSE
--            RETURN NEW;
--        END IF;
--    END;
--'LANGUAGE plpgsql;
--
--CREATE OR REPLACE FUNCTION check_task_uniqueness()
--RETURN TRIGGER AS
--    'BEGIN
--        IF(SELECT EXISTS(SELECT 1 FROM tasks WHERE title = NEW.title) = true) THEN
--            RAISE EXCEPTION ''Task  already exists'';
--        ELSE
--            RETURN NEW;
--        END IF,
--    END;
--'LANGUAGE plpgsql;
--
--CREATE OR REPLACE FUNCTION check_days()
--RETURN TRIGGER AS
--    'BEGIN
--        IF
--
--CREATE TRIGGER check_account_uniqueness
--    BEFORE INSERT ON accounts
--    FOR EACH ROW
--    EXECUTE PROCEDURE check_account_uniqueness();


INSERT INTO accounts (username ,email,password, permission) VALUES
    ('admin','admin@admin','admin','Admin') on CONFLICT DO NOTHING
