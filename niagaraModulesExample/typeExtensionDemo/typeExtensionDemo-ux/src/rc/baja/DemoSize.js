/**
 * @module nmodule/typeExtensionDemo/rc/baja/DemoSize
 */
define([ 'baja!' ], function (baja) {
  'use strict';

  /**
   * @class
   * @alias module:nmodule/typeExtensionDemo/rc/baja/DemoSize
   * @extends baja.Simple
   */
  class DemoSize extends baja.Simple {
    constructor(width, height) {
      super();
      this.$width = width;
      this.$height = height;
    }

    /**
     * @returns {string}
     */
    encodeToString() { return this.$width + ',' + this.$height; }

    /**
     * @param {string} str
     * @returns {module:nmodule/typeExtensionDemo/rc/baja/DemoSize}
     */
    decodeFromString(str) { return new DemoSize(...str.split(',').map(parseFloat)); }

    /**
     * @param {number} width
     * @param {number} height
     * @returns {module:nmodule/typeExtensionDemo/rc/baja/DemoSize}
     */
    make(width, height) { return new DemoSize(width, height); }

    /**
     * @returns {number}
     */
    getWidth() { return this.$width; }

    /**
     * @returns {number}
     */
    getHeight() { return this.$height; }

    /**
     * @returns {string}
     */
    toString() { return `Width: ${ this.$width } Height: ${ this.$height }`; }

    /**
     * @returns {module:nmodule/typeExtensionDemo/rc/baja/DemoSize}
     */
    static get DEFAULT() { return DEFAULT; }
  }

  const DEFAULT = new DemoSize();

  return DemoSize;
});
