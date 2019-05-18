angular
  .module("hourReportingSystem")
  .directive("uniqueEmail", function(EmployeesService) {
    return {
      restrict: "A",
      require: "ngModel",
      link: function(scope, element, attrs, ngModel) {
        ngModel.$asyncValidators.uniqueEmail = function(modelValue, viewValue) {
          return EmployeesService.checkEmailUniqueness(viewValue);
        };
      }
    };
  });
