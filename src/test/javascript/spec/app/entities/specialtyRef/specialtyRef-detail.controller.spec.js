'use strict';

describe('SpecialtyRef Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSpecialtyRef, MockPublication, MockMedicalTypeRef;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSpecialtyRef = jasmine.createSpy('MockSpecialtyRef');
        MockPublication = jasmine.createSpy('MockPublication');
        MockMedicalTypeRef = jasmine.createSpy('MockMedicalTypeRef');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SpecialtyRef': MockSpecialtyRef,
            'Publication': MockPublication,
            'MedicalTypeRef': MockMedicalTypeRef
        };
        createController = function() {
            $injector.get('$controller')("SpecialtyRefDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:specialtyRefUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
