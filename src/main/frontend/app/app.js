angular
  .module("reportingApp", [
    "ui.bootstrap",
    "ui.router",
    "ngTable",
    "ui.select",
    "ngSanitize",
    "ngCookies"
  ])
    .run(function(AuthService, $rootScope, $state, $transitions) {

        //consider adding Interceptors in case getting 401 - unauthorized - maybe session has expired or someone has no authorization - should be moven to login page

        $transitions.onBefore({}, function(transition) {
            if (!AuthService.isLogged()) {
                if (transition.to().name !== 'login') {
                    return transition.router.stateService.target('login');
                }
            } else {
                if (transition.data && transition.data.role) {
                    var hasAccess = false;
                    for (var i = 0; i < AuthService.user.roles.length; i++) {
                        var role = AuthService.user.roles[i];
                        if (transition.data.role == role) {
                            hasAccess = true;
                            break;
                        }
                    }
                    if (!hasAccess) {
                        event.preventDefault();
                        $state.go('access-denied');
                    }

                }
            }
        })
    });