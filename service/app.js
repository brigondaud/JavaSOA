const express = require("express");
const path = require("path");
const logger = require("morgan");
const cookieParser = require("cookie-parser");
const bodyParser = require("body-parser");
const session = require("express-session");
const fs = require("fs");

require("./lib/db");
const receiptsRoute = require("./routes/api_routes/receipts");
const mostUsedProductRoute = require("./routes/result.js");

const app = express();


if(process.env.NODE_ENV !== 'test') {
  app.use(logger("dev"));
}

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

// initialize the session
app.use(session({
  secret: 'log4420',
  resave: false,
  saveUninitialized: true,
  cookie: { secure: false }
}));

// Parse the content of config.json
app.config = JSON.parse(fs.readFileSync("config.json"));

// Register the routes to the api.
app.use("/rest/receipts", receiptsRoute);

// Register the routes to le bail Spark
app.use("/mostUsedProduct", mostUsedProductRoute);

// catch 404 and forward to error handler
app.use((req, res, next)=> {
  const err = new Error("Not Found");
  err.status = 404;
  next(err);
});

// error handler
app.use((err, req, res) => {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render("error");
});

module.exports = app;
