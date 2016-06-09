angular.module('amadeusoundsApp').controller("HomeController", function ($scope, $rootScope, PlayerService) {
    $scope.songs = [
        {
            id: 1,
            url: "assets/songs/1.mp3"
        },
        {
            id: 2,
            url: 'file:///D:/z.mp3'
        }
    ];
});