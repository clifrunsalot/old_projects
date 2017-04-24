// Import the required third-party modules
var express = require('express');
var morgan = require('morgan');

// Import custom modules
var dishRouter = require('./dishRouter');
var promoRouter = require('./promoRouter');
var leaderRouter = require('./leaderRouter');

// Define server attributes
var hostname = 'localhost';
var port = 3000;

// Set app to the express module
var app = express();

// Tell app to use the 'dev' level of morgan logging
app.use(morgan('dev'));

// Tell app to process requests containing specific paths in the URI.
app.use('/dishes',dishRouter);
app.use('/promotions',promoRouter);
app.use('/leadership',leaderRouter);

// Tell app where to find the server files
app.use(express.static(__dirname + '/public'));

// Tell app to listen
app.listen(port, hostname, function(){
  console.log('Server running at http://${hostname}:${port}/');
});
