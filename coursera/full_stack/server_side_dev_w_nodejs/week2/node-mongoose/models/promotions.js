// import modules
var mongoose = require('mongoose');

// declare and load schema API
var Schema = mongoose.Schema;

// Use mongoose currency type for 'price' attribute
require('mongoose-currency').loadType(mongoose);
var Currency = mongoose.Types.Currency;

// create a schema for this collection
var promoSchema = new Schema({
  name: {
    type: String,
    required: true,
    unique: true
  },
  image: {
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
  }
}, {
  timestamps: true
});

// Define a model for this collection schema
var Promotions = mongoose.model('Promotion', promoSchema);

// make this available to the app.
module.exports = Promotions;
