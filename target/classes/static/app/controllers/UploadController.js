/**
 * Created by Angela on 4/24/2016.
 */

amadeusounds.directive('fileInput',['$parse',function($parse){
    return{
        restrict: 'A',
        link: function(scope,elm,attrs){
            elm.bind('change',function(){
                $parse(attrs.fileInput).assign(scope,elm[0].files)
                scope.$apply()
            })
        }
    }
}]).
controller('UploadController',['$scope','$http',
    function($scope,$http){
        $scope.filesChanged = function(elm){
            $scope.files=elm.files;
            $scope.$apply();
        }
        $scope.upload = function(){
            var fd = new FormData()
            angular.forEach($scope.files,function(file){
                fd.append('file',file)
            })

            $http.post('upload.txt',fd,{
                    transformRequest: angular.identity,
                    headers:{'Content-Type':undefined}
                })
                .success(function(fd){
                    console.log(fd)
                })
        }
    }]);