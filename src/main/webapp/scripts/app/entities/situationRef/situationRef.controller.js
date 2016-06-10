'use strict';

angular.module('hipokApp')
    .controller('SituationRefController', function ($scope, $state, $modal, SituationRef) {
      
        $scope.situationRefs = [];
        $scope.loadAll = function() {
            SituationRef.query(function(result) {
               $scope.situationRefs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.situationRef = {
                label: null,
                code: null,
                id: null
            };
        };
    });
