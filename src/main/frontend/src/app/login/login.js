angular
  .module("hourReportingSystem")
  .controller("LoginController", function(
    $http,
    $scope,
    $state,
    AuthService,
    $rootScope
  ) {
    $scope.login = function() {
      $http({
        url: "api/authenticate",
        method: "POST",
        params: {
          username: $scope.username,
          password: $scope.password
        }
      }).then(
        function(res) {
          $scope.password = null;
          if (res.data.token) {
            $scope.message = "";

            AuthService.setUser(res.data);
            $rootScope.$broadcast("LoginSuccessful");
            $state.go("calendar");
          } else {
            $scope.message = "Authetication Failed!";
          }
        },
        function(error) {
          $scope.message = "Authetication Failed!";
        }
      );
    };
  });
