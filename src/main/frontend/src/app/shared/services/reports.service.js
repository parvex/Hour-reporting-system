angular
  .module("hourReportingSystem")
  .factory("ReportsService", function($http) {
    const service = this;
    service.getReports = getReports;

    function getReports(request) {
      return $http.post("/api/work-reports", request).then(function(response) {
        return response.data;
      });
    }

    return service;
  });
