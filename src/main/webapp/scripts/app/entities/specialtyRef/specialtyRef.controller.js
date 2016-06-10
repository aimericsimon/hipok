'use strict';

angular.module('hipokApp')
    .controller('SpecialtyRefController', function ($scope, $state, $modal, SpecialtyRef) {
      
        $scope.specialtyRefs = [];
        $scope.loadAll = function() {
            SpecialtyRef.query(function(result) {
               $scope.specialtyRefs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.specialtyRef = {
                label: null,
                id: null
            };
        };
    });
