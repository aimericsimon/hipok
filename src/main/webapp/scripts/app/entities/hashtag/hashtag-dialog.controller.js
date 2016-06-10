'use strict';

angular.module('hipokApp').controller('HashtagDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Hashtag',
        function($scope, $stateParams, $modalInstance, entity, Hashtag) {

        $scope.hashtag = entity;
        $scope.load = function(id) {
            Hashtag.get({id : id}, function(result) {
                $scope.hashtag = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:hashtagUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.hashtag.id != null) {
                Hashtag.update($scope.hashtag, onSaveSuccess, onSaveError);
            } else {
                Hashtag.save($scope.hashtag, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
