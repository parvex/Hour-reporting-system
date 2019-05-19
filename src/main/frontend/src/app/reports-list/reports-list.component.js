angular
  .module("hourReportingSystem")
  .component("reportsList", {
    templateUrl: "app/reports-list/reports-list.template.html",
    controller: "ReportsListCtrl",
    controllerAs: "rlCtrl"
  })
  .controller("ReportsListCtrl", function(
    projectId,
    state,
    $uibModalInstance,
    NgTableParams,
    ReportsService
  ) {
    const rlCtrl = this;
    rlCtrl.filterCriteria = {};
    rlCtrl.filterCriteria.sorted = "";
    rlCtrl.state = state;
    rlCtrl.projectId = projectId;
    rlCtrl.save = save;
    rlCtrl.cancel = cancel;
    rlCtrl.reloadTable = reloadProjectsTable;
    rlCtrl.setReportAccepted = setReportAccepted;
    rlCtrl.removeReport = removeReport;

    rlCtrl.reportsTable = new NgTableParams(
      {},
      {
        getData: function(params) {
          const request = generateLoadReportsRequest(params);

          return ReportsService.getReportsByState(request)
            .then(function(response) {
              const reportsList = response.list;
              params.total(response.totalCount);
              return reportsList;
          });
        }
      }
    );

    function generateLoadReportsRequest(params) {
      return {
        criteria: rlCtrl.projectId,
        state: rlCtrl.state,
        order: rlCtrl.filterCriteria.sorted,
        options: {
          page: params.page() - 1,
          count: params.count()
        }
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

    function reloadProjectsTable() {
      rlCtrl.reportsTable.page(1);
      rlCtrl.reportsTable.reload();
    }

    function setReportAccepted(reportId){
      if(confirm("Are you sure you want to accept the report?")) {
        ReportsService.setReportAccepted(reportId).then(function(){
         reloadProjectsTable();
        });
      }
    }

    function removeReport(reportId){
      if(confirm("Are you sure you want to remove the report?")) {
        ReportsService.removeReport(reportId).then(function () {
          reloadProjectsTable();
        });
      }
    }

  });
