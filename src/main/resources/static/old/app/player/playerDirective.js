amadeusounds.directive("player", function () {
    return {
        scope: true,
        restrict: 'E',
        templateUrl: 'app/player/index.html'
    }
});

angular.module("amadeusoundsApp").directive('someAudio', function () {
    return {
        scope: {
            videoCurrentTime: "=videoCurrentTime"
        },
        controller: function ($scope, $element) {

            $scope.onTimeUpdate = function () {
                console.log($scope.videoCurrentTime);
            };

            console.log("set");
        }
    }
});