angular.module('reportingApp')
.controller('RegisterController', function($http, $scope, AuthService) {
	$scope.submit = function() {
		$http.post('api/register', $scope.appUser).then(function(res) {
			$scope.appUser = null;
			$scope.confirmPassword = null;
			$scope.userForm.$setPristine();
			$scope.message = "Registration successfull !";
		}, function(error) {
			$scope.message = error.message;
		});
	};
});
