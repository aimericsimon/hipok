'use strict';

describe('Controller Tests', function() {

    describe('ImageAuth Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockImageAuth;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockImageAuth = jasmine.createSpy('MockImageAuth');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ImageAuth': MockImageAuth
            };
            createController = function() {
                $injector.get('$controller')("ImageAuthDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:imageAuthUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
