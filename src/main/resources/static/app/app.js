var amadeusounds = angular.module('amadeusounds-app', [
    'ui.router',
    'ngResource',
    'ngAnimate',
    'xeditable',
    'angularMoment',
    'ui.bootstrap',
    'ngPasswordStrength'
]);

amadeusounds.config(['$stateProvider','$urlRouterProvider',function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'views/homeView.html'
        })
        .state('register', {
            url: '/register',
            templateUrl: 'views/registerView.html'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'views/loginView.html'
        })
        .state('upload', {
            url: '/upload',
            templateUrl: 'views/uploadSongView.html'
        });

}]);