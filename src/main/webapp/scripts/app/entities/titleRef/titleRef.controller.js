'use strict';

angular.module('hipokApp')
    .controller('TitleRefController', function ($scope, $state, $modal, TitleRef) {
      
        $scope.titleRefs = [];
        $scope.loadAll = function() {
            TitleRef.query(function(result) {
               $scope.titleRefs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.titleRef = {
                label: null,
                abbreviation: null,
                code: null,
                id: null
            };
        };
    });
