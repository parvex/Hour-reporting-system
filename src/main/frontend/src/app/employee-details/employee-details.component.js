angular
  .module("hourReportingSystem")
  .component("employeeDetails", {
    templateUrl: "app/employee-details/employee-details.template.html",
    controller: "EmployeeDetailsCtrl",
    controllerAs: "edCtrl"
  })
  .controller("EmployeeDetailsCtrl", function(
    employeeId,
    $uibModalInstance,
    EmployeesService
  ) {
    const edCtrl = this;
    edCtrl.employeeId = employeeId;

    edCtrl.save = save;
    edCtrl.cancel = cancel;

    if (employeeId) {
      EmployeesService.getEmployee(employeeId).then(function(response) {
        edCtrl.employee = response;
      });
    } else {
      edCtrl.employee = new Object();
    }

    function save() {
      if (employeeId) {
        EmployeesService.updateEmployee(edCtrl.employee).then(function() {
          cancel();
        });
      } else {
        EmployeesService.saveEmployee(edCtrl.employee).then(function() {
          cancel();
        });
      }
    }

    function cancel() {
      $uibModalInstance.close();
    }
  });
