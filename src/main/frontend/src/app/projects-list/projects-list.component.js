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

    plCtrl.search = search;
    plCtrl.openProjectModal = openProjectModal;
    plCtrl.provideProjects = provideProjects;

    plCtrl.projectsTable = new NgTableParams(
      {},
      {
        getData: function(params) {
          const request = generateLoadProjectsRequest(params);

          return ProjectsService.getProjects(request)
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
        criteria: plCtrl.filterCriteria,
        options: {
          page: params.page() - 1,
          count: params.count()
        }
      };
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

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }
  });
