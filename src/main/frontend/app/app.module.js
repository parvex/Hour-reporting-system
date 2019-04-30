angular
  .module("reportingApp", [
    "ui.bootstrap",
    "ui.router",
    "ngTable",
    "ui.select",
    "ngSanitize"
  ])
    .run(function(AuthService, $rootScope, $state) {
        $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
            if (!AuthService.user) {
                if (toState.name != 'login' && toState.name != 'register') {
                    event.preventDefault();
                    $state.go('login');
                }
            } else {
                if (toState.data && toState.data.role) {
                    var hasAccess = false;
                    for (var i = 0; i < AuthService.user.roles.length; i++) {
                        var role = AuthService.user.roles[i];
                        if (toState.data.role == role) {
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
        });
    })
    .config(function($stateProvider, $urlRouterProvider) {

        // the ui router will redirect if a invalid state has come.
        $urlRouterProvider.otherwise('/page-not-found');
        // parent view - navigation state
        $stateProvider.state('nav', {
            abstract : true,
            url : '',
            views : {
                'nav@' : {
                    templateUrl : 'nav/nav.html',
                    controller : 'NavController'
                }
            }
        }).state('login', {
            parent : 'nav',
            url : '/login',
            views : {
                'content@' : {
                    templateUrl : 'login/login.html',
                    controller : 'LoginController'
                }
            }
        }).state('users', {
            parent : 'nav',
            url : '/users',
            data : {
                role : 'ADMIN'
            },
            views : {
                'content@' : {
                    templateUrl : 'users/users.html',
                    controller : 'UsersController',
                }
            }
        }).state('home', {
            parent : 'nav',
            url : '/',
            views : {
                'content@' : {
                    templateUrl : 'home/home.html',
                    controller : 'HomeController'
                }
            }
        }).state('page-not-found', {
            parent : 'nav',
            url : '/page-not-found',
            views : {
                'content@' : {
                    templateUrl : 'page-not-found/page-not-found.html',
                    controller : 'PageNotFoundController'
                }
            }
        }).state('access-denied', {
            parent : 'nav',
            url : '/access-denied',
            views : {
                'content@' : {
                    templateUrl : 'access-denied/access-denied.html',
                    controller : 'AccessDeniedController'
                }
            }
        }).state('register', {
            parent : 'nav',
            url : '/register',
            views : {
                'content@': {
                    templateUrl: 'register/register.html',
                    controller: 'RegisterController'
                }
            }
            }).state('employees', {
             parent: "nav",
             url: "/employees",
             views : {
                 'content@': {
                     templateUrl: 'employees-list/employees-list.template.html',
                     controller: 'EmployeesListCtrl'
                 }
             }
      }).state("employee", {
            parent: "nav",
            url: "/employees/{employeeId}",
            views: {
                'content@': {
                    templateUrl: 'employee-details/employee-details.template.html',
                    controller: 'EmployeeDetailsCtrl'
                }
            }
        });
  });
