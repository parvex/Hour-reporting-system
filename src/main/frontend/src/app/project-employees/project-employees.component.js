angular
  .module("hourReportingSystem")
  .component("projectEmployees", {
    templateUrl: "app/project-employees/project-employees.template.html",
    controller: "ProjectEmployeesCtrl",
    controllerAs: "peCtrl"
  })
  .controller("ProjectEmployeesCtrl", function(
  projectId,
  $uibModalInstance,
  NgTableParams,
  EmployeesService
) {
  const peCtrl = this;
  peCtrl.filterCriteria = {};
  peCtrl.filterCriteria.sorted = "";
  peCtrl.projectId = projectId;
  peCtrl.reloadTable = reloadProjectsTable;

  peCtrl.employeesTable = new NgTableParams(
    {},
    {
      getData: function(params) {
        const request = generateLoadEmployeesRequest(params);

        return EmployeesService.getAssignedEmployees(request)
          .then(function(response) {
            const employeesList = response.list;
            params.total(response.totalCount);
            return employeesList;
          });
      }
    }
  );

  function generateLoadEmployeesRequest(params) {
    return {
      criteria: peCtrl.projectId,
      order: peCtrl.filterCriteria.sorted,
      options: {
        page: params.page() - 1,
        count: params.count()
      }
    };
  }

  function reloadProjectsTable() {
    peCtrl.employeesTable.page(1);
    peCtrl.employeesTable.reload();
  }
});
