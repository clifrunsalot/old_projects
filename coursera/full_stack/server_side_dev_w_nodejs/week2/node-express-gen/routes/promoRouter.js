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
	res.end('Will send all the promotions to you!');
	console.log('Will send all the promotions to you!');
})

.post(function (req, res, next) {
	res.end('Will add the promotion: ' + req.body.name + ' with details: ' + req.body.description);
	console.log('\nWill add the promotion: ' + req.body.name + ' with details: ' + req.body.description);
})

.delete (function (req, res, next) {
	res.end('Deleting all promotions');
	console.log('Deleting all promotions');
});

promoRouter.route('/:promotionId')
.all(function (req, res, next) {
	res.writeHead(200, {
		'Content-Type': 'text/plain'
	});
	next();
})

.get(function (req, res, next) {
	res.end('Will send details of the promotion: ' + req.params.promotionId + ' to you!');
	console.log('Will send details of the promotion: ' + req.params.promotionId + ' to you!');
})

.put(function (req, res, next) {
	res.write('Updating the promotion: ' + req.params.promotionId + '\n');
	res.end('Will update the promotion: ' + req.body.name +
		' with details: ' + req.body.description);
	console.log('Updating the promotion: ' + req.params.promotionId + '\n');
	console.log('Will update the promotion: ' + req.body.name +
		' with details: ' + req.body.description);
})

.delete (function (req, res, next) {
	res.end('Deleting promotion: ' + req.params.promotionId);
	console.log('Deleting promotion: ' + req.params.promotionId);
});

// Make module public
module.exports = promoRouter;
