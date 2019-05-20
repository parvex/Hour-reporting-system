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
    edCtrl.provideSupervisors = provideSupervisors;
    edCtrl.provideRoles = provideRoles;

    edCtrl.save = save;
    edCtrl.cancel = cancel;

    if (employeeId) {
      EmployeesService.getEmployee(employeeId).then(function(response) {
        edCtrl.employee = response;
      });
    } else {
      edCtrl.employee = {};
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }

    function provideSupervisors(request) {
      return EmployeesService.getSupervisors(request);
    }

    function provideRoles(request) {
      const roles = [
        { id: 0, name: "ADMIN" },
        { id: 1, name: "USER" },
        { id: 2, name: "SUPERVISOR" }
      ];

      const filteredRoles = roles.filter(function(role) {
        return !request.chosenIds.includes(role.id);
      });

      return Promise.resolve(filteredRoles);
    }

    function save(form) {
      form.$setSubmitted(true);
      if (form.$valid && edCtrl.employee.roles.length > 0) {
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
