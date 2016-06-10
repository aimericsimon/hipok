'use strict';

angular.module('hipokApp')
    .controller('AddressDetailController', function ($scope, $rootScope, $stateParams, entity, Address, ExtendedUser) {
        $scope.address = entity;
        $scope.load = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:addressUpdate', function(event, result) {
            $scope.address = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
