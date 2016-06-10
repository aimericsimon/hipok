(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('ImageAuthController', ImageAuthController);

    ImageAuthController.$inject = ['$scope', '$state', 'ImageAuth'];

    function ImageAuthController ($scope, $state, ImageAuth) {
        var vm = this;
        
        vm.imageAuths = [];

        loadAll();

        function loadAll() {
            ImageAuth.query(function(result) {
                vm.imageAuths = result;
            });
        }
    }
})();
