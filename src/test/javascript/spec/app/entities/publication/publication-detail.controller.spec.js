'use strict';

describe('Controller Tests', function() {

    describe('Publication Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPublication, MockProfile, MockComment, MockReporting, MockShare, MockHashtag, MockAnatomicZoneRef, MockSpecialtyRef, MockImage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPublication = jasmine.createSpy('MockPublication');
            MockProfile = jasmine.createSpy('MockProfile');
            MockComment = jasmine.createSpy('MockComment');
            MockReporting = jasmine.createSpy('MockReporting');
            MockShare = jasmine.createSpy('MockShare');
            MockHashtag = jasmine.createSpy('MockHashtag');
            MockAnatomicZoneRef = jasmine.createSpy('MockAnatomicZoneRef');
            MockSpecialtyRef = jasmine.createSpy('MockSpecialtyRef');
            MockImage = jasmine.createSpy('MockImage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Publication': MockPublication,
                'Profile': MockProfile,
                'Comment': MockComment,
                'Reporting': MockReporting,
                'Share': MockShare,
                'Hashtag': MockHashtag,
                'AnatomicZoneRef': MockAnatomicZoneRef,
                'SpecialtyRef': MockSpecialtyRef,
                'Image': MockImage
            };
            createController = function() {
                $injector.get('$controller')("PublicationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hipokApp:publicationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
