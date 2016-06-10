'use strict';

angular.module('hipokApp').controller('TitleRefDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TitleRef', 'ExtendedUser',
        function($scope, $stateParams, $modalInstance, entity, TitleRef, ExtendedUser) {

        $scope.titleRef = entity;
        $scope.extendedusers = ExtendedUser.query();
        $scope.load = function(id) {
            TitleRef.get({id : id}, function(result) {
                $scope.titleRef = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:titleRefUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.titleRef.id != null) {
                TitleRef.update($scope.titleRef, onSaveSuccess, onSaveError);
            } else {
                TitleRef.save($scope.titleRef, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
