// Import modules
var express = require('express');
var bodyParser = require('body-parser');

// Initialize specific API
var promoRouter = express.Router();

// Tell router to use the imported module body-parser
promoRouter.use(bodyParser.json());

// Chain http requests
promoRouter.route('/')

.all(function (req, res, next) {
	res.writeHead(200, {
		'Content-Type': 'text/plain'
	});
	next();
})

.get(function (req, res, next) {
	res.end('Will send all the leaders to you!');
	console.log('Will send all the leaders to you!');
})

.post(function (req, res, next) {
	res.end('Will add the leader: ' + req.body.name + ' with details: ' + req.body.description);
	console.log('\nWill add the leader: ' + req.body.name + ' with details: ' + req.body.description);
})

.delete (function (req, res, next) {
	res.end('Deleting all leaders');
	console.log('Deleting all leaders');
});

promoRouter.route('/:leaderId')
.all(function (req, res, next) {
	res.writeHead(200, {
		'Content-Type': 'text/plain'
	});
	next();
})

.get(function (req, res, next) {
	res.end('Will send details of the leader: ' + req.params.leaderId + ' to you!');
	console.log('Will send details of the leader: ' + req.params.leaderId + ' to you!');
})

.put(function (req, res, next) {
	res.write('Updating the leader: ' + req.params.leaderId + '\n');
	res.end('Will update the leader: ' + req.body.name +
		' with details: ' + req.body.description);
	console.log('Updating the leader: ' + req.params.leaderId + '\n');
	console.log('Will update the leader: ' + req.body.name +
		' with details: ' + req.body.description);
})

.delete (function (req, res, next) {
	res.end('Deleting leader: ' + req.params.leaderId);
	console.log('Deleting leader: ' + req.params.leaderId);
});

// Make module public
module.exports = promoRouter;
