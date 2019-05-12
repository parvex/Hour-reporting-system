angular
  .module("hourReportingSystem")
  .factory("EmployeesService", function($http) {
    const service = this;
    service.getEmployee = getEmployee;
    service.saveEmployee = saveEmployee;
    service.updateEmployee = updateEmployee;

    service.getEmployees = getEmployees;
    service.getAvailableEmployees = getAvailableEmployees;
    service.getManagers = getManagers;

    function getEmployee(employeeId) {
      return $http.get("/api/employees/" + employeeId).then(function(response) {
        return response.data;
      });
    }

    function saveEmployee(request) {
      return $http.post("/api/employees", request).then(function(response) {
        return response.data;
      });
    }

    function updateEmployee(request) {
      return $http.put("/api/employees", request).then(function(response) {
        return response.data;
      });
    }

    function getEmployees(request) {
      return $http.post("/api/employees", request).then(function(response) {
        return response.data;
      });
    }

    function getAvailableEmployees(request) {
      return $http
        .post("/api/available-employees", request)
        .then(function(response) {
          return response.data;
        });
    }

    function getManagers(request) {
      return $http.post("/api/managers", request).then(function(response) {
        return response.data;
      });
    }

    return service;
  });
