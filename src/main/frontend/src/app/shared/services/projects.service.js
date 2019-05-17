angular
  .module("hourReportingSystem")
  .factory("ProjectsService", function($http) {
    const service = this;
    service.getProjects = getProjects;
    service.saveProject = saveProject;
    service.updateProject = updateProject;
    service.getAllProjects = getAllProjects;
    service.getProject = getProject;

    // by phrase
    function getProjects(request) {
      return $http.post("/api/projects", request).then(function(response) {
        return response.data;
      });
    }

    function getProject(projectId) {
      return $http.get("/api/projects/" + projectId).then(function(response) {
        return response.data;
      });
    }

    function saveProject(request) {
      return $http.post("/api/project", request).then(function(response) {
        return response.data;
      });
    }

    function updateProject(request) {
      return $http.put("/api/project", request).then(function(response) {
        return response.data;
      });
    }

    function getAllProjects(request) {
      return $http.get("/api/projects", request).then(function(response) {
        return response.data;
      });
    }

    return service;
  });
