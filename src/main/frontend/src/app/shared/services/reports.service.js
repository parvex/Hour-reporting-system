angular
  .module("hourReportingSystem")
  .factory("ReportsService", function($http) {
    const service = this;
    service.getReport = getReport;
    service.getReports = getReports;
    service.saveReport = saveReport;

    function getReport(reportId) {
      return $http
        .get("/api/work-reports/" + reportId)
        .then(function(response) {
          return response.data;
        });
    }

    function getReports(request) {
      return $http.post("/api/work-reports", request).then(function(response) {
        return response.data;
      });
    }

    function saveReport(request) {
      return $http.post("/api/work-reports", request).then(function(response) {
        return response.data;
      });
    }

    return service;
  });
