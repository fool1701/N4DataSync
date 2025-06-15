/* eslint-env browser */
/*global testGlobals */

(function () {
  
  'use strict';
  
  require.config({
    baseUrl: '/base',
    hbs: {
      disableI18n: true
    },
    paths: {
      baja: '/module/bajaScript/rc/plugin/baja',
      bajaScript: '/module/bajaScript/rc',
      bajaux: '/module/bajaux/rc',
      css: '/module/js/com/tridium/js/ext/require/css',
      Handlebars: '/module/js/rc/handlebars/handlebars',
      hbs: '/module/js/rc/require-handlebars-plugin/hbs.built.min',
      jquery: '/module/js/rc/jquery/jquery.min',
      lex: '/module/js/rc/lex/lexplugin',
      log: '/module/js/rc/log/logPlugin',
      nmodule: '/module',
      'nmodule/typeExtensionDemo': 'build/karma/src',
      'nmodule/typeExtensionDemoTest': 'build/karma/srcTest',
      Promise: '/module/js/rc/bluebird/bluebird',
      'niagara-test-server': '/niagara-test-server',
      underscore: '/module/js/rc/underscore/underscore'
    },
    map: {
      '*': {
        'jquery': 'nmodule/js/rc/shims/jquery/jquery'
      },
      'nmodule/js/rc/shims/jquery/jquery': {
        'jquery': 'jquery'
      }
    }
  });

  // ensure that builtfiles don't get loaded during tests in case webdev is turned off
  define('nmodule/typeExtensionDemo/rc/typeExtensionDemo.built.min');
  
  function testOnly(regex) {
    if (regex) {
      testGlobals.testOnly = regex;
    }
  }

  require([ 'niagara-test-server/karmaUtils',
           'niagara-test-server/globals' ], function (karmaUtils) {

    /*
     * if your test suite grows very large, you can change which specs
     * are run here, without having to restart Karma with a different
     * --testOnly flag. just be sure not to commit this change.
     */
    //testOnly('rc/typeExtensionDemo');
    testOnly('');

    karmaUtils.setupAndRunSpecs({
      user: 'admin',
      pass: 'asdf1234',
      specs: [ 'nmodule/typeExtensionDemoTest/rc/spec/allSpecs' ]
    }, function (err) {
      if (err) { console.error(err); }
    });
  });
}());
