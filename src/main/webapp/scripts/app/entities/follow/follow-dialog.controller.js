'use strict';

angular.module('hipokApp').controller('FollowDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Follow', 'Profile',
        function($scope, $stateParams, $uibModalInstance, entity, Follow, Profile) {

        $scope.follow = entity;
        $scope.profiles = Profile.query();
        $scope.load = function(id) {
            Follow.get({id : id}, function(result) {
                $scope.follow = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:followUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.follow.id != null) {
                Follow.update($scope.follow, onSaveSuccess, onSaveError);
            } else {
                Follow.save($scope.follow, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
