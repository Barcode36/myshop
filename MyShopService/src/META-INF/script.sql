ALTER TABLE "client" ADD  "nbPoints" DOUBLE DEFAULT 0.0;
ALTER TABLE "contenirVente" ADD  "dtVente" DATE;
ALTER TABLE "contenirVente" ADD  "montVente" DOUBLE DEFAULT 0.0;

PRAGMA foreign_keys = 0;

BEGIN TRANSACTION;

ALTER TABLE "contenirVente" RENAME TO sqlitemanager_temp_table_45355483656;

CREATE TABLE "contenirVente" ("idCon" NUMBER(19) PRIMARY KEY NOT NULL, "idProd" NUMBER(10), "idVen" NUMBER(10), "prixProd" NUMBER(10), "qteVen" NUMBER(10), "montVente" DOUBLE(10));

-- Update the following indexes: 

-- sqlite_autoindex_contenirVente_1

DROP TABLE sqlitemanager_temp_table_45355483656;

COMMIT;

PRAGMA foreign_keys = 1;



