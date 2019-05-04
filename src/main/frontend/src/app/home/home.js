angular
  .module("hourReportingSystem")
  .controller("HomeController", function($http, $scope, AuthService) {
    $scope.user = AuthService.getUser();
  });
