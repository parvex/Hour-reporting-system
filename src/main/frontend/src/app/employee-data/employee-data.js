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
    var userId = null;

    $scope.getProjects = function() {
      console.log($rootScope.userSelectId);
      console.log("check");

      if($rootScope.userSelectId) {
        userId = $rootScope.userSelectId;
      }
      else {
        userId = $cookies.getObject("user").id;
      }

      if(userId != null)
      {
        EmployeesService.getProjectsHours(userId).then(function (response) {
            $scope.projects = response;
        }, function(error) {
            console.log("Error receiving projects");
        });
      }
    }

    $scope.getUsedLeave = function() {
      if($rootScope.userSelectId) {
        userId = $rootScope.userSelectId.toString();
        $rootScope.userSelectId = null;
      }
      else {
        userId = $cookies.getObject("user").id;
      }

      if(userId != null)
      {
        EmployeesService.getUsedLeave(userId).then(function (response) {
          $scope.usedLeave = response;
        }, function (error) {
          console.log("Error getting used leave");
        })
      }
    }
  });
