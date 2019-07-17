create table if not exists accounts(
    id SERIAL primary key,
    username text not null,
    email text unique not null,
    password text not null,
    permission text not null
);
 create table if not exists schedules(
    id SERIAL primary key,
    title text not null,
    days numeric (1),
    accounts_id int REFERENCES accounts(id)
);
create table if not exists tasks(
    id serial primary key,
    title text not null,
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

CREATE OR REPLACE FUNCTION check_account_uniqueness()
RETURNS TRIGGER AS
    'BEGIN
        IF (SELECT EXISTS(SELECT 1 FROM accounts WHERE email = NEW.email AND username != ''admin'') = true) THEN
            RAISE EXCEPTION ''Email already in use'';
        ELSE
            RETURN NEW;
        END IF;
    END;
'LANGUAGE plpgsql;
--
CREATE OR REPLACE FUNCTION check_schedule_uniqueness()
RETURNS TRIGGER AS
    'BEGIN
        IF (SELECT EXISTS(SELECT 1 FROM schedules WHERE title = NEW.title AND accounts_id = NEW.accounts_id) = true) THEN
            RAISE EXCEPTION ''Schedule already exists by this user'';
        ELSIF (NEW.days > 7 OR NEW.days < 1) THEN
            RAISE EXCEPTION ''Schedule length must been between 1 and 7'';
        ELSE
            RETURN NEW;
        END IF;
    END;
'LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_task_uniqueness()
RETURNS TRIGGER AS
    'BEGIN
        IF (SELECT EXISTS(SELECT 1 FROM tasks WHERE title = NEW.title AND accounts_id = NEW.accounts_id) = true) THEN
            RAISE EXCEPTION ''Task  already exists by this user'';
        ELSE
            RETURN NEW;
        END IF;
    END;
'LANGUAGE plpgsql;

--CREATE OR REPLACE FUNCTION check_schedule_days()
--    RETURNS TRIGGER AS
--    'BEGIN
--        DECLARE
--            c integer;
--        BEGIN
--            SELECT NEW.days INTO c FROM schedules
--            IF(days >= 8) THEN
--                RAISE EXCEPTION ''Day counter is a value between 1 and 7'';
--            ELSE
--                RETURN NEW
--            END IF;
--        END;
--    END;
--' LANGUAGE plpgsql;
--
--CREATE OR REPLACE FUNCTION check_days()
--RETURN TRIGGER AS
--    'BEGIN
--        IF
--
DROP TRIGGER IF EXISTS check_account_uniqueness ON accounts;
DROP TRIGGER IF EXISTS check_schedule_uniqueness ON schedules;
DROP TRIGGER IF EXISTS check_task_uniqueness ON tasks;
--DROP TRIGGER IF EXISTS check_schedule_days ON schedules;

CREATE TRIGGER check_account_uniqueness
    BEFORE INSERT ON accounts
    FOR EACH ROW
    EXECUTE PROCEDURE check_account_uniqueness();

CREATE TRIGGER check_schedule_uniqueness
    BEFORE INSERT ON schedules
    FOR EACH ROW
    EXECUTE PROCEDURE check_schedule_uniqueness();

CREATE TRIGGER check_task_uniqueness
    BEFORE INSERT ON tasks
    FOR EACH ROW
    EXECUTE PROCEDURE check_task_uniqueness();

--CREATE TRIGGER check_schedule_days
--    BEFORE INSERT ON schedules
--    FOR EACH ROW
--    EXECUTE PROCEDURE check_schedule_days();
--
--CREATE TRIGGER check_days
--    BEFORE INSERT ON schedules
--    FOR EACH ROW
--    EXECUTE PROCEDURE check_days();

INSERT INTO accounts (username ,email,password, permission) VALUES
    ('admin','admin@admin','admin','Admin') ON CONFLICT DO NOTHING;
