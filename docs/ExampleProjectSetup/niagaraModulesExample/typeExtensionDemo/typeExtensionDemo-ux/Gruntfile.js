/* jshint node: true *//* eslint-env node */

"use strict";

var loadTasksRelative = require('grunt-niagara/lib/loadTasksRelative');

var SRC_FILES = [
    'src/rc/**/*.js',
    'Gruntfile.js'
  ],
  SPEC_FILES = [
    'srcTest/rc/spec/**/*.js'
  ],
  TEST_FILES = [
    'srcTest/rc/*.js'
  ],
  ALL_FILES = SRC_FILES.concat(SPEC_FILES).concat(TEST_FILES);

module.exports = function runGrunt(grunt) {

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    eslint: { src: ALL_FILES },
    watch: { src: ALL_FILES },
    babel: {},
    karma: {},
    requirejs: {},
    niagara: {
      station: {
        stationName: 'typeExtensionDemo',
        forceCopy: true,
        sourceStationFolder: './srcTest/rc/stations/typeExtensionDemoUnitTest'
      }
    }
  });

  loadTasksRelative(grunt, 'grunt-niagara');
};
