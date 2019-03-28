const { series } = require("gulp");
const { buildDev, buildProd } = require("./build");
const { cleanDev, cleanProd } = require("./clean");
const { serve } = require("./serve");

exports.build = series(cleanProd, buildProd);
exports.buildDev = series(cleanDev, buildDev);
exports.serve = serve;

//TODO: fix serve
