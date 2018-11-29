/**
 * Provides every handlers needed for the products endpoint.
 */
const models = require("../db");
const utils = require('../utils');

const categories = ["cameras", "computers", "consoles", "screens"];
const criterias = ["price-asc", "alpha-asc", "alpha-dsc", "price-dsc"];
const sorting = {
  "price-asc": "price",
  "price-dsc": "-price",
  "alpha-asc": "name",
  "alpha-dsc": "-name"
};

module.exports = {

  /**
   * Retrieves all the products from database
   */
  retrieveAllProducts: function(category, criteria) {
    // Defaults if not provided.
    if(!criteria) criteria = criterias[0];

    // Query building.
    let query = models.ProductModel.find();
    query = category ? query.where("category").equals(category): query;

    return query.sort(sorting[criteria]).lean().exec();
  },

  /**
   * Retrieves a product given it's id from database
   */
  retrieveOneProduct: function(id) {
    return models.ProductModel.findOne({id: id})
    .lean()
    .exec()
  },

  /**
   * Get all the products in the database. Can be sorted with a criteria and reduced to only
   * a product category.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getHandler: function(req, res) {
    let category = req.query.category;
    let criteria = req.query.criteria;

    // Bad category.
    if(category && categories.indexOf(category) == -1) {
      res.status(400).json({category});
      return;
    }

    // Bad criteria.
    if(criteria && criterias.indexOf(criteria) == -1) {
      res.status(400).json({criteria});
      return;
    }

    this.retrieveAllProducts(category, criteria)
    .then(products => {
      // As Mongoose is not providing proper case-insensitive sorting, we hase to do it manually
      if (criteria === 'alpha-asc')
        res.status(200).send(utils.sortProductsAlpha(products, true));
      else if (criteria === 'alpha-dsc')
        res.status(200).send(utils.sortProductsAlpha(products, false));
      else
        res.status(200).send(products);
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Retrieves the product associated to the given id.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getIdHandler: function(req, res) {
    // Prepare query for a specific id.
    let id = req.params.id;
    if(isNaN(id)) {
      res.status(404).json({id});
      return;
    }
    this.retrieveOneProduct(id)
    .then(product => {
      if(!product)
        res.status(404).json({id: id});
      else
        res.status(200).send(product);
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Creates a new product in the database.
   *
   * @param {Object} req
   * @param {Object} res
   */
  postHandler: function(req, res) {
    let product = new models.ProductModel();
    product.id = req.body.id;
    product.name = req.body.name;
    product.price = req.body.price;
    product.image = req.body.image;
    product.category = req.body.category;
    product.description = req.body.description;
    product.features = req.body.features;

    // Check if all the fields are valid
    if (this.isProductInvalid(product)) {
      res.status(400).end();
      return;
    }

    // Finally, check if a product with the same id already exists in the database
    this.retrieveOneProduct(product.id)
    .then(entry => {
      if (entry) {
        res.status(400).end();
      }
      else {
        product.save()
        .then(product => {
          res.status(201).send(product);
        })
        .catch(err => {
          res.status(503).end();
        });
      }
    });
  },

  /**
   * Indicates if a given product is invalid
   *
   * @param product The product to test
   */
  isProductInvalid: function(product) {
    // First test the features, each of them must be a non-empty string
    let invalidFeature = false;
    product.features.forEach(feature => {
      if (!utils.isStringValid(feature))
        invalidFeature = true;
    });
    // Then test category
    if (!categories.find(category => { return category === product.category;}))
      return true;
    return invalidFeature
    || isNaN(product.id)
    || !utils.isStringValid(product.name)
    || isNaN(product.price)
    || product.price <= 0
    || !utils.isStringValid(product.image)
    || !utils.isStringValid(product.description);
  },

  /**
   * Delete a single product in the database, if it exists
   */
  removeIdHandler: function(req, res) {
    models.ProductModel.findOneAndRemove({id: req.params.id})
    .lean()
    .exec()
    .then(product => {
      if (product) {
        res.status(204).end();
      } else {
        res.status(404).end();
      }
    })
    .catch(err => {
      res.status(503).end();
    });
  },

  /**
   * Deletes all the products in the database
   */
  removeAllHandler: function(req, res) {
    models.ProductModel.remove()
    .exec()
    .then(() => {
      res.status(204).end();
    })
    .catch(err => {
      res.status(503).end();
    });
  },

};
