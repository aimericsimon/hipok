'use strict';

describe('TitleRef Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTitleRef, MockExtendedUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTitleRef = jasmine.createSpy('MockTitleRef');
        MockExtendedUser = jasmine.createSpy('MockExtendedUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TitleRef': MockTitleRef,
            'ExtendedUser': MockExtendedUser
        };
        createController = function() {
            $injector.get('$controller')("TitleRefDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:titleRefUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
