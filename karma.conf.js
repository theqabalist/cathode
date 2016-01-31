module.exports = function (config) {
    "use strict";
    config.set({
        frameworks: ["jasmine"],
        files: [
            "target/cljsbuild/public/js/spec.js"
        ],
        browsers: ["PhantomJS"],
        reporters: ["progress", "coverage"],
        preprocessors: {
            "target/cljsbuild/public/js/spec.js": ['sourcemap', 'coverage']
        },
        coverageReporter: {
            type: 'json',
            subdir: '.',
            file: 'coverage.json'
        }
    });
};
//Hello
