/**
 * Create the menu
 */
amadeusounds.run(['$rootScope', '$state', 'CategoriesService', 'TagsService',
    function ($rootScope, $state, CategoriesService, TagsService) {
        console.log("Menu setup");

        CategoriesService.getAllCategories().then(function (response) {
            $rootScope.categories = response.data;
        });

        TagsService.getAllTags().then(function (response) {
            $rootScope.tags = response.data;
        });
    }]);