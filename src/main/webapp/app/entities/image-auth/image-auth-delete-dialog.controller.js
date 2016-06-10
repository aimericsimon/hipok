(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('ImageAuthDeleteController',ImageAuthDeleteController);

    ImageAuthDeleteController.$inject = ['$uibModalInstance', 'entity', 'ImageAuth'];

    function ImageAuthDeleteController($uibModalInstance, entity, ImageAuth) {
        var vm = this;

        vm.imageAuth = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ImageAuth.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
