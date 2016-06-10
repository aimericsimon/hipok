'use strict';

angular.module('hipokApp').controller('ImageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Image',
        function($scope, $stateParams, $uibModalInstance, entity, Image) {

        $scope.image = entity;
        $scope.load = function(id) {
            Image.get({id : id}, function(result) {
                $scope.image = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:imageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.image.id != null) {
                Image.update($scope.image, onSaveSuccess, onSaveError);
            } else {
                Image.save($scope.image, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
