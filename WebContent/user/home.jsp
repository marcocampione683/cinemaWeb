<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.FilmBean, java.util.*, model.UserBean"%>
<%
		
		Collection<FilmBean> films = (Collection<FilmBean>) application.getAttribute("films");
	
		if(films==null){
			response.sendRedirect(response.encodeURL("./FilmControl?action=insFilm"));
			return;
		}
		
		UserBean utente = (UserBean)request.getSession().getAttribute("utente");
		
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="../css/home.css" type="text/css" rel="stylesheet">
	<script src="../js/jquery-3.5.1.min.js"></script>
	<title>CINEWEB</title>
</head>
<body>
	<header>
		<h1>CINEWEB</h1>
	</header>
	
	<div id="barraNavigazione">
		<img src="../image/cinema-logo.jpg" id="logo"> 
		<nav id="barNav">
			<a href="<%=response.encodeURL("locals.jsp")%>" class="linknav">Locali</a>
			<a href="<%=response.encodeURL("film.jsp")%>" class="linknav">Film</a>
			<a href="<%=response.encodeURL("Service.jsp")%>" class="linknav">Servizi</a>
			
			<div class="dropdown-userOptions">
			  <button class="dropbtn-user"> <%=utente.getName()%> <%=utente.getSurname()%></button>
			  <div class="dropdown-content-options">
			  	<form action="<%=response.encodeURL("LogoutControl")%>" method="get" > 
     				<input type="submit" value="Logout"/>
				</form>
				<form action="<%=response.encodeURL("UserControl?action=deleteuser")%>" method="post" > 
     				<input type="email" value="<%=utente.getEmail()%>" name="email" style="display:none"/>
     				<input type="submit" value="Elimina">
				</form>
			    <a href="<%=response.encodeURL("AreaPersonale.jsp")%>" id="area">Area personale</a>
			  </div>
			</div>	 
		</nav>
	</div>

 	<b id="titlepres">FILM IN EVIDENZA</b>
 	
 	<section id="sectionSxImage"></section>
 	<section id="sectionDxImage"></section>
 	
 	<div class="slideshow-container">
 	<% 
		Iterator<FilmBean> itFilm = films.iterator();
		int count=0;
		while(itFilm.hasNext() && count<5) { 
			FilmBean fbean = itFilm.next();
				%>
		<div class="mySlides fade">
		  <div class="numbertext"><%=count+1 %> / 5</div>
		  	<form action="<%=response.encodeURL("FilmControl?action=filmView")%>" method="POST">
				<input type="image" src="../image/immagini_film/<%=fbean.getPhoto()%>" name="film" value="<%=fbean.getTitle()%>" width="400" height="600">
			</form>
		</div>
		<% 
			count++;
		} %>
	
		
		<a class="prev" onclick="plusSlides(-1)">&#10094;</a>
		<a class="next" onclick="plusSlides(1)">&#10095;</a>
	</div><br>

	<div>
	  <span class="dot" onclick="currentSlide(1)"></span> 
	  <span class="dot" onclick="currentSlide(2)"></span> 
	  <span class="dot" onclick="currentSlide(3)"></span>
	  <span class="dot" onclick="currentSlide(4)"></span> 
	  <span class="dot" onclick="currentSlide(5)"></span> 
	</div>
	<script src="../js/films.js"></script>
	<footer>
		<p>About us</p>
	</footer>
</body>
</html>