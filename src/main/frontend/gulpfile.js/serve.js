const { src, watch, series } = require("gulp");
const plugins = require("gulp-load-plugins")();

const { paths } = require("./config");
const { buildDev } = require("./build");

function watchSrc() {
  watch(paths.app, buildDev);
}

function serve() {
  watchSrc();

  return src(paths.tmp).pipe(
    plugins.webserver({
      port: 3000,
      livereload: true,
      open: true
    })
  );
}

exports.serve = serve;
