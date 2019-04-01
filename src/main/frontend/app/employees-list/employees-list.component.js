angular
  .module("reportingApp")
  .component("employeesList", {
    templateUrl: "employees-list/employees-list.template.html",
    controller: "EmployeesListCtrl",
    controllerAs: "elCtrl"
  })
  .controller("EmployeesListCtrl", function(EmployeesService) {
    const elCtrl = this;

    loadEmployees();

    function loadEmployees() {
      const request = generateLoadEmploeesRequest();

      EmployeesService.getEmployees(request).then(function(response) {
        elCtrl.employees = response.list;
      });
    }

    function generateLoadEmploeesRequest() {
      return {};
    }
  });
