'use strict';

describe('Controller Tests', function() {

    describe('Profile Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProfile, MockFollow, MockPublication, MockComment, MockReporting, MockShare, MockExtendedUser, MockNotification, MockDevice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProfile = jasmine.createSpy('MockProfile');
            MockFollow = jasmine.createSpy('MockFollow');
            MockPublication = jasmine.createSpy('MockPublication');
            MockComment = jasmine.createSpy('MockComment');
            MockReporting = jasmine.createSpy('MockReporting');
            MockShare = jasmine.createSpy('MockShare');
            MockExtendedUser = jasmine.createSpy('MockExtendedUser');
            MockNotification = jasmine.createSpy('MockNotification');
            MockDevice = jasmine.createSpy('MockDevice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Profile': MockProfile,
                'Follow': MockFollow,
                'Publication': MockPublication,
                'Comment': MockComment,
                'Reporting': MockReporting,
                'Share': MockShare,
                'ExtendedUser': MockExtendedUser,
                'Notification': MockNotification,
                'Device': MockDevice
            };
            createController = function() {
                $injector.get('$controller')("ProfileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:profileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
