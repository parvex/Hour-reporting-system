angular
  .module("hourReportingSystem")
  .directive("multiEmployeesSelect", function(AuthService, EmployeesService) {
    return {
      restrict: "E",
      scope: {
        chosenEmployees: "=model",
        placeholder: "@",
        disabled: "=?"
      },
      templateUrl:
        "app/shared/directives/multi-employees-select/multi-employees-select.template.html",
      link: function(scope) {
        scope.loadEmployeesList = loadEmployeesList;
        scope.onSelected = onSelected;
        scope.deleteEmployee = deleteEmployee;
        scope.isEmployeeMe = isEmployeeMe;

        scope.selectedEmployee = new Object();
        scope.me = AuthService.getUser();

        scope.$watch(
          "chosenEmployees",
          function() {
            if (!scope.chosenEmployees) {
              scope.chosenEmployees = [];
            }
            loadEmployeesList();
          },
          true
        );

        function generateChosenIds() {
          return scope.chosenEmployees.map(function(employee) {
            return employee.id;
          });
        }

        function loadEmployeesList(phrase) {
          const request = {
            phrase: phrase,
            chosenIds: generateChosenIds()
          };

          EmployeesService.getAvailableEmployees(request).then(function(
            response
          ) {
            scope.employeesList = response;
          });
        }

        function onSelected(employee) {
          scope.chosenEmployees.push(angular.copy(employee));
          scope.selectedEmployee.model = null;
        }

        function deleteEmployee(item) {
          const index = scope.chosenEmployees.findIndex(function(it) {
            return it == item;
          });

          if (index >= 0) {
            scope.chosenEmployees.splice(index, 1);
          }
        }

        function isEmployeeMe(employee) {
          return scope.me.id == employee.id;
        }
      }
    };
  });
