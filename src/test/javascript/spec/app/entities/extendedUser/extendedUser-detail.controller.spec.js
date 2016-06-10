'use strict';

describe('ExtendedUser Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockExtendedUser, MockMedicalTypeRef, MockRppsRef, MockUser, MockAddress, MockTitleRef, MockSituationRef;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockExtendedUser = jasmine.createSpy('MockExtendedUser');
        MockMedicalTypeRef = jasmine.createSpy('MockMedicalTypeRef');
        MockRppsRef = jasmine.createSpy('MockRppsRef');
        MockUser = jasmine.createSpy('MockUser');
        MockAddress = jasmine.createSpy('MockAddress');
        MockTitleRef = jasmine.createSpy('MockTitleRef');
        MockSituationRef = jasmine.createSpy('MockSituationRef');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ExtendedUser': MockExtendedUser,
            'MedicalTypeRef': MockMedicalTypeRef,
            'RppsRef': MockRppsRef,
            'User': MockUser,
            'Address': MockAddress,
            'TitleRef': MockTitleRef,
            'SituationRef': MockSituationRef
        };
        createController = function() {
            $injector.get('$controller')("ExtendedUserDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:extendedUserUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
