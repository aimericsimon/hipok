'use strict';

angular.module('hipokApp').controller('ExtendedUserDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'ExtendedUser', 'MedicalTypeRef', 'RppsRef', 'User', 'Address', 'TitleRef', 'SituationRef',
        function($scope, $stateParams, $modalInstance, $q, entity, ExtendedUser, MedicalTypeRef, RppsRef, User, Address, TitleRef, SituationRef) {

        $scope.extendedUser = entity;
        $scope.medicaltyperefs = MedicalTypeRef.query();
        $scope.rppsrefs = RppsRef.query({filter: 'extendeduser-is-null'});
        $q.all([$scope.extendedUser.$promise, $scope.rppsrefs.$promise]).then(function() {
            if (!$scope.extendedUser.rppsRefId) {
                return $q.reject();
            }
            return RppsRef.get({id : $scope.extendedUser.rppsRefId}).$promise;
        }).then(function(rppsRef) {
            $scope.rppsrefs.push(rppsRef);
        });
        $scope.users = User.query();
        $scope.addresss = Address.query();
        $scope.titlerefs = TitleRef.query();
        $scope.situationrefs = SituationRef.query();
        $scope.load = function(id) {
            ExtendedUser.get({id : id}, function(result) {
                $scope.extendedUser = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hipokApp:extendedUserUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.extendedUser.id != null) {
                ExtendedUser.update($scope.extendedUser, onSaveSuccess, onSaveError);
            } else {
                ExtendedUser.save($scope.extendedUser, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
