angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $http,
    $scope,
    AuthService
  ) {
    $scope.projects = [];
    $scope.user = AuthService.getUser();
    $scope.username = $scope.user.username;
    $scope.getProjects = function() {
      $http({
        url: "api/getProjects",
        method: "GET",
      params: {
        username: AuthService.getUser().username
      }
      }).then(
        function (res) {
          $scope.projects = res.data;
        },
        function (error) {
          console.log(error);
        }
      )
    }
  });
