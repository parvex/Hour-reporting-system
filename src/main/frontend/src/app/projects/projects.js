angular
  .module("hourReportingSystem")
  .controller("ProjectsController", function($http, $scope, AuthService) {

    $scope.selectedIndex = null;
    $scope.selectedPerson = null;
    $scope.selectProject = function(project, index){
      $scope.selectedIndex = index;
      showReports(project);
    };

    let init = function() {
      $http.get("api/projects").then(
        function(res) {
          $scope.projects = res.data;
          // $scope.appProjects = null;
        },
        function(error) {
          $scope.message = error.message;
        }
      );
    };
    init();

    let showReports = function(project){
      $http.get("api/project/id/reports").then( // dorobic endpointa
        function(res) {
          $scope.reports = res.data;
          // $scope.appProjects = null;
        },
        function(error) {
          $scope.message = error.message;
        }
      );

    }
  });
