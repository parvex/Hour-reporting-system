angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $scope,
    $cookies,
    EmployeesService
  ) {
    $scope.projects = [];
    $scope.getProjects = function() {
      if($cookies.getObject("user").id)
      {
        EmployeesService.getProjectsHours($cookies.getObject("user").id).then(function (response) {
            $scope.projects = response;
        }, function(error) {
            console.log("Error receiving projects");
        });
      }
    }
  });
