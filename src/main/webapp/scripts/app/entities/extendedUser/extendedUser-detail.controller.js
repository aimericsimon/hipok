'use strict';

angular.module('hipokApp')
    .controller('ExtendedUserDetailController', function ($scope, $rootScope, $stateParams, entity, ExtendedUser, MedicalTypeRef, RppsRef, User, Address, TitleRef, SituationRef) {
        $scope.extendedUser = entity;
        $scope.load = function (id) {
            ExtendedUser.get({id: id}, function(result) {
                $scope.extendedUser = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:extendedUserUpdate', function(event, result) {
            $scope.extendedUser = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
