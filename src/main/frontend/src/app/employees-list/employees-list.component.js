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
    $state,
    $rootScope,
    $uibModal
  ) {
    const elCtrl = this;
    elCtrl.filterCriteria = {};
    elCtrl.search = search;
    elCtrl.openEmployeeModal = openEmployeeModal;
    elCtrl.provideEmployees = provideEmployees;
    elCtrl.provideProjects = provideProjects;
    elCtrl.showProjectDetails = showProjectDetails;
    elCtrl.openEmployeeDataView = openEmployeeDataView;

    elCtrl.employeesTable = new NgTableParams(
      {},
      {
        getData: function(params) {
          const request = generateLoadEmployeesRequest(params);

          return EmployeesService.getEmployees(request)
            .then(function(response) {
              const employeesList = response.list;
              params.total(response.totalCount);
              return employeesList;
          });
        }
      }
    );

    function generateLoadEmployeesRequest(params) {
      const criteria = angular.copy(elCtrl.filterCriteria);
      criteria.projects = getIdsArray(criteria.projects);

      return {
        criteria: criteria,
        options: {
          page: params.page() - 1,
          count: params.count()
        }
      };
    }

    function getIdsArray(array) {
      if (!angular.isArray(array) || array.length < 1) return [];
      else {
        return array.map(function(el) {
          return el.id;
        });
      }
    }

    function reloadEmployeesTable() {
      elCtrl.employeesTable.page(1);
      elCtrl.employeesTable.reload();
    }

    function search() {
      reloadEmployeesTable();
    }

    function openEmployeeDataView(employeeId){
      $rootScope.userSelectId = employeeId;
      $state.go("employee-data");
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

    function provideEmployees(request) {
      return EmployeesService.getEmployees(request);
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }

    function openProjectModal(projectId) {
      const modalInstance = $uibModal.open({
        templateUrl: "app/project-details/project-details.template.html",
        controller: "ProjectDetailsCtrl",
        controllerAs: "pdCtrl",
        resolve: {
          projectId: function() {
            return projectId;
          }
        }
      });

      modalInstance.result.then(function() {
        reloadEmployeesTable();
      });
    }

    function showProjectDetails(project) {
      openProjectModal(project.id);
    }
  });
