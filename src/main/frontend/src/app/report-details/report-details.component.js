angular
  .module("hourReportingSystem")
  .component("reportDetails", {
    templateUrl: "app/report-details/report-details.template.html",
    controller: "ReporteDetailsCtrl",
    controllerAs: "rdCtrl"
  })
  .controller("ReporteDetailsCtrl", function(
    reportId,
    $uibModalInstance,
    ReportsService,
    ProjectsService,
    EmployeesService
  ) {
    const rdCtrl = this;
    rdCtrl.reportId = reportId;

    rdCtrl.isProjectSelected = isProjectSelected;
    rdCtrl.openDatePickerModal = openDatePickerModal;

    rdCtrl.provideProjects = provideProjects;
    rdCtrl.provideEmployees = provideEmployees;

    rdCtrl.save = save;
    rdCtrl.cancel = cancel;

    rdCtrl.dateOptions = {
      dateDisabled: disabled,
      minDate: getMinDate(),
      startingDay: 1
    };

    if (reportId) {
      ReportsService.getReport(reportId).then(function(response) {
        rdCtrl.report = response;
      });
    } else {
      rdCtrl.report = {};
    }

    function getMinDate() {
      //TODO: return min date
      return new Date();
    }

    // Disable weekend selection
    function disabled(data) {
      var date = data.date,
        mode = data.mode;
      return mode === "day" && (date.getDay() === 0 || date.getDay() === 6);
    }

    function isProjectSelected() {
      return (
        rdCtrl.report && rdCtrl.report.projectName && rdCtrl.report.projectId
      );
    }

    function openDatePickerModal() {
      rdCtrl.datePickerOpened = true;
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }

    function provideEmployees(request) {
      return EmployeesService.getEmployees(request);
    }

    function save() {
      //TODO: implement this function
    }

    function cancel() {
      $uibModalInstance.close();
    }
  });
