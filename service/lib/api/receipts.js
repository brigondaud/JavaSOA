/**
 * Provides every handlers needed for the orders endpoint.
 */
const models = require("../db");
const request = require('request');
const utils = require('../utils');

module.exports = {

  /**
   * Get all the receipts in the database.
   *
   * @param {Object} req
   * @param {Object} res
   */
  get: function(req, res) {

    // Query building.
    let query = models.ReceiptModel.find({}, { _id: 0 }, {
      sort:{id: 1} // Sort by id
    });

    query.lean().exec()
    .then(receipts => {
      res.status(200).json(receipts);
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Retrieves the receipt associated to the given id.
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
    models.ReceiptModel.findOne({id: id}, { _id: 0 })
    .lean()
    .exec()
    .then(receipt => {
      if(!receipt)
        res.status(404).json({id});
      else
        res.status(200).json(receipt);
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Creates a new receipt in the database.
   *
   * @param {Object} req
   * @param {Object} res
   */
  post: function(req, res) {
    let receipt = new models.ReceiptModel();
    receipt.id = req.body.id;
    receipt.products = req.body.products;

    if (isNaN(receipt.id)) {
      res.status(400).end();
      return;
    }
    if (!this.areProductsValid(receipt.products)) {
      res.status(400).end();
      return;
    }
    // Asserts that no other receipt in the db have the same id
    models.ReceiptModel.findOne({id: receipt.id})
    .then(entry => {
      if (entry)
        res.status(400).end();
      else {
        receipt.save()
        .then(receipt => {
          res.status(201).send(receipt);
        })
        .catch(err => {
          res.status(503).end();
        });
      }
    });
  },

  /**
   * Indicates if a given order is invalid
   *
   * @param order The order to test
   */
  isOrderInvalid: function(order) {
    return isNaN(order.id);
  },

  /**
   * Indicates if the products are valid (i.e. every product in the list has a
   * non-null String name parameter as well as a quantity and a price > 0)
   *
   * @param products The products to test
   */
  areProductsValid: function(products) {
    if (products.length === 0)
      return false;
    let valid = true;
    products.forEach(product => {
      if (!product.name
      || product.name.length == 0
      || isNaN(product.price)
      || product.price < 0
      || isNaN(product.quantity)
      || product.quantity <= 0)
        valid = false;
    });
    return valid;
  },

  /**
   * Removes the receipt associated to the given id from database
   */
  remove: function(req, res) {
    models.ReceiptModel.findOneAndRemove({id: req.params.id})
    .lean()
    .exec()
    .then(receipt => {
      if (receipt)
        res.status(204).end();
      else
        res.status(404).end();
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Removes all the receipts from database
   */
  removeAll: function(req, res) {
    models.ReceiptModel.remove()
    .exec()
    .then(() => {
      res.status(204).end();
    })
    .catch(err => {
      res.status(503).end();
    });
  }

};
