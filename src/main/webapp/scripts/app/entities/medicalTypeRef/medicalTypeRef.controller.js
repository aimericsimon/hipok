'use strict';

angular.module('hipokApp')
    .controller('MedicalTypeRefController', function ($scope, $state, $modal, MedicalTypeRef) {
      
        $scope.medicalTypeRefs = [];
        $scope.loadAll = function() {
            MedicalTypeRef.query(function(result) {
               $scope.medicalTypeRefs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.medicalTypeRef = {
                subtype: null,
                label: null,
                id: null
            };
        };
    });
