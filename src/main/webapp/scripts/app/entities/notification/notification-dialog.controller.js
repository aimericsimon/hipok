'use strict';

angular.module('hipokApp').controller('NotificationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Notification', 'Profile',
        function($scope, $stateParams, $uibModalInstance, entity, Notification, Profile) {

        $scope.notification = entity;
        $scope.profiles = Profile.query();
        $scope.load = function(id) {
            Notification.get({id : id}, function(result) {
                $scope.notification = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:notificationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.notification.id != null) {
                Notification.update($scope.notification, onSaveSuccess, onSaveError);
            } else {
                Notification.save($scope.notification, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreationDate = {};

        $scope.datePickerForCreationDate.status = {
            opened: false
        };

        $scope.datePickerForCreationDateOpen = function($event) {
            $scope.datePickerForCreationDate.status.opened = true;
        };
}]);
