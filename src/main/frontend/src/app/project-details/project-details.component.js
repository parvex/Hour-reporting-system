angular
  .module("hourReportingSystem")
  .component("projectDetails", {
    templateUrl: "app/project-details/project-details.template.html",
    controller: "ProjectDetailsCtrl",
    controllerAs: "pdCtrl"
  })
  .controller("ProjectDetailsCtrl", function(
    projectId,
    $uibModalInstance,
    ProjectsService
  ) {
    const pdCtrl = this;
    pdCtrl.projectId = projectId;

    pdCtrl.save = save;
    pdCtrl.cancel = cancel;

    if (projectId) {
      ProjectsService.getProjects(projectId).then(function(response) {
        pdCtrl.project = response;
      });
    } else {
      pdCtrl.project = new Object();
    }

    function generateLoadEmployeesRequest(params) {
      return {
        criteria: pdCtrl.filterCriteria.employees,
        options: {
          page: params.page() - 1,
          count: params.count()
        }
      };
    }












    function save() {
      if (projectId) {
        ProjectsService.updateProject(pdCtrl.project).then(function() {
          cancel();
        });
      } else {
        ProjectsService.saveProject(pdCtrl.project).then(function() {
          cancel();
        });
      }
    }

    function cancel() {
      $uibModalInstance.close();
    }
  });
