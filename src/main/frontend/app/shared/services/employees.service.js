angular.module("reportingApp").factory("EmployeesService", function($http) {
  const service = this;
  service.getEmployees = getEmployees;

  function getEmployees(request) {
    return Promise.resolve({ list: [] });

    return $http.post("/api/employees", request).then(function(response) {
      return response.data;
    });
  }

  return service;
});
