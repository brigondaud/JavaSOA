/**
 * Provides every handlers needed for the orders endpoint.
 */
const models = require("../db");
const validator = require('validator');
const request = require('request');
const utils = require('../utils');
const productsApi = require('./products')

module.exports = {

  /**
   * Get all the orders in the database.
   *
   * @param {Object} req
   * @param {Object} res
   */
  get: function(req, res) {

    // Query building.
    let query = models.OrderModel.find();

    query.lean().exec()
    .then(orders => {
      res.status(200).json(orders);
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Retrieves the order associated to the given id.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getId: function(req, res) {
    // Prepare query for a specific id.
    let id = req.params.id;
    if(isNaN(id)) {
      res.status(404).json({id});
      return;
    }
    models.OrderModel.findOne({id: id})
    .lean()
    .exec()
    .then(order => {
      if(!order)
        res.status(404).json({id});
      else
        res.status(200).json(order);
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Creates a new order in the database.
   *
   * @param {Object} req
   * @param {Object} res
   */
  post: function(req, res) {
    let order = new models.OrderModel();
    order.id = req.body.id;
    order.firstName = req.body.firstName;
    order.lastName = req.body.lastName;
    order.email = req.body.email;
    order.phone = req.body.phone;
    order.products = req.body.products;

    if (this.isOrderInvalid(order)) {
      res.status(400).end();
      return;
    }
    if (!this.areProductsValid(order.products)) {
      res.status(400).end();
      return;
    }
    // For every product in the order, create a Promise
    let promises = [];
    order.products.forEach(product => {
      promises.push(productsApi.retrieveOneProduct(product.id));
    });
    Promise.all(promises)
    .then(items => {
      /*
       * When all the promises have resolved, analyse the result, every item should be not null.
       * Otherwise, the product doesn't exist in the database
       */
      if (items.every(item => {return item != null;})) {
        // Asserts that no other order in the db have the same id
        models.OrderModel.findOne({id: order.id})
        .then(entry => {
          if (entry)
            res.status(400).end();
          else {
            order.save()
            .then(order => {
              res.status(201).send(order);
            })
            .catch(err => {
              res.status(503).end();
            });
          }
        });
      } else {
        res.status(400).end();
      }
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Indicates if a given order is invalid
   *
   * @param order The order to test
   */
  isOrderInvalid: function(order) {
    return isNaN(order.id) || !utils.isStringValid(order.firstName)
      || !utils.isStringValid(order.lastName) || !validator.isEmail(order.email)
      || !validator.isMobilePhone(order.phone, 'en-CA');
  },

  /**
   * Indicates if the products are valid (i.e. every product in the list has an integer
   * attribute id as well as a quantity > 0)
   *
   * @param products The products to test
   */
  areProductsValid: function(products) {
    let valid = true;
    products.forEach(product => {
      if (isNaN(product.id) || isNaN(product.quantity) || product.quantity <= 0)
        valid = false;
    });
    return valid;
  },

  /**
   * Removes the order associated to the given id from database
   */
  remove: function(req, res) {
    models.OrderModel.findOneAndRemove({id: req.params.id})
    .lean()
    .exec()
    .then(order => {
      if (order)
        res.status(204).end();
      else
        res.status(404).end();
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Removes all the orders from database
   */
  removeAll: function(req, res) {
    models.OrderModel.remove()
    .exec()
    .then(() => {
      res.status(204).end();
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Returns a Promise giving the number of element in Order collection when resolving
   */
  getNumberOfOrder: function() {
    return models.OrderModel.count({}).exec();
  }

};
