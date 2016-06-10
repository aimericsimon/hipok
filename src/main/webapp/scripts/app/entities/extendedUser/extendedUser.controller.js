'use strict';

angular.module('hipokApp')
    .controller('ExtendedUserController', function ($scope, $state, $modal, ExtendedUser) {
      
        $scope.extendedUsers = [];
        $scope.loadAll = function() {
            ExtendedUser.query(function(result) {
               $scope.extendedUsers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.extendedUser = {
                birthDate: null,
                sex: null,
                practiceLocation: null,
                adeliNumber: null,
                id: null
            };
        };
    });
