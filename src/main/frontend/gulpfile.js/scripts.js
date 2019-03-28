const { src, dest } = require("gulp");
const plugins = require("gulp-load-plugins")();
const merge = require("merge-stream");

const { paths, errorHandler } = require("./config");
const { buildPartialsToScript } = require("./partials");

function validateScripts() {
  return src(paths.scripts)
    .pipe(
      plugins.jshint({
        esversion: 6
      })
    )
    .pipe(plugins.jshint.reporter("jshint-stylish"));
}

function buildScripsDev() {
  return validateScripts()
    .pipe(plugins.angularFilesort())
    .pipe(dest(paths.tmp));
}

function buildScripsProd() {
  const appScripts = validateScripts();
  const partialScript = buildPartialsToScript();

  return merge(appScripts, partialScript)
    .pipe(plugins.angularFilesort())
    .pipe(plugins.sourcemaps.init())
    .pipe(plugins.concat("app.min.js"))
    .pipe(plugins.terser().on("error", errorHandler("Terser")))
    .pipe(plugins.sourcemaps.write())
    .pipe(plugins.rev())
    .pipe(dest(paths.dist));
}

exports.buildScripsDev = buildScripsDev;
exports.buildScripsProd = buildScripsProd;
