angular.module("reportingApp").directive("multiItemSelect", function() {
  return {
    restrict: "E",
    scope: {
      chosenItems: "=items",
      placeholder: "@",
      disabled: "=?",
      provider: "&"
    },
    templateUrl:
      "shared/directives/multi-item-select/multi-item-select.template.html",
    link: function(scope) {
      scope.loadItemsList = loadItemsList;
      scope.onSelected = onSelected;
      scope.deleteItem = deleteItem;

      scope.$watch(
        "chosenItems",
        function() {
          scope.chosenIds = scope.chosenItems.map(function(item) {
            return item.id;
          });

          loadItemsList();
        },
        true
      );

      function loadItemsList(phrase) {
        const request = {
          phrase: phrase,
          chosenIds: scope.chosenIds
        };

        return provider({ request: request }).then(function(response) {
          scope.itemsList = response.list;
        });
      }

      function onSelected(item) {
        scope.chosenItems.push(item);
      }

      function deleteItem(index) {
        scope.chosenItems.splice(index, 1);
      }
    }
  };
});
