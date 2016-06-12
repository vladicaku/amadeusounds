amadeusounds.directive('file', function(){
    return {
        scope: {
            file: '='
        },
        link: function(scope, el, attrs){
            el.bind('change', function(event){
                var files = event.target.files;
                var file = files[0];
                scope.file = file ? file.name : undefined;
                scope.$apply();
            });
        }
    };
});
amadeusounds.controller("RegisterController", ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {

    $scope.data1 = {};
    $scope.countries={};
    $scope.param={};
    $http.get('json/countries.json').
    success(function(data, status, headers, config) {
        $scope.countries = data;

    }).
    error(function(data, status, headers, config) {
        // log error
    });

    $scope.SendData = function () {
        $scope.data1.firstName = $scope.firstName;
        $scope.data1.lastName= $scope.lastName;
        $scope.data1.email= $scope.email;
        $scope.data1.password= $scope.password;
        $scope.data1.location = $scope.location;
        $scope.data1.image= $scope.image;
        $scope.data1.biography= $scope.biography;
        $scope.data1.website= $scope.website;
        $http({
            method: "POST",
            url: "/api/users/register",
            data: $scope.data1
        })
            .then(
                function success1(response) {
                    console.log(response);
                    console.log($scope.param);
                    console.log($scope.param.file);
                    $scope.uploadSongImage(response.data.message.id,$scope.param.file);

                },
                function error1(response) {
                    console.log(response);
                    return response;
                }
            );
        $scope.uploadSongImage = function (userId, file) {
            console.log('file is ');
            console.log(file);
            //?????
            var uploadUrl = "/api/users/" + userId+ "/image";
            fileUpload.uploadFileToUrl(file, uploadUrl,"image");
        };

    };

}]);