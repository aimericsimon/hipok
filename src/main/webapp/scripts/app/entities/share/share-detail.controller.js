'use strict';

angular.module('hipokApp')
    .controller('ShareDetailController', function ($scope, $rootScope, $stateParams, entity, Share, Profile, Publication) {
        $scope.share = entity;
        $scope.load = function (id) {
            Share.get({id: id}, function(result) {
                $scope.share = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:shareUpdate', function(event, result) {
            $scope.share = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
