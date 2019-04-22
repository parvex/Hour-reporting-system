angular.module("reportingApp").factory("ProjectsService", function($http) {
  const service = this;
  service.getProjects = getProjects;

  function getProjects(request) {
    return Promise.resolve({
      list: [{ id: 1, name: "abc" }, { id: 7, name: "gfhfhfhfghfgh fdgdfg" }]
    });

    return $http.post("/api/projects", request).then(function(response) {
      return response.data;
    });
  }

  return service;
});
