angular.module("hourReportingSystem").directive("multiItemSelect", function() {
  return {
    restrict: "E",
    scope: {
      chosenItems: "=model",
      placeholder: "@",
      disabled: "=?",
      provider: "&",
      name: "@"
    },
    templateUrl:
      "app/shared/directives/multi-item-select/multi-item-select.template.html",
    link: function(scope) {
      scope.loadItemsList = loadItemsList;
      scope.onSelected = onSelected;
      scope.deleteItem = deleteItem;

      scope.selectedItem = {};

      scope.$watch(
        "chosenItems",
        function() {
          if (!scope.chosenItems) {
            scope.chosenItems = [];
          }

          scope.chosenIds = generateChosenIds();
          loadItemsList();
        },
        true
      );

      function generateChosenIds() {
        return scope.chosenItems.map(function(item) {
          return item.id;
        });
      }

      function loadItemsList(phrase) {
        const request = {
          phrase: phrase,
          chosenIds: scope.chosenIds
        };

        scope.provider({ request: request }).then(function(response) {
          scope.itemsList = response;
        });
      }

      function onSelected(item) {
        scope.chosenItems.push(angular.copy(item));
        scope.selectedItem.model = null;
      }

      function deleteItem(item) {
        const index = scope.chosenItems.findIndex(function(it) {
          return it == item;
        });

        if (index >= 0) {
          scope.chosenItems.splice(index, 1);
        }
      }
    }
  };
});
