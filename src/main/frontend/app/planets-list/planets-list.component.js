angular
  .module("planetsList")
  .component("planetsList", {
    templateUrl: "planets-list/planets-list.template.html",
    controller: "PlanetListCtrl",
    controllerAs: "plCtrl"
  })
  .controller("PlanetListCtrl", function($scope) {
    var plCtrl = this;

    plCtrl.planets = [
      { name: "Earth", radius: 6371 },
      { name: "Mars", radius: 3390 },
      { name: "Wenus", radius: 6052 }
    ];

    plCtrl.addPlanet = function() {
      plCtrl.planets.unshift({});
    };

    plCtrl.removePlanet = function(index) {
      plCtrl.planets.splice(index, 1);
    };

    plCtrl.toggleEditMode = function() {
      if ($scope.planetsForm.$valid) {
        plCtrl.editMode = !plCtrl.editMode;
      } else {
        $scope.planetsForm.planetRadius.$validate();
      }
    };
  });
