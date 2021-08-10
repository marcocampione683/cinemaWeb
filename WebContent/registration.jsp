<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="css/registration.css" type="text/css" rel="stylesheet">
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/validationRegistrationForm.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
	<div class="icon-bar">
	  <a class="active" href="<%=response.encodeURL("home.jsp")%>"><i class="fa fa-home"></i></a> 
	</div>

	<form action="<%=response.encodeURL("UserControl?action=newuser")%>" method="post" name="regis" onSubmit="return formValidationRegistration();">
	  <div class="container">
	    <h1>Registrazione</h1>
	    <p>Inserisci le tue informazioni nei rispettivi campi.</p>
	    <hr>
	    <label for="nome"><b>Nome</b></label>
	    <span id="nomeSpan">&#9888; Il nome deve avere solo caratteri alfabetici</span>
	    <input type="text" placeholder="Enter Name" name="nome" required id="regFormNome">
	    
	    <label for="cognome"><b>Cognome</b></label>
	    <span id="cognomeSpan">&#9888; Il cognome deve avere solo caratteri alfabetici</span>
	    <input type="text" placeholder="Enter Surname" name="cognome" required  id="regFormCognome">
	     
	    <label for="nascita"><b>Data di nascita</b></label>
	    <span id="nascitaSpan">&#9888; Il formato corretto da inserire è il seguente: YYYY-MM-DD </span>
	    <input type="text" placeholder="Enter date in the format yyyy-mm-dd" name="nascita" required id="regFormBorn">
		
	    <label for="email"><b>Email</b></label>
	    <span id="emailSpan">&#9888; Email non valida. Inserire parametro valido.</span>
	    <input type="text" placeholder="Enter Email" name="email" required id="regFormEmail">
	
	    <label for="psw"><b>Password</b></label>
	    <span id="pswSpan">&#9888; Formato password non valida</span>
	    <input type="password" placeholder="Enter Password" name="psw" required id="regFormPsw">
		
	    <label for="psw-repeat"><b>Repeat Password</b></label>
	     <span id="pswRepeatSpan">&#9888; La password non coincide</span>
	    <input type="password" placeholder="Repeat Password" name="psw-repeat" required id="regFormRPsw">
	    <hr>
	
	    <button type="submit" class="registerbtn">Registrati</button>
	  </div>

	</form>

</body>
</html>

