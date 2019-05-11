angular
  .module("hourReportingSystem")
  .controller("RegisterController", function($http, $scope, AuthService) {
    $scope.submit = function() {
      $http.post("api/users", $scope.appUser).then(
        function(res) {
          $scope.appUser = null;
          $scope.confirmPassword = null;
          $scope.userForm.$setPristine();
          $scope.message = "Registration successful !";
        },
        function(error) {
          $scope.message = error.message;
        }
      );
    };
  });
