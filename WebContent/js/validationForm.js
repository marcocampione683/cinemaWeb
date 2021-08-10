////////////////////////////////////VALIDAZIONE FORM LOGIN
function formValidationLogin()
{
	var user = document.getElementById("loginFormUs");
	var pass = document.getElementById("loginFormPw"); 
	
	if(user_validation(user)){
		if(pass_validation(pass,7,16)){
			return;
		}
	}
	return false;
}

function user_validation(username){

	var x = username.value;
    var atpos = x.indexOf("@");
    var dotpos = x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
        $("#userSpan").show();
        username.focus();
        return false;
    }
    else{
    	return true;
    }
} 

function pass_validation(password,mx,my){
	var password_len = password.value.length;
	if (password_len == 0 ||password_len >= my || password_len < mx){
		$("#passSpan").show();
		password.focus();
		return false;
	}
	return true;
}

$(function(){
	$("#loginFormUs").click(function(){
		$("#userSpan").hide();
	});
});

$(function(){
	$("#loginFormPw").click(function(){
		$("#passSpan").hide();
	});
});

/////////////////////////////////////////////////////////////VALIDAZIONE FORM REGISTRAZIONE