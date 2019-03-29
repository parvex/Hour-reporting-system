const { src, dest, parallel } = require("gulp");
const plugins = require("gulp-load-plugins")();

const { paths } = require("./config");
const { buildScripsDev, buildScripsProd } = require("./scripts");
const { buildVendorScriptsDev, buildVendorScriptsProd } = require("./vendors");
const { buildStylesDev, buildStylesProd } = require("./styles");
const { buildPartialsDev } = require("./partials");

function validateIndex() {
  return src(paths.index)
    .pipe(plugins.htmlhint())
    .pipe(plugins.htmlhint.reporter());
}

function buildDev() {
  const appScripts = buildScripsDev();
  const vendorScripts = buildVendorScriptsDev();
  const styles = buildStylesDev();

  return validateIndex()
    .pipe(dest(paths.tmp))
    .pipe(plugins.inject(appScripts, { relative: true }))
    .pipe(plugins.inject(vendorScripts, { relative: true, name: "bower" }))
    .pipe(plugins.inject(styles, { relative: true }))
    .pipe(dest(paths.tmp));
}

function buildProd() {
  const appScripts = buildScripsProd();
  const vendorScripts = buildVendorScriptsProd();
  const styles = buildStylesProd();

  return validateIndex()
    .pipe(dest(paths.dist))
    .pipe(plugins.inject(appScripts, { relative: true }))
    .pipe(plugins.inject(vendorScripts, { relative: true, name: "bower" }))
    .pipe(plugins.inject(styles, { relative: true }))
    .pipe(
      plugins.htmlmin({
        removeEmptyAttributes: true,
        removeAttributeQuotes: true,
        collapseBooleanAttributes: true,
        collapseWhitespace: true
      })
    )
    .pipe(dest(paths.dist))
    .pipe(plugins.size({ showFiles: true }));
}

exports.buildDev = parallel(buildPartialsDev, buildDev);
exports.buildProd = buildProd;
