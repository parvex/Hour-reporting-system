angular.module('reportingApp')
.controller('RegisterController', function($http, $scope, AuthService) {
	$scope.submit = function() {
		$http.post('api/register', $scope.appUser).success(function(res) {
			$scope.appUser = null;
			$scope.confirmPassword = null;
			$scope.register.$setPristine();
			$scope.message = "Registration successfull !";
		}).error(function(error) {
			$scope.message = error.message;
		});
	};
});
