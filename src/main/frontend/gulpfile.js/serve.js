const { watch, series } = require("gulp");
const browserSync = require("browser-sync").create();

const { paths } = require("./config");
const { buildDev } = require("./build");

function watchSrc() {
  watch(
    paths.app,
    { ignoreInitial: false },
    series(buildDev, function(done) {
      browserSync.reload();
      done();
    })
  );
}

function serve() {
  browserSync.init({
    server: {
      baseDir: paths.tmp
    }
  });

  watchSrc();
}

exports.serve = serve;
