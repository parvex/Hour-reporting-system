angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $scope,
    $cookies,
    $stateParams,
    $rootScope,
    EmployeesService
  ) {
    $scope.projects = [];
    $scope.usedLeave;
    $scope.noReports = true;
    var userId = $rootScope.userSelectId;

    $scope.getProjects = function() {
      if(userId === null) {
        userId = $cookies.getObject("user").id;
      }

      if(userId != null)
      {
        EmployeesService.getProjectsHours(userId).then(function (response) {
          if(response) {
            $scope.projects = response;
            $scope.noReports = false;
            $rootScope.userSelectId = null;
          }
        }, function(error) {
            console.log("Error receiving projects");
        });
      }
    }

    $scope.getUsedLeave = function() {
      if(userId === null) {
        userId = $cookies.getObject("user").id;
      }

      if(userId != null)
      {
        EmployeesService.getUsedLeave(userId).then(function (response) {
          $scope.usedLeave = response;
          $rootScope.userSelectId = null;
        }, function (error) {
          console.log("Error getting used leave");
        })
      }
    }
  });
