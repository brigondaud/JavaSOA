const express = require("express");
const router = express.Router();
const ordersApi = require('../lib/api/orders');
const cartApi = require('../lib/api/shopping-cart');
const productsApi = require('../lib/api/products');
const utils = require('../lib/utils')

router.get("/", (req, res) => {
  let count = cartApi.getCount(req);
  res.render("index", { receptionActive: true, itemsCount: count });
});

router.get("/accueil", (req, res) => {
  let count = cartApi.getCount(req);
  res.render("index", {receptionActive: true, itemsCount: count});
});

router.get("/produits", (req, res) => {
  let count = cartApi.getCount(req);
  productsApi.retrieveAllProducts(req.params.category, req.params.criteria)
  .then(products => {
    res.render("products",
      { productsActive: true, itemsCount: count, productList: products });
  });
});

router.get("/produits/:id", (req, res) => {
  let count = cartApi.getCount(req);
  productsApi.retrieveOneProduct(req.params.id)
  .then(product => {
      res.render("product", {productsActive: true, itemsCount: count, product: product});
  })
});

router.get("/contact", (req, res) => {
  let count = cartApi.getCount(req);
  res.render("contact", {contactActive: true, itemsCount: count});
});

router.get("/panier", (req, res) => {
  let count = cartApi.getCount(req);
  let items = req.session.cart || [];
  let totalAmount = items.reduce((acc, item) => {
    return acc + item.product.price * item.quantity;
  }, 0);
  res.render("shopping-cart", {productsActive: true, itemsCount: count, cartItems: items, totalAmount: totalAmount});
});

router.get("/commande", (req, res) => {
  let count = cartApi.getCount(req);
  res.render("order", {itemsCount: count});
});

router.post("/confirmation", (req, res) => {
  let count = cartApi.getCount(req);
  let fullName = req.body['first-name'] + ' ' + req.body['last-name'];
  // To get the confirmation number, just get the number of order in the database
  ordersApi.getNumberOfOrder()
  .then(result => {
    res.render("confirmation", {itemsCount: count, confirmationNumber: result, name: fullName});
  })
  .catch(err => res.status(503).end());
});

module.exports = router;
