<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Utente eliminato</title>
<link href="../css/profileDeleted.css" type="text/css" rel="stylesheet">
</head>
<body>
	<nav>
		<a href="<%=response.encodeURL("../home.jsp")%>">Home</a>
	</nav>
	<b>Eliminazione effettuata &#128577;</b>
	<a href="<%=response.encodeURL("../registration.jsp")%>" id="btnReg">Registrati</a>
</body>
</html>