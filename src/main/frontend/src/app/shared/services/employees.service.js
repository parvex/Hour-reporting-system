angular
  .module("hourReportingSystem")
  .factory("EmployeesService", function($http) {
    const service = this;
    service.getProjectsHours = getProjectsHours;
    service.getEmployee = getEmployee;
    service.saveEmployee = saveEmployee;
    service.updateEmployee = updateEmployee;
    service.getEmployees = getEmployees;
    service.getAvailableEmployees = getAvailableEmployees;
    service.getSupervisors = getSupervisors;
    service.checkUsernameUniqueness = checkUsernameUniqueness;
    service.checkEmailUniqueness = checkEmailUniqueness;
    service.getAssignedEmployees = getAssignedEmployees;

    function getProjectsHours(employeeId){
      return $http({url: "/api/projectsHours", method: "GET", params: {id: employeeId}}).then(function (response) {
        return response.data;
      })
    }

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
      return $http.post("/api/employees-list", request)
        .then(function(response) {
          return response.data;
        });
    }

    function getAvailableEmployees(request) {
      return $http.post("/api/available-employees", request)
        .then(function(response) {
          return response.data;
        });
    }

    function getSupervisors(params) {
      return $http.get("/api/findsupervisors", { params: params })
        .then(function(response) {
          return response.data;
        });
    }

    function checkUsernameUniqueness(username) {
      const params = {
        username: username
      };
      return $http.get("/api/unique-username", { params: params });
    }

    function checkEmailUniqueness(email) {
      const params = {
        email: email
      };
      return $http.get("/api/unique-email", { params: params });
    }

    function getAssignedEmployees(request) {
      return $http.post("/api/employees-assigned", request)
        .then(function(response) {
          return response.data;
        });
    }
    return service;
  });
