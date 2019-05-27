angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $scope,
    $cookies,
    EmployeesService
  ) {
    $scope.projects = [];
    $scope.usedLeave;
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

    $scope.getUsedLeave = function() {
      if($cookies.getObject("user").id)
      {
        EmployeesService.getUsedLeave($cookies.getObject("user").id).then(function (response) {
          $scope.usedLeave = response;
        }, function (error) {
          console.log("Error getting used leave");
        })
      }
    }
  });
