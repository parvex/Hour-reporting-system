angular.module("reportingApp").directive("tdUl", function() {
  return {
    restrict: "E",
    scope: {
      list: "="
    },
    templateUrl: "shared/directives/td-ul/td-ul.template.html"
  };
});
