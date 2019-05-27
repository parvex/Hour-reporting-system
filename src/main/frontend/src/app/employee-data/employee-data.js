angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $scope,
    $cookies,
    $stateParams,
    EmployeesService
  ) {
    $scope.projects = [];
    $scope.usedLeave;
    var userId = null;

    $scope.getProjects = function() {
      if($stateParams.employeeId === null) {
        userId = $cookies.getObject("user").id;
      }
      else {
        userId = $stateParams.employeeId;
      }

      if(userId)
      {
        EmployeesService.getProjectsHours(userId).then(function (response) {
            $scope.projects = response;
        }, function(error) {
            console.log("Error receiving projects");
        });
      }
    }

    $scope.getUsedLeave = function() {
      if($stateParams.employeeId === null) {
        userId = $cookies.getObject("user").id;
      }
      else {
        userId = $stateParams.employeeId;
      }

      if(userId)
      {
        EmployeesService.getUsedLeave(userId).then(function (response) {
          $scope.usedLeave = response;
        }, function (error) {
          console.log("Error getting used leave");
        })
      }
    }
  });
