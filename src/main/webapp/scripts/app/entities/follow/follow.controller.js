'use strict';

angular.module('hipokApp')
    .controller('FollowController', function ($scope, $state, Follow) {

        $scope.follows = [];
        $scope.loadAll = function() {
            Follow.query(function(result) {
               $scope.follows = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.follow = {
                state: null,
                date: null,
                id: null
            };
        };
    });
