angular.module('reportingApp').config(function($stateProvider, $urlRouterProvider) {

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
