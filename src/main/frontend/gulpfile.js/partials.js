const { src, dest } = require("gulp");
const plugins = require("gulp-load-plugins")();

const { paths } = require("./config");

function validatePartials() {
  return src(paths.partials)
    .pipe(plugins.htmlhint({ "doctype-first": false }))
    .pipe(plugins.htmlhint.reporter());
}

function buildPartialsDev() {
  return validatePartials().pipe(dest(paths.tmp));
}

function buildPartialsToScript() {
  return validatePartials()
    .pipe(plugins.htmlhint.failOnError())
    .pipe(
      plugins.htmlmin({
        removeEmptyAttributes: true,
        removeAttributeQuotes: true,
        collapseBooleanAttributes: true,
        collapseWhitespace: true
      })
    )
    .pipe(
      plugins.ngHtml2js({
        moduleName: "planetsApp"
      })
    );
}

exports.buildPartialsDev = buildPartialsDev;
exports.buildPartialsToScript = buildPartialsToScript;
