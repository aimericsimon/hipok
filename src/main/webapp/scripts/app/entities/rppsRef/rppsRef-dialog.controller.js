'use strict';

angular.module('hipokApp').controller('RppsRefDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'RppsRef',
        function($scope, $stateParams, $modalInstance, entity, RppsRef) {

        $scope.rppsRef = entity;
        $scope.load = function(id) {
            RppsRef.get({id : id}, function(result) {
                $scope.rppsRef = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:rppsRefUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rppsRef.id != null) {
                RppsRef.update($scope.rppsRef, onSaveSuccess, onSaveError);
            } else {
                RppsRef.save($scope.rppsRef, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
