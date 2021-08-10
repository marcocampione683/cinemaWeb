<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.ShowBean, model.FilmBean, model.CinemaBean"%>

<% 
	    Collection<FilmBean> films =(Collection<FilmBean>) application.getAttribute("films");
	    if(films==null){
		   response.sendRedirect(response.encodeURL("film.jsp"));
		   return;
	    }
		CinemaBean cinema = (CinemaBean) application.getAttribute("Cinema");
		if(cinema==null){
			   response.sendRedirect(response.encodeURL("locals.jsp"));
			   return;
		    }
		Collection<ShowBean> spettacoli;
		String aggiorna =(String) request.getAttribute("aggiorna");
			if(aggiorna!=null){
				response.sendRedirect(response.encodeURL("./SpettacoliControl?action=aggiorna&indirizzo="+cinema.getAddress()));
			    return;
			}
			else{
				spettacoli = (Collection<ShowBean>)application.getAttribute("spettacoli");
				if(spettacoli==null){
					   response.sendRedirect(response.encodeURL("locals.jsp"));
					   return;
				}
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
<link href="../css/Spettacoli.css" type="text/css" rel="stylesheet">
<link href="../css/modificheAdmin.css" type="text/css" rel="stylesheet">
<script src="../js/jquery-3.5.1.min.js"></script>
<script src="../js/adminModifica.js"></script>
<script src="../js/Spettacoli.js"></script>
</head>
<body>
	<header>
		<nav>
			<a href="<%=response.encodeURL("home.jsp")%>" class="linknav">Home</a>
			<a href="<%=response.encodeURL("film.jsp")%>" class="linknav">Film</a>
			<a href="<%=response.encodeURL("locals.jsp")%>" class="linknav">Locali</a>
			<a href="<%=response.encodeURL("Service.jsp")%>" class="linknav">Servizi</a>
			
			<a href="<%=response.encodeURL("AreaPersonale.jsp")%>">
          		<img src="../image/cart.png" id="carrello">
        	</a>
		</nav>
		<div class = "dropdown">
			<button class="dropbtn">Modifica<i class="fa fa-caret-down"></i></button>
			<div class="dropdown-content">
				<button onclick="document.getElementById('id01').style.display='block'">Aggiungi</button>
				<button onclick="openFormDelete()">Elimina</button><!--  document.getElementById('id02').style.display='block'-->
				<button onclick="openFormUpdate()">Aggiorna</button><!-- document.getElementById('id03').style.display='block'-->
			</div>
		</div>
	</header>
	
	<h1 id="nomeCinema"><%=cinema.getName()%></h1>
	<img src="../image/immagini_cinema/<%=cinema.getName()%>.jpg" id="img_cinema"> 

	<section class="sez_sx">
		<h3>PROGRAMMAZIONE</h3>
		<div id="boxfilm">
		<%
			for(FilmBean fIp: filmInProgrammazione){%>
				<input type="image" src="../image/immagini_film/<%=fIp.getPhoto()%>" id="locandina" name="film" value="<%=fIp.getTitle()%>" onclick="viewShow(this.value)"><br>
		  <%}%>
		</div>
	</section>

	<section class="sez_dx">
		<h3>SPETTACOLI</h3>
		<div id="totalContainer">	
		</div>
	</section>
	
	<div id="messaggio"></div>
	
	<!-- aggiungi -->
	<div id="id01" class="modal">
	  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
	  <form class="modal-content" action="<%=response.encodeURL("SpettacoliControl?action=insert")%>" method="post" onSubmit="return formValidationModify('spettacoli','aggiungi');">
	    <div class="container">
	      <h1 class="h1Modifiche">Aggiungi</h1>
	      <p class="pModifiche">aggiungi un nuovo spettacolo ad un film</p>
	      <hr>
	      <label for="id_spettacolo"><b>Id spettacolo</b></label><br>
	      <span id="spanId" style="display:none">Formato Id non valida</span>
	      <input type="text" placeholder="Inserisci id spettacolo" name="id_spettacolo" id="id_spettacolo"required>
	
	      <label for="titolo_film"><b>Titolo film</b></label><br>
	      <input type="text" placeholder="Inserisci titolo del film" name="titolo_film" required>
	
	      <label for="data"><b>Data</b></label><br>
	      <span id="spanData" style="display:none">Formato data non valida</span>
	      <input type="text" placeholder="yyyy-mm-dd" name="data" id="data" required>
	      
	      <label for="indirizzo"><b>Indirizzo</b></label><br>
	      <input type="text" placeholder="Inserisci indirizzo" name="indirizzo" required>
	      
	      <label for="ora"><b>ora</b></label><br>
	      <span id="spanOra" style="display:none">Formato ora non valida</span>
	      <input type="text"  placeholder="hh:mm:ss" name="ora" id="ora" required>
	      
	      <label for="sala"><b>Sala</b></label><br>
	      <span id="spanSala" style="display:none">Formato sala non valida</span>
	      <input type="text" placeholder="Inserisci sala" name="sala" id="sala" required>
	      
	      <label for="prezzo"><b>Prezzo</b></label><br>
	      <input type="number" id="prezzo" name="prezzo" min="1" max="10" step="0.5" value="5" required>
	    
	   
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
	  <form class="modal-content" action="<%=response.encodeURL("SpettacoliControl?action=delete")%>" method="post">
	    <div class="container">
	      <h1 class="h1Modifiche">Elimina</h1>
	      <p class="pModifiche">inserisci lo spettacolo da eliminare</p>
	      <hr>
	      <select name="listShow" id="listShow">
	      </select>
<!-- 	  <label for="id_spettacolo"><b>Id spettacolo</b></label>
	      <input type="text" placeholder="Inserisci id spettacolo" name="id_spettacolo" required> -->
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
	  <form class="modal-content" action="<%=response.encodeURL("SpettacoliControl?action=update")%>" method="post" onSubmit="return formValidationModify('spettacoli','aggiorna');">
	    <div class="container">
	      <h1 class="h1Modifiche">Aggiorna</h1>
	      <hr>
	      <p class="pModifiche">Scegli uno spettacolo presente nella lista che vuoi modificare.</p>
	   <!--   <label for="id_old_spettacolo"><b>Id spettacolo</b></label><br>
	      <span id="spanIdOld" style="display:none">Formato Id non valida</span>
	      <input type="text" placeholder="Inserisci id spettacolo" name="id_old_spettacolo" id="id_old_spettacolo" required> -->
	      <select name="id_old_spettacolo" id="listShowUpdate">
	      </select>
	      
	      <p class="pModifiche">aggiorna i campi del film</p>
	      
	      <label for="id_spettacolo"><b>Id spettacolo</b></label><br>
	      <span id="spanIdUpdate" style="display:none">Formato Id non valida</span>
	      <input type="text" placeholder="Inserisci id spettacolo" name="id_spettacolo" id="id_spettacoloUpdate" >
	
	      <label for="titolo_film"><b>Titolo film</b></label><br>
	      <input type="text" placeholder="Inserisci titolo del film" name="titolo_film" >
	
	      <label for="data"><b>Data</b></label><br>
	      <span id="spanDataUpdate" style="display:none">Formato data non valida</span>
	      <input type="text" placeholder="Inserisci data" name="data" id="dataUpdate">
	      
	      <label for="indirizzo"><b>Indirizzo</b></label><br>
	      <input type="text" placeholder="Inserisci indirizzo" name="indirizzo" >
	      
	      <label for="ora"><b>ora</b></label><br>
	      <span id="spanOraUpdate" style="display:none">Formato ora non valida</span>
	      <input type="text"  placeholder="Inserisci ora" name="ora" id="oraUpdate">
	      
	      <label for="sala"><b>Sala</b></label><br>
	      <span id="spanSalaUpdate" style="display:none">Formato sala non valida</span>
	      <input type="text" placeholder="Inserisci sala" name="sala" id="salaUpdate">
	      
	      <label for="prezzo"><b>Prezzo</b></label><br>
	      <input type="number" id="prezzoUpdate" name="prezzo" min="1" max="10" step="0.5" value="5">
	     
	      <div class="clearfix">
	        <button class="Formbutton" type="button" onclick="document.getElementById('id03').style.display='none'" class="cancelbtn">Annulla</button>
	        <button class="Formbutton" type="submit" class="signupbtn">Aggiorna</button>
	      </div>
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