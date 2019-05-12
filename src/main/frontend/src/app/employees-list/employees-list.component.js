angular
  .module("hourReportingSystem")
  .component("employeesList", {
    templateUrl: "app/employees-list/employees-list.template.html",
    controller: "EmployeesListCtrl",
    controllerAs: "elCtrl"
  })
  .controller("EmployeesListCtrl", function(
    EmployeesService,
    NgTableParams,
    ProjectsService,
    $uibModal
  ) {
    const elCtrl = this;
    elCtrl.filterCriteria = new Object();

    elCtrl.search = search;
    elCtrl.openEmployeeModal = openEmployeeModal;

    elCtrl.provideManagers = provideManagers;
    elCtrl.provideProjects = provideProjects;

    elCtrl.employeesTable = new NgTableParams(
      {},
      {
        getData: function(params) {
          const request = generateLoadEmploeesRequest(params);

          return EmployeesService.getEmployees(request).then(function(
            response
          ) {
            const employeesList = response.list;

            params.total(employeesList.length);
            return employeesList;
          });
        }
      }
    );

    function generateLoadEmploeesRequest(params) {
      return {
        criteria: elCtrl.filterCriteria,
        options: {
          page: params.page() - 1,
          count: params.count()
        }
      };
    }

    function reloadEmployeesTable() {
      elCtrl.employeesTable.page(1);
      elCtrl.employeesTable.reload();
    }

    function search() {
      reloadEmployeesTable();
    }

    function openEmployeeModal(employeeId) {
      const modalInstance = $uibModal.open({
        templateUrl: "app/employee-details/employee-details.template.html",
        controller: "EmployeeDetailsCtrl",
        controllerAs: "edCtrl",
        resolve: {
          employeeId: function() {
            return employeeId;
          }
        }
      });

      modalInstance.result.then(function() {
        reloadEmployeesTable();
      });
    }

    function provideManagers(request) {
      return EmployeesService.getManagers(request);
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }
  });
