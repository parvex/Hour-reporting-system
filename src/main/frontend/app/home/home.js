angular.module('reportingApp')
.controller('HomeController', function($http, $scope, AuthService) {
	$scope.user = AuthService.getUser();
	;
	;
	console.log($scope.user);
});
