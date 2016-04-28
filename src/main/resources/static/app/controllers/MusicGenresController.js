/**
 * Created by Angela on 4/23/2016.
 */
amadeusounds.controller('MusicGenresController',function($scope, $http) {
    $http.get('../../json/music_genres.json').
    success(function(data, status, headers, config) {
        $scope.genres = data;
        $scope.checkAll = function () {
            if ($scope.selectedAll) {
                $scope.selectedAll = true;
            } else {
                $scope.selectedAll = false;
            }
            angular.forEach($scope.genres, function (genre1) {
                genre1.Selected = $scope.selectedAll;

            });

        };
        $scope.isActive = false;

        $scope.toggleActive = function() {
            $scope.isActive = !$scope.isActive;
        };
    }).
    error(function(data, status, headers, config) {
        // log error
    });
});