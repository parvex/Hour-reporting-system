angular
  .module("planetRow")
  .directive("planetRow", function() {
    return {
      restrict: "A",
      templateUrl: "planet-row/planet-row.template.html",
      scope: {
        index: "=",
        name: "=",
        radius: "=",
        remove: "&",
        editMode: "="
      },
      controller: "planetRowCtrl",
      controllerAs: "plrCtrl"
    };
  })
  .controller("planetRowCtrl", function($scope) {
    this.radiusUpdated = function() {
      console.log("radius update:", $scope.radius);
    };
  });
