(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('AuthEtatDeleteController',AuthEtatDeleteController);

    AuthEtatDeleteController.$inject = ['$uibModalInstance', 'entity', 'AuthEtat'];

    function AuthEtatDeleteController($uibModalInstance, entity, AuthEtat) {
        var vm = this;

        vm.authEtat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AuthEtat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
