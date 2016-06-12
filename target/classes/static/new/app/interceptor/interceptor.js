/**
 * Intercept the url change and check if the client is logged in
 */
amadeusounds.run(['$rootScope', '$state', 'AuthenticationService', function ($rootScope, $state, AuthenticationService) {

    $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
        var requireLogin = toState.requireLogin || false;
        var isAuthenticated = AuthenticationService.isAuthenticated();

        if (requireLogin && !isAuthenticated) {
            console.log('You must be logged in. Redirecting to login page.');
            event.preventDefault();
            $state.go('login');
        }
    });

}]);