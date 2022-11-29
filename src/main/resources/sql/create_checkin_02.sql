CREATE TABLE checkin (
    id bigserial NOT NULL,
    hospede_id int NOT NULL,
    dt_entrada VARCHAR(255) NOT NULL,
    dt_saida VARCHAR(255) NOT NULL,
    adicionalVeiculo BOOLEAN NOT NULL,

 PRIMARY KEY(id),
   CONSTRAINT fk_hospede
      FOREIGN KEY(hospede_id)
	  REFERENCES persons(id)
);
