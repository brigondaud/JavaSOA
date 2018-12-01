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

    let ipMasterSpark = req.app.config.ipMS;
    let dbURI = "mongodb://" +
      req.app.config.userName + ":" +
      req.app.config.password + "@" +
      req.app.config.hostName + ".mlab.com:" +
      req.app.config.hostPort + "/" +
      req.app.config.dbName + "." +
      req.app.config.collectionName;
    const adress = "spark://" + ipMasterSpark + ":7077";

    // Submit the job
    let jobLog = shell.exec("~/spark-2.4.0-bin-hadoop2.7/bin/spark-submit \
      --packages org.mongodb.spark:mongo-spark-connector_2.11:2.3.1 \
      --class spark.MostUsedProduct \
      --master " + ipMasterSpark + " \
      --deploy-mode client \
      job-0.1.jar " + dbURI + " 2> /dev/null | grep MostUsedProductResult").stdout;
    let firstSplit = jobLog.split("/");
    if (firstSplit.length != 2)
      res.status(404).json({"product": "No product found!"});
    else {
      let secondSplit = firstSplit[1].split(":");
      let result = {};
      result.name = secondSplit[0];
      result.quantity = secondSplit[1];
      res.status(200).json(result);
    }
  },

  /**
   * Get the result of Spark job.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getResult: function(req, res) {
    let id = req.params.id;
    models.MostUsedProduct.findOne({id: id}, { _id: 0 })
    .lean()
    .exec()
    .then(result => {
      if (result)
        res.status(200).json(result);
      else
        res.status(404).end();
    })
    .catch(err => {
      res.status(503).end();
    })
  }

};
