angular
  .module("hourReportingSystem")
  .component("projectsList", {
    templateUrl: "app/projects-list/projects-list.template.html",
    controller: "ProjectsListCtrl",
    controllerAs: "plCtrl"
  })
  .controller("ProjectsListCtrl", function(
    NgTableParams,
    ProjectsService,
    $uibModal
  ) {
    const plCtrl = this;
    plCtrl.filterCriteria = new Object();
    plCtrl.filterCriteria.sorted = "";
    plCtrl.search = search;
    plCtrl.openProjectModal = openProjectModal;
    plCtrl.openReportModal = openReportModal;
    plCtrl.provideProjects = provideProjects;
    plCtrl.reloadTable = reloadProjectsTable;

    plCtrl.projectsTable = new NgTableParams(
      {},
      {
        getData: function(params) {
          const request = generateLoadProjectsRequest(params);

          return ProjectsService.getProjectsChosen(request)
            .then(function(response) {
              const projectsList = response;
              params.total(projectsList.length);
              return projectsList;
          });
        }
      }
    );

    function generateLoadProjectsRequest(params) {
      return {
        criteria: getIdsList(plCtrl.filterCriteria.projects),
        order: plCtrl.filterCriteria.sorted,
        options: {
          page: params.page() - 1,
          count: params.count()
        }
      };
    }

    function getIdsList(list) {
      if (angular.isArray(list) && list.length > 0) {
        return list.map(function(element) {
          return element.id;
        });
      } else return [];
    }

    function reloadProjectsTable() {
      plCtrl.projectsTable.page(1);
      plCtrl.projectsTable.reload();
    }

    function search() {
      reloadProjectsTable();
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
        reloadProjectsTable();
      });
    }

    function openReportModal(projectId, accepted) {
      const modalInstance = $uibModal.open({
        templateUrl: "app/reports-list/reports-list.template.html",
        controller: "ReportsListCtrl",
        controllerAs: "rlCtrl",
        size: "lg",
        resolve: {
          projectId: function() {
            return projectId;
          },
          accepted: function() {
            return accepted;
          }
        }
      });

      modalInstance.result.then(function() {
        reloadProjectsTable();
      });
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }
  });
