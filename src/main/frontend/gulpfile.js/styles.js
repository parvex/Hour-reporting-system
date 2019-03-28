const { src, dest } = require("gulp");
const plugins = require("gulp-load-plugins")();
const merge = require("merge-stream");

const { paths, errorHandler } = require("./config");

function buildStylesDev() {
  return src(paths.styles)
    .pipe(plugins.less())
    .pipe(dest(paths.tmp));
}

function buildStylesProd() {
  const styleFilter = plugins.filter("**/*.css");
  const vendorStyles = src(paths.bowerJson)
    .pipe(plugins.mainBowerFiles())
    .pipe(styleFilter);

  const appStyles = src(paths.styles);

  return merge(appStyles, vendorStyles)
    .pipe(plugins.sourcemaps.init())
    .pipe(plugins.less().on("error", errorHandler("Less")))
    .pipe(plugins.autoprefixer().on("error", errorHandler("AutoPrefixer")))
    .pipe(plugins.cleanCss())
    .pipe(plugins.sourcemaps.write())
    .pipe(plugins.concat('style.min.css'))
    .pipe(plugins.rev())
    .pipe(dest(paths.dist));
}

exports.buildStylesDev = buildStylesDev;
exports.buildStylesProd = buildStylesProd;
