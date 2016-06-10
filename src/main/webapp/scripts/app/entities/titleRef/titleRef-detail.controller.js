'use strict';

angular.module('hipokApp')
    .controller('TitleRefDetailController', function ($scope, $rootScope, $stateParams, entity, TitleRef, ExtendedUser) {
        $scope.titleRef = entity;
        $scope.load = function (id) {
            TitleRef.get({id: id}, function(result) {
                $scope.titleRef = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:titleRefUpdate', function(event, result) {
            $scope.titleRef = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
