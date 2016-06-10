'use strict';

angular.module('hipokApp')
    .controller('SituationRefDetailController', function ($scope, $rootScope, $stateParams, entity, SituationRef, ExtendedUser) {
        $scope.situationRef = entity;
        $scope.load = function (id) {
            SituationRef.get({id: id}, function(result) {
                $scope.situationRef = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:situationRefUpdate', function(event, result) {
            $scope.situationRef = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
