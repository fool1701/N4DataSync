define([
  'baja!',
  'baja!typeExtensionDemo:DemoSize',
  'nmodule/typeExtensionDemo/rc/baja/DemoSize' ], function (
  baja,
  types,
  DemoSize) {

  'use strict';

  describe('nmodule/typeExtensionDemo/rc/baja/DemoSize', () => {
    it('is registered on typeExtensionDemo:DemoSize', () => {
      expect(baja.$('typeExtensionDemo:DemoSize')).toEqual(jasmine.any(DemoSize));
    });

    describe('#encodeToString()', () => {
      it('encodes to "width,height"', () => {
        expect(new DemoSize(1.2, 3.4).encodeToString()).toBe('1.2,3.4');
      });
    });

    describe('#decodeFromString()', () => {
      it('decodes to a DemoSize', () => {
        expect(DemoSize.DEFAULT.decodeFromString('4.5,6.7'))
          .toEqual(new DemoSize(4.5, 6.7));
      });
    });

    describe('#getWidth()', () => {
      it('returns the width', () => {
        expect(new DemoSize(44, 55).getWidth()).toBe(44);
      });
    });

    describe('#getHeight()', () => {
      it('returns the height', () => {
        expect(new DemoSize(66, 77).getHeight()).toBe(77);
      });
    });
  });
});
