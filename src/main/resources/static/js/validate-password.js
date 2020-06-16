function validatePassword() {
	var password = $('#password').val();
	var confirmpassword = $('#confirm_password').val();
	
	password != confirmpassword ? document.getElementById("confirm_password").setCustomValidity("A két jelszónak meg kell egyeznie!") : document.getElementById("confirm_password").setCustomValidity('');
}

document.getElementsByName("submit")[0].onclick = validatePassword;