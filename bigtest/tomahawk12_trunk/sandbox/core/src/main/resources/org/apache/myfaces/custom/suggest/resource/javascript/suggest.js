/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var ACregExp = /\.|:/g;

function calculateWidth(element) {
  if (navigator && navigator.userAgent.toLowerCase().indexOf("msie") == -1) {
    return element.offsetWidth - 2
  } else {
    return element.offsetWidth
  }
}

function calculateOffsetLeft(element) {
  return calculateOffset(element, "offsetLeft")
}

function calculateOffsetTop(element) {
  return calculateOffset(element, "offsetTop")
}

function calculateOffset(element, attr) {
  var offset = 0;
  while (element) {
    offset += element[attr];
    element = element.offsetParent
  }
  return offset
}

function getSelectionStart(fld) {
  var start = 0;
  if (fld.createTextRange) {
    var selection = document.selection.createRange().duplicate();
    selection.moveEnd("textedit", 1);
    start = fld.value.length - selection.text.length
  } else if (fld.setSelectionRange) {
    start = fld.selectionStart
  } else {
    start = -1
  }
  return start
}

function getSelectionLength(fld) {
  var selLength = 0;
  if (fld.createTextRange){
    var selection = document.selection.createRange().duplicate();
    selLength = selection.text.length
  } else if (fld.setSelectionRange) {
    selLength = fld.selectionEnd - fld.selectionStart
  } else {
    selLength = -1
  }
  return selLength
}

function unSelect(fld) {
  if (fld.createTextRange) {
    var range = fld.createTextRange();
    range.moveStart("character", fld.value.length);
    range.select();
  } else if (fld.setSelectionRange) {
    fld.setSelectionRange(fld.value.length, fld.value.length)
  }
}

function highlightACDiv(div, fldId, index) {
  fldId = fldId.replace(ACregExp, '_');
  if (eval(fldId + "Row") != -1) {
    var currDiv = eval(fldId + "RowDiv");
    currDiv.className = eval(fldId + "NormalClass");
  }

  div.className += " " + eval(fldId + "HighlightClass");
  eval(fldId + "Row = index");
  eval(fldId + "RowDiv = div");
}

function unHighlightACDiv(div, fldId) {
  fldId = fldId.replace(ACregExp, '_');
  div.className = eval(fldId + "NormalClass");
  eval(fldId + "Row = -1");
  eval(fldId + "RowDiv = null");
  eval(fldId + "MinRow = 0");
}

function selectACDiv(fldId) {
  var fld = document.getElementById(fldId);

  var div = document.getElementById("AC" + fldId);

  fldId = fldId.replace(ACregExp, '_');
  var selected = div.getElementsByTagName("DIV")[eval(fldId + "Row")];
  var text = selected.firstChild.data;

  fld.value = text;

  unSelect(fld);
}

function filterACList(fld, backspace) {
  var srchText = fld.value;

  var selStart = getSelectionStart(fld);
  if (selStart > 0 && getSelectionLength(fld) > 0)
    srchText = srchText.substring(0, selStart);

  var div = document.getElementById("AC" + fld.id);
  var options = div.getElementsByTagName("DIV");
  var fldId = fld.id.replace(ACregExp, '_');

  var caseSensitive = eval(fldId + "CaseSensitive");
  if (!caseSensitive) srchText = srchText.toLowerCase();

  var srchTextLen = srchText.length;
  var found = false;
  var optLen = options.length;
  for (var ii=0; ii<optLen; ii++) {
    var optText = options[ii].firstChild.data;
    if (!caseSensitive) optText = optText.toLowerCase();
    if (optText.substr(0, srchTextLen) == srchText) {
      options[ii].style.display = "block";
      if (!found && !backspace) {
        eval(fldId + "MinRow = " + ii);
        highlightACDiv(options[ii], fld.id, ii);
        autoComplete(fld, options[ii])
      }
      found = true;
      if (srchTextLen == 0 || backspace) unHighlightACDiv(options[ii], fld.id)
    } else {
      options[ii].style.display = "none"
    }
  }

  if (found) {
    div.style.visibility = "visible"
    var divShim = document.getElementById(fldId + "Shim");
    if (divShim)
      divShim.style.visibility = "visible";
  }
  else {
    var currRow = eval(fldId + "RowDiv");
    if (currRow)
      unHighlightACDiv(currRow, fld.id);
    blurACfld(fld)
  }
}

function autoComplete(fld, div) {
  var currLen = fld.value.length;

  if (getSelectionStart(fld) != currLen) return;

  fld.value = div.firstChild.data;

  if (fld.createTextRange) {
    var range = fld.createTextRange();
    range.moveStart("character", currLen);
    range.select()
  } else if (fld.setSelectionRange) {
    fld.setSelectionRange(currLen, fld.value.length)
  }
}

function blurThenGetFocus(fld) {
  fld.blur();
  setTimeout("setACfieldFocus('" + fld.id + "')", 10);
  return
}

function setACfieldFocus(fldId) {
  document.getElementById(fldId).focus();
}

function handleACkeyDown(fld, event) {
  var div = document.getElementById("AC" + fld.id);

  if (div.style.visibility == "hidden") return true;

  if (!event && window.event) event = window.event;

  var fldId = fld.id.replace(ACregExp, '_');

  var keyCode = event.keyCode;
  if (keyCode == 40 || keyCode == 38) {
    blurThenGetFocus(fld);

    var options = div.getElementsByTagName("DIV");
    var currRow = eval(fldId + "Row");
    var minRow = eval(fldId + "MinRow");
    var dispRows = eval(fldId + "DisplayRows");
    var scroll = eval(fldId + "Scroll");

    var newRow = eval(fldId + "Row") + ((keyCode == 40) ? 1 : -1);

    if (!scroll && newRow >= (minRow + dispRows))
      return false;

    if (newRow <= -1)
        newRow = ((scroll) ? options.length : (minRow + dispRows)) - 1;

    while (options[newRow] && options[newRow].style.display == "none") {
      newRow = newRow + ((keyCode == 40) ? 1 : -1);
    }

    if (options[newRow]) {
      highlightACDiv(options[newRow], fld.id, newRow);
      selectACDiv(fld.id);

      if (newRow >= (minRow + dispRows))
        options[newRow - dispRows + 1].scrollIntoView()
    }

    return false;
  } else if (keyCode == 13 || keyCode == 3) {
    unSelect(fld);
    fld.blur();
    div.style.visibility = "hidden";
    var divShim = document.getElementById(fldId + "Shim");
    if (divShim)
      divShim.style.visibility = "hidden";
    return false;
  } else
    return true;
}

function handleACkeyUp(fld, event) {
  if (!event && window.event) event = window.event;

  var fldId = fld.id.replace(ACregExp, '_');

  var backspace = false;
  var reset = false;
  switch (event.keyCode) {
    case 8:
    case 46:
      backspace = true;
      break;
    case 38:
    case 40:
      return false;
    case 33:
    case 34:
    case 35:
    case 36:
    case 37:
    case 39:
    case 45:
      reset = true;
      break;
    default:
      break
  }

  if (!reset) {
    filterACList(fld, backspace);
  } else {
    if (eval(fldId + "Row") != -1)
      unHighlightACDiv(eval(fldId + "RowDiv"), fld.id);
  }

  AChandlingKeyUp = false;
}

function blurACfld(fld) {
  var listDiv = document.getElementById("AC" + fld.id);

  listDiv.style.visibility = "hidden";

  var fldId = fld.id.replace(ACregExp, '_');

  var divShim = document.getElementById(fldId + "Shim");
  if (divShim)
    divShim.style.visibility = "hidden";

  var hiddenFldId = eval(fldId + "HiddenFldId");
  var hiddenFld = document.getElementById(hiddenFldId);
  if (!hiddenFld) return;

  var selValue = -1; // if no match, set the value to -1
  var rowDiv = eval(fldId + "RowDiv");
  if (rowDiv)
    selValue = rowDiv.id.substr(hiddenFldId.length);

  hiddenFld.value = selValue;
}

function setACdivs() {
  var allDivs = document.getElementsByTagName("DIV");
  for (var ii=0; ii<allDivs.length; ii++) {
    if (allDivs[ii].id.indexOf("AC") != 0) continue;

    var fldId = allDivs[ii].id.substr(2);
    var fld = document.getElementById(fldId);
    fldId = fldId.replace(ACregExp, '_');
    allDivs[ii].style.border = "black 1px solid";
    allDivs[ii].style.zIndex = 100;
    allDivs[ii].style.backgroundColor = "white";
    allDivs[ii].style.visibility = "hidden";
    allDivs[ii].style.position = "absolute";
    allDivs[ii].style.overflow = (eval(fldId + "Scroll")) ? "auto" : "hidden";
    allDivs[ii].style.width = calculateWidth(fld) + "px";
    allDivs[ii].style.height = 15 * eval(fldId + "DisplayRows") + "px";
    allDivs[ii].style.top = calculateOffsetTop(fld) + fld.offsetHeight;
    allDivs[ii].style.left = calculateOffsetLeft(fld);

    var divShim = document.getElementById(fldId + "Shim");
    if (divShim)
    {
      divShim.style.width = allDivs[ii].style.width;
      divShim.style.height = allDivs[ii].style.height;
      divShim.style.left = allDivs[ii].style.left;
      divShim.style.top = allDivs[ii].style.top;
      divShim.style.zIndex = allDivs[ii].style.zIndex - 1;
      divShim.style.visibility = "hidden";
    }
  }
}

if (window.attachEvent)
 window.attachEvent("onload", setACdivs);
else if (window.addEventListener)
 window.addEventListener("load", setACdivs, false);
