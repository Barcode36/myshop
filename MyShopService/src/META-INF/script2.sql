
ALTER TABLE "contenirVente" RENAME TO sqlitemanager_temp_table_36401344175;

CREATE TABLE "contenirVente" ("idCon" NUMBER(19) PRIMARY KEY NOT NULL, "idProd" NUMBER(10), "idVen" NUMBER(10), "prixProd" NUMBER(10), "qteVen" NUMBER(10), "dtVente" DATE);

DROP TABLE sqlitemanager_temp_table_36401344175;

ALTER TABLE "contenirVente" ADD  "montVente" DOUBLE DEFAULT 0.0;
