const { src, dest } = require("gulp");
const plugins = require("gulp-load-plugins")();

const { paths, errorHandler } = require("./config");

function buildVendorScriptsDev() {
  return src(paths.bowerJson)
    .pipe(plugins.mainBowerFiles())
    .pipe(dest(paths.tmp + "/bower_components"));
}

function orderVendorScripts() {
  return plugins.order(["**/jquery.js", "**/angular.js", "**/moment.js"]);
}

function buildVendorScriptsProd() {
  const scriptFilter = plugins.filter("**/*.js");

  return src(paths.bowerJson)
    .pipe(plugins.mainBowerFiles())
    .pipe(scriptFilter)
    .pipe(orderVendorScripts())
    .pipe(plugins.concat("vendors.min.js"))
    .pipe(plugins.terser().on("error", errorHandler("Terser")))
    .pipe(plugins.rev())
    .pipe(dest(paths.dist));
}

exports.buildVendorScriptsDev = buildVendorScriptsDev;
exports.buildVendorScriptsProd = buildVendorScriptsProd;
