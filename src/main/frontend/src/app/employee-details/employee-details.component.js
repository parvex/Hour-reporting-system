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
    EmployeesService,
    ProjectsService
  ) {
    const edCtrl = this;
    edCtrl.employeeId = employeeId;

    edCtrl.provideProjects = provideProjects;
    edCtrl.provideEmployees = provideEmployees;
    edCtrl.provideRoles = provideRoles;

    edCtrl.save = save;
    edCtrl.cancel = cancel;

    if (employeeId) {
      EmployeesService.getEmployee(employeeId).then(function(response) {
        edCtrl.employee = response;
      });
    } else {
      edCtrl.employee = new Object();
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }

    function provideEmployees(request) {
      return EmployeesService.getEmployees(request);
    }

    function provideRoles() {
      return Promise.resolve({
        list: [
          { id: 0, name: "USER" },
          { id: 1, name: "ADMIN" },
          { id: 2, name: "SUPERVISOR" }
        ]
      });
    }

    function save(form) {
      form.$setSubmitted(true);
      if (form.$valid) {
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
    }

    function cancel() {
      $uibModalInstance.close();
    }
  });
