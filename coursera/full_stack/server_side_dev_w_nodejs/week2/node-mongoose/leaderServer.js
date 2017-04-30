var mongoose = require('mongoose'),
  assert = require('assert');

var Leadership = require('./models/leadership');

// Connection URL
var url = 'mongodb://localhost:27017/conFusion';
mongoose.connect(url);
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
  console.log("Connected correctly to server");

  // create a new leader
  Leadership.create({
    name: 'Jack Morgan',
    image: 'images/jackmorgan.png',
    designation: 'Chief Operating Officer',
    abbr: 'COO',
    description: 'Responsible for managing the entire company'
  }, function(err, leader) {
    if (err) throw err;
    console.log('Leader created!');
    console.log(leader);

    var id = leader._id;

    // get all the leaders
    setTimeout(function() {
      Leadership.findByIdAndUpdate(id, {
          $set: {
            designation: 'Chief Technology Officer',
            abbr: 'CTO',
            description: 'Manages company technology'
          }
        }, {
          new: true
        })
        .exec(function(err, leader) {
          if (err) throw err;
          console.log('Updated Leader!');
          console.log(leader);

          leader.save(function(err, leader) {
            db.collection('leaders').drop(function() {
              db.close();
            });
          });
        });
    }, 3000);
  });
});
