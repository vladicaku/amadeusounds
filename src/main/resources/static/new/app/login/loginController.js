angular.module('amadeusoundsApp')
    .controller("LoginController", ['$scope', '$rootScope', 'AuthenticationService', function ($scope, $rootScope, AuthenticationService) {

        $scope.email = "";
        $scope.password = "";
        $scope.rememberMe = false;
        $scope.invalidUsernameOrPassword = false;

        $scope.login = function () {
            console.log("Login started");
            AuthenticationService.login($scope.email, $scope.password).then(function (result) {
                console.log('Login finished');
                $scope.invalidUsernameOrPassword = true;
            });
        }

    }]);