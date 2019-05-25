angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $scope,
    EmployeesService
  ) {
    const EmpDataController = this;

    EmpDataController.projects = [];
    $scope.getProjects = function() {
      if($scope.user.id)
      {
        EmployeesService.getProjectsHours($scope.user.id).then(function (response) {
            EmpDataController.projects = response;
        });
      }
    }
  });
