/**
 * Contains the routes to the products API endpoint.
 */
const express = require("express");
const handlers = require('../../lib/api/products');
const router = express.Router();

router.get("/", (req, res) => {
  handlers.getHandler(req, res);
});

router.get("/:id", (req, res) => {
  handlers.getIdHandler(req, res);
});

router.post("/", (req, res) => {
  handlers.postHandler(req, res);
});

router.delete("/:id", (req, res) => {
  handlers.removeIdHandler(req, res);
});

router.delete("/", (req, res) => {
  handlers.removeAllHandler(req, res);
});

module.exports = router;
