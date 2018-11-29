/**
 * Provides every handlers needed for the shopping-cart endpoint.
 */
const models = require("../db");
const productsApi = require('./products');

module.exports = {

  /**
   * Get all the products in the shopping cart.
   *
   * @param {Object} req
   * @param {Object} res
   */
  get: function(req, res) {
    if (!req.session.cart)
      req.session.cart = [];

    let tmp = [];
    req.session.cart.forEach(item => {
      tmp.push({productId: item.product.id, quantity: item.quantity});
    });
    res.status(200).json(tmp);
  },

  /**
   * Retrieves the product associated to the given id in the shopping-cart.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getId: function(req, res) {
    if (!req.session.cart)
      req.session.cart = [];

    let item = req.session.cart.find(element => {
      return element.product.id == req.params.productId;
    });
    if (item) {
      res.status(200).json({productId: item.product.id, quantity: item.quantity});
    } else {
      res.status(404).end();
    }
  },

  /**
   * Creates a new entry in the shopping-cart.
   *
   * @param {Object} req
   * @param {Object} res
   */
  post: function(req, res) {
    if (!req.session.cart)
      req.session.cart = [];

    let entry = {};
    entry.quantity = req.body.quantity;

    // Check if the product is already in cart
    let item = req.session.cart.find(element => {
      return element.product.id == req.body.productId;
    });

    if (item || isNaN(req.body.productId) || isNaN(entry.quantity) || entry.quantity <= 0) {
      res.status(400).end();
      return;
    }
    // Finally, check if a product with product exists in the database
    productsApi.retrieveOneProduct(req.body.productId)
    .then(product => {
      if (product) {
        entry.product = product;
        req.session.cart.push(entry);
        res.status(201).json(entry);
      } else {
        res.status(400).end();
      }
    });
  },

  /**
   * Modify the quantity associated to the product of the
   * specified productId in the shopping-cart
   */
  modifyQuantity: function(req, res) {
    if (!req.session.cart)
      req.session.cart = [];

    const quantity = req.body.quantity;
    if (!req.body.quantity || isNaN(quantity) || quantity <= 0) {
      res.status(400).end();
      return;
    }
    let item = req.session.cart.find(element => {
      return element.product.id == req.params.productId;
    });
    if (!item) {
      res.status(404).end();
    } else {
      item.quantity = quantity;
      res.status(204).end();
    }
  },

  /**
   * Removes the entry associated to the given productId in the shopping-cart
   */
  remove: function(req, res) {
    if (!req.session.cart)
      req.session.cart = [];

    let item = req.session.cart.find(element => {
      return element.product.id == req.params.productId;
    });
    if (!item) {
      res.status(404).end();
    } else {
      let index = req.session.cart.indexOf(item);
      req.session.cart.splice(index, 1);
      res.status(204).end();
    }
  },

  /**
   * Removes all the items in the shopping-cart
   */
  removeAll: function(req, res) {
    req.session.cart = [];
    res.status(204).end();
  },

  /**
   * Get the number of items in the cart
   */
  getCount: function(req) {
    if (!req.session.cart)
      req.session.cart = [];
    return req.session.cart.reduce((acc, item) => {
      return acc + item.quantity;
    }, 0);
  }

};
