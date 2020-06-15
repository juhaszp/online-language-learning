function validatePassword() {
	var password = $('#password').val();
	var confirmpassword = $('#confirmpassword').val();
	
	password != confirmpassword ? document.getElementById("confirmpassword").setCustomValidity("A két jelszónak meg kell egyeznie!") : document.getElementById("confirmpassword").setCustomValidity('');
}

document.getElementsByName("registration")[0].onclick = validatePassword;