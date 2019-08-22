PRAGMA foreign_keys = 0;

BEGIN TRANSACTION;


ALTER TABLE "client" RENAME TO clientTemp;

CREATE TABLE "client" ("idClt" NUMBER(10) PRIMARY KEY NOT NULL ON CONFLICT IGNORE, "adrClt" VARCHAR, "etatClt" VARCHAR, "nomClt" VARCHAR, "numClt" VARCHAR, "nbPoints" DOUBLE DEFAULT 0.0 );

INSERT  INTO  "client"   SELECT * FROM "clientTemp";

DROP TABLE clientTemp;

<property name = "javax.persistence.schema-generation.create-source" value = "script" />
      <property name="javax.persistence.sql-load-script-source" value="META-INF/script3.sql"/> 
ALTER TABLE "contenirVente" RENAME TO contVte;

CREATE TABLE "contenirVente" ("idCon" NUMBER(19) PRIMARY KEY NOT NULL, "idProd" NUMBER(10), "idVen" NUMBER(10), "prixProd" NUMBER(10), "qteVen" NUMBER(10),"montVente" DOUBLE DEFAULT 0.0, "dtVente" DATE DEFAULT NULL);

INSERT  INTO  "contenirVente"   SELECT * FROM contVte;

DROP TABLE contVte;


COMMIT;

PRAGMA foreign_keys = 1;

