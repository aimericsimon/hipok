'use strict';

describe('Controller Tests', function() {

    describe('Declaration Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeclaration, MockProfile, MockDeclarationTypeRef;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeclaration = jasmine.createSpy('MockDeclaration');
            MockProfile = jasmine.createSpy('MockProfile');
            MockDeclarationTypeRef = jasmine.createSpy('MockDeclarationTypeRef');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Declaration': MockDeclaration,
                'Profile': MockProfile,
                'DeclarationTypeRef': MockDeclarationTypeRef
            };
            createController = function() {
                $injector.get('$controller')("DeclarationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:declarationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
