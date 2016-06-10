'use strict';

angular.module('hipokApp')
    .controller('ReportingController', function ($scope, $state, $modal, Reporting) {
      
        $scope.reportings = [];
        $scope.loadAll = function() {
            Reporting.query(function(result) {
               $scope.reportings = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.reporting = {
                reportingDate: null,
                id: null
            };
        };
    });
