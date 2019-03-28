angular.module("postalCode").directive("postalCodeInput", function($http, $q) {
  return {
    restrict: "A",
    require: "ngModel",
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.unshift(function formatPostalCode(viewValue) {
        console.log("postalCodeInput parser", viewValue);
        if (viewValue && viewValue.length > 2) {
          return viewValue + "1";
        }
        return viewValue;
      });
    }
  };
});
