/**
 * Home Service
 */
amadeusounds.factory('HomeService', ['$http', function ($http) {
    var songsUrl = 'http://localhost:8080/api/songs/';
    var service = {};

    service.getLatestSongs = function(pagination) {
        var url = songsUrl + '/latest?page=' + (pagination.current - 1) + '&size=' + pagination.itemsPerPage;
        console.log(url);
        return $http({
            method: 'GET',
            url: url
        });
    };

    return service;
}]);
