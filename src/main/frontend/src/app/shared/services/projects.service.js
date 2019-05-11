angular
  .module("hourReportingSystem")
  .factory("ProjectsService", function($http) {
    const service = this;
    service.getProjects = getProjects;

    function getProjects(request) {
      return $http.post("/api/projects", request).then(function(response) {
        return response.data;
      });
    }

    return service;
  });
