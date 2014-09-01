<!doctype html>
<html ng-app>
<head>
<title>Spring 3.2.1 MVC Example: Hello World - Crunchify.com</title>
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>
<script>
	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname
				.indexOf("/", 2));
	}
	var context = getContextPath();

	function LoginController($scope, $http, $window) {

		$scope.register = function(user) {
			$scope.message = "";
			$http.post(context + '/login/register_user', user).success(function(response) {
				if (response.success) {
					$scope.message = "";
					$window.location.href = context + '/app';
				} else
					$scope.message = response.message;
			}).error(function() {
				alert("An error ocurred");
			});
		};
	}
</script>
</head>
<body>
	<h2>Login</h2>
	<div ng-controller="LoginController" class="container-fluid">
		<div class="row">
			<div class="col-md-1">First name:</div>
			<div class="col-md-1">
				<input type="text" id="firstName" name="firstName"
					ng-model="user.firstName"> <br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">Last name:</div>
			<div class="col-md-1">
				<input type="text" id="lastName" name="lastName"
					ng-model="user.lastName"> <br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">User name:</div>
			<div class="col-md-1">
				<input type="text" id="userName" name="userName"
					ng-model="user.userName"> <br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">Address:</div>
			<div class="col-md-1">
				<input type="text" id="address" name="address"
					ng-model="user.address"> <br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">Phone:</div>
			<div class="col-md-1">
				<input type="text" id="phone" name="phone" ng-model="user.phone">
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">Email:</div>
			<div class="col-md-1">
				<input type="text" id="email" name="email" ng-model="user.email">
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">Credit card:</div>
			<div class="col-md-1">
				<input type="text" id="creditCard" name="creditCard"
					ng-model="user.creditCard"> <br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">SSN:</div>
			<div class="col-md-1">
				<input type="text" id="ssn" name="ssn" ng-model="user.ssn">
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">Age:</div>
			<div class="col-md-1">
				<input type="text" id="age" name="age" ng-model="user.age">
				<br>
			</div>
		</div>
		<button type="button" class="btn btn-primary"
			ng-click="register(user)">Register</button>
		<div style="color: red">{{message}}</div>
	</div>
</body>
</html>