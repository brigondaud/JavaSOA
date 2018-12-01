"use strict";

const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const Receipt = new Schema({
  id: { type: Number, unique: true },
  products: Array
}, { versionKey: false });

const ReceiptModel = mongoose.model("Receipt", Receipt);

mongoose.Promise = global.Promise;

mongoose.connect("mongodb://teamtango:log8430@ds121624.mlab.com:21624/tangodb",
  {useMongoClient: true},
  function(error){
    if(error)
      console.log("Error while connecting to the DB:", error);
});

module.exports = {ReceiptModel};
