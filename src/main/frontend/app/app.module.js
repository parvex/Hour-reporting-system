angular
  .module("reportingApp", ["ui.bootstrap", "ui.router"])
  .config(function($stateProvider) {
    $stateProvider
      .state({
        name: "employees",
        url: "/employees",
        component: "employeesList"
      })
      .state({
        name: "employee",
        url: "/employees/{employeeId}",
        component: "employeeDetails"
      });
  });
