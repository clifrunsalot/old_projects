angular.module('conFusion.controllers', [])

  .controller('AppCtrl', function($scope, $ionicModal, $timeout, $localStorage,
    $ionicPlatform, $cordovaCamera, $cordovaImagePicker) {

    // This sets up the registration modal popup.
    $scope.registration = {};

    // Create the registration modal that we will use later
    $ionicModal.fromTemplateUrl('templates/register.html', {
      scope: $scope
    }).then(function(modal) {
      $scope.registerform = modal;
    });

    // Triggered in the registration modal to close it
    $scope.closeRegister = function() {
      $scope.registerform.hide();
    };

    // Open the registration modal
    $scope.register = function() {
      $scope.registerform.show();
    };

    // Perform the registration action when the user submits the registration form
    $scope.doRegister = function() {
      // Simulate a registration delay. Remove this and replace with your registration
      // code if using a registration system
      $timeout(function() {
        $scope.closeRegister();
      }, 1000);
    };

    // This makes use of the Cordova camera plugin.
    $ionicPlatform.ready(function() {

      var options = {
        quality: 50,
        destinationType: Camera.DestinationType.DATA_URL,
        sourceType: Camera.PictureSourceType.CAMERA,
        allowEdit: true,
        encodingType: Camera.EncodingType.JPEG,
        targetWidth: 100,
        targetHeight: 100,
        popoverOptions: CameraPopoverOptions,
        saveToPhotoAlbum: false
      };

      $scope.takePicture = function() {
        $cordovaCamera.getPicture(options).then(function(imageData) {
          $scope.registration.imgSrc = "data:image/jpeg;base64," + imageData;
        }, function(err) {
          console.log(err);
        });
        $scope.registerform.show();
      };

      $scope.regImages = []];
      $scope.selectPicture = function() {
        var galleryOptions = {
          maximumImagesCount: 10,
          width: 800,
          height: 800,
          quality: 80,
          sourceType: Camera.PictureSourceType.PHOTOLIBRARY
        };

        $cordovaImagePicker.getPictures(galleryOptions)
          .then(function(results) {
            for (var i = 0; i < results.length; i++) {
              console.log(results[i]);
              $scope.regImages.push(results[i]);
            }

            $scope.registration.imgSrc = $scope.regImages[0];
            if(!$scope.$$phase){
              $scope.$apply();
            }

          }, function(error) {
            console.log(error);
          });
          // $scope.registration.imgSrc = results[i];
      };

    });

    // Form data for the login modal
    $scope.loginData = $localStorage.getObject('userinfo', '{}');

    // Create the login modal that we will use later
    $ionicModal.fromTemplateUrl('templates/login.html', {
      scope: $scope
    }).then(function(modal) {
      $scope.modal = modal;
    });

    // Triggered in the login modal to close it
    $scope.closeLogin = function() {
      $scope.modal.hide();
    };

    // Open the login modal
    $scope.login = function() {
      $scope.modal.show();
    };

    // Perform the login action when the user submits the login form
    $scope.doLogin = function() {
      console.log('Doing login', $scope.loginData);
      $localStorage.storeObject('userinfo', $scope.loginData);

      // Simulate a login delay. Remove this and replace with your login
      // code if using a login system
      $timeout(function() {
        $scope.closeLogin();
      }, 1000);
    };

    $scope.reservation = {};

    // Create the reserve modal that we will use later
    $ionicModal.fromTemplateUrl('templates/reserve.html', {
      scope: $scope
    }).then(function(modal) {
      $scope.reserveform = modal;
    });

    // Triggered in the reserve modal to close it
    $scope.closeReserve = function() {
      $scope.reserveform.hide();
    };

    // Open the reserve modal
    $scope.reserve = function() {
      $scope.reserveform.show();
    };

    // Perform the reserve action when the user submits the reserve form
    $scope.doReserve = function() {
      console.log('Doing reservation', $scope.reservation);

      // Simulate a reservation delay. Remove this and replace with your reservation
      // code if using a server system
      $timeout(function() {
        $scope.closeReserve();
      }, 1000);
    };

  })

  .controller('MenuController', ['$scope', 'dishes', 'favoriteFactory',
    'baseURL', '$ionicListDelegate', '$ionicPlatform',
    '$cordovaLocalNotification', '$cordovaToast',
    function($scope, dishes, favoriteFactory, baseURL, $ionicListDelegate,
      $ionicPlatform, $cordovaLocalNotification, $cordovaToast) {

      $scope.baseURL = baseURL;
      $scope.tab = 1;
      $scope.filtText = '';
      $scope.showDetails = false;
      $scope.showMenu = false;
      $scope.message = "Loading ...";

      $scope.dishes = dishes
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

      $scope.addFavorite = function(index) {
        console.log("index is " + index);
        favoriteFactory.addToFavorites(index);
        // This dismisses the option button after the choice is made.
        $ionicListDelegate.closeOptionButtons();

        $ionicPlatform.ready(function() {

          $cordovaLocalNotification.schedule({
            id: 1,
            title: "Added Favorite",
            text: $scope.dishes[index].name
          }).then(function() {
              console.log('Added Favorite ' + $scope.dishes[index].name);
            },
            function() {
              console.log('Failed to add Notification ');
            });

          $cordovaToast
            .show('Added Favorite ' + $scope.dishes[index].name, 'long', 'center')
            .then(function(success) {
              // success
            }, function(error) {
              // error
            });
        });
      }

    }
  ])

  .controller('ContactController', ['$scope', 'baseURL', function($scope, baseURL) {

    $scope.baseURL = baseURL;
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

  .controller('FeedbackController', ['$scope', 'feedbackFactory', function($scope, feedbackFactory) {

    $scope.sendFeedback = function() {

      console.log($scope.feedback);

      if ($scope.feedback.agree && ($scope.feedback.mychannel == "")) {
        $scope.invalidChannelSelection = true;
        console.log('incorrect');
      } else {
        $scope.invalidChannelSelection = false;
        feedbackFactory.save($scope.feedback);
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

  .controller('DishDetailController', ['$scope', '$stateParams', 'dish',
    'menuFactory', 'favoriteFactory', 'baseURL', '$ionicModal', '$ionicPopover',
    '$ionicListDelegate', '$ionicPlatform', '$cordovaLocalNotification', '$cordovaToast',
    function($scope, $stateParams, dish, menuFactory, favoriteFactory,
      baseURL, $ionicModal, $ionicPopover, $ionicListDelegate,
      $ionicPlatform, $cordovaLocalNotification, $cordovaToast) {

      $scope.baseURL = baseURL;
      $scope.dish = {};
      $scope.showDish = false;
      $scope.message = "Loading ...";

      $scope.dish = dish;

      /*
       * Nested menu
       */

      $ionicPopover.fromTemplateUrl('templates/dish-detail-popover.html', {
        scope: $scope
      }).then(function(popover) {
        $scope.popover = popover;
      });

      // Triggered by "..."  button
      $scope.openPopover = function($event) {
        $scope.popover.show($event);
      }

      // Triggered after addFavorite is invoked.
      $scope.closePopover = function() {
        $scope.popover.hide();
      };

      $scope.$on('$destroy', function() {
        $scope.popover.remove();
      });

      $scope.$on('popover.hidden', function() {
        // ignored
      });

      $scope.$on('popover.removed', function() {
        // ignored
      });

      /*
       * Methods for processing a favorite dish.
       */

      $scope.addFavorite = function() {
        console.log("index is " + $scope.dish.id);
        favoriteFactory.addToFavorites($scope.dish.id);
        $scope.closePopover();

        $ionicPlatform.ready(function() {
          $cordovaLocalNotification.schedule({
            id: 1,
            title: "Added Favorite",
            text: $scope.dish.name
          }).then(function() {
              console.log('Added Favorite ' + $scope.dish.name);
            },
            function() {
              console.log('Failed to add Notification ');
            });

          $cordovaToast
            .show('Added Favorite ' + $scope.dish.name, 'long', 'center')
            .then(function(success) {
              // success
            }, function(error) {
              // error
            });

        });
      }

      /*
       * Methods for processing a comment.
       */

      $ionicModal.fromTemplateUrl('templates/dish-comment.html', {
        scope: $scope
      }).then(function(modal) {
        $scope.commentForm = modal;
      });

      $scope.closeCommentForm = function() {
        $scope.commentForm.hide();
      };

      $scope.showCommentForm = function() {
        $scope.commentForm.show();
      };

      $scope.mycomment = {
        rating: 5,
        comment: "",
        author: "",
        date: ""
      };

      $scope.submitComment = function() {

        $scope.mycomment.date = new Date().toISOString();

        console.log($scope.mycomment);

        /*
         * The rating must be converted to an int before persisting
         * to accommodate sorting.
         */
        $scope.dish.comments.push({
          rating: parseInt($scope.mycomment.rating, 10),
          comment: $scope.mycomment.comment,
          author: $scope.mycomment.author,
          date: $scope.mycomment.date
        });

        menuFactory.update({
          id: $scope.dish.id
        }, $scope.dish);

        $scope.mycomment = {
          rating: 5,
          comment: "",
          author: "",
          date: ""
        };

        $scope.closePopover();
        $scope.closeCommentForm();
      }

    }
  ])

  .controller('DishCommentController', ['$scope', 'menuFactory', function($scope, menuFactory) {

    $scope.mycomment = {
      rating: 5,
      comment: "",
      author: "",
      date: ""
    };

    $scope.submitComment = function() {

      $scope.mycomment.date = new Date().toISOString();
      console.log($scope.mycomment);

      $scope.dish.comments.push($scope.mycomment);

      menuFactory.update({
        id: $scope.dish.id
      }, $scope.dish);

      // menuFactory.getDishes().update({
      //   id: $scope.dish.id
      // }, $scope.dish);

      $scope.commentForm.$setPristine();

      $scope.mycomment = {
        rating: 5,
        comment: "",
        author: "",
        date: ""
      };
    }
  }])

  // implement the IndexController
  .controller('IndexController', ['$scope', 'dish', 'leader',
    'promotion', 'baseURL',
    function($scope, dish, leader, promotion,
      baseURL) {

      $scope.baseURL = baseURL;
      $scope.leader = leader;
      $scope.promotion = promotion;
      $scope.message = "Loading ...";

      $scope.showDish = false;
      $scope.dish = dish;
    }
  ])

  //Implement About Controller here
  .controller('AboutController', ['$scope', 'leaders', 'corporateFactory', 'baseURL', function($scope, leaders, corporateFactory, baseURL) {

    $scope.baseURL = baseURL;
    $scope.leaders = leaders;
    corporateFactory.query(
      function(response) {
        $scope.leaders = response;
      },
      function(response) {
        $scope.message = "Error: " + response.status + " " + response.statusText;
      }
    );

  }])

  .controller('FavoritesController', ['$scope', 'dishes', 'favorites',
    'favoriteFactory', 'baseURL', '$ionicListDelegate',
    '$ionicPopup', '$ionicLoading', '$timeout', '$ionicPlatform',

    function($scope, dishes, favorites, favoriteFactory, baseURL,
      $ionicListDelegate, $ionicPopup, $ionicLoading, $timeout,
      $ionicPlatform) {

      $scope.baseURL = baseURL;
      $scope.shouldShowDelete = false;

      $scope.favorites = favorites;

      $scope.dishes = dishes;

      console.log($scope.dishes, $scope.favorites);

      $scope.toggleDelete = function() {
        $scope.shouldShowDelete = !$scope.shouldShowDelete;
        console.log($scope.shouldShowDelete);
      }

      $scope.deleteFavorite = function(index) {

        var confirmPopup = $ionicPopup.confirm({
          title: 'Confirm Delete',
          template: 'Are you sure you want to delete this item?'
        });

        confirmPopup.then(function(res) {
          if (res) {
            console.log('Ok to delete');
            favoriteFactory.deleteFromFavorites(index);

            $ionicPlatform.ready(function() {
              // navigator.vibrate(2000);
              console.log('Vibrate called');
            });

          } else {
            console.log('Canceled delete');
          }
        });

        $scope.shouldShowDelete = false;

      }
    }
  ])

  // custom filter for displaying the favorite dishes.
  // this is necessary because the input list is filtered to generate the list "favorites."
  .filter('favoriteFilter', function() {
    return function(dishes, favorites) {
      var out = [];

      // Replace this with a better search algorithm.
      for (var i = 0; i < favorites.length; i++) {
        for (var j = 0; j < dishes.length; j++) {
          if (dishes[j].id === favorites[i].id)
            out.push(dishes[j]);
        }
      }
      return out;

    }
  })

;