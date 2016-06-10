'use strict';

describe('AnatomicZoneRef Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAnatomicZoneRef, MockPublication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAnatomicZoneRef = jasmine.createSpy('MockAnatomicZoneRef');
        MockPublication = jasmine.createSpy('MockPublication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AnatomicZoneRef': MockAnatomicZoneRef,
            'Publication': MockPublication
        };
        createController = function() {
            $injector.get('$controller')("AnatomicZoneRefDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:anatomicZoneRefUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
