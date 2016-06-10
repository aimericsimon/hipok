'use strict';

describe('Controller Tests', function() {

    describe('DeclarationEmail Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeclarationEmail;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeclarationEmail = jasmine.createSpy('MockDeclarationEmail');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DeclarationEmail': MockDeclarationEmail
            };
            createController = function() {
                $injector.get('$controller')("DeclarationEmailDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:declarationEmailUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
