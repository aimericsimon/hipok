'use strict';

angular.module('hipokApp')
    .controller('NotificationDetailController', function ($scope, $rootScope, $stateParams, entity, Notification, Profile) {
        $scope.notification = entity;
        $scope.load = function (id) {
            Notification.get({id: id}, function(result) {
                $scope.notification = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:notificationUpdate', function(event, result) {
            $scope.notification = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
