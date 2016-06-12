/**
 * Created by Angela on 6/9/2016.
 */

amadeusounds.controller('ProfileController',
    ['$scope', '$http', '$rootScope', '$location', '$filter', '$stateParams', '$timeout','fileUpload',

        function($scope, $http, $rootScope, $location, $filter, $stateParams, $timeout, fileUpload) {


            $scope.currentUserId=6;//da se smeni
            $scope.profileImage={};
            $scope.profileImage.file={};
            $scope.stateRead="Edit profile";
            $scope.stateEdit="Save changes";
            $scope.state=$scope.stateRead;
            $scope.stateImage=$scope.stateRead;
            console.log("lalal");

            $http.get('/api/users/userInfo').success(function (data, status, headers, config) {
                $scope.user = data;
                $scope.oldPass=$scope.user.password;
                console.log($scope.user);
                $scope.currentUserId = $scope.user.id;
            }).error(function (data, status, headers, config) {
                console.log("Greska pri zemanje na korisnikot!!!");
            });


            $scope.editOrSave = function(){
                if($scope.state==$scope.stateRead){
                    $scope.state = $scope.stateEdit;
                }else{
                    $scope.state = $scope.stateRead;
                    $scope.editUserInfo();
                }
            }

            $scope.editUserInfo = function () {

                console.log("I am editing!");

                $scope.newUser={};
                $scope.newUser.firstName=$scope.user.firstName;
                $scope.newUser.lastName=$scope.user.lastName;
                console.log($scope.oldPass+" "+$scope.user.password);
                if($scope.oldPass!=$scope.user.password) {
                    $scope.newUser.password = $scope.user.password;
                }
                $scope.newUser.location = $scope.user.location;
                $scope.newUser.website = $scope.user.website;
                $scope.newUser.biography = $scope.user.biography;
                $scope.newUser.email = $scope.user.email;

                $http({
                    method: "PUT",
                    url: "/api/admin/users/"+$scope.currentUserId,
                    data: $scope.newUser
                })
                    .then(
                        function success1(response) {
                            console.log(response.data.message);
                            $scope.state = $scope.stateRead;
                        },
                        function error1(response) {
                            console.log(response);
                            return response;
                        }
                    );

            };
            $scope.cancel=function(){
                if($scope.state==$scope.stateEdit){
                    $scope.state=$scope.stateRead;
                }else{

                }

            };
            $scope.uploadImageFile = function (file, userId) {
                console.log('file is ');
                console.log(file);
                var uploadUrl = "/api/users/" + userId + "/image";
                fileUpload.uploadFileToUrl(file, uploadUrl, "image");
            };

            $scope.changeImage = function(){
                console.log("changing image");
                $scope.uploadImageFile($scope.profileImage.file, $scope.currentUserId);
                // $("#form-image").attr("src","/api/users/"+$scope.currentUserId+"/image");
            };

            $scope.countries={};
            $http.get('json/countries.json').
            success(function(data, status, headers, config) {
                $scope.countries = data;

            }).
            error(function(data, status, headers, config) {
                // log error
            });


        }



    ]);

