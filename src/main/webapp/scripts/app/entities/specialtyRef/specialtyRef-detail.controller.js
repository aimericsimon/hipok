'use strict';

angular.module('hipokApp')
    .controller('SpecialtyRefDetailController', function ($scope, $rootScope, $stateParams, entity, SpecialtyRef, Publication, MedicalTypeRef) {
        $scope.specialtyRef = entity;
        $scope.load = function (id) {
            SpecialtyRef.get({id: id}, function(result) {
                $scope.specialtyRef = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:specialtyRefUpdate', function(event, result) {
            $scope.specialtyRef = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
