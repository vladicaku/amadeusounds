amadeusounds.factory("PlayerService", function ($rootScope) {
    $rootScope.currentTime = 0;
    $rootScope.duration = 0;
    $rootScope.percentage = 0;

    $rootScope.rangeChanged = function () {
        if ($rootScope.audio != undefined) {
            var temp = $rootScope.audio.duration / 100 * parseInt($rootScope.percentage);
            temp = Math.min(temp, $rootScope.audio.duration);
            $rootScope.audio.currentTime = parseInt(temp);
        }
    };

    var player = {
        load: function (song) {
            $rootScope.currentSong = song;

            if ($rootScope.audio === undefined) {
                $rootScope.audio = new Audio($rootScope.currentSong);
            }
            else {
                $rootScope.audio.pause();
                $rootScope.audio = new Audio($rootScope.currentSong);
            }

            $rootScope.audio.ontimeupdate = function () {
                var currentTime = $rootScope.audio.currentTime;
                var duration = $rootScope.audio.duration;

                var percentage = currentTime / duration * 100;
                percentage = parseInt(percentage);
                percentage = Math.min(percentage, 100);

                console.log(percentage);

                $rootScope.percentage = percentage;
                $rootScope.duration = parseInt(duration);
                $rootScope.currentTime = parseInt(currentTime);
                $rootScope.$apply()
            };

            $rootScope.audio.play();
        },
        play: function (song) {
            if ($rootScope.audio != undefined) {
                $rootScope.audio.play();
            }
        },
        stop: function (song) {
            $rootScope.audio.pause();
            $rootScope.audio.currentTime = 0;
        },
        pause: function (song) {
            $rootScope.audio.pause();
        }
    };

    $rootScope.player = player;
    return player;
});