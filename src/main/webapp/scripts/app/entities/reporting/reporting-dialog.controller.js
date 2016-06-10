'use strict';

angular.module('hipokApp').controller('ReportingDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Reporting', 'Profile', 'Publication',
        function($scope, $stateParams, $modalInstance, entity, Reporting, Profile, Publication) {

        $scope.reporting = entity;
        $scope.profiles = Profile.query();
        $scope.publications = Publication.query();
        $scope.load = function(id) {
            Reporting.get({id : id}, function(result) {
                $scope.reporting = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:reportingUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.reporting.id != null) {
                Reporting.update($scope.reporting, onSaveSuccess, onSaveError);
            } else {
                Reporting.save($scope.reporting, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
