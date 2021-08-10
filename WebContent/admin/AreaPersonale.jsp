 <%@page import="model.ShowBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Cart, java.util.*, model.*"%>
<%
	Cart<ShowBean> carrello = (Cart<ShowBean>) request.getSession().getAttribute("carrello");
	if(carrello == null){
		response.sendRedirect(response.encodeURL("./SpettacoliControl?action=Area"));
		return;
	}
	List<ShowBean> showCart = carrello.getItems();
	Collection<FilmBean> showFilm = ( Collection<FilmBean> ) application.getAttribute("films");
	if(showFilm==null){
		 response.sendRedirect(response.encodeURL("film.jsp"));
		 return;
	}
	UserBean admin = (UserBean)request.getSession().getAttribute("utente");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="../css/AreaPersonale.css" type="text/css" rel="stylesheet">
<link href="../css/modificheAdmin.css" type="text/css" rel="stylesheet">
<script src="../js/jquery-3.5.1.min.js"></script>
<script src="../js/UpdateUser.js"></script>
<script src="../js/adminModifica.js"></script>
<title>Area Personale</title>
</head>
<body>

	<header>
	<nav>
		<a href="<%=response.encodeURL("home.jsp")%>">Home</a>
		<a href="<%=response.encodeURL("locals.jsp")%>">Locali</a>
		<div class = "dropdown">
			<button class="dropbtn">Modifica<i class="fa fa-caret-down"></i></button>
			<div class="dropdown-content">
				<button onclick="deleteUser()">Elimina</button>
			</div>
		</div>
	</nav>
	</header>
	
	
	<section id="DataUser">
		<h1 id="intestazione"><%=admin.getName().toUpperCase()+" "+admin.getSurname().toUpperCase()+"-IL TUO PROFILO"%></h1>
		<fieldset id="dati">
			<legend>I tuoi dati</legend>
			<label for="nomeU"><b>Nome</b></label><br>
	    	<i name="nomeU"><%=admin.getName() %></i><br>
	    	
	    	<label for="cognomeU"><b>Cognome</b></label><br>
	    	<i name="cognomeU"><%=admin.getSurname() %></i><br>
	    	
	    	<label for="nascitaU"><b>Data di nascita</b></label><br>
	    	<i name="nascitaU"><%=admin.getBirth() %></i><br>
	    	
	    	<label for="emailU"><b>Email</b></label><br>
	    	<i name="emailU"><%=admin.getEmail() %></i><br>
	    	
	    	<label for="numPrenotazioni"><b>Prenotazioni effettuate</b></label><br>
	    	<i name="numPrenotazioni"><%=admin.getAccessNumber() %></i><br>
		</fieldset>
		
		<fieldset id="datiAggiornati">
			<legend>Aggiorna i tuoi dati</legend>
			<form action="<%=response.encodeURL("UserControl?action=update")%>" method="post" onSubmit="return formValidationRegistration();">
			    
			    <label for="nome"><b>Nome</b></label><br>
			    <input type="text" placeholder="Enter Name" name="nome" id="regFormNome" class="datibox">
			    <span id="nomeSpan" style="display:none">Formato nome non valida</span><br>
			    
			    <label for="cognome"><b>Cognome</b></label><br>
			    <input type="text" placeholder="Enter Surname" name="cognome" id="regFormCognome" class="datibox">
			    <span id="cognomeSpan" style="display:none">Formato cognome non valida</span><br>
			    
			    <label for="nascita"><b>Data di nascita</b></label><br>
			    <input type="date" placeholder="Enter date in the format yyyy-mm-dd" name="nascita" id="regFormBorn" class="datibox">
			    <span id="nascitaSpan" style="display:none">Formato nascita non valida</span><br>
			
			    <label for="email"><b>Email</b></label><br>
			    <input type="text" placeholder="Enter Email" name="email" id="regFormEmail" class="datibox">
			    <span id="emailSpan" style="display:none">Formato nascita non valida</span><br>
			
			    <label for="newPassword"><b>Nuova Password</b></label><br>
			    <input type="password" placeholder="Inserisci la nuova Password" name="newPassword" id="regFormPsw" class="datibox">
			    <span id="pswSpan" style="display:none">Formato nascita non valida</span><br>
			
			    <label for="confirmPassword"><b>Conferma nuova Password</b></label><br>
			    <input type="password" placeholder="Conferma la nuova password" name="confirmPassword" id="regFormRPsw" class="datibox">
			    <span id="pswRepeatSpan" style="display:none">Formato nascita non valida</span><br>
			    
			    <p id="mexConfirm">Se confermi di aggiornare i dati dovrai effettuare una nuova login. Confermare?</p>
			    <input type="submit" value="Conferma" id="confirmUpdate">
			    <span id="noUpdate" style="display:none; color:red">Nessun campo d'aggiornare!</span>
			</form>
			<button id="annulla">Annulla</button>
			<button id="updatebtn">Aggiorna Dati</button>
		</fieldset>
	</section>

	<fieldset id="cartContainer">
		<legend><img src="../image/cart.png" id="cart"></legend>
		<table>
		<%
		String photo=null;
			if(!showCart.isEmpty()){
				for(ShowBean sh: showCart){
					for(FilmBean fb: showFilm){
						if(fb.getTitle().equals(sh.getTitleFilm())){
							photo = fb.getPhoto(); break;}
						}%>
				
					<tr>
						<td class="infoSpett">
						<img src="../image/immagini_film/<%=photo%>" width="150" height="200" class="img">
						<p id="informationSpett">
						<label>titolo: </label><%=sh.getTitleFilm()%> <br/> 
						<label>data: </label><%=sh.getDate()%> <br/> 
						<label>ora: </label><%=sh.getHour()%> <br/> 
						<label>sala: </label><%=sh.getHall()%> <br/> 
						<label>prezzo: </label><%=sh.getPrice()%><br>
						<label>luogo: </label><%=sh.getAddress()%>
						</p>
						</td>
						
						<td class="delete">
							<form action="<%=response.encodeURL("SpettacoliControl?action=deleteTicket")%>" method="post">
							<input type="image" src="../image/cestino.png" id="cestino" name="id" value="<%=sh.getShowId()%>">
							</form>
						</td>
						
					</tr>
		  	  <%}
		  	}
		  	else{%>
		  		<p class="mex">Carrello vuoto</p>
		  	<%}%>
		</table>
		<% if(!showCart.isEmpty()){%>
		
		<form action="<%=response.encodeURL("SpettacoliControl?action=confirm")%>" method="post">
			<input type="submit" id="conferma" name="confirm" value="Conferma prenotazione">
		</form>
		
		<% } %> 
		
		<% String ok = (String) request.getAttribute("ok"); 
			if(ok != null){
		%>
			<p class="mex"><%=ok%></p>
		<% } %>
	</fieldset>
	
	<!-- prenotazioni confermate -->
	<%
		Collection<ShowBean> prenotazioniConfermate = (Collection<ShowBean>) request.getSession().getAttribute("prenotati");
		
		if(prenotazioniConfermate == null){
			response.sendRedirect(response.encodeURL("./SpettacoliControl?action=recoveryPrenotazioni"));
			return;
		}
		String aggiorna = (String) request.getAttribute("aggiorna");
		if(aggiorna != null){
			response.sendRedirect(response.encodeURL("./SpettacoliControl?action=recoveryPrenotazioni"));
			return;
		}%>
		<fieldset id="prenotazioniContainer">
			<legend>Modifica prenotazioni effettuate</legend>
			<table>
			<%
			 String foto=null;
			 for(ShowBean sh: prenotazioniConfermate){
				for(FilmBean fib: showFilm){
					if(fib.getTitle().equals(sh.getTitleFilm())){
						foto = fib.getPhoto();
					}
				}
			%>
				<tr>
						<td class="infoSpett">
						<img src="../image/immagini_film/<%=foto %>" width="150" height="200" class="img">
						<p id="informationSpett">
						<label>titolo: </label><%=sh.getTitleFilm()%> <br/> 
						<label>data: </label><%=sh.getDate()%> <br/> 
						<label>ora: </label><%=sh.getHour()%> <br/> 
						<label>sala: </label><%=sh.getHall()%> <br/> 
						<label>prezzo: </label><%=sh.getPrice()%><br>
						<label>luogo: </label><%=sh.getAddress()%>
						</p>
						</td>
						
						<td class="delete">
							<form action="<%=response.encodeURL("SpettacoliControl?action=deletePrenotazione")%>" method="post">
							<input disabled type="image" src="../image/cestino.png" id="cestino2" name="id" value="<%=sh.getShowId()%>">
							</form>
						</td>
						
					</tr>
			
			<%} %>
			</table>
			
		<%if(!prenotazioniConfermate.isEmpty()){%>
			<button id="btnmodify" onclick="document.getElementById('cestino2').disabled=false">Modifica</button>
		<%	} 
		  else{%>
		  	<b class="mex">Nessuna prenotazione effettuata modificabile!</b>
		<%} %>
	</fieldset>
	
	<!-- elimina -->
	<div id="id01" class="modal">
	  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
	  <form class="modal-content" action="<%=response.encodeURL("UserControl?action=deleteUserFromAdmin")%>" method="post">
	    <div class="container">
	      <h1 class="h1Modifiche">Elimina</h1>
	      <p class="pModifiche">inserisci l'utente da eliminare</p>
	      <hr>
	      <select name="listUser" id="listUser">
	      </select>
	      <div class="clearfix">
	        <button class="Formbutton" type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Annulla</button>
	        <button class="Formbutton" type="submit" class="signupbtn">Elimina</button>
	      </div>
	    </div>
	  </form>
	</div>
	
	<% String confirmDelete = (String) request.getAttribute("utenteEliminato");
		if(confirmDelete != null){
	%>		<script>alert("utente eliminato!")</script>
	  <%} %>
	
</body>
</html>