angular.module("hourReportingSystem").directive("tdUl", function() {
  return {
    restrict: "A",
    scope: {
      list: "="
    },
    templateUrl: "shared/directives/td-ul/td-ul.template.html"
  };
});
