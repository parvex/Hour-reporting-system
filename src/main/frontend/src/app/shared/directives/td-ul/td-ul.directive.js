angular.module("hourReportingSystem").directive("tdUl", function() {
  return {
    restrict: "A",
    scope: {
      list: "="
    },
    templateUrl: "app/shared/directives/td-ul/td-ul.template.html"
  };
});
