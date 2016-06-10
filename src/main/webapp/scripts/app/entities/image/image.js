'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('image', {
                parent: 'entity',
                url: '/images',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Images'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/image/images.html',
                        controller: 'ImageController'
                    }
                },
                resolve: {
                }
            })
            .state('image.detail', {
                parent: 'entity',
                url: '/image/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Image'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/image/image-detail.html',
                        controller: 'ImageDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Image', function($stateParams, Image) {
                        return Image.get({id : $stateParams.id});
                    }]
                }
            })
            .state('image.new', {
                parent: 'image',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/image/image-dialog.html',
                        controller: 'ImageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    imageUrl: null,
                                    imageThumbnailUrl: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('image', null, { reload: true });
                    }, function() {
                        $state.go('image');
                    })
                }]
            })
            .state('image.edit', {
                parent: 'image',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/image/image-dialog.html',
                        controller: 'ImageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Image', function(Image) {
                                return Image.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('image', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('image.delete', {
                parent: 'image',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/image/image-delete-dialog.html',
                        controller: 'ImageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Image', function(Image) {
                                return Image.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('image', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
