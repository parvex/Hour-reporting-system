const { watch, series } = require("gulp");
const browserSync = require("browser-sync").create();

const { paths } = require("./config");
const { buildDev } = require("./build");
const proxyMiddleware = require("http-proxy-middleware");

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

function serve(done) {
  browserSync.init({
    server: {
      baseDir: paths.tmp,
      middleware: proxyMiddleware("/api", {
        target: "http://localhost:8080",
        changeOrigin: true
      })
    }
  });

  watchSrc();
  done();
}

exports.serve = serve;
