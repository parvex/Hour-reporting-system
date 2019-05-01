angular.module('reportingApp')
.controller('LoginController', function($http, $scope, $state, AuthService, $rootScope) {
	$scope.login = function() {
		$http({
			url : 'api/authenticate',
			method : "POST",
			params : {
				username : $scope.username,
				password : $scope.password
			}
		}).then(function(res) {
			$scope.password = null;
			if (res.data.token) {
				$scope.message = '';
				$http.defaults.headers.common['Authorization'] = 'Bearer ' + res.data.token;

				AuthService.setUser(res.data.user);
				$rootScope.$broadcast('LoginSuccessful');
				$state.go('home');
			} else {
				$scope.message = 'Authetication Failed !';
			}
		}, function(error) {
			$scope.message = 'Authetication Failed !';
		});
	};
});