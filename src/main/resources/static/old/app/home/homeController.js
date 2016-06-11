amadeusounds.controller('HomeController', [ '$rootScope', '$scope', '$http', 'HomeService', 'PlayerService',
    function($rootScope, $scope, $http, HomeService, PlayerService) {

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
                $scope.songs.forEach(function(s){
                    s.currentImagePosition = s.images != undefined ? 0 : undefined;
                });
            });
        };

        $scope.load = function(song) {
            song.currentImagePosition = song.images != undefined ? 0 : undefined;
            PlayerService.load(song);
        };
        // Fill the page on load
        $scope.getLatestSongs(1);

        $scope.pageChanged = function(newPage) {
            $scope.getLatestSongs(newPage);
        };

        $scope.hideMe = function(song, image) {
            if (song.id != $rootScope.currentSong.id) {
                return false;
            }

            var position = song.images.indexOf(image);

            if (position == 0) {
                if($rootScope.currentTime > image.timing) {
                    return true;
                } else {
                    return false;
                }
            }

            if (position == song.images.length - 1) {
                if($rootScope.currentTime > image.timing) {
                    return false;
                } else {
                    return true;
                }
            }

            if ($rootScope.currentTime <= song.images[position]) {
                return false;
            } else {
                return true;
            }

        };

        $scope.hasImageInFuture = function(time, images) {
            //console.log("DA BEE!!");
            var flag = false;

            images.forEach(function(item){
                if (item.timing > time) {
                    flag = true;
                    return;
                }
            });

            return flag;
        };
    }
]);