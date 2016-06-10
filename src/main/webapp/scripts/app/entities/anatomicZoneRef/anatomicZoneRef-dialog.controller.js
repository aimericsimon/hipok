'use strict';

angular.module('hipokApp').controller('AnatomicZoneRefDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AnatomicZoneRef', 'Publication',
        function($scope, $stateParams, $modalInstance, entity, AnatomicZoneRef, Publication) {

        $scope.anatomicZoneRef = entity;
        $scope.publications = Publication.query();
        $scope.load = function(id) {
            AnatomicZoneRef.get({id : id}, function(result) {
                $scope.anatomicZoneRef = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:anatomicZoneRefUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.anatomicZoneRef.id != null) {
                AnatomicZoneRef.update($scope.anatomicZoneRef, onSaveSuccess, onSaveError);
            } else {
                AnatomicZoneRef.save($scope.anatomicZoneRef, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
