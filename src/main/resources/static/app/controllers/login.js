amadeusounds.controller('LoginController',
    [
        '$scope',
        '$rootScope',
        '$location',
        '$filter',
        'UserService',

        function($scope, $rootScope, $location, $filter, UserService) {
            $scope.rememberMe = false;

            $scope.login = function() {
                //console.log("YEYEYE");
                UserService.authenticate($.param({
                        username: $scope.username,
                        password: $scope.password,
                        rememberMe: $scope.rememberMe
                    }), function(authenticationResult) {
                        $rootScope.authToken = authenticationResult.token;
                        UserService.get(function(u) {
                            $rootScope.user = u;
                            alert("NAJAVEN");
                        });
                        $rootScope.loginAction = true;
                        if($rootScope.returnPath
                            && $rootScope.returnPath != "/login") {
                            $location.path($rootScope.returnPath);
                            delete $rootScope.returnPath;
                        } else {
                            $location.path("/");
                        }
                        alert("NAJAVEN JOK " + authenticationResult);
                    },
                    function() {
                        //toaster.pop('error', $filter('translate')('generic.login_error'),
                        //    $filter('translate')('generic.invalid_username_or_password'));
                        alert("Greska pri najavuvanje");
                    });
            };
        }]);
