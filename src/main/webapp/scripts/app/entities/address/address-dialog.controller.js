'use strict';

angular.module('hipokApp').controller('AddressDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Address', 'ExtendedUser',
        function($scope, $stateParams, $modalInstance, entity, Address, ExtendedUser) {

        $scope.address = entity;
        $scope.extendedusers = ExtendedUser.query();
        $scope.load = function(id) {
            Address.get({id : id}, function(result) {
                $scope.address = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:addressUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.address.id != null) {
                Address.update($scope.address, onSaveSuccess, onSaveError);
            } else {
                Address.save($scope.address, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
