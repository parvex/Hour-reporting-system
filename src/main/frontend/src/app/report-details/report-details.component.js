angular
  .module("hourReportingSystem")
  .component("reportDetails", {
    templateUrl: "app/report-details/report-details.template.html",
    controller: "ReporteDetailsCtrl",
    controllerAs: "rdCtrl"
  })
  .controller("ReporteDetailsCtrl", function(reportId, $uibModalInstance) {
    const rdCtrl = this;
    rdCtrl.reportId = reportId;

    rdCtrl.save = save;
    rdCtrl.cancel = cancel;

    function save() {
      //TODO: implement this function
    }

    function cancel() {
      $uibModalInstance.close();
    }
  });
