'use strict';

describe('SituationRef Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSituationRef, MockExtendedUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSituationRef = jasmine.createSpy('MockSituationRef');
        MockExtendedUser = jasmine.createSpy('MockExtendedUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SituationRef': MockSituationRef,
            'ExtendedUser': MockExtendedUser
        };
        createController = function() {
            $injector.get('$controller')("SituationRefDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:situationRefUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
