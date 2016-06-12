amadeusounds.factory("PlayerService", function ($rootScope) {
    var playerUrl = "http://localhost:8080/api/play/"

    $rootScope.currentTime = 0;
    $rootScope.duration = 0;
    $rootScope.percentage = 0;
    $rootScope.paused = true;

    $rootScope.rangeChanged = function () {
        if ($rootScope.audio != undefined) {
            var temp = $rootScope.audio.duration / 100 * parseInt($rootScope.percentage);
            temp = Math.min(temp, $rootScope.audio.duration);
            $rootScope.audio.currentTime = parseInt(temp);
        }
    };

    $rootScope.updateSongImage = function() {
        // do not change the image if it has only on or no images at all
        if($rootScope.currentSong.images == undefined || $rootScope.currentSong.images.length == 1) {
            return;
        }

        var currentImagePosition = $rootScope.currentSong.currentImagePosition;
        var currentImage = $rootScope.currentSong.images[currentImagePosition];

        var a = 0;
        var counter = 0;

        $rootScope.currentSong.images.forEach(function(i) {
            if ($rootScope.currentTime > i.timing) {
                a = counter;
            };
            counter++;
        });

        //if (a > 0) {
            $rootScope.currentSong.currentImagePosition = a;
        //}
    };

    var service = {
        load: function (song) {
            $rootScope.currentSong = song;
            var url = playerUrl + song.id;
            console.log(url);

            if ($rootScope.audio === undefined) {
                $rootScope.audio = new Audio(url);
            }
            else {
                $rootScope.percentage = 0;
                $rootScope.audio.ontimeupdate = undefined;
                $rootScope.audio.pause();
                $rootScope.audio = undefined;
                $rootScope.audio = new Audio(url);
            }

            $rootScope.paused = false;
            $rootScope.percentage = 0;

            $rootScope.audio.ontimeupdate = function () {
                var currentTime = $rootScope.audio.currentTime;
                var duration = $rootScope.audio.duration;

                var percentage = currentTime / duration * 100;
                percentage = parseInt(percentage);
                percentage = Math.min(percentage, 100);

                //console.log(percentage);

                $rootScope.percentage = percentage;
                $rootScope.duration = parseInt(duration);
                $rootScope.currentTime = parseInt(currentTime);

                $rootScope.updateSongImage();
                $rootScope.$apply();
            };

            $rootScope.audio.play();

        },
        play: function (song) {
            if ($rootScope.audio != undefined) {
                $rootScope.paused = false;
                $rootScope.audio.play();
            }
        },
        stop: function (song) {
            $rootScope.paused = true;
            $rootScope.audio.pause();
            $rootScope.audio.currentTime = 0;
        },
        pause: function (song) {
            $rootScope.paused = true;
            $rootScope.audio.pause();
        }
    };

    return service;
});