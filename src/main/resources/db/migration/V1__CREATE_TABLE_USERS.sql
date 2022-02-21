CREATE TABLE public.users (
	id int8 NOT NULL,
	"name" varchar(255) NOT NULL,
    "password" varchar(255) NOT NULL,
    cpf varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
	cellphone varchar(255) NULL,
	date_of_birth date NULL,
	enabled bool NULL,
	sex int4 NULL,
	users_role varchar(20) NULL,
	created_at timestamp NULL,
	last_update timestamp NULL,
	CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
	CONSTRAINT uk_7kqluf7wl0oxs7n90fpya03ss UNIQUE (cpf),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);