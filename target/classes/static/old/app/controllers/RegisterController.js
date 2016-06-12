// amadeusounds.controller('ValidateController',
//     [
//         '$scope',
//         '$rootScope',
//         '$location',
//         '$filter',
//         'UserService',
//
//         function($scope, $rootScope, $location, $filter, UserService) {
//
//             $scope.register = function() {
//                 UserService.authenticate($.param({
//                         first: $scope.first,
//                         last: $scope.last,
//                         email: $scope.email,
//                         password: $scope.password,
//                         image: $scope.image,
//                         bio: $scope.bio,
//                         website: $scope.website
//                     }), function(authenticationResult) {
//                         UserService.post(function(u) {
//                             $rootScope.user = u;
//                             alert("NAJAVEN");
//                         });
//                         $rootScope.loginAction = true;
//                         if($rootScope.returnPath
//                             && $rootScope.returnPath != "/login") {
//                             $location.path($rootScope.returnPath);
//                             delete $rootScope.returnPath;
//                         } else {
//                             $location.path("/");
//                         }
//                         alert("NAJAVEN JOK " + authenticationResult);
//                     },
//                     function() {
//                         //toaster.pop('error', $filter('translate')('generic.login_error'),
//                         //    $filter('translate')('generic.invalid_username_or_password'));
//                         alert("Greska pri najavuvanje");
//                     });
//             };
//         }]);


amadeusounds.controller("RegisterController", ['$scope', '$http', function ($scope, $http) {

     $scope.data1 = {};
     $scope.countries={};
     $http.get('json/countries.json').
     success(function(data, status, headers, config) {
         $scope.countries = data;

     }).
     error(function(data, status, headers, config) {
         // log error
     });

    $scope.SendData = function () {
            $scope.data1.firstName = $scope.firstName;
            $scope.data1.lastName= $scope.lastName;
            $scope.data1.email= $scope.email;
            $scope.data1.password= $scope.password;
            $scope.data1.location = $scope.location;
            $scope.data1.image= $scope.image;
            $scope.data1.biography= $scope.biography;
            $scope.data1.website= $scope.website;
        $http({
            method: "POST",
            url: "/api/users/register",
            data: $scope.data1
        })
            .then(
                function success1(response) {
                    $scope.PostDataResponse = response.data.message;
                    console.log(response);
                },
                function error1(response) {
                    console.log(response);
                    return response;
                }
            );

    };

}]);