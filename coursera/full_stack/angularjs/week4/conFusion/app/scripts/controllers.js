'use strict';

angular.module('confusionApp')

    .controller('MenuController', ['$scope', 'menuFactory', function($scope, menuFactory) {

        $scope.tab = 1;
        $scope.filtText = '';
        $scope.showDetails = false;
        $scope.showMenu = false;
        $scope.message = "Loading ...";

        menuFactory.getDishes().query(
            function(response) {
                $scope.dishes = response;
                $scope.showMenu = true;
            },
            function(response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            });

        $scope.select = function(setTab) {
            $scope.tab = setTab;

            if (setTab === 2) {
                $scope.filtText = "appetizer";
            } else if (setTab === 3) {
                $scope.filtText = "mains";
            } else if (setTab === 4) {
                $scope.filtText = "dessert";
            } else {
                $scope.filtText = "";
            }
        };

        $scope.isSelected = function(checkTab) {
            return ($scope.tab === checkTab);
        };

        $scope.toggleDetails = function() {
            $scope.showDetails = !$scope.showDetails;
        };
    }])

    .controller('ContactController', ['$scope', function($scope) {

        $scope.userFeedback = {
            mychannel: "",
            firstName: "",
            lastName: "",
            agree: false,
            email: "",
            comment: ""
        };

        var channels = [{
            value: "tel",
            label: "Tel."
        }, {
            value: "Email",
            label: "Email"
        }];

        $scope.channels = channels;
        $scope.invalidChannelSelection = false;

    }])

    .controller('FeedbackController', ['$scope', 'feedbackFactory', function($scope, feedbackFactory) {

        $scope.feedback = feedbackFactory.getFeedback();

        console.log($scope.feedback);

        feedbackFactory.getFeedback().query(
            function(response) {
                $scope.feedback = response;
            },
            function(response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            }
        );

        $scope.sendFeedback = function() {

            console.log($scope.feedback);

            if ($scope.userFeedback.agree && ($scope.userFeedback.mychannel === "")) {
                $scope.invalidChannelSelection = true;
                console.log('incorrect');
            } else {
                $scope.invalidChannelSelection = false;

                console.log(JSON.stringify($scope.userFeedback));

                feedbackFactory.getFeedback().create($scope.userFeedback);

                $scope.userFeedback = {
                    mychannel: "",
                    firstName: "",
                    lastName: "",
                    agree: "",
                    email: "",
                    comment: ""
                };

                $scope.userFeedback.mychannel = "";
                $scope.feedbackForm.$setPristine();
            }
        };
    }])

    .controller('DishDetailController', ['$scope', '$stateParams', 'menuFactory', function($scope, $stateParams, menuFactory) {

        $scope.showDish = false;
        $scope.message = "Loading ...";
        $scope.dish = menuFactory.getDishes().get({
            id: parseInt($stateParams.id, 10)
        }).$promise.then(
            function(response) {
                $scope.dish = response;
                $scope.showDish = true;
            },
            function(response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            }
        );
    }])

    .controller('DishCommentController', ['$scope', '$stateParams', 'menuFactory', function($scope, $stateParams, menuFactory) {

        $scope.dish.comments = menuFactory.getDishes().get({
            id: parseInt($stateParams.id, 10)
        }).comments;

        $scope.real_time_cmts = {
            rating: 5,
            comment: "",
            author: "",
            date: ""
        };

        $scope.submitComment = function() {

            $scope.real_time_cmts.date = new Date().toISOString();

            console.log(JSON.stringify($scope.real_time_cmts));

            /*
             * The rating must be converted to an int before persisting
             * to accommodate sorting.
             */
            $scope.dish.comments.push({
                rating: parseInt($scope.real_time_cmts.rating, 10),
                comment: $scope.real_time_cmts.comment,
                author: $scope.real_time_cmts.author,
                date: $scope.real_time_cmts.date
            });

            menuFactory.getDishes().update({
                id: $scope.dish.id
            }, $scope.dish);

            $scope.commentForm.$setPristine();
            $scope.real_time_cmts = {
                rating: 5,
                comment: "",
                author: "",
                date: ""
            };
        };

    }])

    // Implement the IndexController
    .controller('IndexController', ['$scope', 'menuFactory', 'corporateFactory', function($scope, menuFactory, corporateFactory) {

        $scope.showDish = false;
        $scope.message = "Loading ...";
        $scope.dish = menuFactory.getDishes().get({
            id: 0
        }).$promise.then(
            function(response) {
                $scope.dish = response;
                $scope.showDish = true;
            },
            function(response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            }
        );

        $scope.showPromotion = false;
        $scope.promotion = menuFactory.getPromotions().get({
            id: 0
        }).$promise.then(
            function(response) {
                $scope.promotion = response;
                $scope.showPromotion = true;
            },
            function(response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            }
        );

        $scope.showLeader = false;
        $scope.leader = corporateFactory.getLeaders().get({
            id: 0
        }).$promise.then(
            function(response) {
                $scope.leader = response;
                $scope.showLeader = true;
            },
            function(response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            }
        );
    }])

    // Implement the AboutController
    .controller('AboutController', ['$scope', 'corporateFactory', function($scope, corporateFactory) {

        $scope.showLeaders = false;
        $scope.leaders = corporateFactory.getLeaders();
        corporateFactory.getLeaders().query(
            function(response) {
                $scope.leaders = response;
                $scope.showLeaders = true;
            },
            function(response) {
                $scope.message = "Error: " + response.status + " " + response.statusText;
            });
    }])

;
