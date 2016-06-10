'use strict';

angular.module('hipokApp')
    .controller('ProfileDetailController', function ($scope, $rootScope, $stateParams, entity, Profile, Follow, Publication, Comment, Reporting, Share, ExtendedUser, Notification, Device) {
        $scope.profile = entity;
        $scope.load = function (id) {
            Profile.get({id: id}, function(result) {
                $scope.profile = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:profileUpdate', function(event, result) {
            $scope.profile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
