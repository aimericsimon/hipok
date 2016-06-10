'use strict';

angular.module('hipokApp')
    .controller('FollowDetailController', function ($scope, $rootScope, $stateParams, entity, Follow, Profile) {
        $scope.follow = entity;
        $scope.load = function (id) {
            Follow.get({id: id}, function(result) {
                $scope.follow = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:followUpdate', function(event, result) {
            $scope.follow = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
