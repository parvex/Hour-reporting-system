angular
  .module("hourReportingSystem")
  .factory("ProjectsService", function($http) {
    const service = this;
    service.getProjects = getProjects;
    service.saveProject = saveProject;
    service.updateProject = updateProject;
    service.saveProjectWithEmployees = saveProjectWithEmployees;
    service.updateProjectWithEmployees = updateProjectWithEmployees;
    service.getAllProjects = getAllProjects;
    service.getProject = getProject;
    service.getProjectsChosen = getProjectsChosen;
    service.checkProjectNameUniqueness = checkProjectNameUniqueness;

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

    function saveProjectWithEmployees(request) {
      return $http.post("/api/project-employees", request).then(function(response) {
        return response.data;
      });
    }

    function updateProjectWithEmployees(request) {
      return $http.put("/api/project-employees", request).then(function(response) {
        return response.data;
      });
    }

    function getAllProjects(request) {
      return $http.get("/api/projects", request).then(function(response) {
        return response.data;
      });
    }

    function getProjectsChosen(request) {
      return $http.post("/api/projects-chosen", request).then(function(response) {
        return response.data;
      });
    }

    function checkProjectNameUniqueness(name) {
      const params = {
        name: name
      };
      return $http.get("/api/unique-project-name", { params: params });
    }

    return service;
  });
