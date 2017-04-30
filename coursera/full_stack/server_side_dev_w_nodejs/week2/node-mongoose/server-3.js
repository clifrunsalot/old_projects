var mongoose = require('mongoose'),
  assert = require('assert');

var Dishes = require('./models/dishes');

// Connection URL
var url = 'mongodb://localhost:27017/conFusion';
mongoose.connect(url);
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
  console.log("Connected correctly to server");

  // create a new dish
  Dishes.create({
    name: 'Apple Pie',
    image: 'images/applepie.png',
    category: 'desserts',
    price: '1.11',
    description: 'A unique ...',
    comments: [{
      rating: 1,
      comment: 'I have apple pie.',
      author: 'Anderson Copper'
    }]
  }, function(err, dish) {
    if (err) throw err;
    console.log('Dish created!');
    console.log(dish);

    var id = dish._id;

    // get all the dishes
    setTimeout(function() {
      Dishes.findByIdAndUpdate(id, {
          $set: {
            description: 'Updated Test',
            price: '9.99'
          }
        }, {
          new: true
        })
        .exec(function(err, dish) {
          if (err) throw err;
          console.log('Updated Dish!');
          console.log(dish.label);

          dish.comments.push({
            rating: 2,
            comment: 'It tastes better than the pecan!',
            author: 'Eli Mopping'
          });

          dish.save(function(err, dish) {
            console.log('Updated Comments!');
            console.log(dish);

            db.collection('dishes').drop(function() {
              db.close();
            });
          });
        });
    }, 3000);
  });
});
