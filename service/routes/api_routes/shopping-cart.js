/**
 * Contains the routes to the shopping-cart API endpoint.
 */
const express = require("express");
const handlers = require('../../lib/api/shopping-cart');
const router = express.Router();

router.get("/", (req, res) => {
  handlers.get(req, res);
});

router.get("/:productId", (req, res) => {
  handlers.getId(req, res);
});

router.post("/", (req, res) => {
  handlers.post(req, res);
});

router.put("/:productId", (req, res) => {
  handlers.modifyQuantity(req, res);
});

router.delete("/:productId", (req, res) => {
  handlers.remove(req, res);
});

router.delete("/", (req, res) => {
  handlers.removeAll(req, res);
});

module.exports = router;
