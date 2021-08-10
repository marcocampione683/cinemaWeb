$(function(){
	$("#login").click(function(){
		$("#myModal").show();
	});
});

$(function(){
	$(".close").click(function(){
		$("#myModal").hide();
	});
});

function viewError(){
	$("#error").text("Utente non autorizzato!");
	$("#myModal").show();
}