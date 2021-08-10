

function formValidationRegistration(){
	var name =	document.getElementById("regFormNome");
	var	surname =	document.getElementById("regFormCognome");
	var born =	document.getElementById("regFormBorn");
	var email =	document.getElementById("regFormEmail");
	var password =	document.getElementById("regFormPsw");
	var repPassword =	document.getElementById("regFormRPsw");
	
	if(name_validation(name)){
		if(surname_validation(surname)){
			if(born_validation(born)){
				if(email_validation(email)){
					if(password_validation(password,7,16)){
						if(repPassword_validation(repPassword,password)){
							return;
						}
					}
				}
			}
		}
	}
	return false;
}

function name_validation(nome){ //allLetter
	var letters = /^[A-Za-z\s]+$/;
	if(nome.value.match(letters)){
		return true;
	}
	else{
		$("#nomeSpan").show();
		$("#nomeSpan").css("color","red");
		$("#regFormNome").css("background-color","red");
		return false;
	}
}

function surname_validation(cognome){
	var letters = /^[A-Za-z\s]+$/;
	if(cognome.value.match(letters)){
		return true;
	}
	else{
		$("#cognomeSpan").show();
		$("#cognomeSpan").css("color","red");
		$("#regFormCognome").css("background-color","red");
		return false;
	}
}

function born_validation(nascita){
	var nasc = nascita.value;
	var year = nasc.substring(0, 4);
	var month = nasc.substring(5, 7);
	var day = nasc.substring(8);
	var numbers = /^[0-9]+$/;
	
	if(year.match(numbers) && month.match(numbers) && day.match(numbers) && (nasc.charAt(4) == "-") && (nasc.charAt(7) == "-")){
		return true;
	}
	else{
		$("#nascitaSpan").show();
		$("#nascitaSpan").css("color","red");
		$("#regFormBorn").css("background-color","red");
		return false;
	}
	
}

function email_validation(mail){
	var x = mail.value
	var atpos = x.indexOf("@");
	var dotpos = x.lastIndexOf(".");
	 if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
		 $("#emailSpan").show();
		 $("#emailSpan").css("color","red");
	        $("#regFormEmail").css("background-color","red");
	        return false;
	 }
	 else{
		 return true;
	 }
}

function password_validation(psw,mx,my){
	var psw_len = psw.value.length;
	if (psw_len == 0 || psw_len >= my || psw_len < mx){
		$("#pswSpan").show();
		$("#pswSpan").css("color","red");
		$("#regFormPsw").css("background-color","red");
		return false;
	}
	return true;
}

function repPassword_validation(repPsw, psw){
	if(repPsw.value != psw.value){
		$("#pswRepeatSpan").show();
		$("#pswRepeatSpan").css("color","red");
		$("#regFormRPsw").css("background-color","red");
		return false;
	}
	else{
		return true;
	}
}
$(document).ready(function(){

$("#regFormNome").blur(function(){
	$(this).css("background-color", "yellow");
	$("#nomeSpan").hide();	  
});
	
$("#regFormCognome").blur(function(){
	  $(this).css("background-color", "yellow");
	  $("#cognomeSpan").hide();
});

$("#regFormBorn").blur(function(){
	  $(this).css("background-color", "yellow");
	  $("#nascitaSpan").hide();
});

$("#regFormEmail").blur(function(){
	  $(this).css("background-color", "yellow");
	  $("#emailSpan").hide();
});

$("#regFormPsw").blur(function(){
	  $(this).css("background-color", "yellow");
	  $("#pswSpan").hide();

});

$("#regFormRPsw").blur(function(){
	  $(this).css("background-color", "yellow");
	  $("#pswRepeatSpan").hide();
});

});