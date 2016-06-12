/**
 * Tags Service
 */
amadeusounds.factory('TagsService', ['$http', function ($http) {
    var tagsUrl = 'http://localhost:8080/api/tags/';
    var service = {};

    service.getAllTags = function() {
        console.log(tagsUrl);
        return $http({
            method: 'GET',
            url: tagsUrl
        });
    };

    return service;
}]);
