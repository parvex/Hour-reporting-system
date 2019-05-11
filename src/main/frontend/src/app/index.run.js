(function() {
  "use strict";

  angular.module("hourReportingSystem").run(runBlock);

  /** @ngInject */
  function runBlock(AuthService, $state, $transitions) {
    /*
      consider adding Interceptors in case getting 401 - unauthorized -
      maybe session has expired or someone has no authorization -
      state  be moved to login page
    */

    $transitions.onBefore({}, function(transition) {
      if (!AuthService.isLogged()) {
        if (transition.to().name !== "login") {
          return transition.router.stateService.target("login");
        }
      } else {
        if (transition.to().data && transition.to().data.role && !AuthService.hasRole(transition.to().data.role) ) {
            return transition.router.stateService.target('access-denied');
        }
      }
    });
  }
})();
