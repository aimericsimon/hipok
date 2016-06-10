'use strict';

describe('Share Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockShare, MockProfile, MockPublication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockShare = jasmine.createSpy('MockShare');
        MockProfile = jasmine.createSpy('MockProfile');
        MockPublication = jasmine.createSpy('MockPublication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Share': MockShare,
            'Profile': MockProfile,
            'Publication': MockPublication
        };
        createController = function() {
            $injector.get('$controller')("ShareDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'hipokApp:shareUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
