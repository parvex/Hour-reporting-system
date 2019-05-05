angular
  .module("hourReportingSystem")
  .component("employeeDetails", {
    templateUrl: "employee-details/employee-details.template.html",
    controller: "EmployeeDetailsCtrl",
    controllerAs: "edCtrl"
  })
  .controller("EmployeeDetailsCtrl", function($scope, $state) {
    const edCtrl = this;
    console.log("employeeId", $state.params.employeeId);
  });
