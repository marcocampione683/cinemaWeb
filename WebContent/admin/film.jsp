<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.FilmBean, java.util.*, model.UserBean"%>
<%
	String aggiorna =(String) request.getAttribute("aggiorna");
	if(aggiorna!=null){
			response.sendRedirect(response.encodeURL("./FilmControl?action=aggiorna"));
			return;
	}
	
	UserBean admin = (UserBean)session.getAttribute("utente");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Film</title>
<link href="../css/film.css" type="text/css" rel="stylesheet">
<link href="../css/modificheAdmin.css" type="text/css" rel="stylesheet">
<script src="../js/jquery-3.5.1.min.js"></script>
<script src="../js/films.js"></script>
<script src="../js/adminModifica.js"></script>
</head>
<body>
	<header id="intestazione">
		<nav id="navigation">
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href=""class="linknav">Film</a>
			<a href="<%=response.encodeURL("locals.jsp")%>"class="linknav">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>"class="linknav">Servizi</a>
			
			<div class="dropdown-userOptions">
			  <button class="dropbtn-user"> <%=admin.getName()%> <%=admin.getSurname()%></button>
			  <div class="dropdown-content-options">
			  	<a href="<%=response.encodeURL("AreaPersonale.jsp")%>" id="area">Area personale</a>
			  	<div class = "dropdown">
				<button class="dropbtn">Modifica<i class="fa fa-caret-down"></i></button>
				<div class="dropdown-content">
					<button onclick="document.getElementById('id01').style.display='block'">Aggiungi</button>
					<button onclick="document.getElementById('id02').style.display='block'">Elimina</button>
					<button onclick="document.getElementById('id03').style.display='block'">Aggiorna</button>
				</div>
				</div>
			  	<form action="<%=response.encodeURL("LogoutControl")%>" method="get" > 
     				<input type="submit" value="Logout"/>
				</form>
			  </div>
			</div>
		</nav>
		
		<div id="button3"> 
		<input type="search" id="searching" name="searching" onkeyup="suggerimento(this.value)">
		<input type="image" src="../image/search_button3.png" id="searchimg" onclick="suggerimento(document.getElementById('searching').value)">
		</div>
	</header>
	
	<div id="Container">
		<div id="BoxFilmContainer">
 			<% Collection<FilmBean> films = (Collection<FilmBean>) application.getAttribute("films");
 			   if(films==null){
				  response.sendRedirect(response.encodeURL("./FilmControl?action=insFilm"));
				  return;
			   }
 			%>
			<table>
				<tr id="rowFilm">
				<% 
				for(FilmBean fbean: films) { 
				%>
				<td>
					<form action="<%=response.encodeURL("FilmControl?action=filmView")%>" method="POST">
						<input type="image" src="../image/immagini_film/<%=fbean.getPhoto()%>" name="film" value="<%=fbean.getTitle()%>" width="288" height="512" id="locandina">
					</form>
					
					<div>
						<h4 id="titoloFilm"><%=fbean.getTitle() %></h4>
					</div>
				
				</td>
			<% } %>
			</tr>
			</table>
		</div>
	</div>
	
	<!-- aggiungi -->
	<div id="id01" class="modal">
	  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
	  <form class="modal-content" action="<%=response.encodeURL("FilmControl?action=insert")%>" method="post" onSubmit="return formValidationModify('film','aggiungi');">
	    <div class="container">
	      <h1 class="h1Modifiche">Aggiungi</h1>
	      <p class="pModifiche">aggiungi un nuovo film alla lista</p>
	      <hr>
	      <label for="titolo_film"><b>Titolo</b></label><br>
	      <input type="text" placeholder="Inserisci titolo del film" name="titolo_film" required>
	
	      <label for="genere"><b>Genere</b></label><br>
	      <span id="spanGenere" style="display:none">Formato genere non valida</span>
	      <input type="text" placeholder="Inserisci genere" name="genere" id="genere" required>
	
	      <label for="durata"><b>Durata</b></label><br>
	      <span id="spanDurata" style="display:none">Formato durata non valida</span>
	      <input type="text" placeholder="hh:mm:ss" name="durata" id="durata" required>
	      
	      <label for="regia"><b>Regia</b></label><br>
	      <span id="spanRegia" style="display:none">Formato regia non valida</span>
	      <input type="text" placeholder="Inserisci regia" name="regia" id="regia" required>
	      
	      <label for="trama"><b>Trama</b></label><br>
	      <textarea placeholder="Inserisci trama" name="trama" required></textarea><br><br>
	    
	      <label for="locandina"><b>Locandina</b></label><br>
	 	  <input type="file" id="fileLoc" placeholder="Inserisci locandina" name="locandina" required><br><br>
	 	  
	      <label for="cod_video"><b>Video trailer</b></label><br>
	      <span id="spanCod" style="display:none">Formato codice non valida</span>
	      <input type="text" placeholder="Inserisci codice trailer" name="cod_video" id="cod_video" required>
	      
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
	  <form class="modal-content" action="<%=response.encodeURL("FilmControl?action=delete")%>" method="post">
	    <div class="container">
	      <h1 class="h1Modifiche">Elimina</h1>
	      <p class="pModifiche">inserisci titolo film per eliminarlo dalla lista</p>
	      <hr>
	      <label for="titolo_film"><b>Titolo</b></label>
	      <input type="text" placeholder="Inserisci titolo del film" name="titolo_film" required>
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
	  <form class="modal-content" action="<%=response.encodeURL("FilmControl?action=update")%>" method="post" onSubmit="return formValidationModify('film','aggiorna');">
	    <div class="container">
	      <h1 class="h1Modifiche">Aggiorna</h1>
	      <hr>
	      <p class="pModifiche">inserisci il titolo del film, presente nella lista, di cui vuoi apportare modifiche.</p>
	      <label for="titolo_old_film"><b>Titolo</b></label><br>
	      <input type="text" placeholder="Inserisci titolo del film" name="titolo_old_film">
	      
	      <p class="pModifiche">aggiorna i campi del film</p>
	      
	      <label for="titolo_film"><b>Titolo</b></label><br>
	      <input type="text" placeholder="Inserisci titolo del film" name="titolo_film">
	
	      <label for="genere"><b>Genere</b></label><br>
	       <span id="spanGenereUpdate" style="display:none">Formato genere non valida</span>
	      <input type="text" placeholder="Inserisci genere" name="genere" id="genereUpdate">
	
	      <label for="durata"><b>Durata</b></label><br>
	      <span id="spanDurataUpdate" style="display:none">Formato durata non valida</span>
	      <input type="text" placeholder="hh:mm:ss" name="durata" id="durataUpdate">
	      
	      <label for="regia"><b>Regia</b></label><br>
	      <span id="spanRegiaUpdate" style="display:none">Formato regia non valida</span>
	      <input type="text" placeholder="Inserisci regia" name="regia" id="regiaUpdate">
	      
	      <label for="trama"><b>Trama</b></label><br><br>
	      <textarea placeholder="Inserisci trama" name="trama"></textarea><br><br>
	    
	      <label for="locandina"><b>Locandina</b></label><br>
	 	  <input type="file" id="fileLoc" placeholder="Inserisci locandina" name="locandina"><br><br>
	 	  
	      <label for="cod_video"><b>Video trailer</b></label><br>
	      <span id="spanCodUpdate" style="display:none">Formato codice non valida</span>
	      <input type="text" placeholder="Inserisci codice trailer" name="cod_video" id="cod_videoUpdate">
	      
	      <div class="clearfix">
	        <button class="Formbutton" type="button" onclick="document.getElementById('id03').style.display='none'" class="cancelbtn">Annulla</button>
	        <button class="Formbutton" type="submit" class="signupbtn">Aggiorna</button>
	      </div>
	      
	    </div>
	  </form>
	</div>
</body>
</html>