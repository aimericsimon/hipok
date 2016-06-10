(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('AuthEtatDetailController', AuthEtatDetailController);

    AuthEtatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'AuthEtat', 'Profile', 'ImageAuth'];

    function AuthEtatDetailController($scope, $rootScope, $stateParams, entity, AuthEtat, Profile, ImageAuth) {
        var vm = this;

        vm.authEtat = entity;

        var unsubscribe = $rootScope.$on('hipokApp:authEtatUpdate', function(event, result) {
            vm.authEtat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
