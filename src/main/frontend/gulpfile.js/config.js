const log = require("fancy-log");

const paths = {
  scripts: "./app/**/*.js",
  styles: ["./app/**/*.css", "./app/**/*.less"],
  index: "./app/index.html",
  partials: ["./app/**/*.html", "!./app/index.html"],
  bowerJson: "./bower.json",
  tmp: "./tmp",
  dist: "./dist",
  app: "./app"
};

function errorHandler(title) {
  return function(err) {
    log.error(`[${title}]: ${err.toString()}`);
    this.emit("end");
  };
}

exports.paths = paths;
exports.errorHandler = errorHandler;
