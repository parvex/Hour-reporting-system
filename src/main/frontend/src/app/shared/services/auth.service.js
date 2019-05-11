angular
  .module("hourReportingSystem")
  //Angular Service for storing logged user details
  .service("AuthService", function($cookies, $state, $injector) {
    const service = this;
    service.setUser = setUser;
    service.getUser = getUser;
    service.isLogged = isLogged;
    service.logout = logout;
    service.hasRole = hasRole;

    function setUser(user) {
      var obj = {
        id: user.id,
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
      $state.go("login");
      $injector.get("$rootScope").$broadcast("LogoutSuccessful")
    }

    //returns true if user has false, otherwise and if he's not logged
    function hasRole(role) {
      var user = $cookies.getObject("user");
      if(user === null || user === undefined)
        return false;
      for (var i = 0; i < user.roles.length; i++) {
        var userRole = user.roles[i];
        if (role === userRole) {
          return true;
        }
      }
      return false;
    }

  });
