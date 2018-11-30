/**
 * Provides every handlers needed to retrieve the most used products in the database
 */
const models = require("../db");
const request = require('request');
const utils = require('../utils');
const shell = require('shelljs');
const eclairjs = require('eclairjs');

module.exports = {

  /**
   * Get the most used product in the database.
   *
   * @param {Object} req
   * @param {Object} res
   */
  getMostUsedProduct: function(req, res) {

    let ipMasterSpark = req.app.config.ipMS;
    let spark = new eclairjs();
    const adress = "spark://" + ipMasterSpark + ":7077";

    let sparkContext = spark.SparkContext(adress, "mostUsedProduct");

    // Query building.
    let query = models.ReceiptModel.find({}, { _id: 0 }, {
      sort:{id: 1} // Sort by id
    });
    query.lean().exec()
    .then(receipts => {// Apply a pre-process on the list of receipts to extract products
        let words = receipts.reduce((acc, receipt) => {
          receipt.products.forEach(product => {
            acc += name.repeat(product.quantity);
          });
          return acc;
        }, "").split(" ");
        let rdd = sparkContext.parallelize(words, 8);

        let productsWithCount = rdd.mapToPair(function(product, Tuple2) {
          return new Tuple2(product, 1);
        }, [eclairjs.Tuple2]);

        let reduceProductsWithCount = productsWithCount.reduceByKey(function(value1, value2) {
          return value1 + value2;
        });

        let reducedProductsSorted = reduceProductsWithCount.sortByKey(false);
        reducedProductsSorted.take(1).then(product => {
          console.log(product);
          sparkContext.stop();
          res.status(200).end();
        });
    })
    .catch(err => {
      sparkContext.stop();
      res.status(503).end();
    });

    /*
    // Submit the job
    shell.exec("./bin/spark-submit \
      --class tango.class.SendJob \
      --master " + ipMasterSpark + " \
      --deploy-mode cluster \
      job.jar");
    res.status(200).location('/result').end();
    */
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
