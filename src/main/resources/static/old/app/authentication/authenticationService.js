/**
 * Authentication Service
 */
amadeusounds.factory('AuthenticationService', ['$http', '$rootScope', '$state', function ($http, $rootScope, $state) {
    var loginUrl = 'http://localhost:8080/login';
    var service = {};

    service.login = function (email, password) {
        var req = {
            method: 'POST',
            url: loginUrl,
            headers: {
                'email': email,
                'password': password
            }
        };

        return $http(req)
            .then(function (result) {
                console.log(result.status);
                if (result.status == 202) {
                    $rootScope.isAuthenticated = true;
                    var token = result.headers("angular-authentication-token");
                    //console.log(token);
                    $http.defaults.headers.common = {
                        'angular-authentication-token': token
                    };
                    $http.defaults.headers.get = {
                        'angular-authentication-token': token
                    };
                }

            }, function (result) {
                console.log(result.status);
                $rootScope.isAuthenticated = false;
            });
    };

    service.logout = function () {
        console.log("Log out");
        $rootScope.token = null;
        $rootScope.isAuthenticated = false;

        $http.defaults.headers.common = {
            'angular-authentication-token': undefined
        };

        $http.defaults.headers.get = {
            'angular-authentication-token': undefined
        };
    };

    service.isAuthenticated = function () {
        if ($rootScope.isAuthenticated == undefined) {
            return false;
        }
        return $rootScope.isAuthenticated;
    };

    service.redirect = function (url) {
        $state.go(url);
    };

    return service;
}]);
