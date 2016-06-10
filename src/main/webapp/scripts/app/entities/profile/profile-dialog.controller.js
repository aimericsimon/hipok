'use strict';

angular.module('hipokApp').controller('ProfileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Profile', 'Follow', 'Publication', 'Comment', 'Reporting', 'Share', 'ExtendedUser', 'Notification', 'Device',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Profile, Follow, Publication, Comment, Reporting, Share, ExtendedUser, Notification, Device) {

        $scope.profile = entity;
        $scope.follows = Follow.query();
        $scope.publications = Publication.query();
        $scope.comments = Comment.query();
        $scope.reportings = Reporting.query();
        $scope.shares = Share.query();
        $scope.extendedusers = ExtendedUser.query({filter: 'profile-is-null'});
        $q.all([$scope.profile.$promise, $scope.extendedusers.$promise]).then(function() {
            if (!$scope.profile.extendedUserId) {
                return $q.reject();
            }
            return ExtendedUser.get({id : $scope.profile.extendedUserId}).$promise;
        }).then(function(extendedUser) {
            $scope.extendedusers.push(extendedUser);
        });
        $scope.notifications = Notification.query();
        $scope.devices = Device.query();
        $scope.load = function(id) {
            Profile.get({id : id}, function(result) {
                $scope.profile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:profileUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.profile.id != null) {
                Profile.update($scope.profile, onSaveSuccess, onSaveError);
            } else {
                Profile.save($scope.profile, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
