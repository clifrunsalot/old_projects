"use strict";

angular.module("confusionApp")

.controller('IndexController', ['$scope', '$stateParams', 'menuFactory', function ($scope, $stateParams, menuFactory) {
        //        var dish = menuFactory.getDish(parseInt($stateParams.id, 10));
        //$scope.dish = dish;
            }
        ])

.controller('MenuController', ['$scope', 'menuFactory', function ($scope, menuFactory) {

    $scope.tab = 1;
    $scope.filtText = "";
    $scope.showDetails = false;

    $scope.dishes = menuFactory.getDishes();

    $scope.select = function (setTab) {
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

    $scope.isSelected = function (checkTab) {
        return ($scope.tab === checkTab);
    };

    $scope.toggleDetails = function () {
        $scope.showDetails = !$scope.showDetails;
    };
        }])


.controller("ContactController", ["$scope", function ($scope) {

    $scope.feedback = {
        mychannel: "",
        firstName: "",
        lastName: "",
        agree: false,
        email: ""
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

.controller("FeedbackController", ["$scope", function ($scope) {

    $scope.sendFeedback = function () {

        console.log($scope.feedback);

        if ($scope.feedback.agree && ($scope.feedback.mychannel == "")) {
            $scope.invalidChannelSelection = true;
            console.log("incorrect");
        } else {
            $scope.invalidChannelSelection = false;
            $scope.feedback = {
                mychannel: "",
                firstName: "",
                lastName: "",
                agree: false,
                email: ""
            };
            $scope.feedback.mychannel = "";
            $scope.feedbackForm.$setPristine();
            console.log($scope.feedback);
        }
    };
}])

.controller('DishDetailController', ['$scope', '$stateParams', 'menuFactory', function ($scope, $stateParams, menuFactory) {
        var dish = menuFactory.getDish(parseInt($stateParams.id, 10));
        $scope.dish = dish;
            }
        ])

.controller("DishCommentController", ["$scope", function ($scope) {

    /*
    Prepopulate comments list.
    */
    var comments = [
        {
            rating: 5,
            comment: "Imagine all the eatables, living in conFusion!",
            author: "John Lemon",
            date: "2012-10-16T17:57:28.556094Z"
        },
        {
            rating: 4,
            comment: "Sends anyone to heaven, I wish I could get my mother-in-law to eat it!",
            author: "Paul McVites",
            date: "2014-09-05T17:57:28.556094Z"
        },
        {
            rating: 3,
            comment: "Eat it, just eat it!",
            author: "Michael Jaikishan",
            date: "2015-02-13T17:57:28.556094Z"
        },
        {
            rating: 4,
            comment: "Ultimate, Reaching for the stars!",
            author: "Ringo Starry",
            date: "2013-12-02T17:57:28.556094Z"
        },
        {
            rating: 2,
            comment: "It's your birthday, were gonna party!",
            author: "25 Cent",
            date: "2011-12-02T17:57:28.556094Z"
        }];

    $scope.comments = comments;

    /*
    Capture real-time entry here.
    */
    var real_time_cmts = {
        rating: 5,
        comment: "",
        name: "",
        date_time: ""
    };
    $scope.real_time_cmts = real_time_cmts;

    /*
    Assuming the angularjs error handling in the form is 
    correct, this method is executed without 
    additional checks.
    */
    $scope.submitComment = function () {

        // Use the current date and time upon submission.
        $scope.real_time_cmts.date_time = new Date().toISOString();

        /*
        Add viewers comments to the list.
        Invoke parseInt on rating for sorting purposes later.
        */
        $scope.comments.push({
            rating: parseInt($scope.real_time_cmts.rating),
            comment: $scope.real_time_cmts.comment,
            author: $scope.real_time_cmts.name,
            date: $scope.real_time_cmts.date_time
        });

        // Reset the real-time field values.
        $scope.real_time_cmts = {
            name: "",
            rating: 5,
            comment: "",
            date_time: ""
        };

        // Reset the form to a pristine state.
        $scope.commentForm.$setPristine();

    };
}])

;