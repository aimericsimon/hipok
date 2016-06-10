'use strict';

angular.module('hipokApp')
    .controller('HashtagDetailController', function ($scope, $rootScope, $stateParams, entity, Hashtag) {
        $scope.hashtag = entity;
        $scope.load = function (id) {
            Hashtag.get({id: id}, function(result) {
                $scope.hashtag = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:hashtagUpdate', function(event, result) {
            $scope.hashtag = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
