'use strict';

angular.module('hipokApp')
    .controller('CommentDetailController', function ($scope, $rootScope, $stateParams, entity, Comment, Profile, Publication, Hashtag) {
        $scope.comment = entity;
        $scope.load = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:commentUpdate', function(event, result) {
            $scope.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
