/**
 * Created by Angela on 5/21/2016.
 */

amadeusounds.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);
amadeusounds.directive('validFile', function () {
    return {
        require: 'ngModel',
        link: function (scope, el, attrs, ngModel) {
            ngModel.$render = function () {
                ngModel.$setViewValue(el.val());
            };

            el.bind('change', function () {
                scope.$apply(function () {
                    ngModel.$render();
                });
            });
        }
    };
});
amadeusounds.directive("repeatEnd", function () {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            if (scope.$last) {
                scope.$eval(attrs.repeatEnd);
            }
        }
    };
});
amadeusounds.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function (file, uploadUrl, type) {
        var fd = new FormData();
        console.log("file " + file);
        fd.append(type, file);
        $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function (response) {
                console.log(response);
            })
            .error(function (response) {
                console.log(response);
                alert("Upload unsuccessful, please try again.");
            });
    }
}]);
amadeusounds.controller('SongUploadController',
    ['$scope', '$http', '$rootScope', '$location', '$filter', '$stateParams', '$timeout', 'fileUpload', 'UserService',

        function ($scope, $http, $rootScope, $location, $filter, $stateParams, $timeout, fileUpload, UserService) {
            $rootScope.currentUser = {"username": "lala@gmail.com", "id": 1};


            $http.get('/api/categories').success(function (data, status, headers, config) {
                $scope.categories = data;
                $scope.category = data[1].name;
                console.log($scope.categories);
            }).error(function (data, status, headers, config) {
                // log error
            });

            $scope.uploadSongFile = function (file, songId) {
                console.log('file is ');
                console.log(file);
                var uploadUrl = "/api/admin/songs/" + songId + "/upload";
                fileUpload.uploadFileToUrl(file, uploadUrl, "song");
            };
            $scope.newValue = function (categoryName) {
                $scope.category = categoryName;
            };
            $scope.submit = function () {

                $scope.song = {};
                $scope.song.name = $scope.name;
                $scope.song.description = $scope.description;
                console.log(JSON.stringify($scope.song));

                $http({
                    method: "POST",
                    url: "/api/admin/songs",
                    data: $scope.song,
                    params: {"category": $scope.category, "username": $scope.currentUser.username}
                })
                    .then(
                        function success1(response) {
                            console.log(response.data.message);
                            $rootScope.songId = response.data.message;
                            $scope.uploadSongFile($scope.songFile, response.data.message);
                        },
                        function error1(response) {
                            console.log(response);
                            return response;
                        }
                    );
            }


            $scope.uploadSongImage = function (imageId, file) {
                console.log('file is ');
                console.log(file);
                //?????
                var uploadUrl = "/api/admin/songs/" + $rootScope.songId + "/images/" + imageId + "/upload";
                fileUpload.uploadFileToUrl(file, uploadUrl,"image");
            };

            function uploadImage(image, imageFile) {
                console.log("image");
                console.log(imageFile);
                $http({
                    method: "POST",
                    url: "/api/admin/songs/" + $rootScope.songId + "/images",
                    data: image
                })
                    .then(
                        function success1(response) {
                            console.log(response.data.message);
                            $scope.uploadSongImage(response.data.message, imageFile);
                        },
                        function error1(response) {
                            console.log(response);
                            return response;
                        }
                    );
            }


            $scope.submit2 = function () {

                //alert($rootScope.songId);

                $scope.image1 = {};
                $scope.image1.song_id = $rootScope.songId;
                $scope.image1.timing = $scope.minutes1 * 60 + $scope.seconds1;

                $scope.image2 = {};
                $scope.image2.song_id = $rootScope.songId;
                $scope.image2.timing = $scope.minutes2 * 60 + $scope.seconds2;

                $scope.image3 = {};
                $scope.image3.song_id = $rootScope.songId;
                $scope.image3.timing = $scope.minutes3 * 60 + $scope.seconds3;

                $scope.image4 = {};
                $scope.image4.song_id = $rootScope.songId;
                $scope.image4.timing = $scope.minutes4 * 60 + $scope.seconds4;

                $scope.image5 = {};
                $scope.image5.song_id = $rootScope.songId;
                $scope.image5.timing = $scope.minutes5 * 60 + $scope.seconds5;

                $scope.image6 = {};
                $scope.image6.song_id = $rootScope.songId;
                $scope.image6.timing = $scope.minutes6 * 60 + $scope.seconds6;
                // alert("song: "+$scope.seconds1 + " "+$scope.minutes1);
                if ($scope.imageFile1) {
                    uploadImage($scope.image1, $scope.imageFile1);
                }
                if ($scope.imageFile2) {
                    uploadImage($scope.image2, $scope.imageFile2);
                }
                if ($scope.imageFile3) {
                    uploadImage($scope.image3, $scope.imageFile3);
                }
                if ($scope.imageFile4) {
                    uploadImage($scope.image4, $scope.imageFile4);
                }
                if ($scope.imageFile5) {
                    uploadImage($scope.image5, $scope.imageFile5);
                }
                if ($scope.imageFile6) {
                    uploadImage($scope.image6, $scope.imageFile6);
                }
            }

            $scope.onEnd = function () {
                $timeout(function () {
                    console.log("end of repeat");
                    $("select").imagepicker({
                        show_label: true,
                        limit: 5
                    });
                    /*
                     angular.forEach($rootScope.tags, function(tag,index){
                     console.log(tag.id);
                     $http({
                     method: "GET",
                     url: 'api/tags/'+tag.id+'/image',
                     contentType: "image/jpg"
                     })
                     .then(
                     function success1(response) {
                     console.log(response);
                     $("#"+tag.id).attr("data-img-src",response.data);
                     },
                     function error1(response) {
                     console.log(response);
                     return response;
                     }
                     );

                     });
                     */

                }, 1);
            };

            $http.get('/api/tags').success(function (data, status, headers, config) {
                $scope.tags = data;
                console.log("i am here for tags");
                console.log($scope.tags);
            }).error(function (data, status, headers, config) {
                // log error
            });


            $scope.submit3 = function () {
                var values = $('#selectedTags').val();
                //console.log("selected")
                angular.forEach(values, function (tagId, index) {
                    $http({
                        method: "POST",
                        url: '/api/tags/' + tagId + '/songs/' + $rootScope.songId
                    })
                        .then(
                            function success1(response) {
                                console.log(response);
                            },
                            function error1(response) {
                                console.log(response);
                                return response;
                            }
                        );
                });

            }

        }


    ]);
