<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.FilmBean"%>
<% FilmBean film = (FilmBean) request.getAttribute("film");
   if(film == null){
	  response.sendRedirect(response.encodeURL("film.jsp"));
	  return;
    }
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Scheda Film</title>
	<link href="../css/SchedaFilmView.css" type="text/css" rel="stylesheet">
</head>
<body>
	<header>
		<nav>
			<a href="<%=response.encodeURL("home.jsp")%>">Home</a>
			<a href="<%=response.encodeURL("film.jsp")%>">Film</a>
			<a href="<%=response.encodeURL("locals.jsp")%>">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>">Servizi</a>
		</nav>
	</header>
		
	<iframe src="https://www.youtube.com/embed/<%=film.getVideo()%>" id="video"></iframe>
	
	
	<div id=infofilm>
		
		<img src="../image/immagini_film/<%=film.getPhoto()%>" width="288" height="512"><br>
		<div id="info">
			<p id="titolo">Titolo: <%=film.getTitle()%></p><br>
			<p id="genere">Genere: <%=film.getType()%></p><br>
			<p id="durata">Durata: <%=film.getDuration()%></p><br>
			<p id="regia">Regia: <%=film.getDirectedBy()%></p><br>
			<p id="trama">Trama: <%=film.getStory()%></p><br>
		</div>
	</div>	
</body>
</html>