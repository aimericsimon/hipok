'use strict';

describe('Controller Tests', function() {

    describe('DeclarationTypeRef Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeclarationTypeRef, MockDeclaration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeclarationTypeRef = jasmine.createSpy('MockDeclarationTypeRef');
            MockDeclaration = jasmine.createSpy('MockDeclaration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DeclarationTypeRef': MockDeclarationTypeRef,
                'Declaration': MockDeclaration
            };
            createController = function() {
                $injector.get('$controller')("DeclarationTypeRefDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:declarationTypeRefUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
