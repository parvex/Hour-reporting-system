angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $http,
    $scope
  ) {
    $scope.projects = [];
    $scope.displayReports = function() {
      $http({
        url: "api/getProjects",
        method: "POST",
      params: {
        username: $scope.username
      }
      }).then(
        function (res) {
          $scope.projects = res;
        },
        function (error) {
          console.log(error);
        }
      )
    }
  });
