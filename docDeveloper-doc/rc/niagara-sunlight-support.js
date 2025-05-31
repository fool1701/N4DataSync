(function() {
  "use strict";
  function highlight() {
    Sunlight.highlightAll();
    var elems = document.getElementsByClassName("sunlight-highlighted");
    if (elems.length) {
      for (var i = 0; i < elems.length; i++) {
        var e = elems[i].parentElement;
        if (e && e.tagName.toLowerCase() === "pre") { e.className += "doc-code-example"; }
      }
    }
  }
  
  window.niagara = window.niagara || {};
  window.niagara.docDevUtil = window.niagara.docDevUtil || {
    highlightCode: highlight
  };
}());