'use strict';

angular.module('hipokApp')
    .controller('NotificationController', function ($scope, $state, Notification) {

        $scope.notifications = [];
        $scope.loadAll = function() {
            Notification.query(function(result) {
               $scope.notifications = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.notification = {
                creationDate: null,
                read: null,
                type: null,
                itemId: null,
                data: null,
                id: null
            };
        };
    });
