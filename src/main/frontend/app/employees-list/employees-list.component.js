angular
  .module("reportingApp")
  .component("employeesList", {
    templateUrl: "employees-list/employees-list.template.html",
    controller: "EmployeesListCtrl",
    controllerAs: "elCtrl"
  })
  .controller("EmployeesListCtrl", function(EmployeesService, NgTableParams) {
    const elCtrl = this;
    elCtrl.search = search;
    elCtrl.openEmployeeModal = openEmployeeModal;

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
      const criteria = {
        name: elCtrl.employeesNameFilter,
        surname: elCtrl.employeesSurnameFilter,
        email: elCtrl.employeesEmailFilter,
        manager: elCtrl.employeesManagerFilter
      };

      return {
        page: params.page() - 1,
        count: params.count(),
        criteria: criteria
      };
    }

    function search() {
      elCtrl.employeesTable.reload();
    }

    function openEmployeeModal(employeeId) {
      //TODO: open modal
      //TODO: pass employeeId to modal
    }
  });
