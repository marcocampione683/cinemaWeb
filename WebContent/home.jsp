<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.FilmBean, java.util.*"%>
<%
		
		Collection<FilmBean> films = (Collection<FilmBean>) application.getAttribute("films");

		if(films==null){
			response.sendRedirect(response.encodeURL("./FilmControl?action=insFilm"));
			return;
		}
		
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="js/jquery-3.5.1.min.js"></script>
	<script src="js/LoginScript.js"></script>
	<script src="js/validationForm.js"></script>
	<script src="js/SuccesfulRegistration.js"></script>
	<link href="css/home.css" type="text/css" rel="stylesheet">
	<link href="css/LoginStyle.css" type="text/css" rel="stylesheet">
	<link href="css/MessageSuccesfulRegistration.css" type="text/css" rel="stylesheet">
	<title>CINEWEB</title>
</head>
<body>
	<header>
		<h1>CINEWEB</h1>
	</header>
	
	<div id="barraNavigazione">
		<img src="image/cinema-logo.jpg" id="logo"> 
		<nav id="barNav">
		
			<a href="<%=response.encodeURL("locals.jsp")%>" class="linknav">Locali</a>
			<a class="linknav" href="<%=response.encodeURL("film.jsp")%>">Film</a>
			<a class="linknav" href="<%=response.encodeURL("Service.jsp")%>">Servizi</a>
				
			<button id="login"><span>Login</span></button>
			<a href="<%=response.encodeURL("registration.jsp")%>" id="registration">Registrati</a>
		
		</nav>
	</div>

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
				<input type="image" src="image/immagini_film/<%=fbean.getPhoto()%>" name="film" value="<%=fbean.getTitle()%>" width="400" height="600">
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
	<script src="js/films.js"></script>
	
	<% String registrazione = (String) request.getAttribute("Registrazione"); System.out.println(registrazione);
	System.out.println("registrazione= "+registrazione);
	if(registrazione != null){ %>
	
	<div id="avvenutaRegistrazione" class="aR">
	  <!-- Modal content -->
	  <div class="aR-content">
	    <div class="aR-header">
	      <span class="aR-close">&times;</span>
	      <h2>Benvenuto</h2>
	    </div>
	    <div class="aR-body">
		     <p>Registrazione avvenuta con successo &#128512;. Effettua il login per accedere alla propria area utente.</p>
	    </div>
	  </div>
	</div>
	<%} %>
	
	<footer>
		<p>About us</p>
	</footer>
</body>
</html>