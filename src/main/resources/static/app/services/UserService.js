/**
 * Created by Angela on 4/18/2016.
 */
amadeusounds.factory('UserService', function($resource) {

    return $resource('/api/user/:action', {}, {
        authenticate : {
            method : 'POST',
            params : {
                'action' : 'authenticate'
            },
            headers : {
                'Content-Type' : 'application/x-www-form-urlencoded'
            }
        }
    });
});
