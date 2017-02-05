'use strict';

angular.module('confusionApp')

.controller('MenuController', ['$scope', 'menuFactory', function ($scope, menuFactory) {

    $scope.tab = 1;
    $scope.filtText = '';
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

.controller('ContactController', ['$scope', function ($scope) {

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

.controller('FeedbackController', ['$scope', function ($scope) {

    $scope.sendFeedback = function () {

        console.log($scope.feedback);

        if ($scope.feedback.agree && ($scope.feedback.mychannel === "")) {
            $scope.invalidChannelSelection = true;
            console.log('incorrect');
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

}])

.controller('DishCommentController', ['$scope', '$stateParams', 'menuFactory', function ($scope, $stateParams, menuFactory) {

    $scope.mycomment = {
        rating: 5,
        comment: "",
        author: "",
        date: ""
    };
    
    /*
    * Be sure to add the comments of the dish shown in dishdetail.html.
    */
    $scope.comments = menuFactory.getDish(parseInt($stateParams.id, 10)).comments;

    $scope.submitComment = function () {

        $scope.mycomment.date = new Date().toISOString();
        console.log($scope.mycomment);

        $scope.dish.comments.push($scope.mycomment);

        $scope.commentForm.$setPristine();

        $scope.mycomment = {
            rating: 5,
            comment: "",
            author: "",
            date: ""
        };
    };
}])

// Implement the IndexController
.controller('IndexController', ['$scope', 'menuFactory', 'corporateFactory', function ($scope, menuFactory, corporateFactory) {

    $scope.dish = menuFactory.getDish(0);
    $scope.promotion = menuFactory.getPromotion(0);
    $scope.leader = corporateFactory.getLeader(3);

}])

// Implement the AboutController
.controller('AboutController', ['$scope', 'corporateFactory', function ($scope, corporateFactory) {

    $scope.leaders = corporateFactory.getLeaders();
}])

;