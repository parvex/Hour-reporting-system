angular
  .module("hourReportingSystem")
  .controller("EmpDataController", function (
    $scope,
    $cookies,
    EmployeesService,
    AuthService
  ) {
    const EmpDataController = this;

    EmpDataController.projects = [];
    $scope.getProjects = function() {
      if($cookies.getObject("user").id)
      {
        EmployeesService.getProjectsHours($cookies.getObject("user").id).then(function (response) {
            EmpDataController.projects = response;
        }, function(error) {
            console.log("Error receiving projects");
        });
      }
    }
  });
