/*  DepressedPress.com DP_Debug

Author: Jim Davis, the Depressed Press of Boston
Date: August 22, 2005
Contact: webmaster@depressedpress.com
Website: www.depressedpress.com

Full documentation can be found at:
http://www.depressedpress.com/depressedpress/Content/Development/JavaScript/Extensions/DP_Debug/Index.cfm

DP_Debug provides extensions to the JavaScript Object class to assist with debugging.

	+ The Object.dpDump method allows you to dump an HTML representation of any JavaScript object to a debugging window.  It supports nested objects, recursive references and chained method calls.
	+ The Object.dpGetType method provides a more specific answer as to an object type than the native typeof operator.
	+ The Object.dpDebugInstances method places or removes the other methods in the library into the Object prototype.

Copyright (c) 1996-2005, The Depressed Press of Boston (depressedpres.com)

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

+) Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 

+) Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 

+) Neither the name of the DEPRESSED PRESS OF BOSTON (DEPRESSEDPRESS.COM) nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/


	// Extend the Object prototype with the "dpDump" method
Object.dpDump = function(Ob, DevLabel, ShowFunctions, MaxRecurseLevel) {

	if ( typeof DevLabel != "string" || DevLabel == "" ) {
		var DevLabel = "Root Element";
	};

	if ( typeof ShowFunctions != "boolean" ) {
		var ShowFunctions = false;
	};
	if ( typeof MaxRecurseLevel != "number" ) {
		var MaxRecurseLevel = -1;
	};

		// Create the Dump Window
	DPDumpWindow = window.open("","DPDumpWindow","scrollbars=yes,resizable=yes,width=500,height=400");
	var DPW = DPDumpWindow.document;

	if ( DPW.getElementById("DPDump_Content") == null ) {

			// Write the HTML
		DPW.write(	"<html><head>",
					"	<title>dpDump Information</title>",
					"	<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />",
					"	<script type='text/javascript'>",
					"		dpDumpInstances = 0;",
					"	</script>",
					"</head>",
					"<frameset  rows='25,*'>",
					"    <frame name='DPDump_Header' id='DPDump_Header' marginwidth='0' marginheight='0' scrolling='no' frameborder='0' noresize>",
					"    <frame name='DPDump_Content' id='DPDump_Content' marginwidth='0' marginheight='0' scrolling='auto' frameborder='0'>",
					"</frameset>",
					"</html>");
					DPW.close();
			// Get shortcuts to the frames
		DPDH = DPDumpWindow.DPDump_Header.document;
		DPDC = DPDumpWindow.DPDump_Content.document;
		DPDH.write(	"<html><head>",
					"	<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />",
					"	<style type='text/css' media='screen'>",
					"		body, table, td {",
					"			font-family: Verdana, Arial, sans-serif;",
					"			font-size: 11pt;",
					"			color: #28166f;",
					"			background-color: #b8db7c;",
					"			padding: 0px 0px 0px 0px;",
					"			margin: 2px 2px 2px 2px;",
					"			}",
					"		A:LINK {",
					"			color : #28166f;",
					"			text-decoration : none;",
					"			}",
					"		A:VISITED {",
					"			color : #28166f;",
					"			text-decoration : none;",
					"			}",
					"		A:HOVER {",
					"			text-decoration : underline;",
					"			}",
					"		#DumpHeader {",
					"			width: 100%;",
					"			vertical-align: top;",
					"			padding: 0px;",
					"			}",
					"		#DumpTitle {",
					"			font-size: 10pt;",
					"			font-weight: bold;",
					"			}",
					"		#DumpTools {",
					"			font-size: 10pt;",
					"			font-weight: bold;",
					"			text-align: right;",
					"			padding: 0px 5px 0px 5px;",
					"			}",
					"	</style>",
					"</head><body>",
					"<table id='DumpHeader' cellspacing='0'>",
					"	<tr><td id='DumpTitle'>dpDump by the <a href='http://www.depressedpress.com/' target='_blank'>DepressedPress</a></td>",
					"		<td id='DumpTools'><a href='#' onclick='parent.DPDump_Content.document.getElementById(\"dpDumpDisplay\").innerHTML = \"\"; return false;'>Clear</a> | <a href='#' onclick='parent.close(); return false;'>Close</a></td>",
					"	</tr>",
					"</table>",
					"</body></html>");
		DPDC.write(	"<html><head>",
					"	<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />",
					"	<script type='text/javascript'>",
					"	function toggleObDisplay(ObLabelID, ObValueID) {",
					"		var CurLabel = document.getElementById(ObLabelID);",
					"		var CurValue = document.getElementById(ObValueID);",
					"		if ( CurValue.style.display == \"none\" ) {",
					"			CurValue.style.display = \"block\";",
					"			CurLabel.style.fontStyle = \"normal\";",
					"		} else {",
					"			CurValue.style.display = \"none\";",
					"			CurLabel.style.fontStyle = \"italic\";",
					"		};",
					"	};",
					"	</script>",
					"	<style type='text/css' media='screen'>",
					"		body {",
					"			font-family: Verdana, Arial, sans-serif;",
					"			font-size: 11pt;",
					"			background-color: white;",
					"			padding: 0px 5px 0px 5px;",
					"			margin: 0px 0px 0px 0px;",
					"			}",
					"		table.InstTable {",
					"			width: 100%;",
					"			vertical-align: top;",
					"			padding: 0px;",
					"			border: 0px;",
					"			}",
					"		td.InstHeader {",
					"			padding: 1px 4px 1px 4px;",
					"			background-color: blue;",
					"			}",
					"		td.InstLabel {",
					"			font-size: 9pt;",
					"			color: white;",
					"			font-weight: bold;",
					"			text-decoration: underline;",
					"			cursor: pointer;",
					"			}",
					"		td.InstType {",
					"			font-size: 7pt;",
					"			color: white;",
					"			text-align: right;",
					"			}",
					"		td.InstValue {",
					"			}",
					"		table.ObTable {",
					"			width: 100%;",
					"			vertical-align: top;",
					"			padding: 0px;",
					"			border: 2px blue solid;",
					"			}",
					"		td.ObHeader {",
					"			padding: 1px 4px 1px 4px;",
					"			background-color: #eeeeee;",
					"			}",
					"		td.ObLabel {",
					"			font-size: 9pt;",
					"			color: gray;",
					"			font-weight: bold;",
					"			text-decoration: underline;",
					"			cursor: pointer;",
					"			}",
					"		td.ObType {",
					"			font-size: 7pt;",
					"			color: gray;",
					"			text-align: right;",
					"			}",
					"		td.ObValue {",
					"			padding: 4px 4px 4px 30px;",
					"			}",
					"		hr.ObSeparator {",
					"			height: 2px;",
					"			color: #b8db7c;",
					"			background-color: #b8db7c;",
					"			margin: 10px 10% 10px 10%;",
					"			}",
					"	</style>",
					"</head><body>",
					"	<div id='dpDumpDisplay'></div>",
					"</body></html>");

	} else {
			// Update the count for the Dump instances
		DPDumpWindow.dpDumpInstances = DPDumpWindow.dpDumpInstances + 1;
	};

		// Set the encoded character dictionary for the conversion to HTML
	var EncodedChars = new Array();
		// Add simple chars (&amp; must come first!)
	EncodedChars["&"] = "&amp;";
	EncodedChars["<"] = "&lt;";
	EncodedChars[">"] = "&gt;";
	EncodedChars["\""] = "&quot;";

		// Set up the Object Checker
	var ParsedObs = new Array();
		// Define the current Dump InstanceID
	var InstID = DPDumpWindow.dpDumpInstances;
	var ObID = 0;
	var ObRefID = 0;

		// Output the current dump
	DPDumpWindow.DPDump_Content.document.getElementById("dpDumpDisplay").innerHTML += parseToHTML(Ob, DevLabel);

		// Return a reference to the object
	return Ob;


		// Escape characters using the Encoded Chars tables
	function escapeString(CurString) {
		var CurRegEx;
		for ( var CurChar in EncodedChars ) {
			if (typeof EncodedChars[CurChar] != "function") {;
				if ( CurChar != "\\" ) {
					CurRegEx = new RegExp(CurChar, "g");
				} else {
					CurRegEx = /\\/g;
				};
				CurString = CurString.replace(CurRegEx, EncodedChars[CurChar]);
			};
		};
		return CurString;	
	};

		// Has the object been parsed already?
	function checkIfParsedOB(Ob) {
			// Check the parsed array
		for ( var Cnt = 0; Cnt < ParsedObs.length; Cnt++ ) {
			if ( ParsedObs[Cnt] === Ob ) {
				return true;
			};
		};
			// Add the passed object to the parsed array
		ParsedObs[ParsedObs.length] = Ob;
		return false;
	}; 

		// Get the Ref ID
	function getObRefID(Ob) {
			// Check the parsed array
		for ( var Cnt = 0; Cnt < ParsedObs.length; Cnt++ ) {
			if ( ParsedObs[Cnt] === Ob ) {
				return Cnt;
			};
		};
		return "";
	}; 

		// Parse objects to HTML
	function parseToHTML(Ob, ObLabel, RecurseLevel, ForceUnknown) {

			// Manage Arugments
		if ( typeof RecurseLevel != "number" ) {
			RecurseLevel = 0;
		};
		if ( typeof ForceUnknown != "boolean" ) {}
			ForceUnknown = false;
		;
		
			// Update the object ID
		ObID = ObID + 1;

			// Initialize results
		var Results = "";

			// Set options based on whether this is the first pass or a recursive pass
		if ( RecurseLevel == 0 ) {
			var StylePrefix = "Inst";
			Results += "<table class='InstTable' cellspacing='0'>";
		} else {
			var StylePrefix = "Ob";
		};

			// Get some information about the passed Object and set some display fragments
		if ( ForceUnknown ) {
			var ObType = "unknown";
		} else {
			var ObType = Object.dpGetType(Ob);
		};
		var ObLabelID = "ObLabel_" + InstID + "_" + ObID;
		var ObValueID = "ObValue_" + InstID + "_" + ObID;
		var ObIDLink = "";
		var ObDisplayed = false;
		switch ( ObType ) {
	        case "object": case "array":
					// Check to see if the object has already been displayed
				if ( checkIfParsedOB(Ob) ) {
					ObDisplayed = true;
				};
				var CurObRefID = getObRefID(Ob);
				if ( ObDisplayed ) {
						// Only IR can handle local links in popup generated by JavaScript 
					if ( navigator.appName == "Microsoft Internet Explorer" ) {
						ObIDLink = " <a href='#" + InstID + "_" + CurObRefID + "'>(id: " + CurObRefID + ")</a>";
					} else {
						ObIDLink = " (id: " + CurObRefID + ")";
					}; 
				} else {
					ObIDLink = " <a name='" + InstID + "_" + CurObRefID + "'>(id: " + CurObRefID + ")</a>";
				};
		};

			// Display Element Header
		Results += "<tr><td onclick='toggleObDisplay(\"" + ObLabelID + "\", \"" + ObValueID + "\");' id='" + ObLabelID + "' class='" + StylePrefix + "Header " + StylePrefix + "Label'>" + ObLabel + "</td><td class='" + StylePrefix + "Header " + StylePrefix + "Type'>" + ObType + ObIDLink + "</td></tr>";
		Results += "<tr><td colspan='2' class='" + StylePrefix + "Value'><div id='" + ObValueID + "'>";

			// Display Element Content
		switch ( ObType ) {
	        case "object": case "array":
				if ( ObDisplayed ) {
					Results += "<tt>( Previously Displayed )</tt>";
				} else if ( RecurseLevel == MaxRecurseLevel ) {
					Results += "<tt>( Maximum Recursion Depth Reached )</tt>";
				} else {
						// Determine if the object is enumerable
					var ObEnumerable = true;
					try { for ( var Prop in Ob ) { break; } } catch (CurError) {	ObEnumerable = false; };
						// Loop over object
					if ( ObEnumerable ) {
						Results += "<table class='ObTable' cellspacing='0'>";
						for ( var Prop in Ob ) {
							PropEnumerable = true;
							try { typeof Ob[Prop]; Ob[Prop]; } catch (CurError) { 
								PropEnumerable = false;
								Results += parseToHTML(null, Prop, RecurseLevel + 1, true);
							};
							if ( PropEnumerable && ( typeof Ob[Prop] != "function" || ShowFunctions ) && ( Ob[Prop] != undefined ) ) {
								Results += parseToHTML(Ob[Prop], Prop, RecurseLevel + 1);
							};
						};
						Results += "</table>";
					} else {
						Results += "<tt>( Object is not Enumerable )</tt>";
					};
				};
				break;
	        case "function":
				Results += escapeString(Ob.toString());
				break;
	        case "null":
				Results += "<tt>( null )</tt>";
				break;
	        case "date":
				Results += Ob.toString();
				break;
	        case "number":
				Results += Ob.toString();
				break;
	        case "string":
				if ( Ob.length == 0 ) {
					Results += "<tt>( Empty String )</tt>";
				} else {
					Results += escapeString(Ob);
				};
				break;
	        case "boolean":
				Results += Ob.toString();
				break;
	        case "undefined":
				Results += "<tt>( Undefined Entity )</tt>";
				break;
	        case "unknown":
				Results += "<tt>( Entity is not Enumerable )</tt>";
				break;
		};

			// Display Element Footer
		Results += "</div></td></tr>";
		if ( RecurseLevel == 0 ) {
			Results += "</table><hr class='ObSeparator'>"
		};

			// Return Results
		return Results;
		
	};


};



	// Extend the Object prototype with the "dpGetType" method
Object.dpGetType = function dpGetType(Ob) {

	try {
		switch (typeof Ob) {
	        case "object":
				if ( Ob == null ) {
					return "null";
				} else if ( Ob.constructor == Date ) {
					return "date";
				} else if ( Ob.constructor == Array ) {
					return "array";
				} else if ( Ob.constructor == String ) {
					return "string";
				} else if ( Ob.constructor == Number ) {
					return "number";
				} else if ( Ob.constructor == Boolean ) {
					return "boolean";
				} else if ( Ob == undefined ) {
					return "undefined";
				} else {
					return "object";
				};
	        case "function":
				return "function";
	        case "number":
				return "number";
	        case "string":
				return "string";
	        case "boolean":
				return "boolean";
	        case "undefined":
				return "undefined";
	        default:
				return "unknown";
		};
	} catch (CurError) {
		return "unknown";
	};

};


Object.dpDebugInstances = function(Indicator) {

		// Check input
	if ( typeof Indicator != "boolean" ) {
		var Indicator = false;
	};

		// Set the state
	if ( Indicator == true ) {
		Object.prototype.dpDump = function(DevLabel, ShowFunctions) {
			return Object.dpDump(this, DevLabel, ShowFunctions);
		}	
		Object.prototype.dpGetType = function() {
			return Object.dpGetType(this);
		}	
	} else {
		Object.prototype.dpDump = undefined;
		Object.prototype.dpGetType =  undefined;
	};

};

