'use strict';

angular.module('hipokApp').controller('ShareDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Share', 'Profile', 'Publication',
        function($scope, $stateParams, $modalInstance, entity, Share, Profile, Publication) {

        $scope.share = entity;
        $scope.profiles = Profile.query();
        $scope.publications = Publication.query();
        $scope.load = function(id) {
            Share.get({id : id}, function(result) {
                $scope.share = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:shareUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.share.id != null) {
                Share.update($scope.share, onSaveSuccess, onSaveError);
            } else {
                Share.save($scope.share, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
