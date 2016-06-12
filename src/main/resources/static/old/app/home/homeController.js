amadeusounds.controller('HomeController', ['$rootScope', '$scope', '$stateParams', 'HomeService', 'PlayerService',
    function ($rootScope, $scope, $stateParams, HomeService, PlayerService) {

        $scope.songs = {};
        $scope.title = "";

        $scope.pagination = {
            itemsPerPage: 6,
            current: 1,
            totalElements: 0
        };

        var where = $stateParams.arg1 || 'home';
        var what = $stateParams.arg2 || '';
        //alert(where + '-' + what);
        var findObjectById = function getByValue2(arr, value) {
            var result  = arr.filter(function(o){return o.id == value;} );
            return result? result[0] : null; // or undefined
        };

        $scope.searchSongs = function () {
            console.log("searchSongs called");
            HomeService.searchSongs($scope.pagination, what).then(function (response) {
                $scope.songs = response.data.content;
                $scope.pagination.totalElements = response.data.totalElements;
                $scope.songs.forEach(function (s) {
                    s.currentImagePosition = s.images != undefined ? 0 : undefined;
                });
            });
        };

        $scope.getLatestSongs = function () {
            console.log("getLatestSongs called");
            HomeService.getLatestSongs($scope.pagination).then(function (response) {
                $scope.songs = response.data.content;
                $scope.pagination.totalElements = response.data.totalElements;
                $scope.songs.forEach(function (s) {
                    s.currentImagePosition = s.images != undefined ? 0 : undefined;
                });
            });
        };

        $scope.getSongsByCategory = function () {
            console.log("getSongsByCategory called");
            HomeService.getSongsByCategory($scope.pagination, what).then(function (response) {
                $scope.songs = response.data.content;
                $scope.pagination.totalElements = response.data.totalElements;
                $scope.songs.forEach(function (s) {
                    s.currentImagePosition = s.images != undefined ? 0 : undefined;
                });
            });
        };

        $scope.getSongsByTag = function () {
            console.log("getSongsByTag called");
            HomeService.getSongsByTag($scope.pagination, what).then(function (response) {
                $scope.songs = response.data.content;
                $scope.pagination.totalElements = response.data.totalElements;
                $scope.songs.forEach(function (s) {
                    s.currentImagePosition = s.images != undefined ? 0 : undefined;
                });
            });
        };

        $scope.getTrendingSongs = function () {
            console.log("getTrendingSongs called");
            $scope.title = "Trending"
            HomeService.getTrendingSongs($scope.pagination).then(function (response) {
                $scope.songs = response.data.content;
                $scope.pagination.totalElements = response.data.totalElements;
                $scope.songs.forEach(function (s) {
                    s.currentImagePosition = s.images != undefined ? 0 : undefined;
                });
            });
        };

        // load the file into the player
        $scope.load = function (song) {
            song.currentImagePosition = song.images != undefined ? 0 : undefined;
            PlayerService.load(song);
        };

        if(where == 'home') {
            $scope.title = "Home";
            $scope.func = $scope.getLatestSongs;
        }
        else if(where == 'trending') {
            $scope.title = "Trending";
            $scope.func = $scope.getTrendingSongs;
        }
        else if(where == 'categories') {
            $scope.title = findObjectById($rootScope.categories, what).name;
            $scope.func = $scope.getSongsByCategory;
        }
        else if(where == 'tags') {
            $scope.title = findObjectById($rootScope.tags, what).name;
            $scope.func = $scope.getSongsByTag;
        }
        else if(where == 'search') {
            $scope.title = "Search: " + "'" + what + "'";
            $scope.func = $scope.searchSongs;
        }
        else {
            $scope.title = "Home";
            $scope.func = $scope.getLatestSongs;
        };

        // Fill the page on load
        //$scope.func();
        $scope.func();

        $scope.pageChanged = function (newPage) {
            $scope.func(newPage);
        };


    }
]);