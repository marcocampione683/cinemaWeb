<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.CinemaBean, model.FilmBean"%>
    
<%
		
		Collection<FilmBean> films = (Collection<FilmBean>) application.getAttribute("films");
	
		if(films==null){
			 response.sendRedirect(response.encodeURL("film.jsp"));
			 return;
		}

%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Locali</title>
	<link href="css/searchbar.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="css/LoginStyle.css" type="text/css" rel="stylesheet">
	<link href="css/ChoiceCinemaStyle.css" type="text/css" rel="stylesheet">
	<link href="css/locals.css" type="text/css" rel="stylesheet">
	<script src="js/jquery-3.5.1.min.js"></script>
	<script src="js/ChoiceCinema.js"></script>
	<script src="js/LoginScript.js"></script>
	<script src="js/validationForm.js"></script>
</head>
<body >
	<header id="intest">
		<nav id="navigation">
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href="<%=response.encodeURL("film.jsp")%>" class="linknav">Film</a>
			<a href="" class="linknav">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>" class="linknav">Servizi</a>
		</nav>
		
		<button id="login"><span>Login</span></button>
	
		<form id="regForm" action="<%=response.encodeURL("registration.jsp")%>">
			<button type="submit" id="registration"><span>Registrati</span></button>
		</form>
		
    <img alt="search" src="image/search_button3.png" id="searchButton" onclick="openSearch()"><!--  <button class="openButton" onclick="openSearch()"> -->

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
	
	<div id="myModal" class="modal">	
  		<div class="modal-content">
	    	<div class="modal-header">
	      		<span class="close">&times;</span>
	      		<h2>LOGIN</h2>
	    	</div>
	    		
	    	<div class="modal-body">
	    		<img src="image/key-icon.png" id="key">
	    			
	      		<form action="<%=response.encodeURL("LoginControl?chiamante=home.jsp")%>" method="post" id="autenticazione" name="auth" onSubmit="return formValidationLogin();">
					<label for="username">Username</label>
					<input type="text" name="username" placeholder="Inserisci username" required id="loginFormUs"><br>
		  			<span id="userSpan">Username non valida</span>
				
					<label for="password">Password</label>
					<input type="password" name="password" placeholder="Inserisci password" required id="loginFormPw"><br>
		  			<span id="passSpan">password non valida</span>
						
					<%if(request.getAttribute("error") != null) {%>
						<p id="error"></p>
						<script>viewError();</script>
					<% } %>
						
					<button type="submit" id="accedi">accedi</button>
			
				</form>
	    	</div>
	  	</div>
 	</div>
	
	<h2 id="intestazione">SCEGLI LA REGIONE IN CUI RISIEDE IL TUO CINEMA PREFERITO</h2>

	
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
					<input type="image" src="image/immagini_regioni/<%=img_reg[i]%>" id="imgReg" name="country" value="<%=regioni[i] %>" onclick="showCinema(this.value)">
					<p><%=regioni[i]%></p>
				</td>
			<%}%>
		</tr>
	</table>
	
	<div id="modalRegioni" class="modalReg"></div>
	
</body>
</html>