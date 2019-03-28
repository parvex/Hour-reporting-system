const { src } = require("gulp");
const plugins = require("gulp-load-plugins")();

const { paths } = require("./config");

function clean(path) {
  return function() {
    return src(path, { read: false, allowEmpty: true }).pipe(plugins.clean());
  };
}

exports.cleanDev = clean(paths.tmp);
exports.cleanProd = clean(paths.dist);
