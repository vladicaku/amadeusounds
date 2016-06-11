amadeusounds.controller('HomeController', [ '$scope', '$http', 'HomeService', 'PlayerService',
    function($scope, $http, HomeService, PlayerService) {

        $scope.songs = {};

        $scope.pagination = {
            itemsPerPage: 6,
            current: 1,
            totalElements: 0
        };

        $scope.getLatestSongs = function() {
            console.log("getLatestSongs called");
            HomeService.getLatestSongs($scope.pagination).then(function(response){
                $scope.songs = response.data.content;
                $scope.pagination.totalElements = response.data.totalElements;
            });
        };

        // Fill the page on load
        $scope.getLatestSongs(1);

        $scope.pageChanged = function(newPage) {
            $scope.getLatestSongs(newPage);
        };
    }
]);