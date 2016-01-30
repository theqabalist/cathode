module.exports = function (config) {
    "use strict";
    config.set({
        frameworks: ["jasmine"],
        files: [
            "target/cljsbuild/public/js/spec.js"
        ],
        browsers: ["PhantomJS"],
        reporters: ["progress"]
    });
};
