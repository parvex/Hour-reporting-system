angular.module("reportingApp").directive("itemSelect", function() {
  return {
    restrict: "E",
    scope: {
      chosenItem: "=model",
      chosenItemId: "=modelId",
      placeholder: "@",
      disabled: "=?",
      provider: "&"
    },
    templateUrl: "shared/directives/item-select/item-select.template.html",
    link: function(scope) {
      scope.loadItemsList = loadItemsList;
      scope.onSelected = onSelected;

      scope.selectedItem = {};

      scope.$watch("chosenItem", function() {
        loadItemsList();
      });

      scope.$watch("chosenItemId", function() {
        loadItemsList();
      });

      function loadItemsList(phrase) {
        const request = {
          phrase: phrase
        };

        scope.provider({ request: request }).then(function(response) {
          scope.itemsList = response.list;

          if (!containsItem(scope.itemsList, scope.chosenItem)) {
            scope.itemsList.push({
              id: scope.chosenItemId,
              name: scope.chosenItem
            });
          }
        });
      }

      function containsItem(array, name) {
        const filteredArray = array.filter(el => {
          return el.name == name;
        });

        return filteredArray.length > 0;
      }

      function onSelected(item) {
        scope.chosenItem = item.name;
        scope.chosenItem = item.id;
      }
    }
  };
});
