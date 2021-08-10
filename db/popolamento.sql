SET GLOBAL local_infile = TRUE;
SHOW GLOBAL VARIABLES LIKE 'local_infile';


load data local infile '/Users/marcocampione/CinemaDB_TSW/datiUtente.sql'
into table utente(nome,cognome,nascita,email,psw,data_registraz,ruolo,n_accessi);

load data local infile '/Users/marcocampione/CinemaDB_TSW/datiTelefono.sql'
into table telefono(numero,email);

load data local infile '/Users/marcocampione/CinemaDB_TSW/datiFilm.sql'
into table film(titolo_film,genere,durata,regia,trama,locandina,cod_video);

load data local infile '/Users/marcocampione/CinemaDB_TSW/datiCinema.sql'
into table cinema(citta,indirizzo,nome,regione,mappa,provincia);

load data local infile '/Users/marcocampione/CinemaDB_TSW/datiSpettacolo.sql'
into table spettacolo(id_spettacolo,titolo_film,data_s,sala,prezzo,indirizzo);

load data local infile '/Users/marcocampione/CinemaDB_TSW/datiOra.sql'
into table ora(id_spettacolo,orario);


/*load data local infile '/Users/marcocampione/CinemaDB_TSW/datiAccede.sql'
into table accede(email,id_spettacolo);*/
insert into accede(email,id_spettacolo,m_accesso) value('MarioRossi@libero.it', 'A4', 'prenotato');
insert into accede(email,id_spettacolo,m_accesso) value('MarioRossi@libero.it', 'B7', 'prenotato');
insert into accede(email,id_spettacolo,m_accesso) value('FrancescaFilippone@gmail.com', 'C3', 'acquistato');
insert into accede(email,id_spettacolo,m_accesso) value('GerryPag@libero.it', 'B1', 'prenotato');
insert into accede(email,id_spettacolo,m_accesso) value('Stef64@hotmail.it', 'C9', 'acquistato');
insert into accede(email,id_spettacolo,m_accesso) value('Mastro_G@hotmail.it', 'A7', 'prenotato');
insert into accede(email,id_spettacolo,m_accesso) value('Mastro_G@hotmail.it', 'B5', 'prenotato');