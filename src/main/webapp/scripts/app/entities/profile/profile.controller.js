'use strict';

angular.module('hipokApp')
    .controller('ProfileController', function ($scope, $state, Profile) {

        $scope.profiles = [];
        $scope.loadAll = function() {
            Profile.query(function(result) {
               $scope.profiles = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.profile = {
                avatarUrl: null,
                description: null,
                avatarThumbnailUrl: null,
                id: null
            };
        };
    });
