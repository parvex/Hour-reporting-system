angular
  .module("hourReportingSystem")
  .factory('SessionStopper', ['$q', '$injector', '$rootScope', function($q, $injector, $rootScope) {
  var SessionStopper = {
    responseError: function(response) {
      // Session has expired
      if (response.status === 401){
        var AuthService = $injector.get('AuthService');
        var rootScope = $injector.get("$rootScope");
        var modalService = $injector.get("$uibModal")
        //logout
        AuthService.logout();
        alert("Session Expired!");
      }
      return $q.reject(response);
    }
  };
  return SessionStopper;
}]);
