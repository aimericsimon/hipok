'use strict';

angular.module('hipokApp').controller('EditoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Edito',
        function($scope, $stateParams, $modalInstance, entity, Edito) {

        $scope.edito = entity;
        $scope.load = function(id) {
            Edito.get({id : id}, function(result) {
                $scope.edito = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:editoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.edito.id != null) {
                Edito.update($scope.edito, onSaveSuccess, onSaveError);
            } else {
                Edito.save($scope.edito, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
