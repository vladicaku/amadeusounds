var amadeusounds = angular.module('amadeusoundsApp', [
    'ui.router',
    'ngResource',
    'ngAnimate',
    'xeditable',
    'ui.bootstrap',
    'ngPasswordStrength',
    'angular-loading-bar'
]);

amadeusounds.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'app/home/index.html'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'app/login/index.html',
            controller: 'LoginController'
        })
        .state('/admin/upload', {
            url: '/admin/upload',
            templateUrl: 'app/admin/upload/index.html',
            requireLogin: true
        })
        .state('register', {
            url: '/register',
            templateUrl: 'views/registerView.html'
        })
        .state('upload', {
            url: '/upload',
            templateUrl: 'views/uploadSongView.html'
        })
        .state('upload.first', {
            url: '/uploadFirst',
            templateUrl: 'views/uploadSongForm1.html'
        })
        .state('upload.second', {
            url: '/uploadSecond',
            templateUrl: 'views/uploadSongForm2.html'
        })
        .state('upload.third', {
            url: '/uploadThird',
            templateUrl: 'views/uploadSongForm3.html'
        });

}]);

//amadeusounds.config(['$httpProvider', function($httpProvider) {
//    $httpProvider.defaults.useXDomain = true;
//    delete $httpProvider.defaults.headers.common['X-Requested-With'];
//}
//]);

