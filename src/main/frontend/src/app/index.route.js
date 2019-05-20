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
      .state("calendar", {
        parent: "nav",
        url: "/calendar",
        views: {
          "content@": {
            component: "logCalendar"
          }
        }
      })
      .state("employees", {
        parent: "nav",
        url: "/employees",
        data: {
          roles: ["ADMIN" , "SUPERVISOR"]
        },
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
      .state("projects", {
        parent: "nav",
        url: "/projects",
        views: {
          "content@": {
            component: "projectsList"
          }
        }
      })
      .state("project", {
        parent: "nav",
        url: "/projects/{projectId}",
        views: {
          "content@": {
            component: "projectDetails"
          }
        }
      })
      .state("project-employees", {
        parent: "nav",
        url: "/project-employees/{projectId}",
        views: {
          "content@": {
            component: "projectEmployees"
          }
        }
      });
  }
})();
