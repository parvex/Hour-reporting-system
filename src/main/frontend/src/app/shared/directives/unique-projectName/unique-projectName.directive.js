angular
  .module("hourReportingSystem")
  .directive("uniqueProjectName", function(ProjectsService) {
    return {
      restrict: "A",
      require: "ngModel",
      link: function(scope, element, attrs, ngModel) {
        ngModel.$asyncValidators.uniqueProjectName = function(modelValue, viewValue) {
          return ProjectsService.checkProjectNameUniqueness(viewValue);
        };
      }
    };
  });
