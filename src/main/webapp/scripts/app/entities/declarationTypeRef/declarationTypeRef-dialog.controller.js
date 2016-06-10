'use strict';

angular.module('hipokApp').controller('DeclarationTypeRefDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeclarationTypeRef', 'Declaration',
        function($scope, $stateParams, $uibModalInstance, entity, DeclarationTypeRef, Declaration) {

        $scope.declarationTypeRef = entity;
        $scope.declarations = Declaration.query();
        $scope.load = function(id) {
            DeclarationTypeRef.get({id : id}, function(result) {
                $scope.declarationTypeRef = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:declarationTypeRefUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.declarationTypeRef.id != null) {
                DeclarationTypeRef.update($scope.declarationTypeRef, onSaveSuccess, onSaveError);
            } else {
                DeclarationTypeRef.save($scope.declarationTypeRef, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
