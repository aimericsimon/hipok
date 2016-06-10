'use strict';

angular.module('hipokApp').controller('SituationRefDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SituationRef', 'ExtendedUser',
        function($scope, $stateParams, $modalInstance, entity, SituationRef, ExtendedUser) {

        $scope.situationRef = entity;
        $scope.extendedusers = ExtendedUser.query();
        $scope.load = function(id) {
            SituationRef.get({id : id}, function(result) {
                $scope.situationRef = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:situationRefUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.situationRef.id != null) {
                SituationRef.update($scope.situationRef, onSaveSuccess, onSaveError);
            } else {
                SituationRef.save($scope.situationRef, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
