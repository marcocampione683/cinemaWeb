<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.ShowBean, model.FilmBean, model.CinemaBean"%>

<% 
	   Collection<FilmBean> films =(Collection<FilmBean>) application.getAttribute("films");
	   if(films==null){
		   response.sendRedirect(response.encodeURL("film.jsp"));
		   return;
	   }
	   
	   Collection<ShowBean> spettacoli = (Collection<ShowBean>)application.getAttribute("spettacoli");
	   if(spettacoli==null){
		   response.sendRedirect(response.encodeURL("locals.jsp"));
		   return;
	   }
	   CinemaBean cinema = (CinemaBean) application.getAttribute("Cinema");
	   if(cinema==null){
		   response.sendRedirect(response.encodeURL("locals.jsp"));
		   return;
	    }
	   ArrayList<FilmBean> filmInProgrammazione = new ArrayList<FilmBean>();
	   
	   if(films!=null){
			for(FilmBean f: films){
				for(ShowBean show: spettacoli){
					if(f.getTitle().equals(show.getTitleFilm())){
						filmInProgrammazione.add(f);
						break;
					}
				}
			}
		}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link href="css/Spettacoli.css" type="text/css" rel="stylesheet">
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/Spettacoli.js"></script>
<script src="js/validationForm.js"></script>
</head>
<body>
	<header>
		<nav>
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href="<%=response.encodeURL("film.jsp")%>" class="linknav">Film</a>
			<a href="<%=response.encodeURL("locals.jsp")%>" class="linknav">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>" class="linknav">Servizi</a>
		</nav>
	</header>
	
	<h1 id="nomeCinema"><%=cinema.getName()%></h1>
	<img src="image/immagini_cinema/<%=cinema.getName()%>.jpg" id="img_cinema"> 

	<section class="sez_sx">
		<h3>PROGRAMMAZIONE</h3>
		<div id="boxfilm">
		<%
			for(FilmBean fIp: filmInProgrammazione){%>
				<input type="image" src="image/immagini_film/<%=fIp.getPhoto()%>" id="locandina" name="film" value="<%=fIp.getTitle()%>" onclick="viewShow(this.value)"><br>
		  <%}%>
		</div>
	</section>

	<section class="sez_dx">
		<h3>SPETTACOLI</h3>
		<div id="totalContainer">	
		</div>
	</section>
	
	<div id="windowLogin" class="modalLogin">
  
	  <form class="modal-contentLogin animate" action="<%=response.encodeURL("LoginControl?chiamante=Spettacoli.jsp")%>" method="post" onSubmit="return formValidationLogin()"><!-- Inserire la action per la login -->
	    <div class="imgcontainer">
	      <span onclick="document.getElementById('windowLogin').style.display='none'" class="closeLogin" title="Close Modal">&times;</span>
	      <img src="image/key-icon.png" class="keyImg">
	    </div>
	
	    <div class="containerLogin">
	      <label for="username"><b>Username</b></label><br>
	      <span id="userSpan">Username non valida</span>
	      <input type="text" placeholder="Enter Username" name="username" id="loginFormUs" required>
	
	      <label for="password"><b>Password</b></label><br>
	      <span id="passSpan">password non valida</span>
	      <input type="password" placeholder="Enter Password" name="password" id="loginFormPw" required>
	       
	       <%if(request.getAttribute("error") != null) {%>
				<p id="error"></p>
				<script>viewError();</script>
		  <% } %>
	       
	      <button type="submit" class="logBtn">Login</button>
	     
	    </div>
	
	    <div class="containerLogin">
	      <button type="button" onclick="document.getElementById('windowLogin').style.display='none'" class="cancelbtnLogin">Annulla</button>
	    </div>
	  </form>
	</div>
	
	<footer>
		<fieldset>
			<legend>Come trovarci</legend>
			<iframe src="<%=cinema.getMaps() %>" width="1100" height="450" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
		</fieldset>
	</footer>
</body>
</html>