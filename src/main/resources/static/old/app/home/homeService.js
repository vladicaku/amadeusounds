/**
 * Home Service
 */
amadeusounds.factory('HomeService', ['$http', function ($http) {
    var songsUrl = 'http://localhost:8080/api/songs/';
    var service = {};

    service.searchSongs = function(pagination, term) {
        var url = songsUrl + '/search/' + term + '?page=' + (pagination.current - 1) + '&size=' + pagination.itemsPerPage;
        console.log(url);
        return $http({
            method: 'GET',
            url: url
        });
    };

    service.getLatestSongs = function(pagination) {
        var url = songsUrl + '/latest?page=' + (pagination.current - 1) + '&size=' + pagination.itemsPerPage;
        console.log(url);
        return $http({
            method: 'GET',
            url: url
        });
    };

    service.getSongsByTag = function(pagination, tag) {
        var url = songsUrl + '/by-tag/' + tag +'/?page=' + (pagination.current - 1) + '&size=' + pagination.itemsPerPage;
        console.log(url);
        return $http({
            method: 'GET',
            url: url
        });
    };

    service.getSongsByCategory = function(pagination, category) {
        var url = songsUrl + '/by-category/' + category +'/?page=' + (pagination.current - 1) + '&size=' + pagination.itemsPerPage;
        console.log(url);
        return $http({
            method: 'GET',
            url: url
        });
    };


    service.getTrendingSongs = function(pagination) {
        var url = songsUrl + '/trending?page=' + (pagination.current - 1) + '&size=' + pagination.itemsPerPage;
        console.log(url);
        return $http({
            method: 'GET',
            url: url
        });
    };

    service.getTopRatedSongs = function(pagination) {
        var url = songsUrl + '/top-rated?page=' + (pagination.current - 1) + '&size=' + pagination.itemsPerPage;
        console.log(url);
        return $http({
            method: 'GET',
            url: url
        });
    };

    return service;
}]);
