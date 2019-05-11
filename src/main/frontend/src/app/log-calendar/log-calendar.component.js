angular
  .module("hourReportingSystem")
  .component("logCalendar", {
    templateUrl: "app/log-calendar/log-calendar.template.html",
    controller: "LogCalendarCtrl",
    controllerAs: "lcCtrl"
  })
  .controller("LogCalendarCtrl", function(ReportsService) {
    const lcCtrl = this;
    lcCtrl.fliterCriteria = new Object();

    lcCtrl.openDateFromPickerModal = openDateFromPickerModal;
    lcCtrl.openDateToPickerModal = openDateToPickerModal;

    lcCtrl.toggleListViewActive = toggleListViewActive;

    lcCtrl.dateOptions = {
      dateDisabled: disabled,
      minDate: getMinDate(),
      startingDay: 1
    };

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

    function toggleListViewActive() {
      lcCtrl.listViewActive = !lcCtrl.listViewActive;
    }
  });
