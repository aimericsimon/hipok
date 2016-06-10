'use strict';

angular.module('hipokApp')
    .controller('AddressController', function ($scope, $state, $modal, Address) {
      
        $scope.addresss = [];
        $scope.loadAll = function() {
            Address.query(function(result) {
               $scope.addresss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.address = {
                label: null,
                postalCode: null,
                city: null,
                id: null
            };
        };
    });
