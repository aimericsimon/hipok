'use strict';

angular.module('hipokApp')
    .controller('PublicationController', function ($scope, $state, Publication, ParseLinks) {

        $scope.publications = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Publication.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.publications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.publication = {
                description: null,
                location: null,
                visibility: null,
                publicationDate: null,
                nbVizualisations: null,
                processedDescription: null,
                id: null
            };
        };
    });
