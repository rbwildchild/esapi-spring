<!doctype html>
<html ng-app>
<head>
<title>Spring 3.2.1 MVC Example: Hello World - Crunchify.com</title>
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>
<script>
	function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	var context = getContextPath();

	function LoginController($scope, $http, $window) {

		$scope.login = function(user) {
			$scope.message = "";
			$http.post(context + '/login/authenticate', user).success(function(response) {
				if (response.success){
					$scope.message = "";
					$window.location.href = context + '/app';
				}
				else
					$scope.message = response.message;
			}).error(function() {
				alert("An error ocurred");
			});
		};
		
		$scope.goRegister = function(user) {
			$window.location.href = context + '/login/register';
		};
	}
</script>
</head>
<body>
	<h2>Login</h2>
	<div ng-controller="LoginController" class="container-fluid">
		<div class="row">
			<div class="col-md-1">User:</div>
			<div class="col-md-1">
				<input type="text" id="user" name="user" ng-model="user.user">
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-1">Password:</div>
			<div class="col-md-1">
				<input type="password" id="password" name="password"
					ng-model="user.password"> <br>
			</div>
		</div>
		<button type="button" class="btn btn-primary" ng-click="login(user)">Login</button>
		<div style="color: red">{{message}}</div>
		<br>
		<button type="button" class="btn btn-danger" ng-click="goRegister()">Register</button>
	</div>
</body>
</html>