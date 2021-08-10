<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>
    
<%
		
		Collection<FilmBean> films = (Collection<FilmBean>) application.getAttribute("films");
	
		if(films==null){
			 response.sendRedirect(response.encodeURL("film.jsp"));
			 return;
		}
		UserBean user = (UserBean)session.getAttribute("utente");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Locali</title>
	<link href="../css/searchbar.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="../css/locals.css" type="text/css" rel="stylesheet">
	<link href="../css/ChoiceCinemaStyle.css" type="text/css" rel="stylesheet">
	<script src="../js/jquery-3.5.1.min.js"></script>
	<script src="../js/ChoiceCinema.js"></script>
</head>
<body >
	<header>
		<nav>
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href="<%=response.encodeURL("film.jsp")%>" class="linknav">Film</a>
			<a href="" class="linknav">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>" class="linknav">Servizi</a>
			
			<div class="dropdown-userOptions">
			  <button class="dropbtn-user"> <%=user.getName()%> <%=user.getSurname()%></button>
			  <div class="dropdown-content-options">
			  	<a href="<%=response.encodeURL("AreaPersonale.jsp")%>" id="area">Area personale</a>
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

</body>
</html>