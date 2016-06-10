'use strict';

angular.module('hipokApp')
    .controller('ReportingDetailController', function ($scope, $rootScope, $stateParams, entity, Reporting, Profile, Publication) {
        $scope.reporting = entity;
        $scope.load = function (id) {
            Reporting.get({id: id}, function(result) {
                $scope.reporting = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:reportingUpdate', function(event, result) {
            $scope.reporting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
