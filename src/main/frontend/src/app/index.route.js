(function() {
  "use strict";

  angular.module("hourReportingSystem").config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    // the ui router will redirect if a invalid state has come.
    $urlRouterProvider.otherwise("/page-not-found");
    // parent view - navigation state
    $stateProvider
      .state("nav", {
        abstract: true,
        url: "",
        views: {
          "nav@": {
            templateUrl: "app/nav/nav.html",
            controller: "NavController"
          }
        }
      })
      .state("login", {
        parent: "nav",
        url: "/login",
        views: {
          "content@": {
            templateUrl: "app/login/login.html",
            controller: "LoginController"
          }
        }
      })
      .state("home", {
        parent: "nav",
        url: "/",
        views: {
          "content@": {
            templateUrl: "app/home/home.html",
            controller: "HomeController"
          }
        }
      })
      .state("page-not-found", {
        parent: "nav",
        url: "/page-not-found",
        views: {
          "content@": {
            templateUrl: "app/page-not-found/page-not-found.html",
            controller: "PageNotFoundController"
          }
        }
      })
      .state("access-denied", {
        parent: "nav",
        url: "/access-denied",
        views: {
          "content@": {
            templateUrl: "app/access-denied/access-denied.html",
            controller: "AccessDeniedController"
          }
        }
      })
      .state("register", {
        parent: "nav",
        url: "/register",
        data : {
          role : 'ADMIN'
        },
        views: {
          "content@": {
            templateUrl: "app/register/register.html",
            controller: "RegisterController"
          }
        }
      })
      .state("employees", {
        parent: "nav",
        url: "/employees",
        views: {
          "content@": {
            component: "employeesList"
          }
        }
      })
      .state("employee", {
        parent: "nav",
        url: "/employees/{employeeId}",
        views: {
          "content@": {
            component: "employeeDetails"
          }
        }
      })
      .state("calendar", {
        parent: "nav",
        url: "/calendar",
        views: {
          "content@": {
            component: "logCalendar"
          }
        }
      })
  }
})();
