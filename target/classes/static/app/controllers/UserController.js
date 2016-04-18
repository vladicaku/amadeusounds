/**
 * Created by Angela on 4/14/2016.
 */
amadeusounds.controller('UserController',
    ['$scope', '$http', '$rootScope', '$location', '$filter', '$stateParams', 'UserService',

        function($scope, $http, $rootScope, $location, $filter, $stateParams, UserService)
        {
            $scope.user={};

            $scope.userComments={};

            $scope.userRatings={};


            $scope.getUser = function(user_id) {
                console.log("got here");
                UserService.getUser(user_id).success(function (data) {
                    $scope.user = data;
                    console.log(data);
                }).error(function (data) {
                    console.log(data);
                })
            };

            $scope.getUser(1);


        }

    ]);
