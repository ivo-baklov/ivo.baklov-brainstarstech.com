
CREATE TABLE product1
(
    id integer NOT NULL,
    category character varying(255) COLLATE pg_catalog."default",
    created_date date,
    description character varying(255) COLLATE pg_catalog."default",
    last_modified_date date,
    name character varying(255) COLLATE pg_catalog."default",
    quantity integer NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE product1
    OWNER to postgres;

INSERT INTO product1( id, name, category, description, quantity, created_date, last_modified_date) VALUES(1001,'Dell 5401', 'laptop', 'Dell description', 12, '20-05-2020','06-05-2020');
--INSERT INTO product( id, name, category, description, quantity, created_date, last_modified_date) VALUES(1001,'Dell 5401', 'laptop', 'Dell description', 12, '2020-05-20','2020-06-30');