'use strict';

angular.module('hipokApp')
    .controller('AnatomicZoneRefController', function ($scope, $state, $modal, AnatomicZoneRef) {
      
        $scope.anatomicZoneRefs = [];
        $scope.loadAll = function() {
            AnatomicZoneRef.query(function(result) {
               $scope.anatomicZoneRefs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.anatomicZoneRef = {
                label: null,
                id: null
            };
        };
    });
