'use strict';

describe('Reporting Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockReporting, MockProfile, MockPublication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockReporting = jasmine.createSpy('MockReporting');
        MockProfile = jasmine.createSpy('MockProfile');
        MockPublication = jasmine.createSpy('MockPublication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Reporting': MockReporting,
            'Profile': MockProfile,
            'Publication': MockPublication
        };
        createController = function() {
            $injector.get('$controller')("ReportingDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:reportingUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
