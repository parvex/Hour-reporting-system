angular.module("hourReportingSystem").directive("tdUl", function() {
  return {
    restrict: "A",
    scope: {
      list: "=",
      onItemClick: "&"
    },
    templateUrl: "app/shared/directives/td-ul/td-ul.template.html"
  };
});
