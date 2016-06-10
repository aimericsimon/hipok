'use strict';

describe('Controller Tests', function() {

    describe('AuthEtat Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAuthEtat, MockProfile, MockImageAuth;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAuthEtat = jasmine.createSpy('MockAuthEtat');
            MockProfile = jasmine.createSpy('MockProfile');
            MockImageAuth = jasmine.createSpy('MockImageAuth');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AuthEtat': MockAuthEtat,
                'Profile': MockProfile,
                'ImageAuth': MockImageAuth
            };
            createController = function() {
                $injector.get('$controller')("AuthEtatDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:authEtatUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
