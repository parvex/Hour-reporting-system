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
    EmployeesService,
    $scope
  ) {
    const rdCtrl = this;
    rdCtrl.reportId = reportId;

    $scope.$watch("rdCtrl.report.startDate", function(startDate) {
      if (rdCtrl.report) rdCtrl.report.endDate = null;
      rdCtrl.endDateOptions.minDate = startDate;
    });

    rdCtrl.isProjectSelected = isProjectSelected;
    rdCtrl.openStartDatePickerModal = openStartDatePickerModal;
    rdCtrl.openEndDatePickerModal = openEndDatePickerModal;

    rdCtrl.provideProjects = provideProjects;
    rdCtrl.provideEmployees = provideEmployees;

    rdCtrl.save = save;
    rdCtrl.cancel = cancel;

    rdCtrl.dateOptions = {
      dateDisabled: disabled,
      minDate: getMinDate(),
      startingDay: 1
    };

    rdCtrl.endDateOptions = {
      dateDisabled: disabled,
      minDate: getMinDate(),
      startingDay: 1
    };

    rdCtrl.endDateOptions = {
      dateDisabled: disabled,

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
      const today = new Date();
      const lastWeek = new Date(
        today.getFullYear(),
        today.getMonth(),
        today.getDate() - 7
      );
      return lastWeek;
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

    function openStartDatePickerModal() {
      rdCtrl.startDatePickerOpened = true;
    }

    function openEndDatePickerModal() {
      rdCtrl.endDatePickerOpened = true;
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }

    function provideEmployees(request) {
      return EmployeesService.getEmployees(request);
    }

    function save(form) {
      form.$setSubmitted(true);
      if (form.$valid) {
        const request = generateRequest();

        if (reportId) {
          ReportsService.updateReport(request).then(function() {
            $uibModalInstance.close();
          });
        } else {
          ReportsService.saveReport(request).then(function() {
            $uibModalInstance.close();
          });
        }
      }
    }

    function generateRequest() {
      const reportRequest = angular.copy(rdCtrl.report);
      if (!reportRequest.endDate) {
        reportRequest.endDate = reportRequest.startDate;
      }

      return reportRequest;
    }

    function cancel() {
      $uibModalInstance.close();
    }
  });
