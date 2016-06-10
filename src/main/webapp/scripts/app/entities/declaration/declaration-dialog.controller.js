'use strict';

angular.module('hipokApp').controller('DeclarationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Declaration', 'Profile', 'DeclarationTypeRef',
        function($scope, $stateParams, $uibModalInstance, entity, Declaration, Profile, DeclarationTypeRef) {

        $scope.declaration = entity;
        $scope.profiles = Profile.query();
        $scope.declarationtyperefs = DeclarationTypeRef.query();
        $scope.load = function(id) {
            Declaration.get({id : id}, function(result) {
                $scope.declaration = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:declarationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.declaration.id != null) {
                Declaration.update($scope.declaration, onSaveSuccess, onSaveError);
            } else {
                Declaration.save($scope.declaration, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDeclarationDate = {};

        $scope.datePickerForDeclarationDate.status = {
            opened: false
        };

        $scope.datePickerForDeclarationDateOpen = function($event) {
            $scope.datePickerForDeclarationDate.status.opened = true;
        };
}]);
