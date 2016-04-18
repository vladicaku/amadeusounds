/**
 * Created by Angela on 4/18/2016.
 */
amadeusounds.factory('UserService', ['$http', function($http) {

     return {

         getUser: function(user_id) {
             return $http({
                 url: '/api/users/'+user_id,
                 method: "GET"
             });
         },


         getUserComments: function(user_id) {
             return $http({
                 url: '/api/users/' + user_id + '/comments',
                 method: "GET"
             });
         },

         getUserRatings: function(user_id) {
             return $http({
                 url: '/api/users/' + $user_id + '/ratings',
                 method: "GET"
             });
         },

         storeUser: function(userData) {
             return $http({
                 url: '/api/users',
                 method: "POST",
                 // Necessary to indicate that the sent data is JSON
                 headers: { 'Content-Type' : 'application/json' },
                 data: userData
             });
         },


     }

 }]);
