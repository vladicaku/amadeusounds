/**
 * @author Vladica Jovanovski
 */

amadeusounds.factory('AuthenticationService', ['$http', '$rootScope', function($http, $rootScope) {
    var service;

    service.login = function(email, password) {
        $http.post('/login', {
            headers: {
                'email': email,
                'password': password
            }
        }).then(function (result) {
            alert(result, status);
            //if (result.status == "")
        });
    };

    return service;
}]);
