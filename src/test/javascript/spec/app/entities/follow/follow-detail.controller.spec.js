'use strict';

describe('Controller Tests', function() {

    describe('Follow Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFollow, MockProfile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFollow = jasmine.createSpy('MockFollow');
            MockProfile = jasmine.createSpy('MockProfile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Follow': MockFollow,
                'Profile': MockProfile
            };
            createController = function() {
                $injector.get('$controller')("FollowDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:followUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
