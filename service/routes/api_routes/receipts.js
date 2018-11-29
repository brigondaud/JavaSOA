/**
 * Contains the routes to the receipts API endpoint.
 */
const express = require("express");
const handlers = require('../../lib/api/receipts');
const router = express.Router();

router.get("/", (req, res) => {
  handlers.get(req, res);
});

router.get("/:id", (req, res) => {
  handlers.getId(req, res);
});

router.post("/", (req, res) => {
  handlers.post(req, res);
});

router.delete("/:id", (req, res) => {
  handlers.remove(req, res);
});

router.delete("/", (req, res) => {
  handlers.removeAll(req, res);
});

module.exports = router;
