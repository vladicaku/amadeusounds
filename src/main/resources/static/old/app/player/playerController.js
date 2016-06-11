amadeusounds.controller("PlayerController", ['$scope', '$rootScope', 'PlayerService',
    function ($scope, $rootScope, PlayerService) {

        $scope.play = function() {
            PlayerService.play();
        };

        $scope.pause = function() {
            PlayerService.pause();
        };

}]);