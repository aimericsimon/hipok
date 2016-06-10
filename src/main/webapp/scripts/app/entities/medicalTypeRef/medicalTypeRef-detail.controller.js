'use strict';

angular.module('hipokApp')
    .controller('MedicalTypeRefDetailController', function ($scope, $rootScope, $stateParams, entity, MedicalTypeRef, SpecialtyRef, ExtendedUser) {
        $scope.medicalTypeRef = entity;
        $scope.load = function (id) {
            MedicalTypeRef.get({id: id}, function(result) {
                $scope.medicalTypeRef = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:medicalTypeRefUpdate', function(event, result) {
            $scope.medicalTypeRef = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
