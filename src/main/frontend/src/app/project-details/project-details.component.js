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

    if (projectId) {  // update
      ProjectsService.getProject(projectId).then(function(response) {
        pdCtrl.project = response;
        pdCtrl.project.projectId = projectId;
        pdCtrl.project.keepEmployees = true;
      });
    } else { // new
        pdCtrl.project = {};
    }

    function save(form) {
      form.$setSubmitted(true);
      if (form.$valid && ((!pdCtrl.project.keepEmployees && pdCtrl.employees.length > 0) || (pdCtrl.project.keepEmployees))) {
        pdCtrl.project.list = getIdsList(pdCtrl.employees);
        if (projectId) {
          ProjectsService.updateProjectWithEmployees(pdCtrl.project).then(function() {
            cancel();
          });
        } else {
          ProjectsService.saveProjectWithEmployees(pdCtrl.project).then(function() {
            cancel();
          });
        }
      }
    }

    function getIdsList(list) {
      if (angular.isArray(list) && list.length > 0) {
        return list.map(function(element) {
          return element.id;
        });
      } else return [];
    }

    function cancel() {
      $uibModalInstance.close();
    }

    function provideEmployees(request) {
      return EmployeesService.getEmployees(request);
    }
  });
