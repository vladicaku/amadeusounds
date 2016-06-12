/**
 * Authentication Service
 */
amadeusounds.factory('AuthenticationService', ['$http', '$rootScope', '$state', '$cookies', function ($http, $rootScope, $state, $cookies) {
    var loginUrl = 'http://localhost:8080/login';
    var service = {};

    var cookieToken = $cookies.get('angular-authentication-token');
    if (cookieToken != undefined) {
        $rootScope.token = cookieToken;
        $rootScope.isAuthenticated = true;
    }

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
                    $cookies.put('angular-authentication-token', token);
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

        $cookies.remove('angular-authentication-token');
        $state.go("home-index");
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
