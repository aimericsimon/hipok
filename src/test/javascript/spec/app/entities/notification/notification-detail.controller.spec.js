'use strict';

describe('Controller Tests', function() {

    describe('Notification Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockNotification, MockProfile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockNotification = jasmine.createSpy('MockNotification');
            MockProfile = jasmine.createSpy('MockProfile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Notification': MockNotification,
                'Profile': MockProfile
            };
            createController = function() {
                $injector.get('$controller')("NotificationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:notificationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
