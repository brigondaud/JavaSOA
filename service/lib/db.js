"use strict";

const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const Receipt = new Schema({
  id: { type: Number, unique: true },
  products: Array
}, { versionKey: false });

const ReceiptModel = mongoose.model("Receipt", Receipt);

mongoose.Promise = global.Promise;

mongoose.connect("mongodb://log4420_lopon_barig:log4420@ds147003.mlab.com:47003/online-shop",
  {useMongoClient: true},
  function(error){
    if(error)
      console.log("Error while connecting to the DB:", error);
});

module.exports = {ReceiptModel};
