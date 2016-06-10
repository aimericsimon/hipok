'use strict';

angular.module('hipokApp').controller('PublicationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Publication', 'Profile', 'Comment', 'Reporting', 'Share', 'Hashtag', 'AnatomicZoneRef', 'SpecialtyRef', 'Image',
        function($scope, $stateParams, $uibModalInstance, entity, Publication, Profile, Comment, Reporting, Share, Hashtag, AnatomicZoneRef, SpecialtyRef, Image) {

        $scope.publication = entity;
        $scope.profiles = Profile.query();
        $scope.comments = Comment.query();
        $scope.reportings = Reporting.query();
        $scope.shares = Share.query();
        $scope.hashtags = Hashtag.query();
        $scope.anatomiczonerefs = AnatomicZoneRef.query();
        $scope.specialtyrefs = SpecialtyRef.query();
        $scope.images = Image.query();
        $scope.load = function(id) {
            Publication.get({id : id}, function(result) {
                $scope.publication = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:publicationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.publication.id != null) {
                Publication.update($scope.publication, onSaveSuccess, onSaveError);
            } else {
                Publication.save($scope.publication, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForPublicationDate = {};

        $scope.datePickerForPublicationDate.status = {
            opened: false
        };

        $scope.datePickerForPublicationDateOpen = function($event) {
            $scope.datePickerForPublicationDate.status.opened = true;
        };
}]);
