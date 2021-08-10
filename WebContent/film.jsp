<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.FilmBean, java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Film</title>
<link href="css/film.css" type="text/css" rel="stylesheet">
<link href="css/LoginStyle.css" type="text/css" rel="stylesheet">
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/films.js"></script>
<script src="js/validationForm.js"></script>
<script src="js/LoginScript.js"></script>
</head>
<body>
	<header id="intestazione">
		<nav id="navigation">
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href="" class="linknav">Film</a>
			<a href="<%=response.encodeURL("locals.jsp")%>" class="linknav">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>" class="linknav">Servizi</a>
		</nav>
		
		<button id="login"><span>Login</span></button>
	
		<form id="regForm" action="<%=response.encodeURL("registration.jsp")%>">
			<button type="submit" id="registration"><span>Registrati</span></button>
		</form>
		
		<div id="button3"> 
		<input type="search" id="searching" name="searching" onkeyup="suggerimento(this.value)">
		<input type="image" src="image/search_button3.png" id="searchimg" onclick="suggerimento(document.getElementById('searching').value)">
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
 	
	<div id="Container">
		<div id="BoxFilmContainer">
 			<%  Collection<FilmBean> films = (Collection<FilmBean>) application.getAttribute("films"); 
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
						<input type="image" src="image/immagini_film/<%=fbean.getPhoto()%>" name="film" value="<%=fbean.getTitle()%>" width="288" height="512" id="locandina">
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