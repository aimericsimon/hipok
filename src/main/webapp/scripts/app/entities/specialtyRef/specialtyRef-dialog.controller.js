'use strict';

angular.module('hipokApp').controller('SpecialtyRefDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SpecialtyRef', 'Publication', 'MedicalTypeRef',
        function($scope, $stateParams, $modalInstance, entity, SpecialtyRef, Publication, MedicalTypeRef) {

        $scope.specialtyRef = entity;
        $scope.publications = Publication.query();
        $scope.medicaltyperefs = MedicalTypeRef.query();
        $scope.load = function(id) {
            SpecialtyRef.get({id : id}, function(result) {
                $scope.specialtyRef = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:specialtyRefUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.specialtyRef.id != null) {
                SpecialtyRef.update($scope.specialtyRef, onSaveSuccess, onSaveError);
            } else {
                SpecialtyRef.save($scope.specialtyRef, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
