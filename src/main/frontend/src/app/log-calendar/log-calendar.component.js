angular
  .module("hourReportingSystem")
  .component("logCalendar", {
    templateUrl: "app/log-calendar/log-calendar.template.html",
    controller: "LogCalendarCtrl",
    controllerAs: "lcCtrl"
  })
  .controller("LogCalendarCtrl", function(
    ReportsService,
    ProjectsService,
    $uibModal,
    $scope,
    $timeout
  ) {
    const lcCtrl = this;
    lcCtrl.fliterCriteria = new Object();
    lcCtrl.listViewItemsPerPage = 6;

    lcCtrl.openDateFromPickerModal = openDateFromPickerModal;
    lcCtrl.openDateToPickerModal = openDateToPickerModal;

    lcCtrl.openReportModal = openReportModal;
    lcCtrl.toggleListViewActive = toggleListViewActive;

    lcCtrl.provideProjects = provideProjects;

    lcCtrl.dateOptions = {
      dateDisabled: disabled,
      minDate: getMinDate(),
      startingDay: 1
    };

    lcCtrl.uiConfig = {
      calendar: {
        editable: false,
        header: {
          left: "title",
          center: "",
          right: "today prev,next"
        },
        eventClick: calendarReportClick
      }
    };

    $scope.$watch(
      "lcCtrl.fliterCriteria",
      function() {
        if (lcCtrl.reloadReportsPromise) {
          $timeout.cancel(lcCtrl.reloadReportsPromise);
        }

        lcCtrl.reloadReportsPromise = $timeout(function() {
          loadReports();
        }, 400);
      },
      true
    );

    function openDateFromPickerModal() {
      lcCtrl.dateFromPickerOpened = true;
    }

    function openDateToPickerModal() {
      lcCtrl.dateToPickerOpened = true;
    }

    // Disable weekend selection
    function disabled(data) {
      var date = data.date,
        mode = data.mode;
      return mode === "day" && (date.getDay() === 0 || date.getDay() === 6);
    }

    function getMinDate() {
      //TODO: return min date
      return new Date();
    }

    function loadReports() {
      const request = generateRequest();

      ReportsService.getReports(request).then(function(response) {
        lcCtrl.reportsList = response.list;
        lcCtrl.reportsEvents = generateCalendarReportEvents(lcCtrl.reportsList);
      });
    }

    function generateRequest() {
      const request = {
        dateFrom: lcCtrl.fliterCriteria.dateFrom,
        dateTo: lcCtrl.fliterCriteria.dateTo,
        employeesIds: lcCtrl.fliterCriteria.employees,
        projectsIds: lcCtrl.fliterCriteria.projects
      };

      return request;
    }

    function toggleListViewActive() {
      lcCtrl.listViewActive = !lcCtrl.listViewActive;
    }

    function provideProjects(request) {
      return ProjectsService.getProjects(request);
    }

    function openReportModal(reportId) {
      const modalInstance = $uibModal.open({
        templateUrl: "app/report-details/report-details.template.html",
        controller: "ReporteDetailsCtrl",
        controllerAs: "rdCtrl",
        resolve: {
          reportId: function() {
            return reportId;
          }
        }
      });

      modalInstance.result.then(function() {
        loadReports();
      });
    }

    function calendarReportClick(report) {
      openReportModal(report.id);
    }

    function generateCalendarReportEvents(reports) {
      return reports.map(function(report) {
        return {
          title: report.employeeName + " " + report.employeeSurname,
          start: report.date,
          end: report.date
        };
      });
    }
  });
