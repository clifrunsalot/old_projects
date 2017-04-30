// Import modules
var express = require('express');
var bodyParser = require('body-parser');

// Initialize specific API
var dishRouter = express.Router();

// Tell router to use the imported module body-parser
dishRouter.use(bodyParser.json());

// Chain http requests
dishRouter.route('/')

.all(function (req, res, next) {
	res.writeHead(200, {
		'Content-Type': 'text/plain'
	});
	next();
})

.get(function (req, res, next) {
	res.end('Will send all the dishes to you!');
	console.log('Will send all the dishes to you!');
})

.post(function (req, res, next) {
	res.end('Will add the dish: ' + req.body.name + ' with details: ' + req.body.description);
	console.log('\nWill add the dish: ' + req.body.name + ' with details: ' + req.body.description);
})

.delete (function (req, res, next) {
	res.end('Deleting all dishes');
	console.log('Deleting all dishes');
});

dishRouter.route('/:dishId')
.all(function (req, res, next) {
	res.writeHead(200, {
		'Content-Type': 'text/plain'
	});
	next();
})

.get(function (req, res, next) {
	res.end('Will send details of the dish: ' + req.params.dishId + ' to you!');
	console.log('Will send details of the dish: ' + req.params.dishId + ' to you!');
})

.put(function (req, res, next) {
	res.write('Updating the dish: ' + req.params.dishId + '\n');
	res.end('Will update the dish: ' + req.body.name +
		' with details: ' + req.body.description);
	console.log('Updating the dish: ' + req.params.dishId + '\n');
	console.log('Will update the dish: ' + req.body.name +
		' with details: ' + req.body.description);
})

.delete (function (req, res, next) {
	res.end('Deleting dish: ' + req.params.dishId);
	console.log('Deleting dish: ' + req.params.dishId);
});

// Make module public
module.exports = dishRouter;
