'use strict';

angular.module('confusionApp')

    .constant("baseURL", "http://localhost:3000/")

    .service('menuFactory', ['$resource', 'baseURL', function($resource, baseURL) {

        this.getPromotions = function() {
            return $resource(baseURL + "promotions/:id", null, {
                'update': {
                    method: 'PUT'
                }
            });
        };

        this.getDishes = function() {
            return $resource(baseURL + "dishes/:id", null, {
                'update': {
                    method: 'PUT'
                }
            });
        };
    }])

    .factory('feedbackFactory', ['$resource', 'baseURL', function($resource, baseURL) {

        var feedbackList = {};

        feedbackList.getFeedback = function() {
            return $resource(baseURL + 'feedback/', null, {
                'create': {
                    method: 'POST'
                }
            });
        };

        return feedbackList;
    }])

    // .factory('corporateFactory', function() {
    .factory('corporateFactory', ['$resource', 'baseURL', function($resource, baseURL) {

        var corpfac = {};

        corpfac.getLeaders = function() {
            return $resource(baseURL + "leadership/:id", null, {
                'update': {
                    method: 'PUT'
                }
            });
        };
        // Remember this is a factory not a service
        return corpfac;

    }]);
