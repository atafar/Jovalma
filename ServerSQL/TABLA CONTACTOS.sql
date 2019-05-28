/* Base de datos POKEMON */

# Creamos la base de datos

DROP DATABASE IF EXISTS INFORMACION;
CREATE DATABASE INFORMACION;
DROP USER IF EXISTS InformacionAdmin;
/* Base de datos POKEMON */

# Creamos la base de datos

DROP DATABASE IF EXISTS INFORMACION;
CREATE DATABASE INFORMACION;
DROP USER IF EXISTS InformacionAdmin;
CREATE USER InformacionAdmin IDENTIFIED BY 'InformacionPass';
GRANT ALL PRIVILEGES ON INFORMACION.* TO InformacionAdmin WITH GRANT OPTION;
USE INFORMACION;

# Creamos las tablas
CREATE TABLE CONTACTES(
	TELEFON INT(9) PRIMARY KEY,
    NOM VARCHAR(100) NOT NULL,
    SERVEIes VARCHAR(100),
    SERVEIen VARCHAR(100),
    SERVEIca VARCHAR(100),
    ADRECA VARCHAR(100) NOT NULL,
    LONGITUT DOUBLE(8,6) NOT NULL,
    LATITUT DOUBLE(8,6) NOT NULL,
    WEB VARCHAR(150),
    CORREU VARCHAR(50)
);

USE INFORMACION;
INSERT INTO CONTACTES VALUES(937530062,"ITA Argentona","Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Unitat d’hospitalització, hospital de dia i consultes externes",
"C/Baró de Viver, 58, 08310, Argentona",41.556310, 2.398183, "https://itasaludmental.com/","infoita@itasaludmental.com");
INSERT INTO CONTACTES VALUES(931998415,"ITA Urgell", "Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Hospital de dia i consultes externes",
"C/Compte Urgell, 143, 08036, Barcelona",41.386765, 2.152145, "https://itasaludmental.com/","infoita@itasaludmental.com");
INSERT INTO CONTACTES VALUES(932530137,"ITA Avenir",  "Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Hospital de dia i consultes externes",
"C/Avenir, 14, 08006, Barcelona",41.397216, 2.148798, "https://itasaludmental.com/","infoita@itasaludmental.com");
INSERT INTO CONTACTES VALUES(937220855,"ITA Sabadell",  "Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Hospital de dia i consultes externes",
"C/de la República,2, 08202, Sabadell",41.545757, 2.109956, "https://itasaludmental.com/","infoita@itasaludmental.com");
INSERT INTO CONTACTES VALUES(936114120,"SETCA: Servei Especialitzat en TCA", "Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Hospital de dia i consultes externes",
"C/Balmes, 203, principal 1a, 08006 Barcelona",41.398048, 2.151553, "http://www.setcabarcelona.com/","info@setcabarcelona.com");
INSERT INTO CONTACTES VALUES(934959203,"UMAIN: Instituto de Neurociencias",  "Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Hospital de dia i consultes externes",
"C/ Londres, 38, 08029, Barcelona",41.389434, 2.145540, "https://es.linkedin.com/company/umain-instituto-neurociencias","null");
INSERT INTO CONTACTES VALUES(936836965,"Desconect@", "Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Hospital de dia i consultes externes",
"C/ Berlinés, 13, 08022, Barcelona",41.404038, 2.139013, "https://www.programadesconecta.com/","hospitaldedia@programadesconcta.com");
INSERT INTO CONTACTES VALUES(931172041,"Hestia Palau Centre de Trastorns Alimentaris",  "Unidad de hospitalización, hospital de día y consultas externas","
Hospitalization unit, day hospital and external consultations","Hospital de dia i consultes externes",
"C/ Antoni Maria Claret, 135, Barcelona",41.410434, 2.172439, "http://www.hestiaalliance.org/","centredediapalau@gmail.com");
INSERT INTO CONTACTES VALUES(934161793,"Dra Paola Espinoza", "Consulta externa / ambulatorio","
Day hospital and external consultations","Consulta externa / ambulatori ",
"C/ Gran de Gràcia, 1, 1r, 2a, 08012 Barcelona",41.397852, 2.157335, "https://www.paolaespinozaguzman.com","info@paolaespinozaguzman.com");
INSERT INTO CONTACTES VALUES(932090899,"Galton gabinet col·lectiu","Consulta externa / ambulatorio","
Day hospital and external consultations","Consulta externa / ambulatori ",
"C/ Muntaner, 208, entresol 2a, 08036, Barcelona",41.397852, 2.157335, "https://www.galtonbarcelona.com","galton.barcelona@gmail.com");
INSERT INTO CONTACTES VALUES(625640719,"Marta Andújar – Espai Gestal de Barcelona",  "Consulta externa / ambulatorio","
Day hospital and external consultations","Consulta externa / ambulatori ",
"Plaça Universitat, 4, 3r 1a, Barcelona",41.384975, 2.163557, "https://www.gestaltcentre.es","martandujarc@gestaltcentre.es");
INSERT INTO CONTACTES VALUES(932607600,"Hospital de Bellvitge",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"Feixa llarga s/n, 08907, L’Hospitalet de Llobregat",41.346508, 2.101109, "http://www.bellvitgehospital.cat/es","uac@bellvitgehospital.cat");
INSERT INTO CONTACTES VALUES(935537843,"Hospital de la Santa Creu i Sant Pau",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"Carrer de Sant Quintí, 89, 08026, Barcelona",41.416024, 2.174897, "http://santpau.cat/","atenciousuari@santpau.cat");
INSERT INTO CONTACTES VALUES(934978814,"Hospital Germans Trias i Pujol – Can Rut",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"Crta. De Canyet, s/n, 08916, Badalona",41.467935, 2.234754, "http://www.hospitalgermanstrias.cat/es","comunicacio.germanstrias@gencat.cat");
INSERT INTO CONTACTES VALUES(932279970,"Hospital Clínic",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"C/ Villaroel 170, 08036, Barcelona",41.389192, 2.151683, "https://www.hospitalclinic.org/","null");
INSERT INTO CONTACTES VALUES(932804000,"Hospital Sant Joan de Déu",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"Passeig Sant Joan de Déu, s/n, 08950, Esplugues de Llobregat",41.384088, 2.102324, "https://www.sjdhospitalbarcelona.org/es/especialistas","infovisidoc@sjdhospitalbarcelona.org");
INSERT INTO CONTACTES VALUES(937365050,"Hospital Mútua de Terrassa",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"C/ Castell, 31, Edifici Vallparadís planta -2, 08221 Terrassa",41.564881, 2.017870, "https://mutuaterrassa.com/es/hospital-universitario-mutuaterrassa","trastornsalimentaris@mutuaterrassa.cat");
INSERT INTO CONTACTES VALUES(937231010,"Hospital parc Taulí",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"Parc Taulí, 1, 08208, Sabadell",41.557355, 2.109842, "https://www.tauli.cat/tauli/","salutmental@tauli.cat");
INSERT INTO CONTACTES VALUES(938733003,"Althaia – Hospital Sant Joan de Déu",  "UTCA UNIDAD DE TRASTORNOS DE LA CONDUCTA ALIMENTARIA","
UTCA UND DEVELOPMENT OF FOOD CONDUCT","UTCA UNITAT DE TRASTORNS DE LA CONDUCTA ALIMENTÀRIA",
"C/ Dr. Llatjós s/n, 08243 Manresa",41.721369, 1.834358, "https://www.althaia.cat/althaia/ca/assistencial/atencio-especialitzada/area-medica/nefrologia/hospital-de-sant-joan-de-deu","althaia@althaia.cat");