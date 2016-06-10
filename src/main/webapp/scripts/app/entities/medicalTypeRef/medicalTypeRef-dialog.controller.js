'use strict';

angular.module('hipokApp').controller('MedicalTypeRefDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MedicalTypeRef', 'SpecialtyRef', 'ExtendedUser',
        function($scope, $stateParams, $modalInstance, entity, MedicalTypeRef, SpecialtyRef, ExtendedUser) {

        $scope.medicalTypeRef = entity;
        $scope.specialtyrefs = SpecialtyRef.query();
        $scope.extendedusers = ExtendedUser.query();
        $scope.load = function(id) {
            MedicalTypeRef.get({id : id}, function(result) {
                $scope.medicalTypeRef = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:medicalTypeRefUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.medicalTypeRef.id != null) {
                MedicalTypeRef.update($scope.medicalTypeRef, onSaveSuccess, onSaveError);
            } else {
                MedicalTypeRef.save($scope.medicalTypeRef, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
