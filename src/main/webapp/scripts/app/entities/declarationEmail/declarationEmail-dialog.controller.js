'use strict';

angular.module('hipokApp').controller('DeclarationEmailDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeclarationEmail',
        function($scope, $stateParams, $uibModalInstance, entity, DeclarationEmail) {

        $scope.declarationEmail = entity;
        $scope.load = function(id) {
            DeclarationEmail.get({id : id}, function(result) {
                $scope.declarationEmail = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:declarationEmailUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.declarationEmail.id != null) {
                DeclarationEmail.update($scope.declarationEmail, onSaveSuccess, onSaveError);
            } else {
                DeclarationEmail.save($scope.declarationEmail, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
