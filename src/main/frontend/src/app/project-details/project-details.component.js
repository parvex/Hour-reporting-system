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
    pdCtrl.provideEmployees = provideEmployees;
    pdCtrl.save = save;
    pdCtrl.cancel = cancel;

    if (projectId) {
      ProjectsService.getProject(projectId).then(function(response) {
        pdCtrl.project = response;
      });
    } else {
      pdCtrl.project = new Object();
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

    function provideEmployees(request) {
      return EmployeesService.getEmployees(request);
    }
  });
