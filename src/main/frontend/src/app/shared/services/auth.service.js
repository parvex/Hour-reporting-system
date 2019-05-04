angular
  .module("hourReportingSystem")
  //Angular Service for storing logged user details
  .service("AuthService", function($cookies) {
    const service = this;
    service.setUser = setUser;
    service.getUser = getUser;
    service.isLogged = isLogged;
    service.logout = logout;

    function setUser(user) {
      var obj = {
        name: user.name,
        surname: user.surname,
        username: user.username,
        roles: user.roles
      };

      $cookies.putObject("user", obj);
    }

    function getUser() {
      return $cookies.getObject("user");
    }

    function isLogged() {
      var user = $cookies.getObject("user");
      return user === null || user === undefined ? false : true;
    }

    function logout() {
      $cookies.remove("user");
    }
  });
