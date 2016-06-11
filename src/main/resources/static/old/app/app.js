var amadeusounds = angular.module('amadeusounds-app', [
    'ui.router',
    'ngResource',
    'ngAnimate',
    'xeditable',
    //'angularMoment',
    'ui.bootstrap',
    'ngPasswordStrength'
]);

amadeusounds.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'app/home/index.html'
        })
        .state('register', {
            url: '/register',
            templateUrl: 'views/registerView.html'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'views/loginView.html',
            controller: 'LoginController'
        })
        .state('upload', {
            url: '/upload',
            templateUrl: 'views/uploadSongView.html',
            requireLogin: true
        })
        .state('upload.first', {
            url: '/uploadFirst',
            templateUrl: 'views/uploadSongForm1.html',
            requireLogin: true
        })
        .state('upload.second', {
            url: '/uploadSecond',
            templateUrl: 'views/uploadSongForm2.html',
            requireLogin: true
        })
        .state('upload.third', {
            url: '/uploadThird',
            templateUrl: 'views/uploadSongForm3.html',
            requireLogin: true
        });

}]);