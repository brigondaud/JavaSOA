/**
 * Contains the routes to the most used product API endpoint.
 */
const express = require("express");
const handlers = require('../lib/api/result');
const router = express.Router();

router.get("/", (req, res) => {
  handlers.getMostUsedProduct(req, res);
});

router.get("/:id", (req, res) => {
  handlers.getResult(req, res);
});

module.exports = router;
