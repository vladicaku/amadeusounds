/**
 * Categories Service
 */
amadeusounds.factory('CategoriesService', ['$http', function ($http) {
    var songsUrl = 'http://localhost:8080/api/categories/';
    var service = {};

    service.getAllCategories = function() {
        console.log(songsUrl);
        return $http({
            method: 'GET',
            url: songsUrl
        });
    };

    return service;
}]);
