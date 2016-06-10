'use strict';

angular.module('hipokApp')
    .controller('ShareController', function ($scope, $state, $modal, Share) {
      
        $scope.shares = [];
        $scope.loadAll = function() {
            Share.query(function(result) {
               $scope.shares = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.share = {
                id: null
            };
        };
    });
