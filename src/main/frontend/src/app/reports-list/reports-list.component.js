angular
  .module("hourReportingSystem")
  .component("reportsList", {
    templateUrl: "app/reports-list/reports-list.template.html",
    controller: "ReportsListCtrl",
    controllerAs: "rlCtrl"
  })
  .controller("ReportsListCtrl", function(
    projectId,
    accepted,
    $uibModalInstance,
    NgTableParams,
    ReportsService,
    $scope
  ) {
    const rlCtrl = this;
    rlCtrl.projectId = projectId;
    rlCtrl.accepted = accepted;

    rlCtrl.save = save;
    rlCtrl.cancel = cancel;

    rlCtrl.reportsTable = new NgTableParams(
      {},
      {
        getData: function(params) {
          const request = generateLoadReportsRequest(params);

          return ReportsService.getReportsAccepted(request).then(function(response) {
            const reportsList = response;

            params.total(reportsList.length);
            return reportsList;
          });
        }
      }
    );

    function generateLoadReportsRequest(params) {
      return {
        id: rlCtrl.projectId,
        accepted: rlCtrl.accepted
      };
    }

    function save(form) {
      form.$setSubmitted(true);
      if (form.$valid) {
        ReportsService.saveReport(rlCtrl.report).then(function() {
          $uibModalInstance.close();
        });
      }
    }

    function cancel() {
      $uibModalInstance.close();
    }
  });
