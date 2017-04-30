// import modules
var mongoose = require('mongoose');

// declare and load schema API
var Schema = mongoose.Schema;

// Use mongoose currency type for 'price' attribute
require('mongoose-currency').loadType(mongoose);
var Currency = mongoose.Types.Currency;

// define schema for a comment attribute
var commentSchema = new Schema({
  rating: {
    type: Number,
    min: 1,
    max: 5,
    required: true
  },
  comment: {
    type: String,
    required: true
  },
  author: {
    type: String,
    required: true
  }
}, {
  timestamps: true
});

// create a schema for this collection
var dishSchema = new Schema({
  name: {
    type: String,
    required: true,
    unique: true
  },
  image: {
    type: String,
    required: true
  },
  category: {
    type: String,
    required: true
  },
  label: {
    type: String,
    default: ''
  },
  price: {
    // Use the currency type here
    type: Currency,
    required: true
  },
  description: {
    type: String,
    required: true
  },
  comments: [commentSchema]
}, {
  timestamps: true
});

// Define a model for this collection schema
var Dishes = mongoose.model('Dish', dishSchema);

// make this available to the app.
module.exports = Dishes;
