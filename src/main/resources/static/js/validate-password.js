document.querySelector('[name="submit"]').addEventListener('click',
	function(e) {
	
		const passwordElement = document.querySelector('#password');
		const confirmpasswordElement = document.querySelector('#confirm_password');
		
		passwordElement.value != confirmpasswordElement.value ? confirmpasswordElement.setCustomValidity("A két jelszónak meg kell egyeznie!") : confirmpasswordElement.setCustomValidity('');
	}
);