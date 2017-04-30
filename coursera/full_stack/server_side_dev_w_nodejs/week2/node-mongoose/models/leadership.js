// import modules
var mongoose = require('mongoose');

// declare and load schema API
var Schema = mongoose.Schema;

// create a schema for this collection
var leaderSchema = new Schema({
  name: {
    type: String,
    required: true,
    unique: true
  },
  image: {
    type: String,
    required: true
  },
  designation: {
    type: String,
    required: true
  },
  abbr: {
    type: String,
    required: true
  },
  description: {
    type: String,
    required: true
  }
}, {
  timestamps: true
});

// Define a model for this collection schema
var Leadership = mongoose.model('Leader', leaderSchema);

// make this available to the app.
module.exports = Leadership;
