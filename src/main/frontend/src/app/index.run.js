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
        if (transition.to().data && transition.to().data.roles) {
          let hasAuthority = false;
          let roles = transition.to().data.roles;
          for(let i = 0; i < roles.length; ++i) {
            if(AuthService.hasRole(roles[i])){
              hasAuthority = true;
            }
          }
            if(!hasAuthority){
              return transition.router.stateService.target('access-denied');
            }
        }
      }
    });
  }
})();
