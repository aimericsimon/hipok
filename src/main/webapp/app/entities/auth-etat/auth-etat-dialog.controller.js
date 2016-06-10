(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('AuthEtatDialogController', AuthEtatDialogController);

    AuthEtatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'AuthEtat', 'Profile', 'ImageAuth'];

    function AuthEtatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, AuthEtat, Profile, ImageAuth) {
        var vm = this;

        vm.authEtat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profiles = Profile.query({filter: 'authetat-is-null'});
        $q.all([vm.authEtat.$promise, vm.profiles.$promise]).then(function() {
            if (!vm.authEtat.profileId) {
                return $q.reject();
            }
            return Profile.get({id : vm.authEtat.profileId}).$promise;
        }).then(function(profile) {
            vm.profiles.push(profile);
        });
        vm.imageauths = ImageAuth.query({filter: 'authetat-is-null'});
        $q.all([vm.authEtat.$promise, vm.imageauths.$promise]).then(function() {
            if (!vm.authEtat.imageAuthId) {
                return $q.reject();
            }
            return ImageAuth.get({id : vm.authEtat.imageAuthId}).$promise;
        }).then(function(imageAuth) {
            vm.imageauths.push(imageAuth);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.authEtat.id !== null) {
                AuthEtat.update(vm.authEtat, onSaveSuccess, onSaveError);
            } else {
                AuthEtat.save(vm.authEtat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hipokApp:authEtatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
