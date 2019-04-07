angular.module("reportingApp").factory("ProjectsService", function($http) {
  const service = this;
  service.getProjects = getProjects;

  function getProjects(phrase) {
    return $http
      .get("/api/projects", { params: { phrase: phrase } })
      .then(function(response) {
        return response.data;
      });
  }

  return service;
});
