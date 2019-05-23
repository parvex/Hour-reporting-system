angular
  .module("hourReportingSystem")
  .directive("uniqueUsername", function(EmployeesService) {
    return {
      restrict: "A",
      require: "ngModel",
      link: function(scope, element, attrs, ngModel) {
        ngModel.$asyncValidators.uniqueUsername = function(
          modelValue,
          viewValue
        ) {
          return EmployeesService.checkUsernameUniqueness(viewValue);
        };
      }
    };
  });
