DROP database IF EXISTS cinemaDB;
CREATE database cinemaDB;

DROP USER IF EXISTS 'cineuser'@'localhost';
CREATE USER 'cineuser'@'localhost' IDENTIFIED BY 'cineuseR1!';
GRANT ALL ON cinemaDB.* TO 'cineuser'@'localhost';

USE cinemaDB;

DROP TABLE IF EXISTS utente; 
CREATE TABLE utente(
    nome VARCHAR(20),
    cognome VARCHAR(20),
    nascita DATE,
    email VARCHAR(50) not null,
	psw VARCHAR(30) not null,
    data_registraz DATE,
    ruolo VARCHAR(15),
	n_accessi INT,
    PRIMARY KEY(email)
);

DROP TABLE IF EXISTS telefono; 
CREATE TABLE telefono(
    numero CHAR(10) not null,
    email VARCHAR(50) not null,
    PRIMARY KEY(numero, email),
    FOREIGN KEY(email) REFERENCES utente(email)
    ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS film; 
CREATE TABLE film(
    titolo_film VARCHAR(100) not null,
    genere VARCHAR(50),
    durata TIME,
    regia VARCHAR(50),
    trama VARCHAR(1500),
    locandina VARCHAR(30),
    cod_video VARCHAR(20),
    PRIMARY KEY(titolo_film)
);

DROP TABLE IF EXISTS cinema; 
CREATE TABLE cinema(
	citta VARCHAR(30),
	indirizzo VARCHAR(60) not null,
	nome VARCHAR(30),
    regione VARCHAR(30),
	mappa VARCHAR(400),
    provincia CHAR(2),
	PRIMARY KEY(indirizzo)
);

DROP TABLE IF EXISTS spettacolo; 
CREATE TABLE spettacolo(
    id_spettacolo CHAR(2) not null,
    titolo_film VARCHAR(100) not null,
    data_s DATE,
    sala INT,
    prezzo FLOAT,
    indirizzo VARCHAR(500) not null,
    PRIMARY KEY(id_spettacolo),
    FOREIGN KEY(titolo_film) REFERENCES film(titolo_film)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(indirizzo) REFERENCES cinema(indirizzo)
    ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS ora; 
CREATE TABLE ora(
	id_spettacolo CHAR(2) not null,
	orario TIME,
	PRIMARY KEY(id_spettacolo, orario),
	FOREIGN KEY(id_spettacolo) REFERENCES spettacolo(id_spettacolo)
	ON DELETE CASCADE ON UPDATE CASCADE	
);

DROP TABLE IF EXISTS accede; 
CREATE TABLE accede(
	email VARCHAR(30) not null,
	id_spettacolo CHAR(2) not null,
	m_accesso VARCHAR(20),
	PRIMARY KEY(email, id_spettacolo),
	FOREIGN KEY(email) REFERENCES utente(email)
	ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(id_spettacolo) REFERENCES spettacolo(id_spettacolo)
	ON DELETE CASCADE ON UPDATE CASCADE	
);