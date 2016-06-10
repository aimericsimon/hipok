'use strict';

angular.module('hipokApp').controller('DeviceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Device', 'Profile',
        function($scope, $stateParams, $uibModalInstance, entity, Device, Profile) {

        $scope.device = entity;
        $scope.profiles = Profile.query();
        $scope.load = function(id) {
            Device.get({id : id}, function(result) {
                $scope.device = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:deviceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.device.id != null) {
                Device.update($scope.device, onSaveSuccess, onSaveError);
            } else {
                Device.save($scope.device, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
