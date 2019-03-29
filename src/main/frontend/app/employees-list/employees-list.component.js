angular
  .module("reportingApp")
  .component("employeesList", {
    templateUrl: "employees-list/employees-list.template.html",
    controller: "EmployeesListCtrl",
    controllerAs: "elCtrl"
  })
  .controller("EmployeesListCtrl", function() {
    const elCtrl = this;
  });
