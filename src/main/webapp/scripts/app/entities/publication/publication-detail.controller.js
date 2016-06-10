'use strict';

angular.module('hipokApp')
    .controller('PublicationDetailController', function ($scope, $rootScope, $stateParams, entity, Publication, Profile, Comment, Reporting, Share, Hashtag, AnatomicZoneRef, SpecialtyRef, Image) {
        $scope.publication = entity;
        $scope.load = function (id) {
            Publication.get({id: id}, function(result) {
                $scope.publication = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:publicationUpdate', function(event, result) {
            $scope.publication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
