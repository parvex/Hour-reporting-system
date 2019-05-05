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
    ProjectsService
  ) {
    const elCtrl = this;
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
      const criteria = {};

      let projects = [];
      if (angular.isArray(elCtrl.projectsFilter)) {
        projects = elCtrl.projectsFilter.map(function(project) {
          return { id: project.id };
        });
      }

      if (projects.length > 0) {
        criteria.projects = projects;
      }

      if (elCtrl.employeesNameFilter) {
        criteria.name = elCtrl.employeesNameFilter;
      }

      if (elCtrl.employeesSurnameFilter) {
        criteria.surname = elCtrl.employeesSurnameFilter;
      }

      if (elCtrl.employeesEmailFilter) {
        criteria.email = elCtrl.employeesEmailFilter;
      }

      if (elCtrl.employeesManagerIdFilter) {
        criteria.manager = elCtrl.employeesManagerIdFilter;
      }

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

    function provideManagers(request) {
      return EmployeesService.getManagers(request);
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }
  });
