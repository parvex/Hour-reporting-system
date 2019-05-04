angular
  .module("hourReportingSystem")
  .factory("EmployeesService", function($http) {
    const service = this;
    service.getEmployees = getEmployees;
    service.getManagers = getManagers;

    function getEmployees(request) {
      return Promise.resolve({ list: [] });

      return $http.post("/api/employees", request).then(function(response) {
        return response.data;
      });
    }

    function getManagers(request) {
      return Promise.resolve({ list: [] });

      return $http.post("/api/managers", request).then(function(response) {
        return response.data;
      });
    }

    return service;
  });
