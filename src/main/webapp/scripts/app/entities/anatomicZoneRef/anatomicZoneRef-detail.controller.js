'use strict';

angular.module('hipokApp')
    .controller('AnatomicZoneRefDetailController', function ($scope, $rootScope, $stateParams, entity, AnatomicZoneRef, Publication) {
        $scope.anatomicZoneRef = entity;
        $scope.load = function (id) {
            AnatomicZoneRef.get({id: id}, function(result) {
                $scope.anatomicZoneRef = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:anatomicZoneRefUpdate', function(event, result) {
            $scope.anatomicZoneRef = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
