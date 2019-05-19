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
    $timeout,
    $compile
  ) {
    const lcCtrl = this;
    lcCtrl.fliterCriteria = {
      startDate: getInitialStartDate()
    };
    lcCtrl.listViewItemsPerPage = 6;

    lcCtrl.openStartDatePickerModal = openStartDatePickerModal;
    lcCtrl.openEndDatePickerModal = openEndDatePickerModal;

    lcCtrl.openReportModal = openReportModal;
    lcCtrl.toggleListViewActive = toggleListViewActive;

    lcCtrl.provideProjects = provideProjects;

    lcCtrl.startDateOptions = {
      startingDay: 1
    };

    lcCtrl.endDateOptions = {
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
        eventClick: calendarReportClick,
        eventRender: eventRender
      }
    };

    lcCtrl.reportsEvents = [];

    function eventRender(event, element, view) {
      element = generateCalendarEventElement(element, event);

      $compile(element)($scope);
    }

    function generateCalendarEventElement(element, event) {
      element.attr({
        "uib-tooltip": "show details",
        "tooltip-append-to-body": true
      });

      element.context.innerHTML =
        '<div class="fc-content"><span class="fc-title">' +
        event.title +
        " (" +
        event.hoursNumber +
        "h)</span></div>";

      element.context.innerText = event.title + " (" + event.hoursNumber + "h)";

      element.addClass("calendar-event");

      return element;
    }

    function getInitialStartDate() {
      const today = new Date();
      const lastWeek = new Date(
        today.getFullYear(),
        today.getMonth(),
        today.getDate() - 7
      );
      return lastWeek;
    }

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

    $scope.$watch("lcCtrl.fliterCriteria.startDate", function(minDate) {
      if (lcCtrl.fliterCriteria.endDate) lcCtrl.fliterCriteria.endDate = null;
      lcCtrl.endDateOptions.minDate = minDate;
    });

    function openStartDatePickerModal() {
      lcCtrl.startDatePickerOpened = true;
    }

    function openEndDatePickerModal() {
      lcCtrl.endDatePickerOpened = true;
    }

    function loadReports() {
      const request = generateRequest();

      ReportsService.getReports(request).then(function(response) {
        lcCtrl.reportsList = response;
        lcCtrl.reportsEvents[0] = generateCalendarReportEvents(
          lcCtrl.reportsList
        );
      });
    }

    function generateRequest() {
      const request = {
        dateFrom: lcCtrl.fliterCriteria.startDate,
        dateTo: lcCtrl.fliterCriteria.endDate,
        employeeIds: getIdsList(lcCtrl.fliterCriteria.employees),
        projectIds: getIdsList(lcCtrl.fliterCriteria.projects)
      };

      return request;
    }

    function getIdsList(list) {
      if (angular.isArray(list) && list.length > 0) {
        return list.map(function(element) {
          return element.id;
        });
      } else return [];
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
      openReportModal(report.workReportId);
    }

    function generateCalendarReportEvents(reports) {
      return reports.map(function(report) {
        return {
          workReportId: report.workReportId,
          hoursNumber: report.hoursNumber,
          title: report.employeeName + " " + report.employeeSurname,
          start: new Date(report.date),
          end: new Date(report.date),
          allDay: true
        };
      });
    }
  });
