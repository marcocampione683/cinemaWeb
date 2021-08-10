<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>
    
<%
		
		Collection<FilmBean> films = (Collection<FilmBean>) application.getAttribute("films");
	
		if(films==null){
			 response.sendRedirect(response.encodeURL("film.jsp"));
			 return;
		}
		UserBean admin = (UserBean)session.getAttribute("utente");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Locali</title>
	<link href="../css/searchbar.css" type="text/css" rel="stylesheet" />
	<link href="../css/locals.css" type="text/css" rel="stylesheet">
	<link href="../css/ChoiceCinemaStyle.css" type="text/css" rel="stylesheet">
 	<link href="../css/modificheAdmin.css" type="text/css" rel="stylesheet"> 
	<script src="../js/jquery-3.5.1.min.js"></script>
	<script src="../js/ChoiceCinema.js"></script>
	<script src="../js/adminModifica.js"></script>
</head>
<body >
	<header>
		<nav>
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href="<%=response.encodeURL("film.jsp")%>" class="linknav">Film</a>
			<a href="" class="linknav">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>" class="linknav">Servizi</a>
			
			<div class="dropdown-userOptions">
			  <button class="dropbtn-user"> <%=admin.getName()%> <%=admin.getSurname()%></button>
			  <div class="dropdown-content-options">
			  	<a href="<%=response.encodeURL("AreaPersonale.jsp")%>" id="area">Area personale</a>
			  	<div class = "dropdown">
				<button class="dropbtn">Modifica<i class="fa fa-caret-down"></i></button>
				<div class="dropdown-content">
					<button onclick="document.getElementById('id01').style.display='block'">Aggiungi</button>
					<button onclick="openForm()">Elimina</button>
					<button onclick="document.getElementById('id03').style.display='block'">Aggiorna</button>
				</div>
				</div>
			  	<form action="<%=response.encodeURL("LogoutControl")%>" method="get" > 
     				<input type="submit" value="Logout"/>
				</form>
			  </div>
			</div>
		</nav>
		
		<img alt="search" src="../image/search_button3.png" id="searchButton" onclick="openSearch()">

    	<div id="full-content">
    		<div id="myOverlay" class="overlay">
        		<span class="closebtn" onclick="closeSearch()" title="Close Overlay">&times;</span>
       			<div class="overlay-content">
       				<input id="searchBar" type="text" placeholder="Search..." name="search" onkeyup="suggerimento(this.value)">
       				<button id="submitbar" type="submit" onclick="suggerimento(document.getElementById('searching').value)"><i class="fa fa-search"></i></button>	
       				<section id="resultSearch"></section> 
       			</div>	
    		</div> 	
   		</div>
	</header>
	
	<div>
		<h2>SCEGLI LA REGIONE IN CUI RISIEDE IL TUO CINEMA PREFERITO</h2>
	</div>
	
	<% String[] regioni = {"Valle d'Aosta", "Piemonte", "Liguria", "Lombardia", "Trentino-Alto Adige", "Veneto", 
						   "Friuli-Venezia Giulia", "Emilia Romagna", "Toscana", "Umbria", "Marche", "Lazio", 
						   "Abruzzo", "Molise", "Campania", "Puglia", "Basilicata", "Calabria", "Sicilia","Sardegna"};
	   
	   String[] img_reg = {"aosta.jpg","piemonte.jpg","liguria.jpg","lombardia.jpg","trentino.jpg",
			   			   "veneto.jpg","friuli.jpg","romagna.png","toscana.jpg","umbria.jpg",
			   			   "marche.jpg","lazio.jpg","abruzzo.jpg","molise.jpg","campania.jpg",
			   			   "puglia.jpg","basilicata.jpg","calabria.jpg","sicilia.jpg","sardegna.jpg"};
	%>
	<table id="rowCountry">
		<tr id="colCountry">
		<%	
			for(int i=0; i<regioni.length; i++){%>
				<td>
					<input type="image" src="../image/immagini_regioni/<%=img_reg[i]%>" id="imgReg" name="country" value="<%=regioni[i] %>" onclick="showCinema(this.value)">
					<p><%=regioni[i]%></p>
				</td>
			<%}%>
		</tr>
	</table>
	
	<div id="modalRegioni" class="modalReg"></div>
 	
 	<!-- aggiungi -->
	<div id="id01" class="modal">
	  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
	  <form class="modal-content" action="<%=response.encodeURL("LocalControl?action=insert")%>" method="post" onSubmit="return formValidationModify('locali','aggiungi');">
	    <div class="container">
	      <h1 class="h1Modifiche">Aggiungi</h1>
	      <p class="pModifiche">aggiungi un nuovo cinema ad una regione</p>
	      <hr>
	      <label for="citta"><b>Città</b></label><br>
	      <span id="spanCity" style="display:none">Formato città non valida</span>
	      <input type="text" placeholder="Inserisci Città" name="citta" id="city" required>
	
	      <label for="indirizzo"><b>Indirizzo</b></label><br>
	      <input type="text" placeholder="Inserisci indirizzo" name="indirizzo" required>
	
	      <label for="nome"><b>Nome</b></label><br>
	      <input type="text" placeholder="Inserisci nome del cinema" name="nome" required>
	      
	      
	      <label for="regione"><b>Regione</b></label><br>
	      <span id="spanRegione" style="display:none">Formato regione non valida</span>
	      <input type="text"  placeholder="Inserisci regione" name="regione" id="reg"required>
	      
	      <label for="mappa"><b>Mappa</b></label><br>
	      <span id="spanMap" style="display:none">Formato mappa non valida</span>
	      <input type="text" placeholder="Inserisci mappa" name="mappa" id="mappa" required>
	      
	      <label for="provincia"><b>Provincia</b></label><br>
	      <span id="spanProv" style="display:none">Formato provincia non valida</span>
	      <input type="text" placeholder="Inserisci provincia" name="provincia" id="prov" required>
	    
	   
	      <div class="clearfix">
	        <button class="Formbutton" type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Annulla</button>
	        <button class="Formbutton" type="submit" class="signupbtn">Aggiungi</button>
	      </div>
	    </div>
	  </form>
	</div>
	
		<!-- elimina -->
	<div id="id02" class="modal">
	  <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
	  <form class="modal-content" action="<%=response.encodeURL("LocalControl?action=delete")%>" method="post">
	    <div class="container">
	      <h1 class="h1Modifiche">Elimina</h1>
	      <p class="pModifiche">Inserisci il cinema che si vuole eliminare</p>
	      <hr>
	      <select name="listCinema" id="listCinema">
	      </select>
	      <div class="clearfix">
	        <button class="Formbutton" type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn">Annulla</button>
	        <button class="Formbutton" type="submit" class="signupbtn">Elimina</button>
	      </div>
	    </div>
	  </form>
	</div>
	
	
	<!-- aggiorna -->
	<div id="id03" class="modal">
	  <span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">&times;</span>
	  <form class="modal-content" action="<%=response.encodeURL("LocalControl?action=update")%>" method="post" onSubmit="return formValidationModify('locali','aggiorna');">
	    <div class="container">
	      <h1 class="h1Modifiche">Aggiorna</h1>
	      <hr>
	      <p class="pModifiche">inserisci l'indirizzo del cinema di cui vuoi apportare modifiche.</p>
	      <label for="address_old_cinema"><b>Indirizzo</b></label>
	      <input type="text" placeholder="Inserisci l'indirizzo" name="address_old_cinema" required>
	      
	      <p class="pModifiche">aggiorna i campi del film</p>
	      
	      <label for="indirizzo"><b>Indirizzo</b></label><br>
	      <input type="text" placeholder="Inserisci l'indirizzo" name="indirizzo">
	
	      <label for="citta"><b>Città</b></label><br>
	      <span id="spanCityUpdate" style="display:none">Formato città non valida</span>
	      <input type="text" placeholder="Inserisci citta" name="citta" id="cityUpdate" >
	
	      <label for="nome"><b>Nome</b></label><br>
	      <input type="text" placeholder="Inserisci nome" name="nome" >
	      
	      <label for="regione"><b>Regione</b></label><br>
	      <span id="spanRegioneUpdate" style="display:none">Formato regione non valida</span>
	      <input type="text"  placeholder="Inserisci regione" name="regione" id="regUpdate">
	      
	      <label for="mappa"><b>Mappa</b></label><br>
	      <span id="spanMapUpdate" style="display:none">Formato mappa non valida</span>
	      <input type="text" placeholder="Inserisci mappa" name="mappa" id="mappaUpdate">
	      
	      <label for="provincia"><b>Provincia</b></label><br>
	      <span id="spanProvUpdate" style="display:none">Formato provincia non valida</span>
	      <input type="text" placeholder="Inserisci provincia" name="provincia" id="provUpdate">
	     
	      <div class="clearfix">
	        <button class="Formbutton" type="button" onclick="document.getElementById('id03').style.display='none'" class="cancelbtn">Annulla</button>
	        <button class="Formbutton" type="submit" class="signupbtn">Aggiorna</button>
	      </div>
	    </div>
	  </form>
	</div>
 
</body>
</html>