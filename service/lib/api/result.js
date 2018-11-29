/**
 * Provides every handlers needed to retrieve the most used products in the database
 */
const models = require("../db");
const request = require('request');
const utils = require('../utils');
const shell = require('shelljs');

module.exports = {

  /**
   * Get the most used product in the database.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getMostUsedProduct: function(req, res) {

    let ipMasterSpark = req.app.config.ipMS

    // Submit the job
    shell.exec("./bin/spark-submit \
      --class tango.class.SendJob \
      --master " + ipMasterSpark + " \
      --deploy-mode cluster \
      job.jar");
    res.status(200).location('/result').end();
  },

  /**
   * Get the result of Spark job.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getResult: function(req, res) {
    models.ResultModel.findOneAndDelete({id: 1}, { _id: 0 })
    .lean()
    .exec()
    .then(result => {
      if (result)
        res.status(200).send(result);
      else
        res.status(404).end();
    })
    .catch(err => {
      res.status(503).end();
    })
  },

  /**
   * Handle the result being send by Spark.
   *
   * @param {Object} req
   * @param {Object} res
   */
  handleResult: function(req, res) {
    let product = {};
    product.id = 1;
    product.name = req.body.name;

    models.ResultModel.findOneAndUpdate({id: product.id}, {name: product.name}, {upsert: true})
    .lean()
    .exec()
    .then(result => {
      res.status(201).send(result);
    })
    .catch(err => {
      res.status(503).end();
    });
  }
};
