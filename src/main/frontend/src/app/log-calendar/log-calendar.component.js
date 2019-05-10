angular
  .module("hourReportingSystem")
  .component("logCalendar", {
    templateUrl: "app/log-calendar/log-calendar.template.html",
    controller: "LogCalendarCtrl",
    controllerAs: "lcCtrl"
  })
  .controller("LogCalendarCtrl", function($scope) {
    const lcCtrl = this;
  });
