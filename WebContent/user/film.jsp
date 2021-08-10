<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%	UserBean user = (UserBean)session.getAttribute("utente"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Film</title>
<link href="../css/film.css" type="text/css" rel="stylesheet">
<script src="../js/jquery-3.5.1.min.js"></script>
<script src="../js/films.js"></script>
</head>
<body>
	<header id="intestazione">
		<nav id="navigation">
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href="" class="linknav">Film</a>
			<a href="<%=response.encodeURL("locals.jsp")%>" class="linknav">Locali</a>
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
</body>
</html>