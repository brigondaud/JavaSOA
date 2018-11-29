"use strict";

const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const Order = new Schema({
  id: { type: Number, unique: true },
  firstName: String,
  lastName: String,
  email: String,
  phone: String,
  products: Array
}, { versionKey: false });


const Product = new Schema({
  id: { type: Number, unique: true },
  name: String,
  price: Number,
  image: String,
  category: String,
  description: String,
  features: Array
}, { versionKey: false });

const OrderModel = mongoose.model("Order", Order);
const ProductModel = mongoose.model("Product", Product);

mongoose.Promise = global.Promise;

mongoose.connect("mongodb://log4420_lopon_barig:log4420@ds147003.mlab.com:47003/online-shop",
  {useMongoClient: true},
  function(error){
    if(error)
      console.log("Error while connecting to the DB:", error);
});

module.exports = {OrderModel, ProductModel};
