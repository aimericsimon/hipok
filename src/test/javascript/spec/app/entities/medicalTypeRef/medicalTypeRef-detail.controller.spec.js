'use strict';

describe('MedicalTypeRef Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMedicalTypeRef, MockSpecialtyRef, MockExtendedUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMedicalTypeRef = jasmine.createSpy('MockMedicalTypeRef');
        MockSpecialtyRef = jasmine.createSpy('MockSpecialtyRef');
        MockExtendedUser = jasmine.createSpy('MockExtendedUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MedicalTypeRef': MockMedicalTypeRef,
            'SpecialtyRef': MockSpecialtyRef,
            'ExtendedUser': MockExtendedUser
        };
        createController = function() {
            $injector.get('$controller')("MedicalTypeRefDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:medicalTypeRefUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
