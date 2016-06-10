(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('ImageAuthDetailController', ImageAuthDetailController);

    ImageAuthDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ImageAuth'];

    function ImageAuthDetailController($scope, $rootScope, $stateParams, entity, ImageAuth) {
        var vm = this;

        vm.imageAuth = entity;

        var unsubscribe = $rootScope.$on('hipokApp:imageAuthUpdate', function(event, result) {
            vm.imageAuth = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
