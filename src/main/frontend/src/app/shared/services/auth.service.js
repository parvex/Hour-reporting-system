angular
  .module("hourReportingSystem")
  //Angular Service for storing logged user details
  .service("AuthService", function($cookies, $state, $injector, $http) {
    const service = this;
    service.setUser = setUser;
    service.getUser = getUser;
    service.isLogged = isLogged;
    service.logout = logout;
    service.hasRole = hasRole;

    //refreshing token on refresh
    if(isLogged()) {
      var token = $cookies.get('token');
      $http.defaults.headers.common["Authorization"] =
        "Bearer " + token;
    }

    function setUser(data) {
      var obj = {
        id: data.user.id,
        name: data.user.name,
        surname: data.user.surname,
        username: data.user.username,
        roles: data.user.roles
      };

      $cookies.putObject("user", obj);
      $cookies.put("token", data.token);

      $http.defaults.headers.common["Authorization"] =
        "Bearer " + data.token;
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
      $http.defaults.headers.common["Authorization"] = "";
      $injector.get("$rootScope").$broadcast("LogoutSuccessful");
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
