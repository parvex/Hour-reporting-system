angular
  .module("hourReportingSystem")
  .controller("ReportController", function($http, $scope, AuthService) {
    $scope.submit = function() {
      $http.post("api/reports", $scope.appReport).then(
        function(res) {
          $scope.appReport = null;
          $scope.reportForm.$setPristine();
          $scope.message = "New report added successfully!";
        },
        function(error) {
          $scope.message = error.message;
        }
      );
    };
  });
