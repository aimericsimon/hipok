(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('ImageAuthDialogController', ImageAuthDialogController);

    ImageAuthDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ImageAuth'];

    function ImageAuthDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ImageAuth) {
        var vm = this;

        vm.imageAuth = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.imageAuth.id !== null) {
                ImageAuth.update(vm.imageAuth, onSaveSuccess, onSaveError);
            } else {
                ImageAuth.save(vm.imageAuth, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hipokApp:imageAuthUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
