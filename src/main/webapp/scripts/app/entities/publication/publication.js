'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('publication', {
                parent: 'entity',
                url: '/publications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Publications'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/publication/publications.html',
                        controller: 'PublicationController'
                    }
                },
                resolve: {
                }
            })
            .state('publication.detail', {
                parent: 'entity',
                url: '/publication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Publication'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/publication/publication-detail.html',
                        controller: 'PublicationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Publication', function($stateParams, Publication) {
                        return Publication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('publication.new', {
                parent: 'publication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/publication/publication-dialog.html',
                        controller: 'PublicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    location: null,
                                    visibility: null,
                                    publicationDate: null,
                                    nbVizualisations: null,
                                    processedDescription: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('publication', null, { reload: true });
                    }, function() {
                        $state.go('publication');
                    })
                }]
            })
            .state('publication.edit', {
                parent: 'publication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/publication/publication-dialog.html',
                        controller: 'PublicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Publication', function(Publication) {
                                return Publication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('publication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('publication.delete', {
                parent: 'publication',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/publication/publication-delete-dialog.html',
                        controller: 'PublicationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Publication', function(Publication) {
                                return Publication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('publication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
