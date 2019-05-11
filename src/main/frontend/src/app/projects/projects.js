angular
  .module("hourReportingSystem")
  .controller("ProjectsController", function($http, $scope, AuthService) {

    $scope.showReport = function(project){





    };
    $scope.selectedIndex = null;

    $scope.selectProject = function(index){
      $scope.selectedIndex = index;
    };
    let init = function() {
      $scope.projectsForm = null;
      $http.get("api/projects").then(
        function(res) {
          $scope.projects = res.data;
          $scope.projectsForm.$setPristine();
          $scope.appProjects = null;
        },
        function(error) {
          $scope.message = error.message;
        }
      );
    };

    init();
  });
