CREATE TABLE public.confirmation_token (
	id int8 NOT NULL,
	"token" varchar(255) NOT NULL,
	confirmed_at timestamp NULL,
	created_at timestamp NOT NULL,
	expires_at timestamp NOT NULL,
	users_id int8 NOT NULL,
	CONSTRAINT confirmation_token_pkey PRIMARY KEY (id)
);
