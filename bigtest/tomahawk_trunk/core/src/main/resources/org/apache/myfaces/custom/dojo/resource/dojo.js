/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

if(typeof dojo=="undefined"){
var dj_global=this;
var dj_currentContext=this;
function dj_undef(_1,_2){
return (typeof (_2||dj_currentContext)[_1]=="undefined");
}
if(dj_undef("djConfig",this)){
var djConfig={};
}
if(dj_undef("dojo",this)){
var dojo={};
}
dojo.global=function(){
return dj_currentContext;
};
dojo.locale=djConfig.locale;
dojo.version={major:0,minor:4,patch:1,flag:"",revision:Number("$Rev: 6824 $".match(/[0-9]+/)[0]),toString:function(){
with(dojo.version){
return major+"."+minor+"."+patch+flag+" ("+revision+")";
}
}};
dojo.evalProp=function(_3,_4,_5){
if((!_4)||(!_3)){
return undefined;
}
if(!dj_undef(_3,_4)){
return _4[_3];
}
return (_5?(_4[_3]={}):undefined);
};
dojo.parseObjPath=function(_6,_7,_8){
var _9=(_7||dojo.global());
var _a=_6.split(".");
var _b=_a.pop();
for(var i=0,l=_a.length;i<l&&_9;i++){
_9=dojo.evalProp(_a[i],_9,_8);
}
return {obj:_9,prop:_b};
};
dojo.evalObjPath=function(_e,_f){
if(typeof _e!="string"){
return dojo.global();
}
if(_e.indexOf(".")==-1){
return dojo.evalProp(_e,dojo.global(),_f);
}
var ref=dojo.parseObjPath(_e,dojo.global(),_f);
if(ref){
return dojo.evalProp(ref.prop,ref.obj,_f);
}
return null;
};
dojo.errorToString=function(_11){
if(!dj_undef("message",_11)){
return _11.message;
}else{
if(!dj_undef("description",_11)){
return _11.description;
}else{
return _11;
}
}
};
dojo.raise=function(_12,_13){
if(_13){
_12=_12+": "+dojo.errorToString(_13);
}else{
_12=dojo.errorToString(_12);
}
try{
if(djConfig.isDebug){
dojo.hostenv.println("FATAL exception raised: "+_12);
}
}
catch(e){
}
throw _13||Error(_12);
};
dojo.debug=function(){
};
dojo.debugShallow=function(obj){
};
dojo.profile={start:function(){
},end:function(){
},stop:function(){
},dump:function(){
}};
function dj_eval(_15){
return dj_global.eval?dj_global.eval(_15):eval(_15);
}
dojo.unimplemented=function(_16,_17){
var _18="'"+_16+"' not implemented";
if(_17!=null){
_18+=" "+_17;
}
dojo.raise(_18);
};
dojo.deprecated=function(_19,_1a,_1b){
var _1c="DEPRECATED: "+_19;
if(_1a){
_1c+=" "+_1a;
}
if(_1b){
_1c+=" -- will be removed in version: "+_1b;
}
dojo.debug(_1c);
};
dojo.render=(function(){
function vscaffold(_1d,_1e){
var tmp={capable:false,support:{builtin:false,plugin:false},prefixes:_1d};
for(var i=0;i<_1e.length;i++){
tmp[_1e[i]]=false;
}
return tmp;
}
return {name:"",ver:dojo.version,os:{win:false,linux:false,osx:false},html:vscaffold(["html"],["ie","opera","khtml","safari","moz"]),svg:vscaffold(["svg"],["corel","adobe","batik"]),vml:vscaffold(["vml"],["ie"]),swf:vscaffold(["Swf","Flash","Mm"],["mm"]),swt:vscaffold(["Swt"],["ibm"])};
})();
dojo.hostenv=(function(){
var _21={isDebug:false,allowQueryConfig:false,baseScriptUri:"",baseRelativePath:"",libraryScriptUri:"",iePreventClobber:false,ieClobberMinimal:true,preventBackButtonFix:true,delayMozLoadingFix:false,searchIds:[],parseWidgets:true};
if(typeof djConfig=="undefined"){
djConfig=_21;
}else{
for(var _22 in _21){
if(typeof djConfig[_22]=="undefined"){
djConfig[_22]=_21[_22];
}
}
}
return {name_:"(unset)",version_:"(unset)",getName:function(){
return this.name_;
},getVersion:function(){
return this.version_;
},getText:function(uri){
dojo.unimplemented("getText","uri="+uri);
}};
})();
dojo.hostenv.getBaseScriptUri=function(){
if(djConfig.baseScriptUri.length){
return djConfig.baseScriptUri;
}
var uri=new String(djConfig.libraryScriptUri||djConfig.baseRelativePath);
if(!uri){
dojo.raise("Nothing returned by getLibraryScriptUri(): "+uri);
}
var _25=uri.lastIndexOf("/");
djConfig.baseScriptUri=djConfig.baseRelativePath;
return djConfig.baseScriptUri;
};
(function(){
var _26={pkgFileName:"__package__",loading_modules_:{},loaded_modules_:{},addedToLoadingCount:[],removedFromLoadingCount:[],inFlightCount:0,modulePrefixes_:{dojo:{name:"dojo",value:"src"}},setModulePrefix:function(_27,_28){
this.modulePrefixes_[_27]={name:_27,value:_28};
},moduleHasPrefix:function(_29){
var mp=this.modulePrefixes_;
return Boolean(mp[_29]&&mp[_29].value);
},getModulePrefix:function(_2b){
if(this.moduleHasPrefix(_2b)){
return this.modulePrefixes_[_2b].value;
}
return _2b;
},getTextStack:[],loadUriStack:[],loadedUris:[],post_load_:false,modulesLoadedListeners:[],unloadListeners:[],loadNotifying:false};
for(var _2c in _26){
dojo.hostenv[_2c]=_26[_2c];
}
})();
dojo.hostenv.loadPath=function(_2d,_2e,cb){
var uri;
if(_2d.charAt(0)=="/"||_2d.match(/^\w+:/)){
uri=_2d;
}else{
uri=this.getBaseScriptUri()+_2d;
}
if(djConfig.cacheBust&&dojo.render.html.capable){
uri+="?"+String(djConfig.cacheBust).replace(/\W+/g,"");
}
try{
return !_2e?this.loadUri(uri,cb):this.loadUriAndCheck(uri,_2e,cb);
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.hostenv.loadUri=function(uri,cb){
if(this.loadedUris[uri]){
return true;
}
var _33=this.getText(uri,null,true);
if(!_33){
return false;
}
this.loadedUris[uri]=true;
if(cb){
_33="("+_33+")";
}
var _34=dj_eval(_33);
if(cb){
cb(_34);
}
return true;
};
dojo.hostenv.loadUriAndCheck=function(uri,_36,cb){
var ok=true;
try{
ok=this.loadUri(uri,cb);
}
catch(e){
dojo.debug("failed loading ",uri," with error: ",e);
}
return Boolean(ok&&this.findModule(_36,false));
};
dojo.loaded=function(){
};
dojo.unloaded=function(){
};
dojo.hostenv.loaded=function(){
this.loadNotifying=true;
this.post_load_=true;
var mll=this.modulesLoadedListeners;
for(var x=0;x<mll.length;x++){
mll[x]();
}
this.modulesLoadedListeners=[];
this.loadNotifying=false;
dojo.loaded();
};
dojo.hostenv.unloaded=function(){
var mll=this.unloadListeners;
while(mll.length){
(mll.pop())();
}
dojo.unloaded();
};
dojo.addOnLoad=function(obj,_3d){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.modulesLoadedListeners.push(obj);
}else{
if(arguments.length>1){
dh.modulesLoadedListeners.push(function(){
obj[_3d]();
});
}
}
if(dh.post_load_&&dh.inFlightCount==0&&!dh.loadNotifying){
dh.callLoaded();
}
};
dojo.addOnUnload=function(obj,_40){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.unloadListeners.push(obj);
}else{
if(arguments.length>1){
dh.unloadListeners.push(function(){
obj[_40]();
});
}
}
};
dojo.hostenv.modulesLoaded=function(){
if(this.post_load_){
return;
}
if(this.loadUriStack.length==0&&this.getTextStack.length==0){
if(this.inFlightCount>0){
dojo.debug("files still in flight!");
return;
}
dojo.hostenv.callLoaded();
}
};
dojo.hostenv.callLoaded=function(){
if(typeof setTimeout=="object"){
setTimeout("dojo.hostenv.loaded();",0);
}else{
dojo.hostenv.loaded();
}
};
dojo.hostenv.getModuleSymbols=function(_42){
var _43=_42.split(".");
for(var i=_43.length;i>0;i--){
var _45=_43.slice(0,i).join(".");
if((i==1)&&!this.moduleHasPrefix(_45)){
_43[0]="../"+_43[0];
}else{
var _46=this.getModulePrefix(_45);
if(_46!=_45){
_43.splice(0,i,_46);
break;
}
}
}
return _43;
};
dojo.hostenv._global_omit_module_check=false;
dojo.hostenv.loadModule=function(_47,_48,_49){
if(!_47){
return;
}
_49=this._global_omit_module_check||_49;
var _4a=this.findModule(_47,false);
if(_4a){
return _4a;
}
if(dj_undef(_47,this.loading_modules_)){
this.addedToLoadingCount.push(_47);
}
this.loading_modules_[_47]=1;
var _4b=_47.replace(/\./g,"/")+".js";
var _4c=_47.split(".");
var _4d=this.getModuleSymbols(_47);
var _4e=((_4d[0].charAt(0)!="/")&&!_4d[0].match(/^\w+:/));
var _4f=_4d[_4d.length-1];
var ok;
if(_4f=="*"){
_47=_4c.slice(0,-1).join(".");
while(_4d.length){
_4d.pop();
_4d.push(this.pkgFileName);
_4b=_4d.join("/")+".js";
if(_4e&&_4b.charAt(0)=="/"){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,!_49?_47:null);
if(ok){
break;
}
_4d.pop();
}
}else{
_4b=_4d.join("/")+".js";
_47=_4c.join(".");
var _51=!_49?_47:null;
ok=this.loadPath(_4b,_51);
if(!ok&&!_48){
_4d.pop();
while(_4d.length){
_4b=_4d.join("/")+".js";
ok=this.loadPath(_4b,_51);
if(ok){
break;
}
_4d.pop();
_4b=_4d.join("/")+"/"+this.pkgFileName+".js";
if(_4e&&_4b.charAt(0)=="/"){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,_51);
if(ok){
break;
}
}
}
if(!ok&&!_49){
dojo.raise("Could not load '"+_47+"'; last tried '"+_4b+"'");
}
}
if(!_49&&!this["isXDomain"]){
_4a=this.findModule(_47,false);
if(!_4a){
dojo.raise("symbol '"+_47+"' is not defined after loading '"+_4b+"'");
}
}
return _4a;
};
dojo.hostenv.startPackage=function(_52){
var _53=String(_52);
var _54=_53;
var _55=_52.split(/\./);
if(_55[_55.length-1]=="*"){
_55.pop();
_54=_55.join(".");
}
var _56=dojo.evalObjPath(_54,true);
this.loaded_modules_[_53]=_56;
this.loaded_modules_[_54]=_56;
return _56;
};
dojo.hostenv.findModule=function(_57,_58){
var lmn=String(_57);
if(this.loaded_modules_[lmn]){
return this.loaded_modules_[lmn];
}
if(_58){
dojo.raise("no loaded module named '"+_57+"'");
}
return null;
};
dojo.kwCompoundRequire=function(_5a){
var _5b=_5a["common"]||[];
var _5c=_5a[dojo.hostenv.name_]?_5b.concat(_5a[dojo.hostenv.name_]||[]):_5b.concat(_5a["default"]||[]);
for(var x=0;x<_5c.length;x++){
var _5e=_5c[x];
if(_5e.constructor==Array){
dojo.hostenv.loadModule.apply(dojo.hostenv,_5e);
}else{
dojo.hostenv.loadModule(_5e);
}
}
};
dojo.require=function(_5f){
dojo.hostenv.loadModule.apply(dojo.hostenv,arguments);
};
dojo.requireIf=function(_60,_61){
var _62=arguments[0];
if((_62===true)||(_62=="common")||(_62&&dojo.render[_62].capable)){
var _63=[];
for(var i=1;i<arguments.length;i++){
_63.push(arguments[i]);
}
dojo.require.apply(dojo,_63);
}
};
dojo.requireAfterIf=dojo.requireIf;
dojo.provide=function(_65){
return dojo.hostenv.startPackage.apply(dojo.hostenv,arguments);
};
dojo.registerModulePath=function(_66,_67){
return dojo.hostenv.setModulePrefix(_66,_67);
};
dojo.setModulePrefix=function(_68,_69){
dojo.deprecated("dojo.setModulePrefix(\""+_68+"\", \""+_69+"\")","replaced by dojo.registerModulePath","0.5");
return dojo.registerModulePath(_68,_69);
};
dojo.exists=function(obj,_6b){
var p=_6b.split(".");
for(var i=0;i<p.length;i++){
if(!obj[p[i]]){
return false;
}
obj=obj[p[i]];
}
return true;
};
dojo.hostenv.normalizeLocale=function(_6e){
var _6f=_6e?_6e.toLowerCase():dojo.locale;
if(_6f=="root"){
_6f="ROOT";
}
return _6f;
};
dojo.hostenv.searchLocalePath=function(_70,_71,_72){
_70=dojo.hostenv.normalizeLocale(_70);
var _73=_70.split("-");
var _74=[];
for(var i=_73.length;i>0;i--){
_74.push(_73.slice(0,i).join("-"));
}
_74.push(false);
if(_71){
_74.reverse();
}
for(var j=_74.length-1;j>=0;j--){
var loc=_74[j]||"ROOT";
var _78=_72(loc);
if(_78){
break;
}
}
};
dojo.hostenv.localesGenerated;
dojo.hostenv.registerNlsPrefix=function(){
dojo.registerModulePath("nls","nls");
};
dojo.hostenv.preloadLocalizations=function(){
if(dojo.hostenv.localesGenerated){
dojo.hostenv.registerNlsPrefix();
function preload(_79){
_79=dojo.hostenv.normalizeLocale(_79);
dojo.hostenv.searchLocalePath(_79,true,function(loc){
for(var i=0;i<dojo.hostenv.localesGenerated.length;i++){
if(dojo.hostenv.localesGenerated[i]==loc){
dojo["require"]("nls.dojo_"+loc);
return true;
}
}
return false;
});
}
preload();
var _7c=djConfig.extraLocale||[];
for(var i=0;i<_7c.length;i++){
preload(_7c[i]);
}
}
dojo.hostenv.preloadLocalizations=function(){
};
};
dojo.requireLocalization=function(_7e,_7f,_80,_81){
dojo.hostenv.preloadLocalizations();
var _82=dojo.hostenv.normalizeLocale(_80);
var _83=[_7e,"nls",_7f].join(".");
var _84="";
if(_81){
var _85=_81.split(",");
for(var i=0;i<_85.length;i++){
if(_82.indexOf(_85[i])==0){
if(_85[i].length>_84.length){
_84=_85[i];
}
}
}
if(!_84){
_84="ROOT";
}
}
var _87=_81?_84:_82;
var _88=dojo.hostenv.findModule(_83);
var _89=null;
if(_88){
if(djConfig.localizationComplete&&_88._built){
return;
}
var _8a=_87.replace("-","_");
var _8b=_83+"."+_8a;
_89=dojo.hostenv.findModule(_8b);
}
if(!_89){
_88=dojo.hostenv.startPackage(_83);
var _8c=dojo.hostenv.getModuleSymbols(_7e);
var _8d=_8c.concat("nls").join("/");
var _8e;
dojo.hostenv.searchLocalePath(_87,_81,function(loc){
var _90=loc.replace("-","_");
var _91=_83+"."+_90;
var _92=false;
if(!dojo.hostenv.findModule(_91)){
dojo.hostenv.startPackage(_91);
var _93=[_8d];
if(loc!="ROOT"){
_93.push(loc);
}
_93.push(_7f);
var _94=_93.join("/")+".js";
_92=dojo.hostenv.loadPath(_94,null,function(_95){
var _96=function(){
};
_96.prototype=_8e;
_88[_90]=new _96();
for(var j in _95){
_88[_90][j]=_95[j];
}
});
}else{
_92=true;
}
if(_92&&_88[_90]){
_8e=_88[_90];
}else{
_88[_90]=_8e;
}
if(_81){
return true;
}
});
}
if(_81&&_82!=_84){
_88[_82.replace("-","_")]=_88[_84.replace("-","_")];
}
};
(function(){
var _98=djConfig.extraLocale;
if(_98){
if(!_98 instanceof Array){
_98=[_98];
}
var req=dojo.requireLocalization;
dojo.requireLocalization=function(m,b,_9c,_9d){
req(m,b,_9c,_9d);
if(_9c){
return;
}
for(var i=0;i<_98.length;i++){
req(m,b,_98[i],_9d);
}
};
}
})();
}
if(typeof window!="undefined"){
(function(){
if(djConfig.allowQueryConfig){
var _9f=document.location.toString();
var _a0=_9f.split("?",2);
if(_a0.length>1){
var _a1=_a0[1];
var _a2=_a1.split("&");
for(var x in _a2){
var sp=_a2[x].split("=");
if((sp[0].length>9)&&(sp[0].substr(0,9)=="djConfig.")){
var opt=sp[0].substr(9);
try{
djConfig[opt]=eval(sp[1]);
}
catch(e){
djConfig[opt]=sp[1];
}
}
}
}
}
if(((djConfig["baseScriptUri"]=="")||(djConfig["baseRelativePath"]==""))&&(document&&document.getElementsByTagName)){
var _a6=document.getElementsByTagName("script");
var _a7=/(__package__|dojo|bootstrap1)\.js([\?\.]|$)/i;
for(var i=0;i<_a6.length;i++){
var src=_a6[i].getAttribute("src");
if(!src){
continue;
}
var m=src.match(_a7);
if(m){
var _ab=src.substring(0,m.index);
if(src.indexOf("bootstrap1")>-1){
_ab+="../";
}
if(!this["djConfig"]){
djConfig={};
}
if(djConfig["baseScriptUri"]==""){
djConfig["baseScriptUri"]=_ab;
}
if(djConfig["baseRelativePath"]==""){
djConfig["baseRelativePath"]=_ab;
}
break;
}
}
}
var dr=dojo.render;
var drh=dojo.render.html;
var drs=dojo.render.svg;
var dua=(drh.UA=navigator.userAgent);
var dav=(drh.AV=navigator.appVersion);
var t=true;
var f=false;
drh.capable=t;
drh.support.builtin=t;
dr.ver=parseFloat(drh.AV);
dr.os.mac=dav.indexOf("Macintosh")>=0;
dr.os.win=dav.indexOf("Windows")>=0;
dr.os.linux=dav.indexOf("X11")>=0;
drh.opera=dua.indexOf("Opera")>=0;
drh.khtml=(dav.indexOf("Konqueror")>=0)||(dav.indexOf("Safari")>=0);
drh.safari=dav.indexOf("Safari")>=0;
var _b3=dua.indexOf("Gecko");
drh.mozilla=drh.moz=(_b3>=0)&&(!drh.khtml);
if(drh.mozilla){
drh.geckoVersion=dua.substring(_b3+6,_b3+14);
}
drh.ie=(document.all)&&(!drh.opera);
drh.ie50=drh.ie&&dav.indexOf("MSIE 5.0")>=0;
drh.ie55=drh.ie&&dav.indexOf("MSIE 5.5")>=0;
drh.ie60=drh.ie&&dav.indexOf("MSIE 6.0")>=0;
drh.ie70=drh.ie&&dav.indexOf("MSIE 7.0")>=0;
var cm=document["compatMode"];
drh.quirks=(cm=="BackCompat")||(cm=="QuirksMode")||drh.ie55||drh.ie50;
dojo.locale=dojo.locale||(drh.ie?navigator.userLanguage:navigator.language).toLowerCase();
dr.vml.capable=drh.ie;
drs.capable=f;
drs.support.plugin=f;
drs.support.builtin=f;
var _b5=window["document"];
var tdi=_b5["implementation"];
if((tdi)&&(tdi["hasFeature"])&&(tdi.hasFeature("org.w3c.dom.svg","1.0"))){
drs.capable=t;
drs.support.builtin=t;
drs.support.plugin=f;
}
if(drh.safari){
var tmp=dua.split("AppleWebKit/")[1];
var ver=parseFloat(tmp.split(" ")[0]);
if(ver>=420){
drs.capable=t;
drs.support.builtin=t;
drs.support.plugin=f;
}
}else{
}
})();
dojo.hostenv.startPackage("dojo.hostenv");
dojo.render.name=dojo.hostenv.name_="browser";
dojo.hostenv.searchIds=[];
dojo.hostenv._XMLHTTP_PROGIDS=["Msxml2.XMLHTTP","Microsoft.XMLHTTP","Msxml2.XMLHTTP.4.0"];
dojo.hostenv.getXmlhttpObject=function(){
var _b9=null;
var _ba=null;
try{
_b9=new XMLHttpRequest();
}
catch(e){
}
if(!_b9){
for(var i=0;i<3;++i){
var _bc=dojo.hostenv._XMLHTTP_PROGIDS[i];
try{
_b9=new ActiveXObject(_bc);
}
catch(e){
_ba=e;
}
if(_b9){
dojo.hostenv._XMLHTTP_PROGIDS=[_bc];
break;
}
}
}
if(!_b9){
return dojo.raise("XMLHTTP not available",_ba);
}
return _b9;
};
dojo.hostenv._blockAsync=false;
dojo.hostenv.getText=function(uri,_be,_bf){
if(!_be){
this._blockAsync=true;
}
var _c0=this.getXmlhttpObject();
function isDocumentOk(_c1){
var _c2=_c1["status"];
return Boolean((!_c2)||((200<=_c2)&&(300>_c2))||(_c2==304));
}
if(_be){
var _c3=this,_c4=null,gbl=dojo.global();
var xhr=dojo.evalObjPath("dojo.io.XMLHTTPTransport");
_c0.onreadystatechange=function(){
if(_c4){
gbl.clearTimeout(_c4);
_c4=null;
}
if(_c3._blockAsync||(xhr&&xhr._blockAsync)){
_c4=gbl.setTimeout(function(){
_c0.onreadystatechange.apply(this);
},10);
}else{
if(4==_c0.readyState){
if(isDocumentOk(_c0)){
_be(_c0.responseText);
}
}
}
};
}
_c0.open("GET",uri,_be?true:false);
try{
_c0.send(null);
if(_be){
return null;
}
if(!isDocumentOk(_c0)){
var err=Error("Unable to load "+uri+" status:"+_c0.status);
err.status=_c0.status;
err.responseText=_c0.responseText;
throw err;
}
}
catch(e){
this._blockAsync=false;
if((_bf)&&(!_be)){
return null;
}else{
throw e;
}
}
this._blockAsync=false;
return _c0.responseText;
};
dojo.hostenv.defaultDebugContainerId="dojoDebug";
dojo.hostenv._println_buffer=[];
dojo.hostenv._println_safe=false;
dojo.hostenv.println=function(_c8){
if(!dojo.hostenv._println_safe){
dojo.hostenv._println_buffer.push(_c8);
}else{
try{
var _c9=document.getElementById(djConfig.debugContainerId?djConfig.debugContainerId:dojo.hostenv.defaultDebugContainerId);
if(!_c9){
_c9=dojo.body();
}
var div=document.createElement("div");
div.appendChild(document.createTextNode(_c8));
_c9.appendChild(div);
}
catch(e){
try{
document.write("<div>"+_c8+"</div>");
}
catch(e2){
window.status=_c8;
}
}
}
};
dojo.addOnLoad(function(){
dojo.hostenv._println_safe=true;
while(dojo.hostenv._println_buffer.length>0){
dojo.hostenv.println(dojo.hostenv._println_buffer.shift());
}
});
function dj_addNodeEvtHdlr(_cb,_cc,fp){
var _ce=_cb["on"+_cc]||function(){
};
_cb["on"+_cc]=function(){
fp.apply(_cb,arguments);
_ce.apply(_cb,arguments);
};
return true;
}
function dj_load_init(e){
var _d0=(e&&e.type)?e.type.toLowerCase():"load";
if(arguments.callee.initialized||(_d0!="domcontentloaded"&&_d0!="load")){
return;
}
arguments.callee.initialized=true;
if(typeof (_timer)!="undefined"){
clearInterval(_timer);
delete _timer;
}
var _d1=function(){
if(dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
};
if(dojo.hostenv.inFlightCount==0){
_d1();
dojo.hostenv.modulesLoaded();
}else{
dojo.hostenv.modulesLoadedListeners.unshift(_d1);
}
}
if(document.addEventListener){
if(dojo.render.html.opera||(dojo.render.html.moz&&!djConfig.delayMozLoadingFix)){
document.addEventListener("DOMContentLoaded",dj_load_init,null);
}
window.addEventListener("load",dj_load_init,null);
}
if(dojo.render.html.ie&&dojo.render.os.win){
document.attachEvent("onreadystatechange",function(e){
if(document.readyState=="complete"){
dj_load_init();
}
});
}
if(/(WebKit|khtml)/i.test(navigator.userAgent)){
var _timer=setInterval(function(){
if(/loaded|complete/.test(document.readyState)){
dj_load_init();
}
},10);
}
if(dojo.render.html.ie){
dj_addNodeEvtHdlr(window,"beforeunload",function(){
dojo.hostenv._unloading=true;
window.setTimeout(function(){
dojo.hostenv._unloading=false;
},0);
});
}
dj_addNodeEvtHdlr(window,"unload",function(){
dojo.hostenv.unloaded();
if((!dojo.render.html.ie)||(dojo.render.html.ie&&dojo.hostenv._unloading)){
dojo.hostenv.unloaded();
}
});
dojo.hostenv.makeWidgets=function(){
var _d3=[];
if(djConfig.searchIds&&djConfig.searchIds.length>0){
_d3=_d3.concat(djConfig.searchIds);
}
if(dojo.hostenv.searchIds&&dojo.hostenv.searchIds.length>0){
_d3=_d3.concat(dojo.hostenv.searchIds);
}
if((djConfig.parseWidgets)||(_d3.length>0)){
if(dojo.evalObjPath("dojo.widget.Parse")){
var _d4=new dojo.xml.Parse();
if(_d3.length>0){
for(var x=0;x<_d3.length;x++){
var _d6=document.getElementById(_d3[x]);
if(!_d6){
continue;
}
var _d7=_d4.parseElement(_d6,null,true);
dojo.widget.getParser().createComponents(_d7);
}
}else{
if(djConfig.parseWidgets){
var _d7=_d4.parseElement(dojo.body(),null,true);
dojo.widget.getParser().createComponents(_d7);
}
}
}
}
};
dojo.addOnLoad(function(){
if(!dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
});
try{
if(dojo.render.html.ie){
document.namespaces.add("v","urn:schemas-microsoft-com:vml");
document.createStyleSheet().addRule("v\\:*","behavior:url(#default#VML)");
}
}
catch(e){
}
dojo.hostenv.writeIncludes=function(){
};
if(!dj_undef("document",this)){
dj_currentDocument=this.document;
}
dojo.doc=function(){
return dj_currentDocument;
};
dojo.body=function(){
return dojo.doc().body||dojo.doc().getElementsByTagName("body")[0];
};
dojo.byId=function(id,doc){
if((id)&&((typeof id=="string")||(id instanceof String))){
if(!doc){
doc=dj_currentDocument;
}
var ele=doc.getElementById(id);
if(ele&&(ele.id!=id)&&doc.all){
ele=null;
eles=doc.all[id];
if(eles){
if(eles.length){
for(var i=0;i<eles.length;i++){
if(eles[i].id==id){
ele=eles[i];
break;
}
}
}else{
ele=eles;
}
}
}
return ele;
}
return id;
};
dojo.setContext=function(_dc,_dd){
dj_currentContext=_dc;
dj_currentDocument=_dd;
};
dojo._fireCallback=function(_de,_df,_e0){
if((_df)&&((typeof _de=="string")||(_de instanceof String))){
_de=_df[_de];
}
return (_df?_de.apply(_df,_e0||[]):_de());
};
dojo.withGlobal=function(_e1,_e2,_e3,_e4){
var _e5;
var _e6=dj_currentContext;
var _e7=dj_currentDocument;
try{
dojo.setContext(_e1,_e1.document);
_e5=dojo._fireCallback(_e2,_e3,_e4);
}
finally{
dojo.setContext(_e6,_e7);
}
return _e5;
};
dojo.withDoc=function(_e8,_e9,_ea,_eb){
var _ec;
var _ed=dj_currentDocument;
try{
dj_currentDocument=_e8;
_ec=dojo._fireCallback(_e9,_ea,_eb);
}
finally{
dj_currentDocument=_ed;
}
return _ec;
};
}
(function(){
if(typeof dj_usingBootstrap!="undefined"){
return;
}
var _ee=false;
var _ef=false;
var _f0=false;
if((typeof this["load"]=="function")&&((typeof this["Packages"]=="function")||(typeof this["Packages"]=="object"))){
_ee=true;
}else{
if(typeof this["load"]=="function"){
_ef=true;
}else{
if(window.widget){
_f0=true;
}
}
}
var _f1=[];
if((this["djConfig"])&&((djConfig["isDebug"])||(djConfig["debugAtAllCosts"]))){
_f1.push("debug.js");
}
if((this["djConfig"])&&(djConfig["debugAtAllCosts"])&&(!_ee)&&(!_f0)){
_f1.push("browser_debug.js");
}
var _f2=djConfig["baseScriptUri"];
if((this["djConfig"])&&(djConfig["baseLoaderUri"])){
_f2=djConfig["baseLoaderUri"];
}
for(var x=0;x<_f1.length;x++){
var _f4=_f2+"src/"+_f1[x];
if(_ee||_ef){
load(_f4);
}else{
try{
document.write("<scr"+"ipt type='text/javascript' src='"+_f4+"'></scr"+"ipt>");
}
catch(e){
var _f5=document.createElement("script");
_f5.src=_f4;
document.getElementsByTagName("head")[0].appendChild(_f5);
}
}
}
})();
dojo.provide("dojo.lang.common");
dojo.lang.inherits=function(_f6,_f7){
if(!dojo.lang.isFunction(_f7)){
dojo.raise("dojo.inherits: superclass argument ["+_f7+"] must be a function (subclass: ["+_f6+"']");
}
_f6.prototype=new _f7();
_f6.prototype.constructor=_f6;
_f6.superclass=_f7.prototype;
_f6["super"]=_f7.prototype;
};
dojo.lang._mixin=function(obj,_f9){
var _fa={};
for(var x in _f9){
if((typeof _fa[x]=="undefined")||(_fa[x]!=_f9[x])){
obj[x]=_f9[x];
}
}
if(dojo.render.html.ie&&(typeof (_f9["toString"])=="function")&&(_f9["toString"]!=obj["toString"])&&(_f9["toString"]!=_fa["toString"])){
obj.toString=_f9.toString;
}
return obj;
};
dojo.lang.mixin=function(obj,_fd){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(obj,arguments[i]);
}
return obj;
};
dojo.lang.extend=function(_100,_101){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(_100.prototype,arguments[i]);
}
return _100;
};
dojo.inherits=dojo.lang.inherits;
dojo.mixin=dojo.lang.mixin;
dojo.extend=dojo.lang.extend;
dojo.lang.find=function(_104,_105,_106,_107){
if(!dojo.lang.isArrayLike(_104)&&dojo.lang.isArrayLike(_105)){
dojo.deprecated("dojo.lang.find(value, array)","use dojo.lang.find(array, value) instead","0.5");
var temp=_104;
_104=_105;
_105=temp;
}
var _109=dojo.lang.isString(_104);
if(_109){
_104=_104.split("");
}
if(_107){
var step=-1;
var i=_104.length-1;
var end=-1;
}else{
var step=1;
var i=0;
var end=_104.length;
}
if(_106){
while(i!=end){
if(_104[i]===_105){
return i;
}
i+=step;
}
}else{
while(i!=end){
if(_104[i]==_105){
return i;
}
i+=step;
}
}
return -1;
};
dojo.lang.indexOf=dojo.lang.find;
dojo.lang.findLast=function(_10d,_10e,_10f){
return dojo.lang.find(_10d,_10e,_10f,true);
};
dojo.lang.lastIndexOf=dojo.lang.findLast;
dojo.lang.inArray=function(_110,_111){
return dojo.lang.find(_110,_111)>-1;
};
dojo.lang.isObject=function(it){
if(typeof it=="undefined"){
return false;
}
return (typeof it=="object"||it===null||dojo.lang.isArray(it)||dojo.lang.isFunction(it));
};
dojo.lang.isArray=function(it){
return (it&&it instanceof Array||typeof it=="array");
};
dojo.lang.isArrayLike=function(it){
if((!it)||(dojo.lang.isUndefined(it))){
return false;
}
if(dojo.lang.isString(it)){
return false;
}
if(dojo.lang.isFunction(it)){
return false;
}
if(dojo.lang.isArray(it)){
return true;
}
if((it.tagName)&&(it.tagName.toLowerCase()=="form")){
return false;
}
if(dojo.lang.isNumber(it.length)&&isFinite(it.length)){
return true;
}
return false;
};
dojo.lang.isFunction=function(it){
return (it instanceof Function||typeof it=="function");
};
(function(){
if((dojo.render.html.capable)&&(dojo.render.html["safari"])){
dojo.lang.isFunction=function(it){
if((typeof (it)=="function")&&(it=="[object NodeList]")){
return false;
}
return (it instanceof Function||typeof it=="function");
};
}
})();
dojo.lang.isString=function(it){
return (typeof it=="string"||it instanceof String);
};
dojo.lang.isAlien=function(it){
if(!it){
return false;
}
return !dojo.lang.isFunction(it)&&/\{\s*\[native code\]\s*\}/.test(String(it));
};
dojo.lang.isBoolean=function(it){
return (it instanceof Boolean||typeof it=="boolean");
};
dojo.lang.isNumber=function(it){
return (it instanceof Number||typeof it=="number");
};
dojo.lang.isUndefined=function(it){
return ((typeof (it)=="undefined")&&(it==undefined));
};
dojo.provide("dojo.lang");
dojo.deprecated("dojo.lang","replaced by dojo.lang.common","0.5");
dojo.provide("dojo.dom");
dojo.dom.ELEMENT_NODE=1;
dojo.dom.ATTRIBUTE_NODE=2;
dojo.dom.TEXT_NODE=3;
dojo.dom.CDATA_SECTION_NODE=4;
dojo.dom.ENTITY_REFERENCE_NODE=5;
dojo.dom.ENTITY_NODE=6;
dojo.dom.PROCESSING_INSTRUCTION_NODE=7;
dojo.dom.COMMENT_NODE=8;
dojo.dom.DOCUMENT_NODE=9;
dojo.dom.DOCUMENT_TYPE_NODE=10;
dojo.dom.DOCUMENT_FRAGMENT_NODE=11;
dojo.dom.NOTATION_NODE=12;
dojo.dom.dojoml="http://www.dojotoolkit.org/2004/dojoml";
dojo.dom.xmlns={svg:"http://www.w3.org/2000/svg",smil:"http://www.w3.org/2001/SMIL20/",mml:"http://www.w3.org/1998/Math/MathML",cml:"http://www.xml-cml.org",xlink:"http://www.w3.org/1999/xlink",xhtml:"http://www.w3.org/1999/xhtml",xul:"http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul",xbl:"http://www.mozilla.org/xbl",fo:"http://www.w3.org/1999/XSL/Format",xsl:"http://www.w3.org/1999/XSL/Transform",xslt:"http://www.w3.org/1999/XSL/Transform",xi:"http://www.w3.org/2001/XInclude",xforms:"http://www.w3.org/2002/01/xforms",saxon:"http://icl.com/saxon",xalan:"http://xml.apache.org/xslt",xsd:"http://www.w3.org/2001/XMLSchema",dt:"http://www.w3.org/2001/XMLSchema-datatypes",xsi:"http://www.w3.org/2001/XMLSchema-instance",rdf:"http://www.w3.org/1999/02/22-rdf-syntax-ns#",rdfs:"http://www.w3.org/2000/01/rdf-schema#",dc:"http://purl.org/dc/elements/1.1/",dcq:"http://purl.org/dc/qualifiers/1.0","soap-env":"http://schemas.xmlsoap.org/soap/envelope/",wsdl:"http://schemas.xmlsoap.org/wsdl/",AdobeExtensions:"http://ns.adobe.com/AdobeSVGViewerExtensions/3.0/"};
dojo.dom.isNode=function(wh){
if(typeof Element=="function"){
try{
return wh instanceof Element;
}
catch(e){
}
}else{
return wh&&!isNaN(wh.nodeType);
}
};
dojo.dom.getUniqueId=function(){
var _11d=dojo.doc();
do{
var id="dj_unique_"+(++arguments.callee._idIncrement);
}while(_11d.getElementById(id));
return id;
};
dojo.dom.getUniqueId._idIncrement=0;
dojo.dom.firstElement=dojo.dom.getFirstChildElement=function(_11f,_120){
var node=_11f.firstChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.nextSibling;
}
if(_120&&node&&node.tagName&&node.tagName.toLowerCase()!=_120.toLowerCase()){
node=dojo.dom.nextElement(node,_120);
}
return node;
};
dojo.dom.lastElement=dojo.dom.getLastChildElement=function(_122,_123){
var node=_122.lastChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.previousSibling;
}
if(_123&&node&&node.tagName&&node.tagName.toLowerCase()!=_123.toLowerCase()){
node=dojo.dom.prevElement(node,_123);
}
return node;
};
dojo.dom.nextElement=dojo.dom.getNextSiblingElement=function(node,_126){
if(!node){
return null;
}
do{
node=node.nextSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_126&&_126.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.nextElement(node,_126);
}
return node;
};
dojo.dom.prevElement=dojo.dom.getPreviousSiblingElement=function(node,_128){
if(!node){
return null;
}
if(_128){
_128=_128.toLowerCase();
}
do{
node=node.previousSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_128&&_128.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.prevElement(node,_128);
}
return node;
};
dojo.dom.moveChildren=function(_129,_12a,trim){
var _12c=0;
if(trim){
while(_129.hasChildNodes()&&_129.firstChild.nodeType==dojo.dom.TEXT_NODE){
_129.removeChild(_129.firstChild);
}
while(_129.hasChildNodes()&&_129.lastChild.nodeType==dojo.dom.TEXT_NODE){
_129.removeChild(_129.lastChild);
}
}
while(_129.hasChildNodes()){
_12a.appendChild(_129.firstChild);
_12c++;
}
return _12c;
};
dojo.dom.copyChildren=function(_12d,_12e,trim){
var _130=_12d.cloneNode(true);
return this.moveChildren(_130,_12e,trim);
};
dojo.dom.replaceChildren=function(node,_132){
var _133=[];
if(dojo.render.html.ie){
for(var i=0;i<node.childNodes.length;i++){
_133.push(node.childNodes[i]);
}
}
dojo.dom.removeChildren(node);
node.appendChild(_132);
for(var i=0;i<_133.length;i++){
dojo.dom.destroyNode(_133[i]);
}
};
dojo.dom.removeChildren=function(node){
var _136=node.childNodes.length;
while(node.hasChildNodes()){
dojo.dom.removeNode(node.firstChild);
}
return _136;
};
dojo.dom.replaceNode=function(node,_138){
return node.parentNode.replaceChild(_138,node);
};
dojo.dom.destroyNode=function(node){
if(node.parentNode){
node=dojo.dom.removeNode(node);
}
if(node.nodeType!=3){
if(dojo.evalObjPath("dojo.event.browser.clean",false)){
dojo.event.browser.clean(node);
}
if(dojo.render.html.ie){
node.outerHTML="";
}
}
};
dojo.dom.removeNode=function(node){
if(node&&node.parentNode){
return node.parentNode.removeChild(node);
}
};
dojo.dom.getAncestors=function(node,_13c,_13d){
var _13e=[];
var _13f=(_13c&&(_13c instanceof Function||typeof _13c=="function"));
while(node){
if(!_13f||_13c(node)){
_13e.push(node);
}
if(_13d&&_13e.length>0){
return _13e[0];
}
node=node.parentNode;
}
if(_13d){
return null;
}
return _13e;
};
dojo.dom.getAncestorsByTag=function(node,tag,_142){
tag=tag.toLowerCase();
return dojo.dom.getAncestors(node,function(el){
return ((el.tagName)&&(el.tagName.toLowerCase()==tag));
},_142);
};
dojo.dom.getFirstAncestorByTag=function(node,tag){
return dojo.dom.getAncestorsByTag(node,tag,true);
};
dojo.dom.isDescendantOf=function(node,_147,_148){
if(_148&&node){
node=node.parentNode;
}
while(node){
if(node==_147){
return true;
}
node=node.parentNode;
}
return false;
};
dojo.dom.innerXML=function(node){
if(node.innerXML){
return node.innerXML;
}else{
if(node.xml){
return node.xml;
}else{
if(typeof XMLSerializer!="undefined"){
return (new XMLSerializer()).serializeToString(node);
}
}
}
};
dojo.dom.createDocument=function(){
var doc=null;
var _14b=dojo.doc();
if(!dj_undef("ActiveXObject")){
var _14c=["MSXML2","Microsoft","MSXML","MSXML3"];
for(var i=0;i<_14c.length;i++){
try{
doc=new ActiveXObject(_14c[i]+".XMLDOM");
}
catch(e){
}
if(doc){
break;
}
}
}else{
if((_14b.implementation)&&(_14b.implementation.createDocument)){
doc=_14b.implementation.createDocument("","",null);
}
}
return doc;
};
dojo.dom.createDocumentFromText=function(str,_14f){
if(!_14f){
_14f="text/xml";
}
if(!dj_undef("DOMParser")){
var _150=new DOMParser();
return _150.parseFromString(str,_14f);
}else{
if(!dj_undef("ActiveXObject")){
var _151=dojo.dom.createDocument();
if(_151){
_151.async=false;
_151.loadXML(str);
return _151;
}else{
dojo.debug("toXml didn't work?");
}
}else{
var _152=dojo.doc();
if(_152.createElement){
var tmp=_152.createElement("xml");
tmp.innerHTML=str;
if(_152.implementation&&_152.implementation.createDocument){
var _154=_152.implementation.createDocument("foo","",null);
for(var i=0;i<tmp.childNodes.length;i++){
_154.importNode(tmp.childNodes.item(i),true);
}
return _154;
}
return ((tmp.document)&&(tmp.document.firstChild?tmp.document.firstChild:tmp));
}
}
}
return null;
};
dojo.dom.prependChild=function(node,_157){
if(_157.firstChild){
_157.insertBefore(node,_157.firstChild);
}else{
_157.appendChild(node);
}
return true;
};
dojo.dom.insertBefore=function(node,ref,_15a){
if((_15a!=true)&&(node===ref||node.nextSibling===ref)){
return false;
}
var _15b=ref.parentNode;
_15b.insertBefore(node,ref);
return true;
};
dojo.dom.insertAfter=function(node,ref,_15e){
var pn=ref.parentNode;
if(ref==pn.lastChild){
if((_15e!=true)&&(node===ref)){
return false;
}
pn.appendChild(node);
}else{
return this.insertBefore(node,ref.nextSibling,_15e);
}
return true;
};
dojo.dom.insertAtPosition=function(node,ref,_162){
if((!node)||(!ref)||(!_162)){
return false;
}
switch(_162.toLowerCase()){
case "before":
return dojo.dom.insertBefore(node,ref);
case "after":
return dojo.dom.insertAfter(node,ref);
case "first":
if(ref.firstChild){
return dojo.dom.insertBefore(node,ref.firstChild);
}else{
ref.appendChild(node);
return true;
}
break;
default:
ref.appendChild(node);
return true;
}
};
dojo.dom.insertAtIndex=function(node,_164,_165){
var _166=_164.childNodes;
if(!_166.length||_166.length==_165){
_164.appendChild(node);
return true;
}
if(_165==0){
return dojo.dom.prependChild(node,_164);
}
return dojo.dom.insertAfter(node,_166[_165-1]);
};
dojo.dom.textContent=function(node,text){
if(arguments.length>1){
var _169=dojo.doc();
dojo.dom.replaceChildren(node,_169.createTextNode(text));
return text;
}else{
if(node.textContent!=undefined){
return node.textContent;
}
var _16a="";
if(node==null){
return _16a;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
_16a+=dojo.dom.textContent(node.childNodes[i]);
break;
case 3:
case 2:
case 4:
_16a+=node.childNodes[i].nodeValue;
break;
default:
break;
}
}
return _16a;
}
};
dojo.dom.hasParent=function(node){
return Boolean(node&&node.parentNode&&dojo.dom.isNode(node.parentNode));
};
dojo.dom.isTag=function(node){
if(node&&node.tagName){
for(var i=1;i<arguments.length;i++){
if(node.tagName==String(arguments[i])){
return String(arguments[i]);
}
}
}
return "";
};
dojo.dom.setAttributeNS=function(elem,_170,_171,_172){
if(elem==null||((elem==undefined)&&(typeof elem=="undefined"))){
dojo.raise("No element given to dojo.dom.setAttributeNS");
}
if(!((elem.setAttributeNS==undefined)&&(typeof elem.setAttributeNS=="undefined"))){
elem.setAttributeNS(_170,_171,_172);
}else{
var _173=elem.ownerDocument;
var _174=_173.createNode(2,_171,_170);
_174.nodeValue=_172;
elem.setAttributeNode(_174);
}
};
dojo.provide("dojo.html.common");
dojo.lang.mixin(dojo.html,dojo.dom);
dojo.html.body=function(){
dojo.deprecated("dojo.html.body() moved to dojo.body()","0.5");
return dojo.body();
};
dojo.html.getEventTarget=function(evt){
if(!evt){
evt=dojo.global().event||{};
}
var t=(evt.srcElement?evt.srcElement:(evt.target?evt.target:null));
while((t)&&(t.nodeType!=1)){
t=t.parentNode;
}
return t;
};
dojo.html.getViewport=function(){
var _177=dojo.global();
var _178=dojo.doc();
var w=0;
var h=0;
if(dojo.render.html.mozilla){
w=_178.documentElement.clientWidth;
h=_177.innerHeight;
}else{
if(!dojo.render.html.opera&&_177.innerWidth){
w=_177.innerWidth;
h=_177.innerHeight;
}else{
if(!dojo.render.html.opera&&dojo.exists(_178,"documentElement.clientWidth")){
var w2=_178.documentElement.clientWidth;
if(!w||w2&&w2<w){
w=w2;
}
h=_178.documentElement.clientHeight;
}else{
if(dojo.body().clientWidth){
w=dojo.body().clientWidth;
h=dojo.body().clientHeight;
}
}
}
}
return {width:w,height:h};
};
dojo.html.getScroll=function(){
var _17c=dojo.global();
var _17d=dojo.doc();
var top=_17c.pageYOffset||_17d.documentElement.scrollTop||dojo.body().scrollTop||0;
var left=_17c.pageXOffset||_17d.documentElement.scrollLeft||dojo.body().scrollLeft||0;
return {top:top,left:left,offset:{x:left,y:top}};
};
dojo.html.getParentByType=function(node,type){
var _182=dojo.doc();
var _183=dojo.byId(node);
type=type.toLowerCase();
while((_183)&&(_183.nodeName.toLowerCase()!=type)){
if(_183==(_182["body"]||_182["documentElement"])){
return null;
}
_183=_183.parentNode;
}
return _183;
};
dojo.html.getAttribute=function(node,attr){
node=dojo.byId(node);
if((!node)||(!node.getAttribute)){
return null;
}
var ta=typeof attr=="string"?attr:new String(attr);
var v=node.getAttribute(ta.toUpperCase());
if((v)&&(typeof v=="string")&&(v!="")){
return v;
}
if(v&&v.value){
return v.value;
}
if((node.getAttributeNode)&&(node.getAttributeNode(ta))){
return (node.getAttributeNode(ta)).value;
}else{
if(node.getAttribute(ta)){
return node.getAttribute(ta);
}else{
if(node.getAttribute(ta.toLowerCase())){
return node.getAttribute(ta.toLowerCase());
}
}
}
return null;
};
dojo.html.hasAttribute=function(node,attr){
return dojo.html.getAttribute(dojo.byId(node),attr)?true:false;
};
dojo.html.getCursorPosition=function(e){
e=e||dojo.global().event;
var _18b={x:0,y:0};
if(e.pageX||e.pageY){
_18b.x=e.pageX;
_18b.y=e.pageY;
}else{
var de=dojo.doc().documentElement;
var db=dojo.body();
_18b.x=e.clientX+((de||db)["scrollLeft"])-((de||db)["clientLeft"]);
_18b.y=e.clientY+((de||db)["scrollTop"])-((de||db)["clientTop"]);
}
return _18b;
};
dojo.html.isTag=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
for(var i=1;i<arguments.length;i++){
if(node.tagName.toLowerCase()==String(arguments[i]).toLowerCase()){
return String(arguments[i]).toLowerCase();
}
}
}
return "";
};
if(dojo.render.html.ie&&!dojo.render.html.ie70){
if(window.location.href.substr(0,6).toLowerCase()!="https:"){
(function(){
var _190=dojo.doc().createElement("script");
_190.src="javascript:'dojo.html.createExternalElement=function(doc, tag){ return doc.createElement(tag); }'";
dojo.doc().getElementsByTagName("head")[0].appendChild(_190);
})();
}
}else{
dojo.html.createExternalElement=function(doc,tag){
return doc.createElement(tag);
};
}
dojo.html._callDeprecated=function(_193,_194,args,_196,_197){
dojo.deprecated("dojo.html."+_193,"replaced by dojo.html."+_194+"("+(_196?"node, {"+_196+": "+_196+"}":"")+")"+(_197?"."+_197:""),"0.5");
var _198=[];
if(_196){
var _199={};
_199[_196]=args[1];
_198.push(args[0]);
_198.push(_199);
}else{
_198=args;
}
var ret=dojo.html[_194].apply(dojo.html,args);
if(_197){
return ret[_197];
}else{
return ret;
}
};
dojo.html.getViewportWidth=function(){
return dojo.html._callDeprecated("getViewportWidth","getViewport",arguments,null,"width");
};
dojo.html.getViewportHeight=function(){
return dojo.html._callDeprecated("getViewportHeight","getViewport",arguments,null,"height");
};
dojo.html.getViewportSize=function(){
return dojo.html._callDeprecated("getViewportSize","getViewport",arguments);
};
dojo.html.getScrollTop=function(){
return dojo.html._callDeprecated("getScrollTop","getScroll",arguments,null,"top");
};
dojo.html.getScrollLeft=function(){
return dojo.html._callDeprecated("getScrollLeft","getScroll",arguments,null,"left");
};
dojo.html.getScrollOffset=function(){
return dojo.html._callDeprecated("getScrollOffset","getScroll",arguments,null,"offset");
};
dojo.provide("dojo.uri.Uri");
dojo.uri=new function(){
this.dojoUri=function(uri){
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri(),uri);
};
this.moduleUri=function(_19c,uri){
var loc=dojo.hostenv.getModuleSymbols(_19c).join("/");
if(!loc){
return null;
}
if(loc.lastIndexOf("/")!=loc.length-1){
loc+="/";
}
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri()+loc,uri);
};
this.Uri=function(){
var uri=arguments[0];
for(var i=1;i<arguments.length;i++){
if(!arguments[i]){
continue;
}
var _1a1=new dojo.uri.Uri(arguments[i].toString());
var _1a2=new dojo.uri.Uri(uri.toString());
if((_1a1.path=="")&&(_1a1.scheme==null)&&(_1a1.authority==null)&&(_1a1.query==null)){
if(_1a1.fragment!=null){
_1a2.fragment=_1a1.fragment;
}
_1a1=_1a2;
}else{
if(_1a1.scheme==null){
_1a1.scheme=_1a2.scheme;
if(_1a1.authority==null){
_1a1.authority=_1a2.authority;
if(_1a1.path.charAt(0)!="/"){
var path=_1a2.path.substring(0,_1a2.path.lastIndexOf("/")+1)+_1a1.path;
var segs=path.split("/");
for(var j=0;j<segs.length;j++){
if(segs[j]=="."){
if(j==segs.length-1){
segs[j]="";
}else{
segs.splice(j,1);
j--;
}
}else{
if(j>0&&!(j==1&&segs[0]=="")&&segs[j]==".."&&segs[j-1]!=".."){
if(j==segs.length-1){
segs.splice(j,1);
segs[j-1]="";
}else{
segs.splice(j-1,2);
j-=2;
}
}
}
}
_1a1.path=segs.join("/");
}
}
}
}
uri="";
if(_1a1.scheme!=null){
uri+=_1a1.scheme+":";
}
if(_1a1.authority!=null){
uri+="//"+_1a1.authority;
}
uri+=_1a1.path;
if(_1a1.query!=null){
uri+="?"+_1a1.query;
}
if(_1a1.fragment!=null){
uri+="#"+_1a1.fragment;
}
}
this.uri=uri.toString();
var _1a6="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";
var r=this.uri.match(new RegExp(_1a6));
this.scheme=r[2]||(r[1]?"":null);
this.authority=r[4]||(r[3]?"":null);
this.path=r[5];
this.query=r[7]||(r[6]?"":null);
this.fragment=r[9]||(r[8]?"":null);
if(this.authority!=null){
_1a6="^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$";
r=this.authority.match(new RegExp(_1a6));
this.user=r[3]||null;
this.password=r[4]||null;
this.host=r[5];
this.port=r[7]||null;
}
this.toString=function(){
return this.uri;
};
};
};
dojo.provide("dojo.html.style");
dojo.html.getClass=function(node){
node=dojo.byId(node);
if(!node){
return "";
}
var cs="";
if(node.className){
cs=node.className;
}else{
if(dojo.html.hasAttribute(node,"class")){
cs=dojo.html.getAttribute(node,"class");
}
}
return cs.replace(/^\s+|\s+$/g,"");
};
dojo.html.getClasses=function(node){
var c=dojo.html.getClass(node);
return (c=="")?[]:c.split(/\s+/g);
};
dojo.html.hasClass=function(node,_1ad){
return (new RegExp("(^|\\s+)"+_1ad+"(\\s+|$)")).test(dojo.html.getClass(node));
};
dojo.html.prependClass=function(node,_1af){
_1af+=" "+dojo.html.getClass(node);
return dojo.html.setClass(node,_1af);
};
dojo.html.addClass=function(node,_1b1){
if(dojo.html.hasClass(node,_1b1)){
return false;
}
_1b1=(dojo.html.getClass(node)+" "+_1b1).replace(/^\s+|\s+$/g,"");
return dojo.html.setClass(node,_1b1);
};
dojo.html.setClass=function(node,_1b3){
node=dojo.byId(node);
var cs=new String(_1b3);
try{
if(typeof node.className=="string"){
node.className=cs;
}else{
if(node.setAttribute){
node.setAttribute("class",_1b3);
node.className=cs;
}else{
return false;
}
}
}
catch(e){
dojo.debug("dojo.html.setClass() failed",e);
}
return true;
};
dojo.html.removeClass=function(node,_1b6,_1b7){
try{
if(!_1b7){
var _1b8=dojo.html.getClass(node).replace(new RegExp("(^|\\s+)"+_1b6+"(\\s+|$)"),"$1$2");
}else{
var _1b8=dojo.html.getClass(node).replace(_1b6,"");
}
dojo.html.setClass(node,_1b8);
}
catch(e){
dojo.debug("dojo.html.removeClass() failed",e);
}
return true;
};
dojo.html.replaceClass=function(node,_1ba,_1bb){
dojo.html.removeClass(node,_1bb);
dojo.html.addClass(node,_1ba);
};
dojo.html.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
dojo.html.getElementsByClass=function(_1bc,_1bd,_1be,_1bf,_1c0){
_1c0=false;
var _1c1=dojo.doc();
_1bd=dojo.byId(_1bd)||_1c1;
var _1c2=_1bc.split(/\s+/g);
var _1c3=[];
if(_1bf!=1&&_1bf!=2){
_1bf=0;
}
var _1c4=new RegExp("(\\s|^)(("+_1c2.join(")|(")+"))(\\s|$)");
var _1c5=_1c2.join(" ").length;
var _1c6=[];
if(!_1c0&&_1c1.evaluate){
var _1c7=".//"+(_1be||"*")+"[contains(";
if(_1bf!=dojo.html.classMatchType.ContainsAny){
_1c7+="concat(' ',@class,' '), ' "+_1c2.join(" ') and contains(concat(' ',@class,' '), ' ")+" ')";
if(_1bf==2){
_1c7+=" and string-length(@class)="+_1c5+"]";
}else{
_1c7+="]";
}
}else{
_1c7+="concat(' ',@class,' '), ' "+_1c2.join(" ') or contains(concat(' ',@class,' '), ' ")+" ')]";
}
var _1c8=_1c1.evaluate(_1c7,_1bd,null,XPathResult.ANY_TYPE,null);
var _1c9=_1c8.iterateNext();
while(_1c9){
try{
_1c6.push(_1c9);
_1c9=_1c8.iterateNext();
}
catch(e){
break;
}
}
return _1c6;
}else{
if(!_1be){
_1be="*";
}
_1c6=_1bd.getElementsByTagName(_1be);
var node,i=0;
outer:
while(node=_1c6[i++]){
var _1cc=dojo.html.getClasses(node);
if(_1cc.length==0){
continue outer;
}
var _1cd=0;
for(var j=0;j<_1cc.length;j++){
if(_1c4.test(_1cc[j])){
if(_1bf==dojo.html.classMatchType.ContainsAny){
_1c3.push(node);
continue outer;
}else{
_1cd++;
}
}else{
if(_1bf==dojo.html.classMatchType.IsOnly){
continue outer;
}
}
}
if(_1cd==_1c2.length){
if((_1bf==dojo.html.classMatchType.IsOnly)&&(_1cd==_1cc.length)){
_1c3.push(node);
}else{
if(_1bf==dojo.html.classMatchType.ContainsAll){
_1c3.push(node);
}
}
}
}
return _1c3;
}
};
dojo.html.getElementsByClassName=dojo.html.getElementsByClass;
dojo.html.toCamelCase=function(_1cf){
var arr=_1cf.split("-"),cc=arr[0];
for(var i=1;i<arr.length;i++){
cc+=arr[i].charAt(0).toUpperCase()+arr[i].substring(1);
}
return cc;
};
dojo.html.toSelectorCase=function(_1d3){
return _1d3.replace(/([A-Z])/g,"-$1").toLowerCase();
};
dojo.html.getComputedStyle=function(node,_1d5,_1d6){
node=dojo.byId(node);
var _1d5=dojo.html.toSelectorCase(_1d5);
var _1d7=dojo.html.toCamelCase(_1d5);
if(!node||!node.style){
return _1d6;
}else{
if(document.defaultView&&dojo.html.isDescendantOf(node,node.ownerDocument)){
try{
var cs=document.defaultView.getComputedStyle(node,"");
if(cs){
return cs.getPropertyValue(_1d5);
}
}
catch(e){
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_1d5);
}else{
return _1d6;
}
}
}else{
if(node.currentStyle){
return node.currentStyle[_1d7];
}
}
}
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_1d5);
}else{
return _1d6;
}
};
dojo.html.getStyleProperty=function(node,_1da){
node=dojo.byId(node);
return (node&&node.style?node.style[dojo.html.toCamelCase(_1da)]:undefined);
};
dojo.html.getStyle=function(node,_1dc){
var _1dd=dojo.html.getStyleProperty(node,_1dc);
return (_1dd?_1dd:dojo.html.getComputedStyle(node,_1dc));
};
dojo.html.setStyle=function(node,_1df,_1e0){
node=dojo.byId(node);
if(node&&node.style){
var _1e1=dojo.html.toCamelCase(_1df);
node.style[_1e1]=_1e0;
}
};
dojo.html.setStyleText=function(_1e2,text){
try{
_1e2.style.cssText=text;
}
catch(e){
_1e2.setAttribute("style",text);
}
};
dojo.html.copyStyle=function(_1e4,_1e5){
if(!_1e5.style.cssText){
_1e4.setAttribute("style",_1e5.getAttribute("style"));
}else{
_1e4.style.cssText=_1e5.style.cssText;
}
dojo.html.addClass(_1e4,dojo.html.getClass(_1e5));
};
dojo.html.getUnitValue=function(node,_1e7,_1e8){
var s=dojo.html.getComputedStyle(node,_1e7);
if((!s)||((s=="auto")&&(_1e8))){
return {value:0,units:"px"};
}
var _1ea=s.match(/(\-?[\d.]+)([a-z%]*)/i);
if(!_1ea){
return dojo.html.getUnitValue.bad;
}
return {value:Number(_1ea[1]),units:_1ea[2].toLowerCase()};
};
dojo.html.getUnitValue.bad={value:NaN,units:""};
dojo.html.getPixelValue=function(node,_1ec,_1ed){
var _1ee=dojo.html.getUnitValue(node,_1ec,_1ed);
if(isNaN(_1ee.value)){
return 0;
}
if((_1ee.value)&&(_1ee.units!="px")){
return NaN;
}
return _1ee.value;
};
dojo.html.setPositivePixelValue=function(node,_1f0,_1f1){
if(isNaN(_1f1)){
return false;
}
node.style[_1f0]=Math.max(0,_1f1)+"px";
return true;
};
dojo.html.styleSheet=null;
dojo.html.insertCssRule=function(_1f2,_1f3,_1f4){
if(!dojo.html.styleSheet){
if(document.createStyleSheet){
dojo.html.styleSheet=document.createStyleSheet();
}else{
if(document.styleSheets[0]){
dojo.html.styleSheet=document.styleSheets[0];
}else{
return null;
}
}
}
if(arguments.length<3){
if(dojo.html.styleSheet.cssRules){
_1f4=dojo.html.styleSheet.cssRules.length;
}else{
if(dojo.html.styleSheet.rules){
_1f4=dojo.html.styleSheet.rules.length;
}else{
return null;
}
}
}
if(dojo.html.styleSheet.insertRule){
var rule=_1f2+" { "+_1f3+" }";
return dojo.html.styleSheet.insertRule(rule,_1f4);
}else{
if(dojo.html.styleSheet.addRule){
return dojo.html.styleSheet.addRule(_1f2,_1f3,_1f4);
}else{
return null;
}
}
};
dojo.html.removeCssRule=function(_1f6){
if(!dojo.html.styleSheet){
dojo.debug("no stylesheet defined for removing rules");
return false;
}
if(dojo.render.html.ie){
if(!_1f6){
_1f6=dojo.html.styleSheet.rules.length;
dojo.html.styleSheet.removeRule(_1f6);
}
}else{
if(document.styleSheets[0]){
if(!_1f6){
_1f6=dojo.html.styleSheet.cssRules.length;
}
dojo.html.styleSheet.deleteRule(_1f6);
}
}
return true;
};
dojo.html._insertedCssFiles=[];
dojo.html.insertCssFile=function(URI,doc,_1f9,_1fa){
if(!URI){
return;
}
if(!doc){
doc=document;
}
var _1fb=dojo.hostenv.getText(URI,false,_1fa);
if(_1fb===null){
return;
}
_1fb=dojo.html.fixPathsInCssText(_1fb,URI);
if(_1f9){
var idx=-1,node,ent=dojo.html._insertedCssFiles;
for(var i=0;i<ent.length;i++){
if((ent[i].doc==doc)&&(ent[i].cssText==_1fb)){
idx=i;
node=ent[i].nodeRef;
break;
}
}
if(node){
var _200=doc.getElementsByTagName("style");
for(var i=0;i<_200.length;i++){
if(_200[i]==node){
return;
}
}
dojo.html._insertedCssFiles.shift(idx,1);
}
}
var _201=dojo.html.insertCssText(_1fb,doc);
dojo.html._insertedCssFiles.push({"doc":doc,"cssText":_1fb,"nodeRef":_201});
if(_201&&djConfig.isDebug){
_201.setAttribute("dbgHref",URI);
}
return _201;
};
dojo.html.insertCssText=function(_202,doc,URI){
if(!_202){
return;
}
if(!doc){
doc=document;
}
if(URI){
_202=dojo.html.fixPathsInCssText(_202,URI);
}
var _205=doc.createElement("style");
_205.setAttribute("type","text/css");
var head=doc.getElementsByTagName("head")[0];
if(!head){
dojo.debug("No head tag in document, aborting styles");
return;
}else{
head.appendChild(_205);
}
if(_205.styleSheet){
var _207=function(){
try{
_205.styleSheet.cssText=_202;
}
catch(e){
dojo.debug(e);
}
};
if(_205.styleSheet.disabled){
setTimeout(_207,10);
}else{
_207();
}
}else{
var _208=doc.createTextNode(_202);
_205.appendChild(_208);
}
return _205;
};
dojo.html.fixPathsInCssText=function(_209,URI){
if(!_209||!URI){
return;
}
var _20b,str="",url="",_20e="[\\t\\s\\w\\(\\)\\/\\.\\\\'\"-:#=&?~]+";
var _20f=new RegExp("url\\(\\s*("+_20e+")\\s*\\)");
var _210=/(file|https?|ftps?):\/\//;
regexTrim=new RegExp("^[\\s]*(['\"]?)("+_20e+")\\1[\\s]*?$");
if(dojo.render.html.ie55||dojo.render.html.ie60){
var _211=new RegExp("AlphaImageLoader\\((.*)src=['\"]("+_20e+")['\"]");
while(_20b=_211.exec(_209)){
url=_20b[2].replace(regexTrim,"$2");
if(!_210.exec(url)){
url=(new dojo.uri.Uri(URI,url).toString());
}
str+=_209.substring(0,_20b.index)+"AlphaImageLoader("+_20b[1]+"src='"+url+"'";
_209=_209.substr(_20b.index+_20b[0].length);
}
_209=str+_209;
str="";
}
while(_20b=_20f.exec(_209)){
url=_20b[1].replace(regexTrim,"$2");
if(!_210.exec(url)){
url=(new dojo.uri.Uri(URI,url).toString());
}
str+=_209.substring(0,_20b.index)+"url("+url+")";
_209=_209.substr(_20b.index+_20b[0].length);
}
return str+_209;
};
dojo.html.setActiveStyleSheet=function(_212){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")){
a.disabled=true;
if(a.getAttribute("title")==_212){
a.disabled=false;
}
}
}
};
dojo.html.getActiveStyleSheet=function(){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")&&!a.disabled){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.getPreferredStyleSheet=function(){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("rel").indexOf("alt")==-1&&a.getAttribute("title")){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.applyBrowserClass=function(node){
var drh=dojo.render.html;
var _21e={dj_ie:drh.ie,dj_ie55:drh.ie55,dj_ie6:drh.ie60,dj_ie7:drh.ie70,dj_iequirks:drh.ie&&drh.quirks,dj_opera:drh.opera,dj_opera8:drh.opera&&(Math.floor(dojo.render.version)==8),dj_opera9:drh.opera&&(Math.floor(dojo.render.version)==9),dj_khtml:drh.khtml,dj_safari:drh.safari,dj_gecko:drh.mozilla};
for(var p in _21e){
if(_21e[p]){
dojo.html.addClass(node,p);
}
}
};
dojo.provide("dojo.html.*");
dojo.provide("dojo.html.display");
dojo.html._toggle=function(node,_221,_222){
node=dojo.byId(node);
_222(node,!_221(node));
return _221(node);
};
dojo.html.show=function(node){
node=dojo.byId(node);
if(dojo.html.getStyleProperty(node,"display")=="none"){
dojo.html.setStyle(node,"display",(node.dojoDisplayCache||""));
node.dojoDisplayCache=undefined;
}
};
dojo.html.hide=function(node){
node=dojo.byId(node);
if(typeof node["dojoDisplayCache"]=="undefined"){
var d=dojo.html.getStyleProperty(node,"display");
if(d!="none"){
node.dojoDisplayCache=d;
}
}
dojo.html.setStyle(node,"display","none");
};
dojo.html.setShowing=function(node,_227){
dojo.html[(_227?"show":"hide")](node);
};
dojo.html.isShowing=function(node){
return (dojo.html.getStyleProperty(node,"display")!="none");
};
dojo.html.toggleShowing=function(node){
return dojo.html._toggle(node,dojo.html.isShowing,dojo.html.setShowing);
};
dojo.html.displayMap={tr:"",td:"",th:"",img:"inline",span:"inline",input:"inline",button:"inline"};
dojo.html.suggestDisplayByTagName=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
var tag=node.tagName.toLowerCase();
return (tag in dojo.html.displayMap?dojo.html.displayMap[tag]:"block");
}
};
dojo.html.setDisplay=function(node,_22d){
dojo.html.setStyle(node,"display",((_22d instanceof String||typeof _22d=="string")?_22d:(_22d?dojo.html.suggestDisplayByTagName(node):"none")));
};
dojo.html.isDisplayed=function(node){
return (dojo.html.getComputedStyle(node,"display")!="none");
};
dojo.html.toggleDisplay=function(node){
return dojo.html._toggle(node,dojo.html.isDisplayed,dojo.html.setDisplay);
};
dojo.html.setVisibility=function(node,_231){
dojo.html.setStyle(node,"visibility",((_231 instanceof String||typeof _231=="string")?_231:(_231?"visible":"hidden")));
};
dojo.html.isVisible=function(node){
return (dojo.html.getComputedStyle(node,"visibility")!="hidden");
};
dojo.html.toggleVisibility=function(node){
return dojo.html._toggle(node,dojo.html.isVisible,dojo.html.setVisibility);
};
dojo.html.setOpacity=function(node,_235,_236){
node=dojo.byId(node);
var h=dojo.render.html;
if(!_236){
if(_235>=1){
if(h.ie){
dojo.html.clearOpacity(node);
return;
}else{
_235=0.999999;
}
}else{
if(_235<0){
_235=0;
}
}
}
if(h.ie){
if(node.nodeName.toLowerCase()=="tr"){
var tds=node.getElementsByTagName("td");
for(var x=0;x<tds.length;x++){
tds[x].style.filter="Alpha(Opacity="+_235*100+")";
}
}
node.style.filter="Alpha(Opacity="+_235*100+")";
}else{
if(h.moz){
node.style.opacity=_235;
node.style.MozOpacity=_235;
}else{
if(h.safari){
node.style.opacity=_235;
node.style.KhtmlOpacity=_235;
}else{
node.style.opacity=_235;
}
}
}
};
dojo.html.clearOpacity=function(node){
node=dojo.byId(node);
var ns=node.style;
var h=dojo.render.html;
if(h.ie){
try{
if(node.filters&&node.filters.alpha){
ns.filter="";
}
}
catch(e){
}
}else{
if(h.moz){
ns.opacity=1;
ns.MozOpacity=1;
}else{
if(h.safari){
ns.opacity=1;
ns.KhtmlOpacity=1;
}else{
ns.opacity=1;
}
}
}
};
dojo.html.getOpacity=function(node){
node=dojo.byId(node);
var h=dojo.render.html;
if(h.ie){
var opac=(node.filters&&node.filters.alpha&&typeof node.filters.alpha.opacity=="number"?node.filters.alpha.opacity:100)/100;
}else{
var opac=node.style.opacity||node.style.MozOpacity||node.style.KhtmlOpacity||1;
}
return opac>=0.999999?1:Number(opac);
};
dojo.provide("dojo.html.layout");
dojo.html.sumAncestorProperties=function(node,prop){
node=dojo.byId(node);
if(!node){
return 0;
}
var _242=0;
while(node){
if(dojo.html.getComputedStyle(node,"position")=="fixed"){
return 0;
}
var val=node[prop];
if(val){
_242+=val-0;
if(node==dojo.body()){
break;
}
}
node=node.parentNode;
}
return _242;
};
dojo.html.setStyleAttributes=function(node,_245){
node=dojo.byId(node);
var _246=_245.replace(/(;)?\s*$/,"").split(";");
for(var i=0;i<_246.length;i++){
var _248=_246[i].split(":");
var name=_248[0].replace(/\s*$/,"").replace(/^\s*/,"").toLowerCase();
var _24a=_248[1].replace(/\s*$/,"").replace(/^\s*/,"");
switch(name){
case "opacity":
dojo.html.setOpacity(node,_24a);
break;
case "content-height":
dojo.html.setContentBox(node,{height:_24a});
break;
case "content-width":
dojo.html.setContentBox(node,{width:_24a});
break;
case "outer-height":
dojo.html.setMarginBox(node,{height:_24a});
break;
case "outer-width":
dojo.html.setMarginBox(node,{width:_24a});
break;
default:
node.style[dojo.html.toCamelCase(name)]=_24a;
}
}
};
dojo.html.boxSizing={MARGIN_BOX:"margin-box",BORDER_BOX:"border-box",PADDING_BOX:"padding-box",CONTENT_BOX:"content-box"};
dojo.html.getAbsolutePosition=dojo.html.abs=function(node,_24c,_24d){
node=dojo.byId(node,node.ownerDocument);
var ret={x:0,y:0};
var bs=dojo.html.boxSizing;
if(!_24d){
_24d=bs.CONTENT_BOX;
}
var _250=2;
var _251;
switch(_24d){
case bs.MARGIN_BOX:
_251=3;
break;
case bs.BORDER_BOX:
_251=2;
break;
case bs.PADDING_BOX:
default:
_251=1;
break;
case bs.CONTENT_BOX:
_251=0;
break;
}
var h=dojo.render.html;
var db=document["body"]||document["documentElement"];
if(h.ie){
with(node.getBoundingClientRect()){
ret.x=left-2;
ret.y=top-2;
}
}else{
if(document.getBoxObjectFor){
_250=1;
try{
var bo=document.getBoxObjectFor(node);
ret.x=bo.x-dojo.html.sumAncestorProperties(node,"scrollLeft");
ret.y=bo.y-dojo.html.sumAncestorProperties(node,"scrollTop");
}
catch(e){
}
}else{
if(node["offsetParent"]){
var _255;
if((h.safari)&&(node.style.getPropertyValue("position")=="absolute")&&(node.parentNode==db)){
_255=db;
}else{
_255=db.parentNode;
}
if(node.parentNode!=db){
var nd=node;
if(dojo.render.html.opera){
nd=db;
}
ret.x-=dojo.html.sumAncestorProperties(nd,"scrollLeft");
ret.y-=dojo.html.sumAncestorProperties(nd,"scrollTop");
}
var _257=node;
do{
var n=_257["offsetLeft"];
if(!h.opera||n>0){
ret.x+=isNaN(n)?0:n;
}
var m=_257["offsetTop"];
ret.y+=isNaN(m)?0:m;
_257=_257.offsetParent;
}while((_257!=_255)&&(_257!=null));
}else{
if(node["x"]&&node["y"]){
ret.x+=isNaN(node.x)?0:node.x;
ret.y+=isNaN(node.y)?0:node.y;
}
}
}
}
if(_24c){
var _25a=dojo.html.getScroll();
ret.y+=_25a.top;
ret.x+=_25a.left;
}
var _25b=[dojo.html.getPaddingExtent,dojo.html.getBorderExtent,dojo.html.getMarginExtent];
if(_250>_251){
for(var i=_251;i<_250;++i){
ret.y+=_25b[i](node,"top");
ret.x+=_25b[i](node,"left");
}
}else{
if(_250<_251){
for(var i=_251;i>_250;--i){
ret.y-=_25b[i-1](node,"top");
ret.x-=_25b[i-1](node,"left");
}
}
}
ret.top=ret.y;
ret.left=ret.x;
return ret;
};
dojo.html.isPositionAbsolute=function(node){
return (dojo.html.getComputedStyle(node,"position")=="absolute");
};
dojo.html._sumPixelValues=function(node,_25f,_260){
var _261=0;
for(var x=0;x<_25f.length;x++){
_261+=dojo.html.getPixelValue(node,_25f[x],_260);
}
return _261;
};
dojo.html.getMargin=function(node){
return {width:dojo.html._sumPixelValues(node,["margin-left","margin-right"],(dojo.html.getComputedStyle(node,"position")=="absolute")),height:dojo.html._sumPixelValues(node,["margin-top","margin-bottom"],(dojo.html.getComputedStyle(node,"position")=="absolute"))};
};
dojo.html.getBorder=function(node){
return {width:dojo.html.getBorderExtent(node,"left")+dojo.html.getBorderExtent(node,"right"),height:dojo.html.getBorderExtent(node,"top")+dojo.html.getBorderExtent(node,"bottom")};
};
dojo.html.getBorderExtent=function(node,side){
return (dojo.html.getStyle(node,"border-"+side+"-style")=="none"?0:dojo.html.getPixelValue(node,"border-"+side+"-width"));
};
dojo.html.getMarginExtent=function(node,side){
return dojo.html._sumPixelValues(node,["margin-"+side],dojo.html.isPositionAbsolute(node));
};
dojo.html.getPaddingExtent=function(node,side){
return dojo.html._sumPixelValues(node,["padding-"+side],true);
};
dojo.html.getPadding=function(node){
return {width:dojo.html._sumPixelValues(node,["padding-left","padding-right"],true),height:dojo.html._sumPixelValues(node,["padding-top","padding-bottom"],true)};
};
dojo.html.getPadBorder=function(node){
var pad=dojo.html.getPadding(node);
var _26e=dojo.html.getBorder(node);
return {width:pad.width+_26e.width,height:pad.height+_26e.height};
};
dojo.html.getBoxSizing=function(node){
var h=dojo.render.html;
var bs=dojo.html.boxSizing;
if(((h.ie)||(h.opera))&&node.nodeName!="IMG"){
var cm=document["compatMode"];
if((cm=="BackCompat")||(cm=="QuirksMode")){
return bs.BORDER_BOX;
}else{
return bs.CONTENT_BOX;
}
}else{
if(arguments.length==0){
node=document.documentElement;
}
var _273=dojo.html.getStyle(node,"-moz-box-sizing");
if(!_273){
_273=dojo.html.getStyle(node,"box-sizing");
}
return (_273?_273:bs.CONTENT_BOX);
}
};
dojo.html.isBorderBox=function(node){
return (dojo.html.getBoxSizing(node)==dojo.html.boxSizing.BORDER_BOX);
};
dojo.html.getBorderBox=function(node){
node=dojo.byId(node);
return {width:node.offsetWidth,height:node.offsetHeight};
};
dojo.html.getPaddingBox=function(node){
var box=dojo.html.getBorderBox(node);
var _278=dojo.html.getBorder(node);
return {width:box.width-_278.width,height:box.height-_278.height};
};
dojo.html.getContentBox=function(node){
node=dojo.byId(node);
var _27a=dojo.html.getPadBorder(node);
return {width:node.offsetWidth-_27a.width,height:node.offsetHeight-_27a.height};
};
dojo.html.setContentBox=function(node,args){
node=dojo.byId(node);
var _27d=0;
var _27e=0;
var isbb=dojo.html.isBorderBox(node);
var _280=(isbb?dojo.html.getPadBorder(node):{width:0,height:0});
var ret={};
if(typeof args.width!="undefined"){
_27d=args.width+_280.width;
ret.width=dojo.html.setPositivePixelValue(node,"width",_27d);
}
if(typeof args.height!="undefined"){
_27e=args.height+_280.height;
ret.height=dojo.html.setPositivePixelValue(node,"height",_27e);
}
return ret;
};
dojo.html.getMarginBox=function(node){
var _283=dojo.html.getBorderBox(node);
var _284=dojo.html.getMargin(node);
return {width:_283.width+_284.width,height:_283.height+_284.height};
};
dojo.html.setMarginBox=function(node,args){
node=dojo.byId(node);
var _287=0;
var _288=0;
var isbb=dojo.html.isBorderBox(node);
var _28a=(!isbb?dojo.html.getPadBorder(node):{width:0,height:0});
var _28b=dojo.html.getMargin(node);
var ret={};
if(typeof args.width!="undefined"){
_287=args.width-_28a.width;
_287-=_28b.width;
ret.width=dojo.html.setPositivePixelValue(node,"width",_287);
}
if(typeof args.height!="undefined"){
_288=args.height-_28a.height;
_288-=_28b.height;
ret.height=dojo.html.setPositivePixelValue(node,"height",_288);
}
return ret;
};
dojo.html.getElementBox=function(node,type){
var bs=dojo.html.boxSizing;
switch(type){
case bs.MARGIN_BOX:
return dojo.html.getMarginBox(node);
case bs.BORDER_BOX:
return dojo.html.getBorderBox(node);
case bs.PADDING_BOX:
return dojo.html.getPaddingBox(node);
case bs.CONTENT_BOX:
default:
return dojo.html.getContentBox(node);
}
};
dojo.html.toCoordinateObject=dojo.html.toCoordinateArray=function(_290,_291,_292){
if(_290 instanceof Array||typeof _290=="array"){
dojo.deprecated("dojo.html.toCoordinateArray","use dojo.html.toCoordinateObject({left: , top: , width: , height: }) instead","0.5");
while(_290.length<4){
_290.push(0);
}
while(_290.length>4){
_290.pop();
}
var ret={left:_290[0],top:_290[1],width:_290[2],height:_290[3]};
}else{
if(!_290.nodeType&&!(_290 instanceof String||typeof _290=="string")&&("width" in _290||"height" in _290||"left" in _290||"x" in _290||"top" in _290||"y" in _290)){
var ret={left:_290.left||_290.x||0,top:_290.top||_290.y||0,width:_290.width||0,height:_290.height||0};
}else{
var node=dojo.byId(_290);
var pos=dojo.html.abs(node,_291,_292);
var _296=dojo.html.getMarginBox(node);
var ret={left:pos.left,top:pos.top,width:_296.width,height:_296.height};
}
}
ret.x=ret.left;
ret.y=ret.top;
return ret;
};
dojo.html.setMarginBoxWidth=dojo.html.setOuterWidth=function(node,_298){
return dojo.html._callDeprecated("setMarginBoxWidth","setMarginBox",arguments,"width");
};
dojo.html.setMarginBoxHeight=dojo.html.setOuterHeight=function(){
return dojo.html._callDeprecated("setMarginBoxHeight","setMarginBox",arguments,"height");
};
dojo.html.getMarginBoxWidth=dojo.html.getOuterWidth=function(){
return dojo.html._callDeprecated("getMarginBoxWidth","getMarginBox",arguments,null,"width");
};
dojo.html.getMarginBoxHeight=dojo.html.getOuterHeight=function(){
return dojo.html._callDeprecated("getMarginBoxHeight","getMarginBox",arguments,null,"height");
};
dojo.html.getTotalOffset=function(node,type,_29b){
return dojo.html._callDeprecated("getTotalOffset","getAbsolutePosition",arguments,null,type);
};
dojo.html.getAbsoluteX=function(node,_29d){
return dojo.html._callDeprecated("getAbsoluteX","getAbsolutePosition",arguments,null,"x");
};
dojo.html.getAbsoluteY=function(node,_29f){
return dojo.html._callDeprecated("getAbsoluteY","getAbsolutePosition",arguments,null,"y");
};
dojo.html.totalOffsetLeft=function(node,_2a1){
return dojo.html._callDeprecated("totalOffsetLeft","getAbsolutePosition",arguments,null,"left");
};
dojo.html.totalOffsetTop=function(node,_2a3){
return dojo.html._callDeprecated("totalOffsetTop","getAbsolutePosition",arguments,null,"top");
};
dojo.html.getMarginWidth=function(node){
return dojo.html._callDeprecated("getMarginWidth","getMargin",arguments,null,"width");
};
dojo.html.getMarginHeight=function(node){
return dojo.html._callDeprecated("getMarginHeight","getMargin",arguments,null,"height");
};
dojo.html.getBorderWidth=function(node){
return dojo.html._callDeprecated("getBorderWidth","getBorder",arguments,null,"width");
};
dojo.html.getBorderHeight=function(node){
return dojo.html._callDeprecated("getBorderHeight","getBorder",arguments,null,"height");
};
dojo.html.getPaddingWidth=function(node){
return dojo.html._callDeprecated("getPaddingWidth","getPadding",arguments,null,"width");
};
dojo.html.getPaddingHeight=function(node){
return dojo.html._callDeprecated("getPaddingHeight","getPadding",arguments,null,"height");
};
dojo.html.getPadBorderWidth=function(node){
return dojo.html._callDeprecated("getPadBorderWidth","getPadBorder",arguments,null,"width");
};
dojo.html.getPadBorderHeight=function(node){
return dojo.html._callDeprecated("getPadBorderHeight","getPadBorder",arguments,null,"height");
};
dojo.html.getBorderBoxWidth=dojo.html.getInnerWidth=function(){
return dojo.html._callDeprecated("getBorderBoxWidth","getBorderBox",arguments,null,"width");
};
dojo.html.getBorderBoxHeight=dojo.html.getInnerHeight=function(){
return dojo.html._callDeprecated("getBorderBoxHeight","getBorderBox",arguments,null,"height");
};
dojo.html.getContentBoxWidth=dojo.html.getContentWidth=function(){
return dojo.html._callDeprecated("getContentBoxWidth","getContentBox",arguments,null,"width");
};
dojo.html.getContentBoxHeight=dojo.html.getContentHeight=function(){
return dojo.html._callDeprecated("getContentBoxHeight","getContentBox",arguments,null,"height");
};
dojo.html.setContentBoxWidth=dojo.html.setContentWidth=function(node,_2ad){
return dojo.html._callDeprecated("setContentBoxWidth","setContentBox",arguments,"width");
};
dojo.html.setContentBoxHeight=dojo.html.setContentHeight=function(node,_2af){
return dojo.html._callDeprecated("setContentBoxHeight","setContentBox",arguments,"height");
};
dojo.provide("dojo.html.util");
dojo.html.getElementWindow=function(_2b0){
return dojo.html.getDocumentWindow(_2b0.ownerDocument);
};
dojo.html.getDocumentWindow=function(doc){
if(dojo.render.html.safari&&!doc._parentWindow){
var fix=function(win){
win.document._parentWindow=win;
for(var i=0;i<win.frames.length;i++){
fix(win.frames[i]);
}
};
fix(window.top);
}
if(dojo.render.html.ie&&window!==document.parentWindow&&!doc._parentWindow){
doc.parentWindow.execScript("document._parentWindow = window;","Javascript");
var win=doc._parentWindow;
doc._parentWindow=null;
return win;
}
return doc._parentWindow||doc.parentWindow||doc.defaultView;
};
dojo.html.gravity=function(node,e){
node=dojo.byId(node);
var _2b8=dojo.html.getCursorPosition(e);
with(dojo.html){
var _2b9=getAbsolutePosition(node,true);
var bb=getBorderBox(node);
var _2bb=_2b9.x+(bb.width/2);
var _2bc=_2b9.y+(bb.height/2);
}
with(dojo.html.gravity){
return ((_2b8.x<_2bb?WEST:EAST)|(_2b8.y<_2bc?NORTH:SOUTH));
}
};
dojo.html.gravity.NORTH=1;
dojo.html.gravity.SOUTH=1<<1;
dojo.html.gravity.EAST=1<<2;
dojo.html.gravity.WEST=1<<3;
dojo.html.overElement=function(_2bd,e){
_2bd=dojo.byId(_2bd);
var _2bf=dojo.html.getCursorPosition(e);
var bb=dojo.html.getBorderBox(_2bd);
var _2c1=dojo.html.getAbsolutePosition(_2bd,true,dojo.html.boxSizing.BORDER_BOX);
var top=_2c1.y;
var _2c3=top+bb.height;
var left=_2c1.x;
var _2c5=left+bb.width;
return (_2bf.x>=left&&_2bf.x<=_2c5&&_2bf.y>=top&&_2bf.y<=_2c3);
};
dojo.html.renderedTextContent=function(node){
node=dojo.byId(node);
var _2c7="";
if(node==null){
return _2c7;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
var _2c9="unknown";
try{
_2c9=dojo.html.getStyle(node.childNodes[i],"display");
}
catch(E){
}
switch(_2c9){
case "block":
case "list-item":
case "run-in":
case "table":
case "table-row-group":
case "table-header-group":
case "table-footer-group":
case "table-row":
case "table-column-group":
case "table-column":
case "table-cell":
case "table-caption":
_2c7+="\n";
_2c7+=dojo.html.renderedTextContent(node.childNodes[i]);
_2c7+="\n";
break;
case "none":
break;
default:
if(node.childNodes[i].tagName&&node.childNodes[i].tagName.toLowerCase()=="br"){
_2c7+="\n";
}else{
_2c7+=dojo.html.renderedTextContent(node.childNodes[i]);
}
break;
}
break;
case 3:
case 2:
case 4:
var text=node.childNodes[i].nodeValue;
var _2cb="unknown";
try{
_2cb=dojo.html.getStyle(node,"text-transform");
}
catch(E){
}
switch(_2cb){
case "capitalize":
var _2cc=text.split(" ");
for(var i=0;i<_2cc.length;i++){
_2cc[i]=_2cc[i].charAt(0).toUpperCase()+_2cc[i].substring(1);
}
text=_2cc.join(" ");
break;
case "uppercase":
text=text.toUpperCase();
break;
case "lowercase":
text=text.toLowerCase();
break;
default:
break;
}
switch(_2cb){
case "nowrap":
break;
case "pre-wrap":
break;
case "pre-line":
break;
case "pre":
break;
default:
text=text.replace(/\s+/," ");
if(/\s$/.test(_2c7)){
text.replace(/^\s/,"");
}
break;
}
_2c7+=text;
break;
default:
break;
}
}
return _2c7;
};
dojo.html.createNodesFromText=function(txt,trim){
if(trim){
txt=txt.replace(/^\s+|\s+$/g,"");
}
var tn=dojo.doc().createElement("div");
tn.style.visibility="hidden";
dojo.body().appendChild(tn);
var _2d0="none";
if((/^<t[dh][\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table><tbody><tr>"+txt+"</tr></tbody></table>";
_2d0="cell";
}else{
if((/^<tr[\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table><tbody>"+txt+"</tbody></table>";
_2d0="row";
}else{
if((/^<(thead|tbody|tfoot)[\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table>"+txt+"</table>";
_2d0="section";
}
}
}
tn.innerHTML=txt;
if(tn["normalize"]){
tn.normalize();
}
var _2d1=null;
switch(_2d0){
case "cell":
_2d1=tn.getElementsByTagName("tr")[0];
break;
case "row":
_2d1=tn.getElementsByTagName("tbody")[0];
break;
case "section":
_2d1=tn.getElementsByTagName("table")[0];
break;
default:
_2d1=tn;
break;
}
var _2d2=[];
for(var x=0;x<_2d1.childNodes.length;x++){
_2d2.push(_2d1.childNodes[x].cloneNode(true));
}
tn.style.display="none";
dojo.html.destroyNode(tn);
return _2d2;
};
dojo.html.placeOnScreen=function(node,_2d5,_2d6,_2d7,_2d8,_2d9,_2da){
if(_2d5 instanceof Array||typeof _2d5=="array"){
_2da=_2d9;
_2d9=_2d8;
_2d8=_2d7;
_2d7=_2d6;
_2d6=_2d5[1];
_2d5=_2d5[0];
}
if(_2d9 instanceof String||typeof _2d9=="string"){
_2d9=_2d9.split(",");
}
if(!isNaN(_2d7)){
_2d7=[Number(_2d7),Number(_2d7)];
}else{
if(!(_2d7 instanceof Array||typeof _2d7=="array")){
_2d7=[0,0];
}
}
var _2db=dojo.html.getScroll().offset;
var view=dojo.html.getViewport();
node=dojo.byId(node);
var _2dd=node.style.display;
node.style.display="";
var bb=dojo.html.getBorderBox(node);
var w=bb.width;
var h=bb.height;
node.style.display=_2dd;
if(!(_2d9 instanceof Array||typeof _2d9=="array")){
_2d9=["TL"];
}
var _2e1,_2e2,_2e3=Infinity,_2e4;
for(var _2e5=0;_2e5<_2d9.length;++_2e5){
var _2e6=_2d9[_2e5];
var _2e7=true;
var tryX=_2d5-(_2e6.charAt(1)=="L"?0:w)+_2d7[0]*(_2e6.charAt(1)=="L"?1:-1);
var tryY=_2d6-(_2e6.charAt(0)=="T"?0:h)+_2d7[1]*(_2e6.charAt(0)=="T"?1:-1);
if(_2d8){
tryX-=_2db.x;
tryY-=_2db.y;
}
if(tryX<0){
tryX=0;
_2e7=false;
}
if(tryY<0){
tryY=0;
_2e7=false;
}
var x=tryX+w;
if(x>view.width){
x=view.width-w;
_2e7=false;
}else{
x=tryX;
}
x=Math.max(_2d7[0],x)+_2db.x;
var y=tryY+h;
if(y>view.height){
y=view.height-h;
_2e7=false;
}else{
y=tryY;
}
y=Math.max(_2d7[1],y)+_2db.y;
if(_2e7){
_2e1=x;
_2e2=y;
_2e3=0;
_2e4=_2e6;
break;
}else{
var dist=Math.pow(x-tryX-_2db.x,2)+Math.pow(y-tryY-_2db.y,2);
if(_2e3>dist){
_2e3=dist;
_2e1=x;
_2e2=y;
_2e4=_2e6;
}
}
}
if(!_2da){
node.style.left=_2e1+"px";
node.style.top=_2e2+"px";
}
return {left:_2e1,top:_2e2,x:_2e1,y:_2e2,dist:_2e3,corner:_2e4};
};
dojo.html.placeOnScreenPoint=function(node,_2ee,_2ef,_2f0,_2f1){
dojo.deprecated("dojo.html.placeOnScreenPoint","use dojo.html.placeOnScreen() instead","0.5");
return dojo.html.placeOnScreen(node,_2ee,_2ef,_2f0,_2f1,["TL","TR","BL","BR"]);
};
dojo.html.placeOnScreenAroundElement=function(node,_2f3,_2f4,_2f5,_2f6,_2f7){
var best,_2f9=Infinity;
_2f3=dojo.byId(_2f3);
var _2fa=_2f3.style.display;
_2f3.style.display="";
var mb=dojo.html.getElementBox(_2f3,_2f5);
var _2fc=mb.width;
var _2fd=mb.height;
var _2fe=dojo.html.getAbsolutePosition(_2f3,true,_2f5);
_2f3.style.display=_2fa;
for(var _2ff in _2f6){
var pos,_301,_302;
var _303=_2f6[_2ff];
_301=_2fe.x+(_2ff.charAt(1)=="L"?0:_2fc);
_302=_2fe.y+(_2ff.charAt(0)=="T"?0:_2fd);
pos=dojo.html.placeOnScreen(node,_301,_302,_2f4,true,_303,true);
if(pos.dist==0){
best=pos;
break;
}else{
if(_2f9>pos.dist){
_2f9=pos.dist;
best=pos;
}
}
}
if(!_2f7){
node.style.left=best.left+"px";
node.style.top=best.top+"px";
}
return best;
};
dojo.html.scrollIntoView=function(node){
if(!node){
return;
}
if(dojo.render.html.ie){
if(dojo.html.getBorderBox(node.parentNode).height<=node.parentNode.scrollHeight){
node.scrollIntoView(false);
}
}else{
if(dojo.render.html.mozilla){
node.scrollIntoView(false);
}else{
var _305=node.parentNode;
var _306=_305.scrollTop+dojo.html.getBorderBox(_305).height;
var _307=node.offsetTop+dojo.html.getMarginBox(node).height;
if(_306<_307){
_305.scrollTop+=(_307-_306);
}else{
if(_305.scrollTop>node.offsetTop){
_305.scrollTop-=(_305.scrollTop-node.offsetTop);
}
}
}
}
};
dojo.provide("dojo.lang.array");
dojo.lang.mixin(dojo.lang,{has:function(obj,name){
try{
return typeof obj[name]!="undefined";
}
catch(e){
return false;
}
},isEmpty:function(obj){
if(dojo.lang.isObject(obj)){
var tmp={};
var _30c=0;
for(var x in obj){
if(obj[x]&&(!tmp[x])){
_30c++;
break;
}
}
return _30c==0;
}else{
if(dojo.lang.isArrayLike(obj)||dojo.lang.isString(obj)){
return obj.length==0;
}
}
},map:function(arr,obj,_310){
var _311=dojo.lang.isString(arr);
if(_311){
arr=arr.split("");
}
if(dojo.lang.isFunction(obj)&&(!_310)){
_310=obj;
obj=dj_global;
}else{
if(dojo.lang.isFunction(obj)&&_310){
var _312=obj;
obj=_310;
_310=_312;
}
}
if(Array.map){
var _313=Array.map(arr,_310,obj);
}else{
var _313=[];
for(var i=0;i<arr.length;++i){
_313.push(_310.call(obj,arr[i]));
}
}
if(_311){
return _313.join("");
}else{
return _313;
}
},reduce:function(arr,_316,obj,_318){
var _319=_316;
if(arguments.length==1){
dojo.debug("dojo.lang.reduce called with too few arguments!");
return false;
}else{
if(arguments.length==2){
_318=_316;
_319=arr.shift();
}else{
if(arguments.lenght==3){
if(dojo.lang.isFunction(obj)){
_318=obj;
obj=null;
}
}else{
if(dojo.lang.isFunction(obj)){
var tmp=_318;
_318=obj;
obj=tmp;
}
}
}
}
var ob=obj?obj:dj_global;
dojo.lang.map(arr,function(val){
_319=_318.call(ob,_319,val);
});
return _319;
},forEach:function(_31d,_31e,_31f){
if(dojo.lang.isString(_31d)){
_31d=_31d.split("");
}
if(Array.forEach){
Array.forEach(_31d,_31e,_31f);
}else{
if(!_31f){
_31f=dj_global;
}
for(var i=0,l=_31d.length;i<l;i++){
_31e.call(_31f,_31d[i],i,_31d);
}
}
},_everyOrSome:function(_322,arr,_324,_325){
if(dojo.lang.isString(arr)){
arr=arr.split("");
}
if(Array.every){
return Array[_322?"every":"some"](arr,_324,_325);
}else{
if(!_325){
_325=dj_global;
}
for(var i=0,l=arr.length;i<l;i++){
var _328=_324.call(_325,arr[i],i,arr);
if(_322&&!_328){
return false;
}else{
if((!_322)&&(_328)){
return true;
}
}
}
return Boolean(_322);
}
},every:function(arr,_32a,_32b){
return this._everyOrSome(true,arr,_32a,_32b);
},some:function(arr,_32d,_32e){
return this._everyOrSome(false,arr,_32d,_32e);
},filter:function(arr,_330,_331){
var _332=dojo.lang.isString(arr);
if(_332){
arr=arr.split("");
}
var _333;
if(Array.filter){
_333=Array.filter(arr,_330,_331);
}else{
if(!_331){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_331=dj_global;
}
_333=[];
for(var i=0;i<arr.length;i++){
if(_330.call(_331,arr[i],i,arr)){
_333.push(arr[i]);
}
}
}
if(_332){
return _333.join("");
}else{
return _333;
}
},unnest:function(){
var out=[];
for(var i=0;i<arguments.length;i++){
if(dojo.lang.isArrayLike(arguments[i])){
var add=dojo.lang.unnest.apply(this,arguments[i]);
out=out.concat(add);
}else{
out.push(arguments[i]);
}
}
return out;
},toArray:function(_338,_339){
var _33a=[];
for(var i=_339||0;i<_338.length;i++){
_33a.push(_338[i]);
}
return _33a;
}});
dojo.provide("dojo.gfx.color");
dojo.gfx.color.Color=function(r,g,b,a){
if(dojo.lang.isArray(r)){
this.r=r[0];
this.g=r[1];
this.b=r[2];
this.a=r[3]||1;
}else{
if(dojo.lang.isString(r)){
var rgb=dojo.gfx.color.extractRGB(r);
this.r=rgb[0];
this.g=rgb[1];
this.b=rgb[2];
this.a=g||1;
}else{
if(r instanceof dojo.gfx.color.Color){
this.r=r.r;
this.b=r.b;
this.g=r.g;
this.a=r.a;
}else{
this.r=r;
this.g=g;
this.b=b;
this.a=a;
}
}
}
};
dojo.gfx.color.Color.fromArray=function(arr){
return new dojo.gfx.color.Color(arr[0],arr[1],arr[2],arr[3]);
};
dojo.extend(dojo.gfx.color.Color,{toRgb:function(_342){
if(_342){
return this.toRgba();
}else{
return [this.r,this.g,this.b];
}
},toRgba:function(){
return [this.r,this.g,this.b,this.a];
},toHex:function(){
return dojo.gfx.color.rgb2hex(this.toRgb());
},toCss:function(){
return "rgb("+this.toRgb().join()+")";
},toString:function(){
return this.toHex();
},blend:function(_343,_344){
var rgb=null;
if(dojo.lang.isArray(_343)){
rgb=_343;
}else{
if(_343 instanceof dojo.gfx.color.Color){
rgb=_343.toRgb();
}else{
rgb=new dojo.gfx.color.Color(_343).toRgb();
}
}
return dojo.gfx.color.blend(this.toRgb(),rgb,_344);
}});
dojo.gfx.color.named={white:[255,255,255],black:[0,0,0],red:[255,0,0],green:[0,255,0],lime:[0,255,0],blue:[0,0,255],navy:[0,0,128],gray:[128,128,128],silver:[192,192,192]};
dojo.gfx.color.blend=function(a,b,_348){
if(typeof a=="string"){
return dojo.gfx.color.blendHex(a,b,_348);
}
if(!_348){
_348=0;
}
_348=Math.min(Math.max(-1,_348),1);
_348=((_348+1)/2);
var c=[];
for(var x=0;x<3;x++){
c[x]=parseInt(b[x]+((a[x]-b[x])*_348));
}
return c;
};
dojo.gfx.color.blendHex=function(a,b,_34d){
return dojo.gfx.color.rgb2hex(dojo.gfx.color.blend(dojo.gfx.color.hex2rgb(a),dojo.gfx.color.hex2rgb(b),_34d));
};
dojo.gfx.color.extractRGB=function(_34e){
var hex="0123456789abcdef";
_34e=_34e.toLowerCase();
if(_34e.indexOf("rgb")==0){
var _350=_34e.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
var ret=_350.splice(1,3);
return ret;
}else{
var _352=dojo.gfx.color.hex2rgb(_34e);
if(_352){
return _352;
}else{
return dojo.gfx.color.named[_34e]||[255,255,255];
}
}
};
dojo.gfx.color.hex2rgb=function(hex){
var _354="0123456789ABCDEF";
var rgb=new Array(3);
if(hex.indexOf("#")==0){
hex=hex.substring(1);
}
hex=hex.toUpperCase();
if(hex.replace(new RegExp("["+_354+"]","g"),"")!=""){
return null;
}
if(hex.length==3){
rgb[0]=hex.charAt(0)+hex.charAt(0);
rgb[1]=hex.charAt(1)+hex.charAt(1);
rgb[2]=hex.charAt(2)+hex.charAt(2);
}else{
rgb[0]=hex.substring(0,2);
rgb[1]=hex.substring(2,4);
rgb[2]=hex.substring(4);
}
for(var i=0;i<rgb.length;i++){
rgb[i]=_354.indexOf(rgb[i].charAt(0))*16+_354.indexOf(rgb[i].charAt(1));
}
return rgb;
};
dojo.gfx.color.rgb2hex=function(r,g,b){
if(dojo.lang.isArray(r)){
g=r[1]||0;
b=r[2]||0;
r=r[0]||0;
}
var ret=dojo.lang.map([r,g,b],function(x){
x=new Number(x);
var s=x.toString(16);
while(s.length<2){
s="0"+s;
}
return s;
});
ret.unshift("#");
return ret.join("");
};
dojo.provide("dojo.lang.func");
dojo.lang.hitch=function(_35d,_35e){
var fcn=(dojo.lang.isString(_35e)?_35d[_35e]:_35e)||function(){
};
return function(){
return fcn.apply(_35d,arguments);
};
};
dojo.lang.anonCtr=0;
dojo.lang.anon={};
dojo.lang.nameAnonFunc=function(_360,_361,_362){
var nso=(_361||dojo.lang.anon);
if((_362)||((dj_global["djConfig"])&&(djConfig["slowAnonFuncLookups"]==true))){
for(var x in nso){
try{
if(nso[x]===_360){
return x;
}
}
catch(e){
}
}
}
var ret="__"+dojo.lang.anonCtr++;
while(typeof nso[ret]!="undefined"){
ret="__"+dojo.lang.anonCtr++;
}
nso[ret]=_360;
return ret;
};
dojo.lang.forward=function(_366){
return function(){
return this[_366].apply(this,arguments);
};
};
dojo.lang.curry=function(_367,func){
var _369=[];
_367=_367||dj_global;
if(dojo.lang.isString(func)){
func=_367[func];
}
for(var x=2;x<arguments.length;x++){
_369.push(arguments[x]);
}
var _36b=(func["__preJoinArity"]||func.length)-_369.length;
function gather(_36c,_36d,_36e){
var _36f=_36e;
var _370=_36d.slice(0);
for(var x=0;x<_36c.length;x++){
_370.push(_36c[x]);
}
_36e=_36e-_36c.length;
if(_36e<=0){
var res=func.apply(_367,_370);
_36e=_36f;
return res;
}else{
return function(){
return gather(arguments,_370,_36e);
};
}
}
return gather([],_369,_36b);
};
dojo.lang.curryArguments=function(_373,func,args,_376){
var _377=[];
var x=_376||0;
for(x=_376;x<args.length;x++){
_377.push(args[x]);
}
return dojo.lang.curry.apply(dojo.lang,[_373,func].concat(_377));
};
dojo.lang.tryThese=function(){
for(var x=0;x<arguments.length;x++){
try{
if(typeof arguments[x]=="function"){
var ret=(arguments[x]());
if(ret){
return ret;
}
}
}
catch(e){
dojo.debug(e);
}
}
};
dojo.lang.delayThese=function(farr,cb,_37d,_37e){
if(!farr.length){
if(typeof _37e=="function"){
_37e();
}
return;
}
if((typeof _37d=="undefined")&&(typeof cb=="number")){
_37d=cb;
cb=function(){
};
}else{
if(!cb){
cb=function(){
};
if(!_37d){
_37d=0;
}
}
}
setTimeout(function(){
(farr.shift())();
cb();
dojo.lang.delayThese(farr,cb,_37d,_37e);
},_37d);
};
dojo.provide("dojo.lfx.Animation");
dojo.lfx.Line=function(_37f,end){
this.start=_37f;
this.end=end;
if(dojo.lang.isArray(_37f)){
var diff=[];
dojo.lang.forEach(this.start,function(s,i){
diff[i]=this.end[i]-s;
},this);
this.getValue=function(n){
var res=[];
dojo.lang.forEach(this.start,function(s,i){
res[i]=(diff[i]*n)+s;
},this);
return res;
};
}else{
var diff=end-_37f;
this.getValue=function(n){
return (diff*n)+this.start;
};
}
};
dojo.lfx.easeDefault=function(n){
if(dojo.render.html.khtml){
return (parseFloat("0.5")+((Math.sin((n+parseFloat("1.5"))*Math.PI))/2));
}else{
return (0.5+((Math.sin((n+1.5)*Math.PI))/2));
}
};
dojo.lfx.easeIn=function(n){
return Math.pow(n,3);
};
dojo.lfx.easeOut=function(n){
return (1-Math.pow(1-n,3));
};
dojo.lfx.easeInOut=function(n){
return ((3*Math.pow(n,2))-(2*Math.pow(n,3)));
};
dojo.lfx.IAnimation=function(){
};
dojo.lang.extend(dojo.lfx.IAnimation,{curve:null,duration:1000,easing:null,repeatCount:0,rate:25,handler:null,beforeBegin:null,onBegin:null,onAnimate:null,onEnd:null,onPlay:null,onPause:null,onStop:null,play:null,pause:null,stop:null,connect:function(evt,_38e,_38f){
if(!_38f){
_38f=_38e;
_38e=this;
}
_38f=dojo.lang.hitch(_38e,_38f);
var _390=this[evt]||function(){
};
this[evt]=function(){
var ret=_390.apply(this,arguments);
_38f.apply(this,arguments);
return ret;
};
return this;
},fire:function(evt,args){
if(this[evt]){
this[evt].apply(this,(args||[]));
}
return this;
},repeat:function(_394){
this.repeatCount=_394;
return this;
},_active:false,_paused:false});
dojo.lfx.Animation=function(_395,_396,_397,_398,_399,rate){
dojo.lfx.IAnimation.call(this);
if(dojo.lang.isNumber(_395)||(!_395&&_396.getValue)){
rate=_399;
_399=_398;
_398=_397;
_397=_396;
_396=_395;
_395=null;
}else{
if(_395.getValue||dojo.lang.isArray(_395)){
rate=_398;
_399=_397;
_398=_396;
_397=_395;
_396=null;
_395=null;
}
}
if(dojo.lang.isArray(_397)){
this.curve=new dojo.lfx.Line(_397[0],_397[1]);
}else{
this.curve=_397;
}
if(_396!=null&&_396>0){
this.duration=_396;
}
if(_399){
this.repeatCount=_399;
}
if(rate){
this.rate=rate;
}
if(_395){
dojo.lang.forEach(["handler","beforeBegin","onBegin","onEnd","onPlay","onStop","onAnimate"],function(item){
if(_395[item]){
this.connect(item,_395[item]);
}
},this);
}
if(_398&&dojo.lang.isFunction(_398)){
this.easing=_398;
}
};
dojo.inherits(dojo.lfx.Animation,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Animation,{_startTime:null,_endTime:null,_timer:null,_percent:0,_startRepeatCount:0,play:function(_39c,_39d){
if(_39d){
clearTimeout(this._timer);
this._active=false;
this._paused=false;
this._percent=0;
}else{
if(this._active&&!this._paused){
return this;
}
}
this.fire("handler",["beforeBegin"]);
this.fire("beforeBegin");
if(_39c>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_39d);
}),_39c);
return this;
}
this._startTime=new Date().valueOf();
if(this._paused){
this._startTime-=(this.duration*this._percent/100);
}
this._endTime=this._startTime+this.duration;
this._active=true;
this._paused=false;
var step=this._percent/100;
var _39f=this.curve.getValue(step);
if(this._percent==0){
if(!this._startRepeatCount){
this._startRepeatCount=this.repeatCount;
}
this.fire("handler",["begin",_39f]);
this.fire("onBegin",[_39f]);
}
this.fire("handler",["play",_39f]);
this.fire("onPlay",[_39f]);
this._cycle();
return this;
},pause:function(){
clearTimeout(this._timer);
if(!this._active){
return this;
}
this._paused=true;
var _3a0=this.curve.getValue(this._percent/100);
this.fire("handler",["pause",_3a0]);
this.fire("onPause",[_3a0]);
return this;
},gotoPercent:function(pct,_3a2){
clearTimeout(this._timer);
this._active=true;
this._paused=true;
this._percent=pct;
if(_3a2){
this.play();
}
return this;
},stop:function(_3a3){
clearTimeout(this._timer);
var step=this._percent/100;
if(_3a3){
step=1;
}
var _3a5=this.curve.getValue(step);
this.fire("handler",["stop",_3a5]);
this.fire("onStop",[_3a5]);
this._active=false;
this._paused=false;
return this;
},status:function(){
if(this._active){
return this._paused?"paused":"playing";
}else{
return "stopped";
}
return this;
},_cycle:function(){
clearTimeout(this._timer);
if(this._active){
var curr=new Date().valueOf();
var step=(curr-this._startTime)/(this._endTime-this._startTime);
if(step>=1){
step=1;
this._percent=100;
}else{
this._percent=step*100;
}
if((this.easing)&&(dojo.lang.isFunction(this.easing))){
step=this.easing(step);
}
var _3a8=this.curve.getValue(step);
this.fire("handler",["animate",_3a8]);
this.fire("onAnimate",[_3a8]);
if(step<1){
this._timer=setTimeout(dojo.lang.hitch(this,"_cycle"),this.rate);
}else{
this._active=false;
this.fire("handler",["end"]);
this.fire("onEnd");
if(this.repeatCount>0){
this.repeatCount--;
this.play(null,true);
}else{
if(this.repeatCount==-1){
this.play(null,true);
}else{
if(this._startRepeatCount){
this.repeatCount=this._startRepeatCount;
this._startRepeatCount=0;
}
}
}
}
}
return this;
}});
dojo.lfx.Combine=function(_3a9){
dojo.lfx.IAnimation.call(this);
this._anims=[];
this._animsEnded=0;
var _3aa=arguments;
if(_3aa.length==1&&(dojo.lang.isArray(_3aa[0])||dojo.lang.isArrayLike(_3aa[0]))){
_3aa=_3aa[0];
}
dojo.lang.forEach(_3aa,function(anim){
this._anims.push(anim);
anim.connect("onEnd",dojo.lang.hitch(this,"_onAnimsEnded"));
},this);
};
dojo.inherits(dojo.lfx.Combine,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Combine,{_animsEnded:0,play:function(_3ac,_3ad){
if(!this._anims.length){
return this;
}
this.fire("beforeBegin");
if(_3ac>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_3ad);
}),_3ac);
return this;
}
if(_3ad||this._anims[0].percent==0){
this.fire("onBegin");
}
this.fire("onPlay");
this._animsCall("play",null,_3ad);
return this;
},pause:function(){
this.fire("onPause");
this._animsCall("pause");
return this;
},stop:function(_3ae){
this.fire("onStop");
this._animsCall("stop",_3ae);
return this;
},_onAnimsEnded:function(){
this._animsEnded++;
if(this._animsEnded>=this._anims.length){
this.fire("onEnd");
}
return this;
},_animsCall:function(_3af){
var args=[];
if(arguments.length>1){
for(var i=1;i<arguments.length;i++){
args.push(arguments[i]);
}
}
var _3b2=this;
dojo.lang.forEach(this._anims,function(anim){
anim[_3af](args);
},_3b2);
return this;
}});
dojo.lfx.Chain=function(_3b4){
dojo.lfx.IAnimation.call(this);
this._anims=[];
this._currAnim=-1;
var _3b5=arguments;
if(_3b5.length==1&&(dojo.lang.isArray(_3b5[0])||dojo.lang.isArrayLike(_3b5[0]))){
_3b5=_3b5[0];
}
var _3b6=this;
dojo.lang.forEach(_3b5,function(anim,i,_3b9){
this._anims.push(anim);
if(i<_3b9.length-1){
anim.connect("onEnd",dojo.lang.hitch(this,"_playNext"));
}else{
anim.connect("onEnd",dojo.lang.hitch(this,function(){
this.fire("onEnd");
}));
}
},this);
};
dojo.inherits(dojo.lfx.Chain,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Chain,{_currAnim:-1,play:function(_3ba,_3bb){
if(!this._anims.length){
return this;
}
if(_3bb||!this._anims[this._currAnim]){
this._currAnim=0;
}
var _3bc=this._anims[this._currAnim];
this.fire("beforeBegin");
if(_3ba>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_3bb);
}),_3ba);
return this;
}
if(_3bc){
if(this._currAnim==0){
this.fire("handler",["begin",this._currAnim]);
this.fire("onBegin",[this._currAnim]);
}
this.fire("onPlay",[this._currAnim]);
_3bc.play(null,_3bb);
}
return this;
},pause:function(){
if(this._anims[this._currAnim]){
this._anims[this._currAnim].pause();
this.fire("onPause",[this._currAnim]);
}
return this;
},playPause:function(){
if(this._anims.length==0){
return this;
}
if(this._currAnim==-1){
this._currAnim=0;
}
var _3bd=this._anims[this._currAnim];
if(_3bd){
if(!_3bd._active||_3bd._paused){
this.play();
}else{
this.pause();
}
}
return this;
},stop:function(){
var _3be=this._anims[this._currAnim];
if(_3be){
_3be.stop();
this.fire("onStop",[this._currAnim]);
}
return _3be;
},_playNext:function(){
if(this._currAnim==-1||this._anims.length==0){
return this;
}
this._currAnim++;
if(this._anims[this._currAnim]){
this._anims[this._currAnim].play(null,true);
}
return this;
}});
dojo.lfx.combine=function(_3bf){
var _3c0=arguments;
if(dojo.lang.isArray(arguments[0])){
_3c0=arguments[0];
}
if(_3c0.length==1){
return _3c0[0];
}
return new dojo.lfx.Combine(_3c0);
};
dojo.lfx.chain=function(_3c1){
var _3c2=arguments;
if(dojo.lang.isArray(arguments[0])){
_3c2=arguments[0];
}
if(_3c2.length==1){
return _3c2[0];
}
return new dojo.lfx.Chain(_3c2);
};
dojo.provide("dojo.html.color");
dojo.html.getBackgroundColor=function(node){
node=dojo.byId(node);
var _3c4;
do{
_3c4=dojo.html.getStyle(node,"background-color");
if(_3c4.toLowerCase()=="rgba(0, 0, 0, 0)"){
_3c4="transparent";
}
if(node==document.getElementsByTagName("body")[0]){
node=null;
break;
}
node=node.parentNode;
}while(node&&dojo.lang.inArray(["transparent",""],_3c4));
if(_3c4=="transparent"){
_3c4=[255,255,255,0];
}else{
_3c4=dojo.gfx.color.extractRGB(_3c4);
}
return _3c4;
};
dojo.provide("dojo.lfx.html");
dojo.lfx.html._byId=function(_3c5){
if(!_3c5){
return [];
}
if(dojo.lang.isArrayLike(_3c5)){
if(!_3c5.alreadyChecked){
var n=[];
dojo.lang.forEach(_3c5,function(node){
n.push(dojo.byId(node));
});
n.alreadyChecked=true;
return n;
}else{
return _3c5;
}
}else{
var n=[];
n.push(dojo.byId(_3c5));
n.alreadyChecked=true;
return n;
}
};
dojo.lfx.html.propertyAnimation=function(_3c8,_3c9,_3ca,_3cb,_3cc){
_3c8=dojo.lfx.html._byId(_3c8);
var _3cd={"propertyMap":_3c9,"nodes":_3c8,"duration":_3ca,"easing":_3cb||dojo.lfx.easeDefault};
var _3ce=function(args){
if(args.nodes.length==1){
var pm=args.propertyMap;
if(!dojo.lang.isArray(args.propertyMap)){
var parr=[];
for(var _3d2 in pm){
pm[_3d2].property=_3d2;
parr.push(pm[_3d2]);
}
pm=args.propertyMap=parr;
}
dojo.lang.forEach(pm,function(prop){
if(dj_undef("start",prop)){
if(prop.property!="opacity"){
prop.start=parseInt(dojo.html.getComputedStyle(args.nodes[0],prop.property));
}else{
prop.start=dojo.html.getOpacity(args.nodes[0]);
}
}
});
}
};
var _3d4=function(_3d5){
var _3d6=[];
dojo.lang.forEach(_3d5,function(c){
_3d6.push(Math.round(c));
});
return _3d6;
};
var _3d8=function(n,_3da){
n=dojo.byId(n);
if(!n||!n.style){
return;
}
for(var s in _3da){
try{
if(s=="opacity"){
dojo.html.setOpacity(n,_3da[s]);
}else{
n.style[s]=_3da[s];
}
}
catch(e){
dojo.debug(e);
}
}
};
var _3dc=function(_3dd){
this._properties=_3dd;
this.diffs=new Array(_3dd.length);
dojo.lang.forEach(_3dd,function(prop,i){
if(dojo.lang.isFunction(prop.start)){
prop.start=prop.start(prop,i);
}
if(dojo.lang.isFunction(prop.end)){
prop.end=prop.end(prop,i);
}
if(dojo.lang.isArray(prop.start)){
this.diffs[i]=null;
}else{
if(prop.start instanceof dojo.gfx.color.Color){
prop.startRgb=prop.start.toRgb();
prop.endRgb=prop.end.toRgb();
}else{
this.diffs[i]=prop.end-prop.start;
}
}
},this);
this.getValue=function(n){
var ret={};
dojo.lang.forEach(this._properties,function(prop,i){
var _3e4=null;
if(dojo.lang.isArray(prop.start)){
}else{
if(prop.start instanceof dojo.gfx.color.Color){
_3e4=(prop.units||"rgb")+"(";
for(var j=0;j<prop.startRgb.length;j++){
_3e4+=Math.round(((prop.endRgb[j]-prop.startRgb[j])*n)+prop.startRgb[j])+(j<prop.startRgb.length-1?",":"");
}
_3e4+=")";
}else{
_3e4=((this.diffs[i])*n)+prop.start+(prop.property!="opacity"?prop.units||"px":"");
}
}
ret[dojo.html.toCamelCase(prop.property)]=_3e4;
},this);
return ret;
};
};
var anim=new dojo.lfx.Animation({beforeBegin:function(){
_3ce(_3cd);
anim.curve=new _3dc(_3cd.propertyMap);
},onAnimate:function(_3e7){
dojo.lang.forEach(_3cd.nodes,function(node){
_3d8(node,_3e7);
});
}},_3cd.duration,null,_3cd.easing);
if(_3cc){
for(var x in _3cc){
if(dojo.lang.isFunction(_3cc[x])){
anim.connect(x,anim,_3cc[x]);
}
}
}
return anim;
};
dojo.lfx.html._makeFadeable=function(_3ea){
var _3eb=function(node){
if(dojo.render.html.ie){
if((node.style.zoom.length==0)&&(dojo.html.getStyle(node,"zoom")=="normal")){
node.style.zoom="1";
}
if((node.style.width.length==0)&&(dojo.html.getStyle(node,"width")=="auto")){
node.style.width="auto";
}
}
};
if(dojo.lang.isArrayLike(_3ea)){
dojo.lang.forEach(_3ea,_3eb);
}else{
_3eb(_3ea);
}
};
dojo.lfx.html.fade=function(_3ed,_3ee,_3ef,_3f0,_3f1){
_3ed=dojo.lfx.html._byId(_3ed);
var _3f2={property:"opacity"};
if(!dj_undef("start",_3ee)){
_3f2.start=_3ee.start;
}else{
_3f2.start=function(){
return dojo.html.getOpacity(_3ed[0]);
};
}
if(!dj_undef("end",_3ee)){
_3f2.end=_3ee.end;
}else{
dojo.raise("dojo.lfx.html.fade needs an end value");
}
var anim=dojo.lfx.propertyAnimation(_3ed,[_3f2],_3ef,_3f0);
anim.connect("beforeBegin",function(){
dojo.lfx.html._makeFadeable(_3ed);
});
if(_3f1){
anim.connect("onEnd",function(){
_3f1(_3ed,anim);
});
}
return anim;
};
dojo.lfx.html.fadeIn=function(_3f4,_3f5,_3f6,_3f7){
return dojo.lfx.html.fade(_3f4,{end:1},_3f5,_3f6,_3f7);
};
dojo.lfx.html.fadeOut=function(_3f8,_3f9,_3fa,_3fb){
return dojo.lfx.html.fade(_3f8,{end:0},_3f9,_3fa,_3fb);
};
dojo.lfx.html.fadeShow=function(_3fc,_3fd,_3fe,_3ff){
_3fc=dojo.lfx.html._byId(_3fc);
dojo.lang.forEach(_3fc,function(node){
dojo.html.setOpacity(node,0);
});
var anim=dojo.lfx.html.fadeIn(_3fc,_3fd,_3fe,_3ff);
anim.connect("beforeBegin",function(){
if(dojo.lang.isArrayLike(_3fc)){
dojo.lang.forEach(_3fc,dojo.html.show);
}else{
dojo.html.show(_3fc);
}
});
return anim;
};
dojo.lfx.html.fadeHide=function(_402,_403,_404,_405){
var anim=dojo.lfx.html.fadeOut(_402,_403,_404,function(){
if(dojo.lang.isArrayLike(_402)){
dojo.lang.forEach(_402,dojo.html.hide);
}else{
dojo.html.hide(_402);
}
if(_405){
_405(_402,anim);
}
});
return anim;
};
dojo.lfx.html.wipeIn=function(_407,_408,_409,_40a){
_407=dojo.lfx.html._byId(_407);
var _40b=[];
dojo.lang.forEach(_407,function(node){
var _40d={};
var _40e,_40f,_410;
with(node.style){
_40e=top;
_40f=left;
_410=position;
top="-9999px";
left="-9999px";
position="absolute";
display="";
}
var _411=dojo.html.getBorderBox(node).height;
with(node.style){
top=_40e;
left=_40f;
position=_410;
display="none";
}
var anim=dojo.lfx.propertyAnimation(node,{"height":{start:1,end:function(){
return _411;
}}},_408,_409);
anim.connect("beforeBegin",function(){
_40d.overflow=node.style.overflow;
_40d.height=node.style.height;
with(node.style){
overflow="hidden";
_411="1px";
}
dojo.html.show(node);
});
anim.connect("onEnd",function(){
with(node.style){
overflow=_40d.overflow;
_411=_40d.height;
}
if(_40a){
_40a(node,anim);
}
});
_40b.push(anim);
});
return dojo.lfx.combine(_40b);
};
dojo.lfx.html.wipeOut=function(_413,_414,_415,_416){
_413=dojo.lfx.html._byId(_413);
var _417=[];
dojo.lang.forEach(_413,function(node){
var _419={};
var anim=dojo.lfx.propertyAnimation(node,{"height":{start:function(){
return dojo.html.getContentBox(node).height;
},end:1}},_414,_415,{"beforeBegin":function(){
_419.overflow=node.style.overflow;
_419.height=node.style.height;
with(node.style){
overflow="hidden";
}
dojo.html.show(node);
},"onEnd":function(){
dojo.html.hide(node);
with(node.style){
overflow=_419.overflow;
height=_419.height;
}
if(_416){
_416(node,anim);
}
}});
_417.push(anim);
});
return dojo.lfx.combine(_417);
};
dojo.lfx.html.slideTo=function(_41b,_41c,_41d,_41e,_41f){
_41b=dojo.lfx.html._byId(_41b);
var _420=[];
var _421=dojo.html.getComputedStyle;
if(dojo.lang.isArray(_41c)){
dojo.deprecated("dojo.lfx.html.slideTo(node, array)","use dojo.lfx.html.slideTo(node, {top: value, left: value});","0.5");
_41c={top:_41c[0],left:_41c[1]};
}
dojo.lang.forEach(_41b,function(node){
var top=null;
var left=null;
var init=(function(){
var _426=node;
return function(){
var pos=_421(_426,"position");
top=(pos=="absolute"?node.offsetTop:parseInt(_421(node,"top"))||0);
left=(pos=="absolute"?node.offsetLeft:parseInt(_421(node,"left"))||0);
if(!dojo.lang.inArray(["absolute","relative"],pos)){
var ret=dojo.html.abs(_426,true);
dojo.html.setStyleAttributes(_426,"position:absolute;top:"+ret.y+"px;left:"+ret.x+"px;");
top=ret.y;
left=ret.x;
}
};
})();
init();
var anim=dojo.lfx.propertyAnimation(node,{"top":{start:top,end:(_41c.top||0)},"left":{start:left,end:(_41c.left||0)}},_41d,_41e,{"beforeBegin":init});
if(_41f){
anim.connect("onEnd",function(){
_41f(_41b,anim);
});
}
_420.push(anim);
});
return dojo.lfx.combine(_420);
};
dojo.lfx.html.slideBy=function(_42a,_42b,_42c,_42d,_42e){
_42a=dojo.lfx.html._byId(_42a);
var _42f=[];
var _430=dojo.html.getComputedStyle;
if(dojo.lang.isArray(_42b)){
dojo.deprecated("dojo.lfx.html.slideBy(node, array)","use dojo.lfx.html.slideBy(node, {top: value, left: value});","0.5");
_42b={top:_42b[0],left:_42b[1]};
}
dojo.lang.forEach(_42a,function(node){
var top=null;
var left=null;
var init=(function(){
var _435=node;
return function(){
var pos=_430(_435,"position");
top=(pos=="absolute"?node.offsetTop:parseInt(_430(node,"top"))||0);
left=(pos=="absolute"?node.offsetLeft:parseInt(_430(node,"left"))||0);
if(!dojo.lang.inArray(["absolute","relative"],pos)){
var ret=dojo.html.abs(_435,true);
dojo.html.setStyleAttributes(_435,"position:absolute;top:"+ret.y+"px;left:"+ret.x+"px;");
top=ret.y;
left=ret.x;
}
};
})();
init();
var anim=dojo.lfx.propertyAnimation(node,{"top":{start:top,end:top+(_42b.top||0)},"left":{start:left,end:left+(_42b.left||0)}},_42c,_42d).connect("beforeBegin",init);
if(_42e){
anim.connect("onEnd",function(){
_42e(_42a,anim);
});
}
_42f.push(anim);
});
return dojo.lfx.combine(_42f);
};
dojo.lfx.html.explode=function(_439,_43a,_43b,_43c,_43d){
var h=dojo.html;
_439=dojo.byId(_439);
_43a=dojo.byId(_43a);
var _43f=h.toCoordinateObject(_439,true);
var _440=document.createElement("div");
h.copyStyle(_440,_43a);
if(_43a.explodeClassName){
_440.className=_43a.explodeClassName;
}
with(_440.style){
position="absolute";
display="none";
var _441=h.getStyle(_439,"background-color");
backgroundColor=_441?_441.toLowerCase():"transparent";
backgroundColor=(backgroundColor=="transparent")?"rgb(221, 221, 221)":backgroundColor;
}
dojo.body().appendChild(_440);
with(_43a.style){
visibility="hidden";
display="block";
}
var _442=h.toCoordinateObject(_43a,true);
with(_43a.style){
display="none";
visibility="visible";
}
var _443={opacity:{start:0.5,end:1}};
dojo.lang.forEach(["height","width","top","left"],function(type){
_443[type]={start:_43f[type],end:_442[type]};
});
var anim=new dojo.lfx.propertyAnimation(_440,_443,_43b,_43c,{"beforeBegin":function(){
h.setDisplay(_440,"block");
},"onEnd":function(){
h.setDisplay(_43a,"block");
_440.parentNode.removeChild(_440);
}});
if(_43d){
anim.connect("onEnd",function(){
_43d(_43a,anim);
});
}
return anim;
};
dojo.lfx.html.implode=function(_446,end,_448,_449,_44a){
var h=dojo.html;
_446=dojo.byId(_446);
end=dojo.byId(end);
var _44c=dojo.html.toCoordinateObject(_446,true);
var _44d=dojo.html.toCoordinateObject(end,true);
var _44e=document.createElement("div");
dojo.html.copyStyle(_44e,_446);
if(_446.explodeClassName){
_44e.className=_446.explodeClassName;
}
dojo.html.setOpacity(_44e,0.3);
with(_44e.style){
position="absolute";
display="none";
backgroundColor=h.getStyle(_446,"background-color").toLowerCase();
}
dojo.body().appendChild(_44e);
var _44f={opacity:{start:1,end:0.5}};
dojo.lang.forEach(["height","width","top","left"],function(type){
_44f[type]={start:_44c[type],end:_44d[type]};
});
var anim=new dojo.lfx.propertyAnimation(_44e,_44f,_448,_449,{"beforeBegin":function(){
dojo.html.hide(_446);
dojo.html.show(_44e);
},"onEnd":function(){
_44e.parentNode.removeChild(_44e);
}});
if(_44a){
anim.connect("onEnd",function(){
_44a(_446,anim);
});
}
return anim;
};
dojo.lfx.html.highlight=function(_452,_453,_454,_455,_456){
_452=dojo.lfx.html._byId(_452);
var _457=[];
dojo.lang.forEach(_452,function(node){
var _459=dojo.html.getBackgroundColor(node);
var bg=dojo.html.getStyle(node,"background-color").toLowerCase();
var _45b=dojo.html.getStyle(node,"background-image");
var _45c=(bg=="transparent"||bg=="rgba(0, 0, 0, 0)");
while(_459.length>3){
_459.pop();
}
var rgb=new dojo.gfx.color.Color(_453);
var _45e=new dojo.gfx.color.Color(_459);
var anim=dojo.lfx.propertyAnimation(node,{"background-color":{start:rgb,end:_45e}},_454,_455,{"beforeBegin":function(){
if(_45b){
node.style.backgroundImage="none";
}
node.style.backgroundColor="rgb("+rgb.toRgb().join(",")+")";
},"onEnd":function(){
if(_45b){
node.style.backgroundImage=_45b;
}
if(_45c){
node.style.backgroundColor="transparent";
}
if(_456){
_456(node,anim);
}
}});
_457.push(anim);
});
return dojo.lfx.combine(_457);
};
dojo.lfx.html.unhighlight=function(_460,_461,_462,_463,_464){
_460=dojo.lfx.html._byId(_460);
var _465=[];
dojo.lang.forEach(_460,function(node){
var _467=new dojo.gfx.color.Color(dojo.html.getBackgroundColor(node));
var rgb=new dojo.gfx.color.Color(_461);
var _469=dojo.html.getStyle(node,"background-image");
var anim=dojo.lfx.propertyAnimation(node,{"background-color":{start:_467,end:rgb}},_462,_463,{"beforeBegin":function(){
if(_469){
node.style.backgroundImage="none";
}
node.style.backgroundColor="rgb("+_467.toRgb().join(",")+")";
},"onEnd":function(){
if(_464){
_464(node,anim);
}
}});
_465.push(anim);
});
return dojo.lfx.combine(_465);
};
dojo.lang.mixin(dojo.lfx,dojo.lfx.html);
dojo.provide("dojo.lfx.*");
dojo.provide("dojo.lang.extras");
dojo.lang.setTimeout=function(func,_46c){
var _46d=window,_46e=2;
if(!dojo.lang.isFunction(func)){
_46d=func;
func=_46c;
_46c=arguments[2];
_46e++;
}
if(dojo.lang.isString(func)){
func=_46d[func];
}
var args=[];
for(var i=_46e;i<arguments.length;i++){
args.push(arguments[i]);
}
return dojo.global().setTimeout(function(){
func.apply(_46d,args);
},_46c);
};
dojo.lang.clearTimeout=function(_471){
dojo.global().clearTimeout(_471);
};
dojo.lang.getNameInObj=function(ns,item){
if(!ns){
ns=dj_global;
}
for(var x in ns){
if(ns[x]===item){
return new String(x);
}
}
return null;
};
dojo.lang.shallowCopy=function(obj,deep){
var i,ret;
if(obj===null){
return null;
}
if(dojo.lang.isObject(obj)){
ret=new obj.constructor();
for(i in obj){
if(dojo.lang.isUndefined(ret[i])){
ret[i]=deep?dojo.lang.shallowCopy(obj[i],deep):obj[i];
}
}
}else{
if(dojo.lang.isArray(obj)){
ret=[];
for(i=0;i<obj.length;i++){
ret[i]=deep?dojo.lang.shallowCopy(obj[i],deep):obj[i];
}
}else{
ret=obj;
}
}
return ret;
};
dojo.lang.firstValued=function(){
for(var i=0;i<arguments.length;i++){
if(typeof arguments[i]!="undefined"){
return arguments[i];
}
}
return undefined;
};
dojo.lang.getObjPathValue=function(_47a,_47b,_47c){
with(dojo.parseObjPath(_47a,_47b,_47c)){
return dojo.evalProp(prop,obj,_47c);
}
};
dojo.lang.setObjPathValue=function(_47d,_47e,_47f,_480){
dojo.deprecated("dojo.lang.setObjPathValue","use dojo.parseObjPath and the '=' operator","0.6");
if(arguments.length<4){
_480=true;
}
with(dojo.parseObjPath(_47d,_47f,_480)){
if(obj&&(_480||(prop in obj))){
obj[prop]=_47e;
}
}
};
dojo.provide("dojo.event.common");
dojo.event=new function(){
this._canTimeout=dojo.lang.isFunction(dj_global["setTimeout"])||dojo.lang.isAlien(dj_global["setTimeout"]);
function interpolateArgs(args,_482){
var dl=dojo.lang;
var ao={srcObj:dj_global,srcFunc:null,adviceObj:dj_global,adviceFunc:null,aroundObj:null,aroundFunc:null,adviceType:(args.length>2)?args[0]:"after",precedence:"last",once:false,delay:null,rate:0,adviceMsg:false};
switch(args.length){
case 0:
return;
case 1:
return;
case 2:
ao.srcFunc=args[0];
ao.adviceFunc=args[1];
break;
case 3:
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isFunction(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
var _485=dl.nameAnonFunc(args[2],ao.adviceObj,_482);
ao.adviceFunc=_485;
}else{
if((dl.isFunction(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=dj_global;
var _485=dl.nameAnonFunc(args[0],ao.srcObj,_482);
ao.srcFunc=_485;
ao.adviceObj=args[1];
ao.adviceFunc=args[2];
}
}
}
}
break;
case 4:
if((dl.isObject(args[0]))&&(dl.isObject(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isString(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isFunction(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
var _485=dl.nameAnonFunc(args[1],dj_global,_482);
ao.srcFunc=_485;
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))&&(dl.isFunction(args[3]))){
ao.srcObj=args[1];
ao.srcFunc=args[2];
var _485=dl.nameAnonFunc(args[3],dj_global,_482);
ao.adviceObj=dj_global;
ao.adviceFunc=_485;
}else{
if(dl.isObject(args[1])){
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=dj_global;
ao.adviceFunc=args[3];
}else{
if(dl.isObject(args[2])){
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
ao.srcObj=ao.adviceObj=ao.aroundObj=dj_global;
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
ao.aroundFunc=args[3];
}
}
}
}
}
}
break;
case 6:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundFunc=args[5];
ao.aroundObj=dj_global;
break;
default:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundObj=args[5];
ao.aroundFunc=args[6];
ao.once=args[7];
ao.delay=args[8];
ao.rate=args[9];
ao.adviceMsg=args[10];
break;
}
if(dl.isFunction(ao.aroundFunc)){
var _485=dl.nameAnonFunc(ao.aroundFunc,ao.aroundObj,_482);
ao.aroundFunc=_485;
}
if(dl.isFunction(ao.srcFunc)){
ao.srcFunc=dl.getNameInObj(ao.srcObj,ao.srcFunc);
}
if(dl.isFunction(ao.adviceFunc)){
ao.adviceFunc=dl.getNameInObj(ao.adviceObj,ao.adviceFunc);
}
if((ao.aroundObj)&&(dl.isFunction(ao.aroundFunc))){
ao.aroundFunc=dl.getNameInObj(ao.aroundObj,ao.aroundFunc);
}
if(!ao.srcObj){
dojo.raise("bad srcObj for srcFunc: "+ao.srcFunc);
}
if(!ao.adviceObj){
dojo.raise("bad adviceObj for adviceFunc: "+ao.adviceFunc);
}
if(!ao.adviceFunc){
dojo.debug("bad adviceFunc for srcFunc: "+ao.srcFunc);
dojo.debugShallow(ao);
}
return ao;
}
this.connect=function(){
if(arguments.length==1){
var ao=arguments[0];
}else{
var ao=interpolateArgs(arguments,true);
}
if(dojo.lang.isString(ao.srcFunc)&&(ao.srcFunc.toLowerCase()=="onkey")){
if(dojo.render.html.ie){
ao.srcFunc="onkeydown";
this.connect(ao);
}
ao.srcFunc="onkeypress";
}
if(dojo.lang.isArray(ao.srcObj)&&ao.srcObj!=""){
var _487={};
for(var x in ao){
_487[x]=ao[x];
}
var mjps=[];
dojo.lang.forEach(ao.srcObj,function(src){
if((dojo.render.html.capable)&&(dojo.lang.isString(src))){
src=dojo.byId(src);
}
_487.srcObj=src;
mjps.push(dojo.event.connect.call(dojo.event,_487));
});
return mjps;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
if(ao.adviceFunc){
var mjp2=dojo.event.MethodJoinPoint.getForMethod(ao.adviceObj,ao.adviceFunc);
}
mjp.kwAddAdvice(ao);
return mjp;
};
this.log=function(a1,a2){
var _48f;
if((arguments.length==1)&&(typeof a1=="object")){
_48f=a1;
}else{
_48f={srcObj:a1,srcFunc:a2};
}
_48f.adviceFunc=function(){
var _490=[];
for(var x=0;x<arguments.length;x++){
_490.push(arguments[x]);
}
dojo.debug("("+_48f.srcObj+")."+_48f.srcFunc,":",_490.join(", "));
};
this.kwConnect(_48f);
};
this.connectBefore=function(){
var args=["before"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectAround=function(){
var args=["around"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectOnce=function(){
var ao=interpolateArgs(arguments,true);
ao.once=true;
return this.connect(ao);
};
this._kwConnectImpl=function(_497,_498){
var fn=(_498)?"disconnect":"connect";
if(typeof _497["srcFunc"]=="function"){
_497.srcObj=_497["srcObj"]||dj_global;
var _49a=dojo.lang.nameAnonFunc(_497.srcFunc,_497.srcObj,true);
_497.srcFunc=_49a;
}
if(typeof _497["adviceFunc"]=="function"){
_497.adviceObj=_497["adviceObj"]||dj_global;
var _49a=dojo.lang.nameAnonFunc(_497.adviceFunc,_497.adviceObj,true);
_497.adviceFunc=_49a;
}
_497.srcObj=_497["srcObj"]||dj_global;
_497.adviceObj=_497["adviceObj"]||_497["targetObj"]||dj_global;
_497.adviceFunc=_497["adviceFunc"]||_497["targetFunc"];
return dojo.event[fn](_497);
};
this.kwConnect=function(_49b){
return this._kwConnectImpl(_49b,false);
};
this.disconnect=function(){
if(arguments.length==1){
var ao=arguments[0];
}else{
var ao=interpolateArgs(arguments,true);
}
if(!ao.adviceFunc){
return;
}
if(dojo.lang.isString(ao.srcFunc)&&(ao.srcFunc.toLowerCase()=="onkey")){
if(dojo.render.html.ie){
ao.srcFunc="onkeydown";
this.disconnect(ao);
}
ao.srcFunc="onkeypress";
}
if(!ao.srcObj[ao.srcFunc]){
return null;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc,true);
mjp.removeAdvice(ao.adviceObj,ao.adviceFunc,ao.adviceType,ao.once);
return mjp;
};
this.kwDisconnect=function(_49e){
return this._kwConnectImpl(_49e,true);
};
};
dojo.event.MethodInvocation=function(_49f,obj,args){
this.jp_=_49f;
this.object=obj;
this.args=[];
for(var x=0;x<args.length;x++){
this.args[x]=args[x];
}
this.around_index=-1;
};
dojo.event.MethodInvocation.prototype.proceed=function(){
this.around_index++;
if(this.around_index>=this.jp_.around.length){
return this.jp_.object[this.jp_.methodname].apply(this.jp_.object,this.args);
}else{
var ti=this.jp_.around[this.around_index];
var mobj=ti[0]||dj_global;
var meth=ti[1];
return mobj[meth].call(mobj,this);
}
};
dojo.event.MethodJoinPoint=function(obj,_4a7){
this.object=obj||dj_global;
this.methodname=_4a7;
this.methodfunc=this.object[_4a7];
this.squelch=false;
};
dojo.event.MethodJoinPoint.getForMethod=function(obj,_4a9){
if(!obj){
obj=dj_global;
}
if(!obj[_4a9]){
obj[_4a9]=function(){
};
if(!obj[_4a9]){
dojo.raise("Cannot set do-nothing method on that object "+_4a9);
}
}else{
if((!dojo.lang.isFunction(obj[_4a9]))&&(!dojo.lang.isAlien(obj[_4a9]))){
return null;
}
}
var _4aa=_4a9+"$joinpoint";
var _4ab=_4a9+"$joinpoint$method";
var _4ac=obj[_4aa];
if(!_4ac){
var _4ad=false;
if(dojo.event["browser"]){
if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
_4ad=true;
dojo.event.browser.addClobberNodeAttrs(obj,[_4aa,_4ab,_4a9]);
}
}
var _4ae=obj[_4a9].length;
obj[_4ab]=obj[_4a9];
_4ac=obj[_4aa]=new dojo.event.MethodJoinPoint(obj,_4ab);
obj[_4a9]=function(){
var args=[];
if((_4ad)&&(!arguments.length)){
var evt=null;
try{
if(obj.ownerDocument){
evt=obj.ownerDocument.parentWindow.event;
}else{
if(obj.documentElement){
evt=obj.documentElement.ownerDocument.parentWindow.event;
}else{
if(obj.event){
evt=obj.event;
}else{
evt=window.event;
}
}
}
}
catch(e){
evt=window.event;
}
if(evt){
args.push(dojo.event.browser.fixEvent(evt,this));
}
}else{
for(var x=0;x<arguments.length;x++){
if((x==0)&&(_4ad)&&(dojo.event.browser.isEvent(arguments[x]))){
args.push(dojo.event.browser.fixEvent(arguments[x],this));
}else{
args.push(arguments[x]);
}
}
}
return _4ac.run.apply(_4ac,args);
};
obj[_4a9].__preJoinArity=_4ae;
}
return _4ac;
};
dojo.lang.extend(dojo.event.MethodJoinPoint,{unintercept:function(){
this.object[this.methodname]=this.methodfunc;
this.before=[];
this.after=[];
this.around=[];
},disconnect:dojo.lang.forward("unintercept"),run:function(){
var obj=this.object||dj_global;
var args=arguments;
var _4b4=[];
for(var x=0;x<args.length;x++){
_4b4[x]=args[x];
}
var _4b6=function(marr){
if(!marr){
dojo.debug("Null argument to unrollAdvice()");
return;
}
var _4b8=marr[0]||dj_global;
var _4b9=marr[1];
if(!_4b8[_4b9]){
dojo.raise("function \""+_4b9+"\" does not exist on \""+_4b8+"\"");
}
var _4ba=marr[2]||dj_global;
var _4bb=marr[3];
var msg=marr[6];
var _4bd;
var to={args:[],jp_:this,object:obj,proceed:function(){
return _4b8[_4b9].apply(_4b8,to.args);
}};
to.args=_4b4;
var _4bf=parseInt(marr[4]);
var _4c0=((!isNaN(_4bf))&&(marr[4]!==null)&&(typeof marr[4]!="undefined"));
if(marr[5]){
var rate=parseInt(marr[5]);
var cur=new Date();
var _4c3=false;
if((marr["last"])&&((cur-marr.last)<=rate)){
if(dojo.event._canTimeout){
if(marr["delayTimer"]){
clearTimeout(marr.delayTimer);
}
var tod=parseInt(rate*2);
var mcpy=dojo.lang.shallowCopy(marr);
marr.delayTimer=setTimeout(function(){
mcpy[5]=0;
_4b6(mcpy);
},tod);
}
return;
}else{
marr.last=cur;
}
}
if(_4bb){
_4ba[_4bb].call(_4ba,to);
}else{
if((_4c0)&&((dojo.render.html)||(dojo.render.svg))){
dj_global["setTimeout"](function(){
if(msg){
_4b8[_4b9].call(_4b8,to);
}else{
_4b8[_4b9].apply(_4b8,args);
}
},_4bf);
}else{
if(msg){
_4b8[_4b9].call(_4b8,to);
}else{
_4b8[_4b9].apply(_4b8,args);
}
}
}
};
var _4c6=function(){
if(this.squelch){
try{
return _4b6.apply(this,arguments);
}
catch(e){
dojo.debug(e);
}
}else{
return _4b6.apply(this,arguments);
}
};
if((this["before"])&&(this.before.length>0)){
dojo.lang.forEach(this.before.concat(new Array()),_4c6);
}
var _4c7;
try{
if((this["around"])&&(this.around.length>0)){
var mi=new dojo.event.MethodInvocation(this,obj,args);
_4c7=mi.proceed();
}else{
if(this.methodfunc){
_4c7=this.object[this.methodname].apply(this.object,args);
}
}
}
catch(e){
if(!this.squelch){
dojo.debug(e,"when calling",this.methodname,"on",this.object,"with arguments",args);
dojo.raise(e);
}
}
if((this["after"])&&(this.after.length>0)){
dojo.lang.forEach(this.after.concat(new Array()),_4c6);
}
return (this.methodfunc)?_4c7:null;
},getArr:function(kind){
var type="after";
if((typeof kind=="string")&&(kind.indexOf("before")!=-1)){
type="before";
}else{
if(kind=="around"){
type="around";
}
}
if(!this[type]){
this[type]=[];
}
return this[type];
},kwAddAdvice:function(args){
this.addAdvice(args["adviceObj"],args["adviceFunc"],args["aroundObj"],args["aroundFunc"],args["adviceType"],args["precedence"],args["once"],args["delay"],args["rate"],args["adviceMsg"]);
},addAdvice:function(_4cc,_4cd,_4ce,_4cf,_4d0,_4d1,once,_4d3,rate,_4d5){
var arr=this.getArr(_4d0);
if(!arr){
dojo.raise("bad this: "+this);
}
var ao=[_4cc,_4cd,_4ce,_4cf,_4d3,rate,_4d5];
if(once){
if(this.hasAdvice(_4cc,_4cd,_4d0,arr)>=0){
return;
}
}
if(_4d1=="first"){
arr.unshift(ao);
}else{
arr.push(ao);
}
},hasAdvice:function(_4d8,_4d9,_4da,arr){
if(!arr){
arr=this.getArr(_4da);
}
var ind=-1;
for(var x=0;x<arr.length;x++){
var aao=(typeof _4d9=="object")?(new String(_4d9)).toString():_4d9;
var a1o=(typeof arr[x][1]=="object")?(new String(arr[x][1])).toString():arr[x][1];
if((arr[x][0]==_4d8)&&(a1o==aao)){
ind=x;
}
}
return ind;
},removeAdvice:function(_4e0,_4e1,_4e2,once){
var arr=this.getArr(_4e2);
var ind=this.hasAdvice(_4e0,_4e1,_4e2,arr);
if(ind==-1){
return false;
}
while(ind!=-1){
arr.splice(ind,1);
if(once){
break;
}
ind=this.hasAdvice(_4e0,_4e1,_4e2,arr);
}
return true;
}});
dojo.provide("dojo.event.topic");
dojo.event.topic=new function(){
this.topics={};
this.getTopic=function(_4e6){
if(!this.topics[_4e6]){
this.topics[_4e6]=new this.TopicImpl(_4e6);
}
return this.topics[_4e6];
};
this.registerPublisher=function(_4e7,obj,_4e9){
var _4e7=this.getTopic(_4e7);
_4e7.registerPublisher(obj,_4e9);
};
this.subscribe=function(_4ea,obj,_4ec){
var _4ea=this.getTopic(_4ea);
_4ea.subscribe(obj,_4ec);
};
this.unsubscribe=function(_4ed,obj,_4ef){
var _4ed=this.getTopic(_4ed);
_4ed.unsubscribe(obj,_4ef);
};
this.destroy=function(_4f0){
this.getTopic(_4f0).destroy();
delete this.topics[_4f0];
};
this.publishApply=function(_4f1,args){
var _4f1=this.getTopic(_4f1);
_4f1.sendMessage.apply(_4f1,args);
};
this.publish=function(_4f3,_4f4){
var _4f3=this.getTopic(_4f3);
var args=[];
for(var x=1;x<arguments.length;x++){
args.push(arguments[x]);
}
_4f3.sendMessage.apply(_4f3,args);
};
};
dojo.event.topic.TopicImpl=function(_4f7){
this.topicName=_4f7;
this.subscribe=function(_4f8,_4f9){
var tf=_4f9||_4f8;
var to=(!_4f9)?dj_global:_4f8;
return dojo.event.kwConnect({srcObj:this,srcFunc:"sendMessage",adviceObj:to,adviceFunc:tf});
};
this.unsubscribe=function(_4fc,_4fd){
var tf=(!_4fd)?_4fc:_4fd;
var to=(!_4fd)?null:_4fc;
return dojo.event.kwDisconnect({srcObj:this,srcFunc:"sendMessage",adviceObj:to,adviceFunc:tf});
};
this._getJoinPoint=function(){
return dojo.event.MethodJoinPoint.getForMethod(this,"sendMessage");
};
this.setSquelch=function(_500){
this._getJoinPoint().squelch=_500;
};
this.destroy=function(){
this._getJoinPoint().disconnect();
};
this.registerPublisher=function(_501,_502){
dojo.event.connect(_501,_502,this,"sendMessage");
};
this.sendMessage=function(_503){
};
};
dojo.provide("dojo.event.browser");
dojo._ie_clobber=new function(){
this.clobberNodes=[];
function nukeProp(node,prop){
try{
node[prop]=null;
}
catch(e){
}
try{
delete node[prop];
}
catch(e){
}
try{
node.removeAttribute(prop);
}
catch(e){
}
}
this.clobber=function(_506){
var na;
var tna;
if(_506){
tna=_506.all||_506.getElementsByTagName("*");
na=[_506];
for(var x=0;x<tna.length;x++){
if(tna[x]["__doClobber__"]){
na.push(tna[x]);
}
}
}else{
try{
window.onload=null;
}
catch(e){
}
na=(this.clobberNodes.length)?this.clobberNodes:document.all;
}
tna=null;
var _50a={};
for(var i=na.length-1;i>=0;i=i-1){
var el=na[i];
try{
if(el&&el["__clobberAttrs__"]){
for(var j=0;j<el.__clobberAttrs__.length;j++){
nukeProp(el,el.__clobberAttrs__[j]);
}
nukeProp(el,"__clobberAttrs__");
nukeProp(el,"__doClobber__");
}
}
catch(e){
}
}
na=null;
};
};
if(dojo.render.html.ie){
dojo.addOnUnload(function(){
dojo._ie_clobber.clobber();
try{
if((dojo["widget"])&&(dojo.widget["manager"])){
dojo.widget.manager.destroyAll();
}
}
catch(e){
}
if(dojo.widget){
for(var name in dojo.widget._templateCache){
if(dojo.widget._templateCache[name].node){
dojo.dom.destroyNode(dojo.widget._templateCache[name].node);
dojo.widget._templateCache[name].node=null;
delete dojo.widget._templateCache[name].node;
}
}
}
try{
window.onload=null;
}
catch(e){
}
try{
window.onunload=null;
}
catch(e){
}
dojo._ie_clobber.clobberNodes=[];
});
}
dojo.event.browser=new function(){
var _50f=0;
this.normalizedEventName=function(_510){
switch(_510){
case "CheckboxStateChange":
case "DOMAttrModified":
case "DOMMenuItemActive":
case "DOMMenuItemInactive":
case "DOMMouseScroll":
case "DOMNodeInserted":
case "DOMNodeRemoved":
case "RadioStateChange":
return _510;
break;
default:
return _510.toLowerCase();
break;
}
};
this.clean=function(node){
if(dojo.render.html.ie){
dojo._ie_clobber.clobber(node);
}
};
this.addClobberNode=function(node){
if(!dojo.render.html.ie){
return;
}
if(!node["__doClobber__"]){
node.__doClobber__=true;
dojo._ie_clobber.clobberNodes.push(node);
node.__clobberAttrs__=[];
}
};
this.addClobberNodeAttrs=function(node,_514){
if(!dojo.render.html.ie){
return;
}
this.addClobberNode(node);
for(var x=0;x<_514.length;x++){
node.__clobberAttrs__.push(_514[x]);
}
};
this.removeListener=function(node,_517,fp,_519){
if(!_519){
var _519=false;
}
_517=dojo.event.browser.normalizedEventName(_517);
if((_517=="onkey")||(_517=="key")){
if(dojo.render.html.ie){
this.removeListener(node,"onkeydown",fp,_519);
}
_517="onkeypress";
}
if(_517.substr(0,2)=="on"){
_517=_517.substr(2);
}
if(node.removeEventListener){
node.removeEventListener(_517,fp,_519);
}
};
this.addListener=function(node,_51b,fp,_51d,_51e){
if(!node){
return;
}
if(!_51d){
var _51d=false;
}
_51b=dojo.event.browser.normalizedEventName(_51b);
if((_51b=="onkey")||(_51b=="key")){
if(dojo.render.html.ie){
this.addListener(node,"onkeydown",fp,_51d,_51e);
}
_51b="onkeypress";
}
if(_51b.substr(0,2)!="on"){
_51b="on"+_51b;
}
if(!_51e){
var _51f=function(evt){
if(!evt){
evt=window.event;
}
var ret=fp(dojo.event.browser.fixEvent(evt,this));
if(_51d){
dojo.event.browser.stopEvent(evt);
}
return ret;
};
}else{
_51f=fp;
}
if(node.addEventListener){
node.addEventListener(_51b.substr(2),_51f,_51d);
return _51f;
}else{
if(typeof node[_51b]=="function"){
var _522=node[_51b];
node[_51b]=function(e){
_522(e);
return _51f(e);
};
}else{
node[_51b]=_51f;
}
if(dojo.render.html.ie){
this.addClobberNodeAttrs(node,[_51b]);
}
return _51f;
}
};
this.isEvent=function(obj){
return (typeof obj!="undefined")&&(obj)&&(typeof Event!="undefined")&&(obj.eventPhase);
};
this.currentEvent=null;
this.callListener=function(_525,_526){
if(typeof _525!="function"){
dojo.raise("listener not a function: "+_525);
}
dojo.event.browser.currentEvent.currentTarget=_526;
return _525.call(_526,dojo.event.browser.currentEvent);
};
this._stopPropagation=function(){
dojo.event.browser.currentEvent.cancelBubble=true;
};
this._preventDefault=function(){
dojo.event.browser.currentEvent.returnValue=false;
};
this.keys={KEY_BACKSPACE:8,KEY_TAB:9,KEY_CLEAR:12,KEY_ENTER:13,KEY_SHIFT:16,KEY_CTRL:17,KEY_ALT:18,KEY_PAUSE:19,KEY_CAPS_LOCK:20,KEY_ESCAPE:27,KEY_SPACE:32,KEY_PAGE_UP:33,KEY_PAGE_DOWN:34,KEY_END:35,KEY_HOME:36,KEY_LEFT_ARROW:37,KEY_UP_ARROW:38,KEY_RIGHT_ARROW:39,KEY_DOWN_ARROW:40,KEY_INSERT:45,KEY_DELETE:46,KEY_HELP:47,KEY_LEFT_WINDOW:91,KEY_RIGHT_WINDOW:92,KEY_SELECT:93,KEY_NUMPAD_0:96,KEY_NUMPAD_1:97,KEY_NUMPAD_2:98,KEY_NUMPAD_3:99,KEY_NUMPAD_4:100,KEY_NUMPAD_5:101,KEY_NUMPAD_6:102,KEY_NUMPAD_7:103,KEY_NUMPAD_8:104,KEY_NUMPAD_9:105,KEY_NUMPAD_MULTIPLY:106,KEY_NUMPAD_PLUS:107,KEY_NUMPAD_ENTER:108,KEY_NUMPAD_MINUS:109,KEY_NUMPAD_PERIOD:110,KEY_NUMPAD_DIVIDE:111,KEY_F1:112,KEY_F2:113,KEY_F3:114,KEY_F4:115,KEY_F5:116,KEY_F6:117,KEY_F7:118,KEY_F8:119,KEY_F9:120,KEY_F10:121,KEY_F11:122,KEY_F12:123,KEY_F13:124,KEY_F14:125,KEY_F15:126,KEY_NUM_LOCK:144,KEY_SCROLL_LOCK:145};
this.revKeys=[];
for(var key in this.keys){
this.revKeys[this.keys[key]]=key;
}
this.fixEvent=function(evt,_529){
if(!evt){
if(window["event"]){
evt=window.event;
}
}
if((evt["type"])&&(evt["type"].indexOf("key")==0)){
evt.keys=this.revKeys;
for(var key in this.keys){
evt[key]=this.keys[key];
}
if(evt["type"]=="keydown"&&dojo.render.html.ie){
switch(evt.keyCode){
case evt.KEY_SHIFT:
case evt.KEY_CTRL:
case evt.KEY_ALT:
case evt.KEY_CAPS_LOCK:
case evt.KEY_LEFT_WINDOW:
case evt.KEY_RIGHT_WINDOW:
case evt.KEY_SELECT:
case evt.KEY_NUM_LOCK:
case evt.KEY_SCROLL_LOCK:
case evt.KEY_NUMPAD_0:
case evt.KEY_NUMPAD_1:
case evt.KEY_NUMPAD_2:
case evt.KEY_NUMPAD_3:
case evt.KEY_NUMPAD_4:
case evt.KEY_NUMPAD_5:
case evt.KEY_NUMPAD_6:
case evt.KEY_NUMPAD_7:
case evt.KEY_NUMPAD_8:
case evt.KEY_NUMPAD_9:
case evt.KEY_NUMPAD_PERIOD:
break;
case evt.KEY_NUMPAD_MULTIPLY:
case evt.KEY_NUMPAD_PLUS:
case evt.KEY_NUMPAD_ENTER:
case evt.KEY_NUMPAD_MINUS:
case evt.KEY_NUMPAD_DIVIDE:
break;
case evt.KEY_PAUSE:
case evt.KEY_TAB:
case evt.KEY_BACKSPACE:
case evt.KEY_ENTER:
case evt.KEY_ESCAPE:
case evt.KEY_PAGE_UP:
case evt.KEY_PAGE_DOWN:
case evt.KEY_END:
case evt.KEY_HOME:
case evt.KEY_LEFT_ARROW:
case evt.KEY_UP_ARROW:
case evt.KEY_RIGHT_ARROW:
case evt.KEY_DOWN_ARROW:
case evt.KEY_INSERT:
case evt.KEY_DELETE:
case evt.KEY_F1:
case evt.KEY_F2:
case evt.KEY_F3:
case evt.KEY_F4:
case evt.KEY_F5:
case evt.KEY_F6:
case evt.KEY_F7:
case evt.KEY_F8:
case evt.KEY_F9:
case evt.KEY_F10:
case evt.KEY_F11:
case evt.KEY_F12:
case evt.KEY_F12:
case evt.KEY_F13:
case evt.KEY_F14:
case evt.KEY_F15:
case evt.KEY_CLEAR:
case evt.KEY_HELP:
evt.key=evt.keyCode;
break;
default:
if(evt.ctrlKey||evt.altKey){
var _52b=evt.keyCode;
if(_52b>=65&&_52b<=90&&evt.shiftKey==false){
_52b+=32;
}
if(_52b>=1&&_52b<=26&&evt.ctrlKey){
_52b+=96;
}
evt.key=String.fromCharCode(_52b);
}
}
}else{
if(evt["type"]=="keypress"){
if(dojo.render.html.opera){
if(evt.which==0){
evt.key=evt.keyCode;
}else{
if(evt.which>0){
switch(evt.which){
case evt.KEY_SHIFT:
case evt.KEY_CTRL:
case evt.KEY_ALT:
case evt.KEY_CAPS_LOCK:
case evt.KEY_NUM_LOCK:
case evt.KEY_SCROLL_LOCK:
break;
case evt.KEY_PAUSE:
case evt.KEY_TAB:
case evt.KEY_BACKSPACE:
case evt.KEY_ENTER:
case evt.KEY_ESCAPE:
evt.key=evt.which;
break;
default:
var _52b=evt.which;
if((evt.ctrlKey||evt.altKey||evt.metaKey)&&(evt.which>=65&&evt.which<=90&&evt.shiftKey==false)){
_52b+=32;
}
evt.key=String.fromCharCode(_52b);
}
}
}
}else{
if(dojo.render.html.ie){
if(!evt.ctrlKey&&!evt.altKey&&evt.keyCode>=evt.KEY_SPACE){
evt.key=String.fromCharCode(evt.keyCode);
}
}else{
if(dojo.render.html.safari){
switch(evt.keyCode){
case 25:
evt.key=evt.KEY_TAB;
evt.shift=true;
break;
case 63232:
evt.key=evt.KEY_UP_ARROW;
break;
case 63233:
evt.key=evt.KEY_DOWN_ARROW;
break;
case 63234:
evt.key=evt.KEY_LEFT_ARROW;
break;
case 63235:
evt.key=evt.KEY_RIGHT_ARROW;
break;
case 63236:
evt.key=evt.KEY_F1;
break;
case 63237:
evt.key=evt.KEY_F2;
break;
case 63238:
evt.key=evt.KEY_F3;
break;
case 63239:
evt.key=evt.KEY_F4;
break;
case 63240:
evt.key=evt.KEY_F5;
break;
case 63241:
evt.key=evt.KEY_F6;
break;
case 63242:
evt.key=evt.KEY_F7;
break;
case 63243:
evt.key=evt.KEY_F8;
break;
case 63244:
evt.key=evt.KEY_F9;
break;
case 63245:
evt.key=evt.KEY_F10;
break;
case 63246:
evt.key=evt.KEY_F11;
break;
case 63247:
evt.key=evt.KEY_F12;
break;
case 63250:
evt.key=evt.KEY_PAUSE;
break;
case 63272:
evt.key=evt.KEY_DELETE;
break;
case 63273:
evt.key=evt.KEY_HOME;
break;
case 63275:
evt.key=evt.KEY_END;
break;
case 63276:
evt.key=evt.KEY_PAGE_UP;
break;
case 63277:
evt.key=evt.KEY_PAGE_DOWN;
break;
case 63302:
evt.key=evt.KEY_INSERT;
break;
case 63248:
case 63249:
case 63289:
break;
default:
evt.key=evt.charCode>=evt.KEY_SPACE?String.fromCharCode(evt.charCode):evt.keyCode;
}
}else{
evt.key=evt.charCode>0?String.fromCharCode(evt.charCode):evt.keyCode;
}
}
}
}
}
}
if(dojo.render.html.ie){
if(!evt.target){
evt.target=evt.srcElement;
}
if(!evt.currentTarget){
evt.currentTarget=(_529?_529:evt.srcElement);
}
if(!evt.layerX){
evt.layerX=evt.offsetX;
}
if(!evt.layerY){
evt.layerY=evt.offsetY;
}
var doc=(evt.srcElement&&evt.srcElement.ownerDocument)?evt.srcElement.ownerDocument:document;
var _52d=((dojo.render.html.ie55)||(doc["compatMode"]=="BackCompat"))?doc.body:doc.documentElement;
if(!evt.pageX){
evt.pageX=evt.clientX+(_52d.scrollLeft||0);
}
if(!evt.pageY){
evt.pageY=evt.clientY+(_52d.scrollTop||0);
}
if(evt.type=="mouseover"){
evt.relatedTarget=evt.fromElement;
}
if(evt.type=="mouseout"){
evt.relatedTarget=evt.toElement;
}
this.currentEvent=evt;
evt.callListener=this.callListener;
evt.stopPropagation=this._stopPropagation;
evt.preventDefault=this._preventDefault;
}
return evt;
};
this.stopEvent=function(evt){
if(window.event){
evt.cancelBubble=true;
evt.returnValue=false;
}else{
evt.preventDefault();
evt.stopPropagation();
}
};
};
dojo.provide("dojo.event.*");
dojo.provide("dojo.lang.declare");
dojo.lang.declare=function(_52f,_530,init,_532){
if((dojo.lang.isFunction(_532))||((!_532)&&(!dojo.lang.isFunction(init)))){
var temp=_532;
_532=init;
init=temp;
}
var _534=[];
if(dojo.lang.isArray(_530)){
_534=_530;
_530=_534.shift();
}
if(!init){
init=dojo.evalObjPath(_52f,false);
if((init)&&(!dojo.lang.isFunction(init))){
init=null;
}
}
var ctor=dojo.lang.declare._makeConstructor();
var scp=(_530?_530.prototype:null);
if(scp){
scp.prototyping=true;
ctor.prototype=new _530();
scp.prototyping=false;
}
ctor.superclass=scp;
ctor.mixins=_534;
for(var i=0,l=_534.length;i<l;i++){
dojo.lang.extend(ctor,_534[i].prototype);
}
ctor.prototype.initializer=null;
ctor.prototype.declaredClass=_52f;
if(dojo.lang.isArray(_532)){
dojo.lang.extend.apply(dojo.lang,[ctor].concat(_532));
}else{
dojo.lang.extend(ctor,(_532)||{});
}
dojo.lang.extend(ctor,dojo.lang.declare._common);
ctor.prototype.constructor=ctor;
ctor.prototype.initializer=(ctor.prototype.initializer)||(init)||(function(){
});
var _539=dojo.parseObjPath(_52f,null,true);
_539.obj[_539.prop]=ctor;
return ctor;
};
dojo.lang.declare._makeConstructor=function(){
return function(){
var self=this._getPropContext();
var s=self.constructor.superclass;
if((s)&&(s.constructor)){
if(s.constructor==arguments.callee){
this._inherited("constructor",arguments);
}else{
this._contextMethod(s,"constructor",arguments);
}
}
var ms=(self.constructor.mixins)||([]);
for(var i=0,m;(m=ms[i]);i++){
(((m.prototype)&&(m.prototype.initializer))||(m)).apply(this,arguments);
}
if((!this.prototyping)&&(self.initializer)){
self.initializer.apply(this,arguments);
}
};
};
dojo.lang.declare._common={_getPropContext:function(){
return (this.___proto||this);
},_contextMethod:function(_53f,_540,args){
var _542,_543=this.___proto;
this.___proto=_53f;
try{
_542=_53f[_540].apply(this,(args||[]));
}
catch(e){
throw e;
}
finally{
this.___proto=_543;
}
return _542;
},_inherited:function(prop,args){
var p=this._getPropContext();
do{
if((!p.constructor)||(!p.constructor.superclass)){
return;
}
p=p.constructor.superclass;
}while(!(prop in p));
return (dojo.lang.isFunction(p[prop])?this._contextMethod(p,prop,args):p[prop]);
},inherited:function(prop,args){
dojo.deprecated("'inherited' method is dangerous, do not up-call! 'inherited' is slated for removal in 0.5; name your super class (or use superclass property) instead.","0.5");
this._inherited(prop,args);
}};
dojo.declare=dojo.lang.declare;
dojo.provide("dojo.logging.Logger");
dojo.provide("dojo.logging.LogFilter");
dojo.provide("dojo.logging.Record");
dojo.provide("dojo.log");
dojo.logging.Record=function(_549,_54a){
this.level=_549;
this.message="";
this.msgArgs=[];
this.time=new Date();
if(dojo.lang.isArray(_54a)){
if(_54a.length>0&&dojo.lang.isString(_54a[0])){
this.message=_54a.shift();
}
this.msgArgs=_54a;
}else{
this.message=_54a;
}
};
dojo.logging.LogFilter=function(_54b){
this.passChain=_54b||"";
this.filter=function(_54c){
return true;
};
};
dojo.logging.Logger=function(){
this.cutOffLevel=0;
this.propagate=true;
this.parent=null;
this.data=[];
this.filters=[];
this.handlers=[];
};
dojo.extend(dojo.logging.Logger,{_argsToArr:function(args){
var ret=[];
for(var x=0;x<args.length;x++){
ret.push(args[x]);
}
return ret;
},setLevel:function(lvl){
this.cutOffLevel=parseInt(lvl);
},isEnabledFor:function(lvl){
return parseInt(lvl)>=this.cutOffLevel;
},getEffectiveLevel:function(){
if((this.cutOffLevel==0)&&(this.parent)){
return this.parent.getEffectiveLevel();
}
return this.cutOffLevel;
},addFilter:function(flt){
this.filters.push(flt);
return this.filters.length-1;
},removeFilterByIndex:function(_553){
if(this.filters[_553]){
delete this.filters[_553];
return true;
}
return false;
},removeFilter:function(_554){
for(var x=0;x<this.filters.length;x++){
if(this.filters[x]===_554){
delete this.filters[x];
return true;
}
}
return false;
},removeAllFilters:function(){
this.filters=[];
},filter:function(rec){
for(var x=0;x<this.filters.length;x++){
if((this.filters[x]["filter"])&&(!this.filters[x].filter(rec))||(rec.level<this.cutOffLevel)){
return false;
}
}
return true;
},addHandler:function(hdlr){
this.handlers.push(hdlr);
return this.handlers.length-1;
},handle:function(rec){
if((!this.filter(rec))||(rec.level<this.cutOffLevel)){
return false;
}
for(var x=0;x<this.handlers.length;x++){
if(this.handlers[x]["handle"]){
this.handlers[x].handle(rec);
}
}
return true;
},log:function(lvl,msg){
if((this.propagate)&&(this.parent)&&(this.parent.rec.level>=this.cutOffLevel)){
this.parent.log(lvl,msg);
return false;
}
this.handle(new dojo.logging.Record(lvl,msg));
return true;
},debug:function(msg){
return this.logType("DEBUG",this._argsToArr(arguments));
},info:function(msg){
return this.logType("INFO",this._argsToArr(arguments));
},warning:function(msg){
return this.logType("WARNING",this._argsToArr(arguments));
},error:function(msg){
return this.logType("ERROR",this._argsToArr(arguments));
},critical:function(msg){
return this.logType("CRITICAL",this._argsToArr(arguments));
},exception:function(msg,e,_564){
if(e){
var _565=[e.name,(e.description||e.message)];
if(e.fileName){
_565.push(e.fileName);
_565.push("line "+e.lineNumber);
}
msg+=" "+_565.join(" : ");
}
this.logType("ERROR",msg);
if(!_564){
throw e;
}
},logType:function(type,args){
return this.log.apply(this,[dojo.logging.log.getLevel(type),args]);
},warn:function(){
this.warning.apply(this,arguments);
},err:function(){
this.error.apply(this,arguments);
},crit:function(){
this.critical.apply(this,arguments);
}});
dojo.logging.LogHandler=function(_568){
this.cutOffLevel=(_568)?_568:0;
this.formatter=null;
this.data=[];
this.filters=[];
};
dojo.lang.extend(dojo.logging.LogHandler,{setFormatter:function(_569){
dojo.unimplemented("setFormatter");
},flush:function(){
},close:function(){
},handleError:function(){
dojo.deprecated("dojo.logging.LogHandler.handleError","use handle()","0.6");
},handle:function(_56a){
if((this.filter(_56a))&&(_56a.level>=this.cutOffLevel)){
this.emit(_56a);
}
},emit:function(_56b){
dojo.unimplemented("emit");
}});
void (function(){
var _56c=["setLevel","addFilter","removeFilterByIndex","removeFilter","removeAllFilters","filter"];
var tgt=dojo.logging.LogHandler.prototype;
var src=dojo.logging.Logger.prototype;
for(var x=0;x<_56c.length;x++){
tgt[_56c[x]]=src[_56c[x]];
}
})();
dojo.logging.log=new dojo.logging.Logger();
dojo.logging.log.levels=[{"name":"DEBUG","level":1},{"name":"INFO","level":2},{"name":"WARNING","level":3},{"name":"ERROR","level":4},{"name":"CRITICAL","level":5}];
dojo.logging.log.loggers={};
dojo.logging.log.getLogger=function(name){
if(!this.loggers[name]){
this.loggers[name]=new dojo.logging.Logger();
this.loggers[name].parent=this;
}
return this.loggers[name];
};
dojo.logging.log.getLevelName=function(lvl){
for(var x=0;x<this.levels.length;x++){
if(this.levels[x].level==lvl){
return this.levels[x].name;
}
}
return null;
};
dojo.logging.log.getLevel=function(name){
for(var x=0;x<this.levels.length;x++){
if(this.levels[x].name.toUpperCase()==name.toUpperCase()){
return this.levels[x].level;
}
}
return null;
};
dojo.declare("dojo.logging.MemoryLogHandler",dojo.logging.LogHandler,{initializer:function(_575,_576,_577,_578){
dojo.logging.LogHandler.call(this,_575);
this.numRecords=(typeof djConfig["loggingNumRecords"]!="undefined")?djConfig["loggingNumRecords"]:((_576)?_576:-1);
this.postType=(typeof djConfig["loggingPostType"]!="undefined")?djConfig["loggingPostType"]:(_577||-1);
this.postInterval=(typeof djConfig["loggingPostInterval"]!="undefined")?djConfig["loggingPostInterval"]:(_577||-1);
},emit:function(_579){
if(!djConfig.isDebug){
return;
}
var _57a=String(dojo.log.getLevelName(_579.level)+": "+_579.time.toLocaleTimeString())+": "+_579.message;
if(!dj_undef("println",dojo.hostenv)){
dojo.hostenv.println(_57a,_579.msgArgs);
}
this.data.push(_579);
if(this.numRecords!=-1){
while(this.data.length>this.numRecords){
this.data.shift();
}
}
}});
dojo.logging.logQueueHandler=new dojo.logging.MemoryLogHandler(0,50,0,10000);
dojo.logging.log.addHandler(dojo.logging.logQueueHandler);
dojo.log=dojo.logging.log;
dojo.provide("dojo.logging.*");
dojo.provide("dojo.string.common");
dojo.string.trim=function(str,wh){
if(!str.replace){
return str;
}
if(!str.length){
return str;
}
var re=(wh>0)?(/^\s+/):(wh<0)?(/\s+$/):(/^\s+|\s+$/g);
return str.replace(re,"");
};
dojo.string.trimStart=function(str){
return dojo.string.trim(str,1);
};
dojo.string.trimEnd=function(str){
return dojo.string.trim(str,-1);
};
dojo.string.repeat=function(str,_581,_582){
var out="";
for(var i=0;i<_581;i++){
out+=str;
if(_582&&i<_581-1){
out+=_582;
}
}
return out;
};
dojo.string.pad=function(str,len,c,dir){
var out=String(str);
if(!c){
c="0";
}
if(!dir){
dir=1;
}
while(out.length<len){
if(dir>0){
out=c+out;
}else{
out+=c;
}
}
return out;
};
dojo.string.padLeft=function(str,len,c){
return dojo.string.pad(str,len,c,1);
};
dojo.string.padRight=function(str,len,c){
return dojo.string.pad(str,len,c,-1);
};
dojo.provide("dojo.string");
dojo.provide("dojo.io.common");
dojo.io.transports=[];
dojo.io.hdlrFuncNames=["load","error","timeout"];
dojo.io.Request=function(url,_591,_592,_593){
if((arguments.length==1)&&(arguments[0].constructor==Object)){
this.fromKwArgs(arguments[0]);
}else{
this.url=url;
if(_591){
this.mimetype=_591;
}
if(_592){
this.transport=_592;
}
if(arguments.length>=4){
this.changeUrl=_593;
}
}
};
dojo.lang.extend(dojo.io.Request,{url:"",mimetype:"text/plain",method:"GET",content:undefined,transport:undefined,changeUrl:undefined,formNode:undefined,sync:false,bindSuccess:false,useCache:false,preventCache:false,load:function(type,data,_596,_597){
},error:function(type,_599,_59a,_59b){
},timeout:function(type,_59d,_59e,_59f){
},handle:function(type,data,_5a2,_5a3){
},timeoutSeconds:0,abort:function(){
},fromKwArgs:function(_5a4){
if(_5a4["url"]){
_5a4.url=_5a4.url.toString();
}
if(_5a4["formNode"]){
_5a4.formNode=dojo.byId(_5a4.formNode);
}
if(!_5a4["method"]&&_5a4["formNode"]&&_5a4["formNode"].method){
_5a4.method=_5a4["formNode"].method;
}
if(!_5a4["handle"]&&_5a4["handler"]){
_5a4.handle=_5a4.handler;
}
if(!_5a4["load"]&&_5a4["loaded"]){
_5a4.load=_5a4.loaded;
}
if(!_5a4["changeUrl"]&&_5a4["changeURL"]){
_5a4.changeUrl=_5a4.changeURL;
}
_5a4.encoding=dojo.lang.firstValued(_5a4["encoding"],djConfig["bindEncoding"],"");
_5a4.sendTransport=dojo.lang.firstValued(_5a4["sendTransport"],djConfig["ioSendTransport"],false);
var _5a5=dojo.lang.isFunction;
for(var x=0;x<dojo.io.hdlrFuncNames.length;x++){
var fn=dojo.io.hdlrFuncNames[x];
if(_5a4[fn]&&_5a5(_5a4[fn])){
continue;
}
if(_5a4["handle"]&&_5a5(_5a4["handle"])){
_5a4[fn]=_5a4.handle;
}
}
dojo.lang.mixin(this,_5a4);
}});
dojo.io.Error=function(msg,type,num){
this.message=msg;
this.type=type||"unknown";
this.number=num||0;
};
dojo.io.transports.addTransport=function(name){
this.push(name);
this[name]=dojo.io[name];
};
dojo.io.bind=function(_5ac){
if(!(_5ac instanceof dojo.io.Request)){
try{
_5ac=new dojo.io.Request(_5ac);
}
catch(e){
dojo.debug(e);
}
}
var _5ad="";
if(_5ac["transport"]){
_5ad=_5ac["transport"];
if(!this[_5ad]){
dojo.io.sendBindError(_5ac,"No dojo.io.bind() transport with name '"+_5ac["transport"]+"'.");
return _5ac;
}
if(!this[_5ad].canHandle(_5ac)){
dojo.io.sendBindError(_5ac,"dojo.io.bind() transport with name '"+_5ac["transport"]+"' cannot handle this type of request.");
return _5ac;
}
}else{
for(var x=0;x<dojo.io.transports.length;x++){
var tmp=dojo.io.transports[x];
if((this[tmp])&&(this[tmp].canHandle(_5ac))){
_5ad=tmp;
break;
}
}
if(_5ad==""){
dojo.io.sendBindError(_5ac,"None of the loaded transports for dojo.io.bind()"+" can handle the request.");
return _5ac;
}
}
this[_5ad].bind(_5ac);
_5ac.bindSuccess=true;
return _5ac;
};
dojo.io.sendBindError=function(_5b0,_5b1){
if((typeof _5b0.error=="function"||typeof _5b0.handle=="function")&&(typeof setTimeout=="function"||typeof setTimeout=="object")){
var _5b2=new dojo.io.Error(_5b1);
setTimeout(function(){
_5b0[(typeof _5b0.error=="function")?"error":"handle"]("error",_5b2,null,_5b0);
},50);
}else{
dojo.raise(_5b1);
}
};
dojo.io.queueBind=function(_5b3){
if(!(_5b3 instanceof dojo.io.Request)){
try{
_5b3=new dojo.io.Request(_5b3);
}
catch(e){
dojo.debug(e);
}
}
var _5b4=_5b3.load;
_5b3.load=function(){
dojo.io._queueBindInFlight=false;
var ret=_5b4.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
var _5b6=_5b3.error;
_5b3.error=function(){
dojo.io._queueBindInFlight=false;
var ret=_5b6.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
dojo.io._bindQueue.push(_5b3);
dojo.io._dispatchNextQueueBind();
return _5b3;
};
dojo.io._dispatchNextQueueBind=function(){
if(!dojo.io._queueBindInFlight){
dojo.io._queueBindInFlight=true;
if(dojo.io._bindQueue.length>0){
dojo.io.bind(dojo.io._bindQueue.shift());
}else{
dojo.io._queueBindInFlight=false;
}
}
};
dojo.io._bindQueue=[];
dojo.io._queueBindInFlight=false;
dojo.io.argsFromMap=function(map,_5b9,last){
var enc=/utf/i.test(_5b9||"")?encodeURIComponent:dojo.string.encodeAscii;
var _5bc=[];
var _5bd=new Object();
for(var name in map){
var _5bf=function(elt){
var val=enc(name)+"="+enc(elt);
_5bc[(last==name)?"push":"unshift"](val);
};
if(!_5bd[name]){
var _5c2=map[name];
if(dojo.lang.isArray(_5c2)){
dojo.lang.forEach(_5c2,_5bf);
}else{
_5bf(_5c2);
}
}
}
return _5bc.join("&");
};
dojo.io.setIFrameSrc=function(_5c3,src,_5c5){
try{
var r=dojo.render.html;
if(!_5c5){
if(r.safari){
_5c3.location=src;
}else{
frames[_5c3.name].location=src;
}
}else{
var idoc;
if(r.ie){
idoc=_5c3.contentWindow.document;
}else{
if(r.safari){
idoc=_5c3.document;
}else{
idoc=_5c3.contentWindow;
}
}
if(!idoc){
_5c3.location=src;
return;
}else{
idoc.location.replace(src);
}
}
}
catch(e){
dojo.debug(e);
dojo.debug("setIFrameSrc: "+e);
}
};
dojo.provide("dojo.string.extras");
dojo.string.substituteParams=function(_5c8,hash){
var map=(typeof hash=="object")?hash:dojo.lang.toArray(arguments,1);
return _5c8.replace(/\%\{(\w+)\}/g,function(_5cb,key){
if(typeof (map[key])!="undefined"&&map[key]!=null){
return map[key];
}
dojo.raise("Substitution not found: "+key);
});
};
dojo.string.capitalize=function(str){
if(!dojo.lang.isString(str)){
return "";
}
if(arguments.length==0){
str=this;
}
var _5ce=str.split(" ");
for(var i=0;i<_5ce.length;i++){
_5ce[i]=_5ce[i].charAt(0).toUpperCase()+_5ce[i].substring(1);
}
return _5ce.join(" ");
};
dojo.string.isBlank=function(str){
if(!dojo.lang.isString(str)){
return true;
}
return (dojo.string.trim(str).length==0);
};
dojo.string.encodeAscii=function(str){
if(!dojo.lang.isString(str)){
return str;
}
var ret="";
var _5d3=escape(str);
var _5d4,re=/%u([0-9A-F]{4})/i;
while((_5d4=_5d3.match(re))){
var num=Number("0x"+_5d4[1]);
var _5d7=escape("&#"+num+";");
ret+=_5d3.substring(0,_5d4.index)+_5d7;
_5d3=_5d3.substring(_5d4.index+_5d4[0].length);
}
ret+=_5d3.replace(/\+/g,"%2B");
return ret;
};
dojo.string.escape=function(type,str){
var args=dojo.lang.toArray(arguments,1);
switch(type.toLowerCase()){
case "xml":
case "html":
case "xhtml":
return dojo.string.escapeXml.apply(this,args);
case "sql":
return dojo.string.escapeSql.apply(this,args);
case "regexp":
case "regex":
return dojo.string.escapeRegExp.apply(this,args);
case "javascript":
case "jscript":
case "js":
return dojo.string.escapeJavaScript.apply(this,args);
case "ascii":
return dojo.string.encodeAscii.apply(this,args);
default:
return str;
}
};
dojo.string.escapeXml=function(str,_5dc){
str=str.replace(/&/gm,"&amp;").replace(/</gm,"&lt;").replace(/>/gm,"&gt;").replace(/"/gm,"&quot;");
if(!_5dc){
str=str.replace(/'/gm,"&#39;");
}
return str;
};
dojo.string.escapeSql=function(str){
return str.replace(/'/gm,"''");
};
dojo.string.escapeRegExp=function(str){
return str.replace(/\\/gm,"\\\\").replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm,"\\$1");
};
dojo.string.escapeJavaScript=function(str){
return str.replace(/(["'\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.escapeString=function(str){
return ("\""+str.replace(/(["\\])/g,"\\$1")+"\"").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r");
};
dojo.string.summary=function(str,len){
if(!len||str.length<=len){
return str;
}
return str.substring(0,len).replace(/\.+$/,"")+"...";
};
dojo.string.endsWith=function(str,end,_5e5){
if(_5e5){
str=str.toLowerCase();
end=end.toLowerCase();
}
if((str.length-end.length)<0){
return false;
}
return str.lastIndexOf(end)==str.length-end.length;
};
dojo.string.endsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.endsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.startsWith=function(str,_5e9,_5ea){
if(_5ea){
str=str.toLowerCase();
_5e9=_5e9.toLowerCase();
}
return str.indexOf(_5e9)==0;
};
dojo.string.startsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.startsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.has=function(str){
for(var i=1;i<arguments.length;i++){
if(str.indexOf(arguments[i])>-1){
return true;
}
}
return false;
};
dojo.string.normalizeNewlines=function(text,_5f0){
if(_5f0=="\n"){
text=text.replace(/\r\n/g,"\n");
text=text.replace(/\r/g,"\n");
}else{
if(_5f0=="\r"){
text=text.replace(/\r\n/g,"\r");
text=text.replace(/\n/g,"\r");
}else{
text=text.replace(/([^\r])\n/g,"$1\r\n").replace(/\r([^\n])/g,"\r\n$1");
}
}
return text;
};
dojo.string.splitEscaped=function(str,_5f2){
var _5f3=[];
for(var i=0,_5f5=0;i<str.length;i++){
if(str.charAt(i)=="\\"){
i++;
continue;
}
if(str.charAt(i)==_5f2){
_5f3.push(str.substring(_5f5,i));
_5f5=i+1;
}
}
_5f3.push(str.substr(_5f5));
return _5f3;
};
dojo.provide("dojo.undo.browser");
try{
if((!djConfig["preventBackButtonFix"])&&(!dojo.hostenv.post_load_)){
document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+"iframe_history.html")+"'></iframe>");
}
}
catch(e){
}
if(dojo.render.html.opera){
dojo.debug("Opera is not supported with dojo.undo.browser, so back/forward detection will not work.");
}
dojo.undo.browser={initialHref:(!dj_undef("window"))?window.location.href:"",initialHash:(!dj_undef("window"))?window.location.hash:"",moveForward:false,historyStack:[],forwardStack:[],historyIframe:null,bookmarkAnchor:null,locationTimer:null,setInitialState:function(args){
this.initialState=this._createState(this.initialHref,args,this.initialHash);
},addToHistory:function(args){
this.forwardStack=[];
var hash=null;
var url=null;
if(!this.historyIframe){
this.historyIframe=window.frames["djhistory"];
}
if(!this.bookmarkAnchor){
this.bookmarkAnchor=document.createElement("a");
dojo.body().appendChild(this.bookmarkAnchor);
this.bookmarkAnchor.style.display="none";
}
if(args["changeUrl"]){
hash="#"+((args["changeUrl"]!==true)?args["changeUrl"]:(new Date()).getTime());
if(this.historyStack.length==0&&this.initialState.urlHash==hash){
this.initialState=this._createState(url,args,hash);
return;
}else{
if(this.historyStack.length>0&&this.historyStack[this.historyStack.length-1].urlHash==hash){
this.historyStack[this.historyStack.length-1]=this._createState(url,args,hash);
return;
}
}
this.changingUrl=true;
setTimeout("window.location.href = '"+hash+"'; dojo.undo.browser.changingUrl = false;",1);
this.bookmarkAnchor.href=hash;
if(dojo.render.html.ie){
url=this._loadIframeHistory();
var _5fa=args["back"]||args["backButton"]||args["handle"];
var tcb=function(_5fc){
if(window.location.hash!=""){
setTimeout("window.location.href = '"+hash+"';",1);
}
_5fa.apply(this,[_5fc]);
};
if(args["back"]){
args.back=tcb;
}else{
if(args["backButton"]){
args.backButton=tcb;
}else{
if(args["handle"]){
args.handle=tcb;
}
}
}
var _5fd=args["forward"]||args["forwardButton"]||args["handle"];
var tfw=function(_5ff){
if(window.location.hash!=""){
window.location.href=hash;
}
if(_5fd){
_5fd.apply(this,[_5ff]);
}
};
if(args["forward"]){
args.forward=tfw;
}else{
if(args["forwardButton"]){
args.forwardButton=tfw;
}else{
if(args["handle"]){
args.handle=tfw;
}
}
}
}else{
if(dojo.render.html.moz){
if(!this.locationTimer){
this.locationTimer=setInterval("dojo.undo.browser.checkLocation();",200);
}
}
}
}else{
url=this._loadIframeHistory();
}
this.historyStack.push(this._createState(url,args,hash));
},checkLocation:function(){
if(!this.changingUrl){
var hsl=this.historyStack.length;
if((window.location.hash==this.initialHash||window.location.href==this.initialHref)&&(hsl==1)){
this.handleBackButton();
return;
}
if(this.forwardStack.length>0){
if(this.forwardStack[this.forwardStack.length-1].urlHash==window.location.hash){
this.handleForwardButton();
return;
}
}
if((hsl>=2)&&(this.historyStack[hsl-2])){
if(this.historyStack[hsl-2].urlHash==window.location.hash){
this.handleBackButton();
return;
}
}
}
},iframeLoaded:function(evt,_602){
if(!dojo.render.html.opera){
var _603=this._getUrlQuery(_602.href);
if(_603==null){
if(this.historyStack.length==1){
this.handleBackButton();
}
return;
}
if(this.moveForward){
this.moveForward=false;
return;
}
if(this.historyStack.length>=2&&_603==this._getUrlQuery(this.historyStack[this.historyStack.length-2].url)){
this.handleBackButton();
}else{
if(this.forwardStack.length>0&&_603==this._getUrlQuery(this.forwardStack[this.forwardStack.length-1].url)){
this.handleForwardButton();
}
}
}
},handleBackButton:function(){
var _604=this.historyStack.pop();
if(!_604){
return;
}
var last=this.historyStack[this.historyStack.length-1];
if(!last&&this.historyStack.length==0){
last=this.initialState;
}
if(last){
if(last.kwArgs["back"]){
last.kwArgs["back"]();
}else{
if(last.kwArgs["backButton"]){
last.kwArgs["backButton"]();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("back");
}
}
}
}
this.forwardStack.push(_604);
},handleForwardButton:function(){
var last=this.forwardStack.pop();
if(!last){
return;
}
if(last.kwArgs["forward"]){
last.kwArgs.forward();
}else{
if(last.kwArgs["forwardButton"]){
last.kwArgs.forwardButton();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("forward");
}
}
}
this.historyStack.push(last);
},_createState:function(url,args,hash){
return {"url":url,"kwArgs":args,"urlHash":hash};
},_getUrlQuery:function(url){
var _60b=url.split("?");
if(_60b.length<2){
return null;
}else{
return _60b[1];
}
},_loadIframeHistory:function(){
var url=dojo.hostenv.getBaseScriptUri()+"iframe_history.html?"+(new Date()).getTime();
this.moveForward=true;
dojo.io.setIFrameSrc(this.historyIframe,url,false);
return url;
}};
dojo.provide("dojo.io.BrowserIO");
if(!dj_undef("window")){
dojo.io.checkChildrenForFile=function(node){
var _60e=false;
var _60f=node.getElementsByTagName("input");
dojo.lang.forEach(_60f,function(_610){
if(_60e){
return;
}
if(_610.getAttribute("type")=="file"){
_60e=true;
}
});
return _60e;
};
dojo.io.formHasFile=function(_611){
return dojo.io.checkChildrenForFile(_611);
};
dojo.io.updateNode=function(node,_613){
node=dojo.byId(node);
var args=_613;
if(dojo.lang.isString(_613)){
args={url:_613};
}
args.mimetype="text/html";
args.load=function(t,d,e){
while(node.firstChild){
dojo.dom.destroyNode(node.firstChild);
}
node.innerHTML=d;
};
dojo.io.bind(args);
};
dojo.io.formFilter=function(node){
var type=(node.type||"").toLowerCase();
return !node.disabled&&node.name&&!dojo.lang.inArray(["file","submit","image","reset","button"],type);
};
dojo.io.encodeForm=function(_61a,_61b,_61c){
if((!_61a)||(!_61a.tagName)||(!_61a.tagName.toLowerCase()=="form")){
dojo.raise("Attempted to encode a non-form element.");
}
if(!_61c){
_61c=dojo.io.formFilter;
}
var enc=/utf/i.test(_61b||"")?encodeURIComponent:dojo.string.encodeAscii;
var _61e=[];
for(var i=0;i<_61a.elements.length;i++){
var elm=_61a.elements[i];
if(!elm||elm.tagName.toLowerCase()=="fieldset"||!_61c(elm)){
continue;
}
var name=enc(elm.name);
var type=elm.type.toLowerCase();
if(type=="select-multiple"){
for(var j=0;j<elm.options.length;j++){
if(elm.options[j].selected){
_61e.push(name+"="+enc(elm.options[j].value));
}
}
}else{
if(dojo.lang.inArray(["radio","checkbox"],type)){
if(elm.checked){
_61e.push(name+"="+enc(elm.value));
}
}else{
_61e.push(name+"="+enc(elm.value));
}
}
}
var _624=_61a.getElementsByTagName("input");
for(var i=0;i<_624.length;i++){
var _625=_624[i];
if(_625.type.toLowerCase()=="image"&&_625.form==_61a&&_61c(_625)){
var name=enc(_625.name);
_61e.push(name+"="+enc(_625.value));
_61e.push(name+".x=0");
_61e.push(name+".y=0");
}
}
return _61e.join("&")+"&";
};
dojo.io.FormBind=function(args){
this.bindArgs={};
if(args&&args.formNode){
this.init(args);
}else{
if(args){
this.init({formNode:args});
}
}
};
dojo.lang.extend(dojo.io.FormBind,{form:null,bindArgs:null,clickedButton:null,init:function(args){
var form=dojo.byId(args.formNode);
if(!form||!form.tagName||form.tagName.toLowerCase()!="form"){
throw new Error("FormBind: Couldn't apply, invalid form");
}else{
if(this.form==form){
return;
}else{
if(this.form){
throw new Error("FormBind: Already applied to a form");
}
}
}
dojo.lang.mixin(this.bindArgs,args);
this.form=form;
this.connect(form,"onsubmit","submit");
for(var i=0;i<form.elements.length;i++){
var node=form.elements[i];
if(node&&node.type&&dojo.lang.inArray(["submit","button"],node.type.toLowerCase())){
this.connect(node,"onclick","click");
}
}
var _62b=form.getElementsByTagName("input");
for(var i=0;i<_62b.length;i++){
var _62c=_62b[i];
if(_62c.type.toLowerCase()=="image"&&_62c.form==form){
this.connect(_62c,"onclick","click");
}
}
},onSubmit:function(form){
return true;
},submit:function(e){
e.preventDefault();
if(this.onSubmit(this.form)){
dojo.io.bind(dojo.lang.mixin(this.bindArgs,{formFilter:dojo.lang.hitch(this,"formFilter")}));
}
},click:function(e){
var node=e.currentTarget;
if(node.disabled){
return;
}
this.clickedButton=node;
},formFilter:function(node){
var type=(node.type||"").toLowerCase();
var _633=false;
if(node.disabled||!node.name){
_633=false;
}else{
if(dojo.lang.inArray(["submit","button","image"],type)){
if(!this.clickedButton){
this.clickedButton=node;
}
_633=node==this.clickedButton;
}else{
_633=!dojo.lang.inArray(["file","submit","reset","button"],type);
}
}
return _633;
},connect:function(_634,_635,_636){
if(dojo.evalObjPath("dojo.event.connect")){
dojo.event.connect(_634,_635,this,_636);
}else{
var fcn=dojo.lang.hitch(this,_636);
_634[_635]=function(e){
if(!e){
e=window.event;
}
if(!e.currentTarget){
e.currentTarget=e.srcElement;
}
if(!e.preventDefault){
e.preventDefault=function(){
window.event.returnValue=false;
};
}
fcn(e);
};
}
}});
dojo.io.XMLHTTPTransport=new function(){
var _639=this;
var _63a={};
this.useCache=false;
this.preventCache=false;
function getCacheKey(url,_63c,_63d){
return url+"|"+_63c+"|"+_63d.toLowerCase();
}
function addToCache(url,_63f,_640,http){
_63a[getCacheKey(url,_63f,_640)]=http;
}
function getFromCache(url,_643,_644){
return _63a[getCacheKey(url,_643,_644)];
}
this.clearCache=function(){
_63a={};
};
function doLoad(_645,http,url,_648,_649){
if(((http.status>=200)&&(http.status<300))||(http.status==304)||(location.protocol=="file:"&&(http.status==0||http.status==undefined))||(location.protocol=="chrome:"&&(http.status==0||http.status==undefined))){
var ret;
if(_645.method.toLowerCase()=="head"){
var _64b=http.getAllResponseHeaders();
ret={};
ret.toString=function(){
return _64b;
};
var _64c=_64b.split(/[\r\n]+/g);
for(var i=0;i<_64c.length;i++){
var pair=_64c[i].match(/^([^:]+)\s*:\s*(.+)$/i);
if(pair){
ret[pair[1]]=pair[2];
}
}
}else{
if(_645.mimetype=="text/javascript"){
try{
ret=dj_eval(http.responseText);
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=null;
}
}else{
if(_645.mimetype=="text/json"||_645.mimetype=="application/json"){
try{
ret=dj_eval("("+http.responseText+")");
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=false;
}
}else{
if((_645.mimetype=="application/xml")||(_645.mimetype=="text/xml")){
ret=http.responseXML;
if(!ret||typeof ret=="string"||!http.getResponseHeader("Content-Type")){
ret=dojo.dom.createDocumentFromText(http.responseText);
}
}else{
ret=http.responseText;
}
}
}
}
if(_649){
addToCache(url,_648,_645.method,http);
}
_645[(typeof _645.load=="function")?"load":"handle"]("load",ret,http,_645);
}else{
var _64f=new dojo.io.Error("XMLHttpTransport Error: "+http.status+" "+http.statusText);
_645[(typeof _645.error=="function")?"error":"handle"]("error",_64f,http,_645);
}
}
function setHeaders(http,_651){
if(_651["headers"]){
for(var _652 in _651["headers"]){
if(_652.toLowerCase()=="content-type"&&!_651["contentType"]){
_651["contentType"]=_651["headers"][_652];
}else{
http.setRequestHeader(_652,_651["headers"][_652]);
}
}
}
}
this.inFlight=[];
this.inFlightTimer=null;
this.startWatchingInFlight=function(){
if(!this.inFlightTimer){
this.inFlightTimer=setTimeout("dojo.io.XMLHTTPTransport.watchInFlight();",10);
}
};
this.watchInFlight=function(){
var now=null;
if(!dojo.hostenv._blockAsync&&!_639._blockAsync){
for(var x=this.inFlight.length-1;x>=0;x--){
try{
var tif=this.inFlight[x];
if(!tif||tif.http._aborted||!tif.http.readyState){
this.inFlight.splice(x,1);
continue;
}
if(4==tif.http.readyState){
this.inFlight.splice(x,1);
doLoad(tif.req,tif.http,tif.url,tif.query,tif.useCache);
}else{
if(tif.startTime){
if(!now){
now=(new Date()).getTime();
}
if(tif.startTime+(tif.req.timeoutSeconds*1000)<now){
if(typeof tif.http.abort=="function"){
tif.http.abort();
}
this.inFlight.splice(x,1);
tif.req[(typeof tif.req.timeout=="function")?"timeout":"handle"]("timeout",null,tif.http,tif.req);
}
}
}
}
catch(e){
try{
var _656=new dojo.io.Error("XMLHttpTransport.watchInFlight Error: "+e);
tif.req[(typeof tif.req.error=="function")?"error":"handle"]("error",_656,tif.http,tif.req);
}
catch(e2){
dojo.debug("XMLHttpTransport error callback failed: "+e2);
}
}
}
}
clearTimeout(this.inFlightTimer);
if(this.inFlight.length==0){
this.inFlightTimer=null;
return;
}
this.inFlightTimer=setTimeout("dojo.io.XMLHTTPTransport.watchInFlight();",10);
};
var _657=dojo.hostenv.getXmlhttpObject()?true:false;
this.canHandle=function(_658){
return _657&&dojo.lang.inArray(["text/plain","text/html","application/xml","text/xml","text/javascript","text/json","application/json"],(_658["mimetype"].toLowerCase()||""))&&!(_658["formNode"]&&dojo.io.formHasFile(_658["formNode"]));
};
this.multipartBoundary="45309FFF-BD65-4d50-99C9-36986896A96F";
this.bind=function(_659){
if(!_659["url"]){
if(!_659["formNode"]&&(_659["backButton"]||_659["back"]||_659["changeUrl"]||_659["watchForURL"])&&(!djConfig.preventBackButtonFix)){
dojo.deprecated("Using dojo.io.XMLHTTPTransport.bind() to add to browser history without doing an IO request","Use dojo.undo.browser.addToHistory() instead.","0.4");
dojo.undo.browser.addToHistory(_659);
return true;
}
}
var url=_659.url;
var _65b="";
if(_659["formNode"]){
var ta=_659.formNode.getAttribute("action");
if((ta)&&(!_659["url"])){
url=ta;
}
var tp=_659.formNode.getAttribute("method");
if((tp)&&(!_659["method"])){
_659.method=tp;
}
_65b+=dojo.io.encodeForm(_659.formNode,_659.encoding,_659["formFilter"]);
}
if(url.indexOf("#")>-1){
dojo.debug("Warning: dojo.io.bind: stripping hash values from url:",url);
url=url.split("#")[0];
}
if(_659["file"]){
_659.method="post";
}
if(!_659["method"]){
_659.method="get";
}
if(_659.method.toLowerCase()=="get"){
_659.multipart=false;
}else{
if(_659["file"]){
_659.multipart=true;
}else{
if(!_659["multipart"]){
_659.multipart=false;
}
}
}
if(_659["backButton"]||_659["back"]||_659["changeUrl"]){
dojo.undo.browser.addToHistory(_659);
}
var _65e=_659["content"]||{};
if(_659.sendTransport){
_65e["dojo.transport"]="xmlhttp";
}
do{
if(_659.postContent){
_65b=_659.postContent;
break;
}
if(_65e){
_65b+=dojo.io.argsFromMap(_65e,_659.encoding);
}
if(_659.method.toLowerCase()=="get"||!_659.multipart){
break;
}
var t=[];
if(_65b.length){
var q=_65b.split("&");
for(var i=0;i<q.length;++i){
if(q[i].length){
var p=q[i].split("=");
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+p[0]+"\"","",p[1]);
}
}
}
if(_659.file){
if(dojo.lang.isArray(_659.file)){
for(var i=0;i<_659.file.length;++i){
var o=_659.file[i];
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}else{
var o=_659.file;
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}
if(t.length){
t.push("--"+this.multipartBoundary+"--","");
_65b=t.join("\r\n");
}
}while(false);
var _664=_659["sync"]?false:true;
var _665=_659["preventCache"]||(this.preventCache==true&&_659["preventCache"]!=false);
var _666=_659["useCache"]==true||(this.useCache==true&&_659["useCache"]!=false);
if(!_665&&_666){
var _667=getFromCache(url,_65b,_659.method);
if(_667){
doLoad(_659,_667,url,_65b,false);
return;
}
}
var http=dojo.hostenv.getXmlhttpObject(_659);
var _669=false;
if(_664){
var _66a=this.inFlight.push({"req":_659,"http":http,"url":url,"query":_65b,"useCache":_666,"startTime":_659.timeoutSeconds?(new Date()).getTime():0});
this.startWatchingInFlight();
}else{
_639._blockAsync=true;
}
if(_659.method.toLowerCase()=="post"){
if(!_659.user){
http.open("POST",url,_664);
}else{
http.open("POST",url,_664,_659.user,_659.password);
}
setHeaders(http,_659);
http.setRequestHeader("Content-Type",_659.multipart?("multipart/form-data; boundary="+this.multipartBoundary):(_659.contentType||"application/x-www-form-urlencoded"));
try{
http.send(_65b);
}
catch(e){
if(typeof http.abort=="function"){
http.abort();
}
doLoad(_659,{status:404},url,_65b,_666);
}
}else{
var _66b=url;
if(_65b!=""){
_66b+=(_66b.indexOf("?")>-1?"&":"?")+_65b;
}
if(_665){
_66b+=(dojo.string.endsWithAny(_66b,"?","&")?"":(_66b.indexOf("?")>-1?"&":"?"))+"dojo.preventCache="+new Date().valueOf();
}
if(!_659.user){
http.open(_659.method.toUpperCase(),_66b,_664);
}else{
http.open(_659.method.toUpperCase(),_66b,_664,_659.user,_659.password);
}
setHeaders(http,_659);
try{
http.send(null);
}
catch(e){
if(typeof http.abort=="function"){
http.abort();
}
doLoad(_659,{status:404},url,_65b,_666);
}
}
if(!_664){
doLoad(_659,http,url,_65b,_666);
_639._blockAsync=false;
}
_659.abort=function(){
try{
http._aborted=true;
}
catch(e){
}
return http.abort();
};
return;
};
dojo.io.transports.addTransport("XMLHTTPTransport");
};
}
dojo.provide("dojo.io.cookie");
dojo.io.cookie.setCookie=function(name,_66d,days,path,_670,_671){
var _672=-1;
if((typeof days=="number")&&(days>=0)){
var d=new Date();
d.setTime(d.getTime()+(days*24*60*60*1000));
_672=d.toGMTString();
}
_66d=escape(_66d);
document.cookie=name+"="+_66d+";"+(_672!=-1?" expires="+_672+";":"")+(path?"path="+path:"")+(_670?"; domain="+_670:"")+(_671?"; secure":"");
};
dojo.io.cookie.set=dojo.io.cookie.setCookie;
dojo.io.cookie.getCookie=function(name){
var idx=document.cookie.lastIndexOf(name+"=");
if(idx==-1){
return null;
}
var _676=document.cookie.substring(idx+name.length+1);
var end=_676.indexOf(";");
if(end==-1){
end=_676.length;
}
_676=_676.substring(0,end);
_676=unescape(_676);
return _676;
};
dojo.io.cookie.get=dojo.io.cookie.getCookie;
dojo.io.cookie.deleteCookie=function(name){
dojo.io.cookie.setCookie(name,"-",0);
};
dojo.io.cookie.setObjectCookie=function(name,obj,days,path,_67d,_67e,_67f){
if(arguments.length==5){
_67f=_67d;
_67d=null;
_67e=null;
}
var _680=[],_681,_682="";
if(!_67f){
_681=dojo.io.cookie.getObjectCookie(name);
}
if(days>=0){
if(!_681){
_681={};
}
for(var prop in obj){
if(obj[prop]==null){
delete _681[prop];
}else{
if((typeof obj[prop]=="string")||(typeof obj[prop]=="number")){
_681[prop]=obj[prop];
}
}
}
prop=null;
for(var prop in _681){
_680.push(escape(prop)+"="+escape(_681[prop]));
}
_682=_680.join("&");
}
dojo.io.cookie.setCookie(name,_682,days,path,_67d,_67e);
};
dojo.io.cookie.getObjectCookie=function(name){
var _685=null,_686=dojo.io.cookie.getCookie(name);
if(_686){
_685={};
var _687=_686.split("&");
for(var i=0;i<_687.length;i++){
var pair=_687[i].split("=");
var _68a=pair[1];
if(isNaN(_68a)){
_68a=unescape(pair[1]);
}
_685[unescape(pair[0])]=_68a;
}
}
return _685;
};
dojo.io.cookie.isSupported=function(){
if(typeof navigator.cookieEnabled!="boolean"){
dojo.io.cookie.setCookie("__TestingYourBrowserForCookieSupport__","CookiesAllowed",90,null);
var _68b=dojo.io.cookie.getCookie("__TestingYourBrowserForCookieSupport__");
navigator.cookieEnabled=(_68b=="CookiesAllowed");
if(navigator.cookieEnabled){
this.deleteCookie("__TestingYourBrowserForCookieSupport__");
}
}
return navigator.cookieEnabled;
};
if(!dojo.io.cookies){
dojo.io.cookies=dojo.io.cookie;
}
dojo.provide("dojo.io.*");
dojo.provide("dojo.uri.*");
dojo.provide("dojo.io.IframeIO");
dojo.io.createIFrame=function(_68c,_68d,uri){
if(window[_68c]){
return window[_68c];
}
if(window.frames[_68c]){
return window.frames[_68c];
}
var r=dojo.render.html;
var _690=null;
var turi=uri||dojo.uri.dojoUri("iframe_history.html?noInit=true");
var _692=((r.ie)&&(dojo.render.os.win))?"<iframe name=\""+_68c+"\" src=\""+turi+"\" onload=\""+_68d+"\">":"iframe";
_690=document.createElement(_692);
with(_690){
name=_68c;
setAttribute("name",_68c);
id=_68c;
}
dojo.body().appendChild(_690);
window[_68c]=_690;
with(_690.style){
if(!r.safari){
position="absolute";
}
left=top="0px";
height=width="1px";
visibility="hidden";
}
if(!r.ie){
dojo.io.setIFrameSrc(_690,turi,true);
_690.onload=new Function(_68d);
}
return _690;
};
dojo.io.IframeTransport=new function(){
var _693=this;
this.currentRequest=null;
this.requestQueue=[];
this.iframeName="dojoIoIframe";
this.fireNextRequest=function(){
try{
if((this.currentRequest)||(this.requestQueue.length==0)){
return;
}
var cr=this.currentRequest=this.requestQueue.shift();
cr._contentToClean=[];
var fn=cr["formNode"];
var _696=cr["content"]||{};
if(cr.sendTransport){
_696["dojo.transport"]="iframe";
}
if(fn){
if(_696){
for(var x in _696){
if(!fn[x]){
var tn;
if(dojo.render.html.ie){
tn=document.createElement("<input type='hidden' name='"+x+"' value='"+_696[x]+"'>");
fn.appendChild(tn);
}else{
tn=document.createElement("input");
fn.appendChild(tn);
tn.type="hidden";
tn.name=x;
tn.value=_696[x];
}
cr._contentToClean.push(x);
}else{
fn[x].value=_696[x];
}
}
}
if(cr["url"]){
cr._originalAction=fn.getAttribute("action");
fn.setAttribute("action",cr.url);
}
if(!fn.getAttribute("method")){
fn.setAttribute("method",(cr["method"])?cr["method"]:"post");
}
cr._originalTarget=fn.getAttribute("target");
fn.setAttribute("target",this.iframeName);
fn.target=this.iframeName;
fn.submit();
}else{
var _699=dojo.io.argsFromMap(this.currentRequest.content);
var _69a=cr.url+(cr.url.indexOf("?")>-1?"&":"?")+_699;
dojo.io.setIFrameSrc(this.iframe,_69a,true);
}
}
catch(e){
this.iframeOnload(e);
}
};
this.canHandle=function(_69b){
return ((dojo.lang.inArray(["text/plain","text/html","text/javascript","text/json","application/json"],_69b["mimetype"]))&&(dojo.lang.inArray(["post","get"],_69b["method"].toLowerCase()))&&(!((_69b["sync"])&&(_69b["sync"]==true))));
};
this.bind=function(_69c){
if(!this["iframe"]){
this.setUpIframe();
}
this.requestQueue.push(_69c);
this.fireNextRequest();
return;
};
this.setUpIframe=function(){
this.iframe=dojo.io.createIFrame(this.iframeName,"dojo.io.IframeTransport.iframeOnload();");
};
this.iframeOnload=function(_69d){
if(!_693.currentRequest){
_693.fireNextRequest();
return;
}
var req=_693.currentRequest;
if(req.formNode){
var _69f=req._contentToClean;
for(var i=0;i<_69f.length;i++){
var key=_69f[i];
if(dojo.render.html.safari){
var _6a2=req.formNode;
for(var j=0;j<_6a2.childNodes.length;j++){
var _6a4=_6a2.childNodes[j];
if(_6a4.name==key){
var _6a5=_6a4.parentNode;
_6a5.removeChild(_6a4);
break;
}
}
}else{
var _6a6=req.formNode[key];
req.formNode.removeChild(_6a6);
req.formNode[key]=null;
}
}
if(req["_originalAction"]){
req.formNode.setAttribute("action",req._originalAction);
}
if(req["_originalTarget"]){
req.formNode.setAttribute("target",req._originalTarget);
req.formNode.target=req._originalTarget;
}
}
var _6a7=function(_6a8){
var doc=_6a8.contentDocument||((_6a8.contentWindow)&&(_6a8.contentWindow.document))||((_6a8.name)&&(document.frames[_6a8.name])&&(document.frames[_6a8.name].document))||null;
return doc;
};
var _6aa;
var _6ab=false;
if(_69d){
this._callError(req,"IframeTransport Request Error: "+_69d);
}else{
var ifd=_6a7(_693.iframe);
try{
var cmt=req.mimetype;
if((cmt=="text/javascript")||(cmt=="text/json")||(cmt=="application/json")){
var js=ifd.getElementsByTagName("textarea")[0].value;
if(cmt=="text/json"||cmt=="application/json"){
js="("+js+")";
}
_6aa=dj_eval(js);
}else{
if(cmt=="text/html"){
_6aa=ifd;
}else{
_6aa=ifd.getElementsByTagName("textarea")[0].value;
}
}
_6ab=true;
}
catch(e){
this._callError(req,"IframeTransport Error: "+e);
}
}
try{
if(_6ab&&dojo.lang.isFunction(req["load"])){
req.load("load",_6aa,req);
}
}
catch(e){
throw e;
}
finally{
_693.currentRequest=null;
_693.fireNextRequest();
}
};
this._callError=function(req,_6b0){
var _6b1=new dojo.io.Error(_6b0);
if(dojo.lang.isFunction(req["error"])){
req.error("error",_6b1,req);
}
};
dojo.io.transports.addTransport("IframeTransport");
};
dojo.provide("dojo.date");
dojo.deprecated("dojo.date","use one of the modules in dojo.date.* instead","0.5");
dojo.provide("dojo.string.Builder");
dojo.string.Builder=function(str){
this.arrConcat=(dojo.render.html.capable&&dojo.render.html["ie"]);
var a=[];
var b="";
var _6b5=this.length=b.length;
if(this.arrConcat){
if(b.length>0){
a.push(b);
}
b="";
}
this.toString=this.valueOf=function(){
return (this.arrConcat)?a.join(""):b;
};
this.append=function(){
for(var x=0;x<arguments.length;x++){
var s=arguments[x];
if(dojo.lang.isArrayLike(s)){
this.append.apply(this,s);
}else{
if(this.arrConcat){
a.push(s);
}else{
b+=s;
}
_6b5+=s.length;
this.length=_6b5;
}
}
return this;
};
this.clear=function(){
a=[];
b="";
_6b5=this.length=0;
return this;
};
this.remove=function(f,l){
var s="";
if(this.arrConcat){
b=a.join("");
}
a=[];
if(f>0){
s=b.substring(0,(f-1));
}
b=s+b.substring(f+l);
_6b5=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.replace=function(o,n){
if(this.arrConcat){
b=a.join("");
}
a=[];
b=b.replace(o,n);
_6b5=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.insert=function(idx,s){
if(this.arrConcat){
b=a.join("");
}
a=[];
if(idx==0){
b=s+b;
}else{
var t=b.split("");
t.splice(idx,0,s);
b=t.join("");
}
_6b5=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.append.apply(this,arguments);
};
dojo.provide("dojo.string.*");
if(!this["dojo"]){
alert("\"dojo/__package__.js\" is now located at \"dojo/dojo.js\". Please update your includes accordingly");
}
dojo.provide("dojo.AdapterRegistry");
dojo.AdapterRegistry=function(_6c0){
this.pairs=[];
this.returnWrappers=_6c0||false;
};
dojo.lang.extend(dojo.AdapterRegistry,{register:function(name,_6c2,wrap,_6c4,_6c5){
var type=(_6c5)?"unshift":"push";
this.pairs[type]([name,_6c2,wrap,_6c4]);
},match:function(){
for(var i=0;i<this.pairs.length;i++){
var pair=this.pairs[i];
if(pair[1].apply(this,arguments)){
if((pair[3])||(this.returnWrappers)){
return pair[2];
}else{
return pair[2].apply(this,arguments);
}
}
}
throw new Error("No match found");
},unregister:function(name){
for(var i=0;i<this.pairs.length;i++){
var pair=this.pairs[i];
if(pair[0]==name){
this.pairs.splice(i,1);
return true;
}
}
return false;
}});
dojo.provide("dojo.json");
dojo.json={jsonRegistry:new dojo.AdapterRegistry(),register:function(name,_6cd,wrap,_6cf){
dojo.json.jsonRegistry.register(name,_6cd,wrap,_6cf);
},evalJson:function(json){
try{
return eval("("+json+")");
}
catch(e){
dojo.debug(e);
return json;
}
},serialize:function(o){
var _6d2=typeof (o);
if(_6d2=="undefined"){
return "undefined";
}else{
if((_6d2=="number")||(_6d2=="boolean")){
return o+"";
}else{
if(o===null){
return "null";
}
}
}
if(_6d2=="string"){
return dojo.string.escapeString(o);
}
var me=arguments.callee;
var _6d4;
if(typeof (o.__json__)=="function"){
_6d4=o.__json__();
if(o!==_6d4){
return me(_6d4);
}
}
if(typeof (o.json)=="function"){
_6d4=o.json();
if(o!==_6d4){
return me(_6d4);
}
}
if(_6d2!="function"&&typeof (o.length)=="number"){
var res=[];
for(var i=0;i<o.length;i++){
var val=me(o[i]);
if(typeof (val)!="string"){
val="undefined";
}
res.push(val);
}
return "["+res.join(",")+"]";
}
try{
window.o=o;
_6d4=dojo.json.jsonRegistry.match(o);
return me(_6d4);
}
catch(e){
}
if(_6d2=="function"){
return null;
}
res=[];
for(var k in o){
var _6d9;
if(typeof (k)=="number"){
_6d9="\""+k+"\"";
}else{
if(typeof (k)=="string"){
_6d9=dojo.string.escapeString(k);
}else{
continue;
}
}
val=me(o[k]);
if(typeof (val)!="string"){
continue;
}
res.push(_6d9+":"+val);
}
return "{"+res.join(",")+"}";
}};
dojo.provide("dojo.Deferred");
dojo.Deferred=function(_6da){
this.chain=[];
this.id=this._nextId();
this.fired=-1;
this.paused=0;
this.results=[null,null];
this.canceller=_6da;
this.silentlyCancelled=false;
};
dojo.lang.extend(dojo.Deferred,{getFunctionFromArgs:function(){
var a=arguments;
if((a[0])&&(!a[1])){
if(dojo.lang.isFunction(a[0])){
return a[0];
}else{
if(dojo.lang.isString(a[0])){
return dj_global[a[0]];
}
}
}else{
if((a[0])&&(a[1])){
return dojo.lang.hitch(a[0],a[1]);
}
}
return null;
},makeCalled:function(){
var _6dc=new dojo.Deferred();
_6dc.callback();
return _6dc;
},repr:function(){
var _6dd;
if(this.fired==-1){
_6dd="unfired";
}else{
if(this.fired==0){
_6dd="success";
}else{
_6dd="error";
}
}
return "Deferred("+this.id+", "+_6dd+")";
},toString:dojo.lang.forward("repr"),_nextId:(function(){
var n=1;
return function(){
return n++;
};
})(),cancel:function(){
if(this.fired==-1){
if(this.canceller){
this.canceller(this);
}else{
this.silentlyCancelled=true;
}
if(this.fired==-1){
this.errback(new Error(this.repr()));
}
}else{
if((this.fired==0)&&(this.results[0] instanceof dojo.Deferred)){
this.results[0].cancel();
}
}
},_pause:function(){
this.paused++;
},_unpause:function(){
this.paused--;
if((this.paused==0)&&(this.fired>=0)){
this._fire();
}
},_continue:function(res){
this._resback(res);
this._unpause();
},_resback:function(res){
this.fired=((res instanceof Error)?1:0);
this.results[this.fired]=res;
this._fire();
},_check:function(){
if(this.fired!=-1){
if(!this.silentlyCancelled){
dojo.raise("already called!");
}
this.silentlyCancelled=false;
return;
}
},callback:function(res){
this._check();
this._resback(res);
},errback:function(res){
this._check();
if(!(res instanceof Error)){
res=new Error(res);
}
this._resback(res);
},addBoth:function(cb,cbfn){
var _6e5=this.getFunctionFromArgs(cb,cbfn);
if(arguments.length>2){
_6e5=dojo.lang.curryArguments(null,_6e5,arguments,2);
}
return this.addCallbacks(_6e5,_6e5);
},addCallback:function(cb,cbfn){
var _6e8=this.getFunctionFromArgs(cb,cbfn);
if(arguments.length>2){
_6e8=dojo.lang.curryArguments(null,_6e8,arguments,2);
}
return this.addCallbacks(_6e8,null);
},addErrback:function(cb,cbfn){
var _6eb=this.getFunctionFromArgs(cb,cbfn);
if(arguments.length>2){
_6eb=dojo.lang.curryArguments(null,_6eb,arguments,2);
}
return this.addCallbacks(null,_6eb);
return this.addCallbacks(null,cbfn);
},addCallbacks:function(cb,eb){
this.chain.push([cb,eb]);
if(this.fired>=0){
this._fire();
}
return this;
},_fire:function(){
var _6ee=this.chain;
var _6ef=this.fired;
var res=this.results[_6ef];
var self=this;
var cb=null;
while(_6ee.length>0&&this.paused==0){
var pair=_6ee.shift();
var f=pair[_6ef];
if(f==null){
continue;
}
try{
res=f(res);
_6ef=((res instanceof Error)?1:0);
if(res instanceof dojo.Deferred){
cb=function(res){
self._continue(res);
};
this._pause();
}
}
catch(err){
_6ef=1;
res=err;
}
}
this.fired=_6ef;
this.results[_6ef]=res;
if((cb)&&(this.paused)){
res.addBoth(cb);
}
}});
dojo.provide("dojo.rpc.RpcService");
dojo.rpc.RpcService=function(url){
if(url){
this.connect(url);
}
};
dojo.lang.extend(dojo.rpc.RpcService,{strictArgChecks:true,serviceUrl:"",parseResults:function(obj){
return obj;
},errorCallback:function(_6f8){
return function(type,e){
_6f8.errback(new Error(e.message));
};
},resultCallback:function(_6fb){
var tf=dojo.lang.hitch(this,function(type,obj,e){
if(obj["error"]!=null){
var err=new Error(obj.error);
err.id=obj.id;
_6fb.errback(err);
}else{
var _701=this.parseResults(obj);
_6fb.callback(_701);
}
});
return tf;
},generateMethod:function(_702,_703,url){
return dojo.lang.hitch(this,function(){
var _705=new dojo.Deferred();
if((this.strictArgChecks)&&(_703!=null)&&(arguments.length!=_703.length)){
dojo.raise("Invalid number of parameters for remote method.");
}else{
this.bind(_702,arguments,_705,url);
}
return _705;
});
},processSmd:function(_706){
dojo.debug("RpcService: Processing returned SMD.");
if(_706.methods){
dojo.lang.forEach(_706.methods,function(m){
if(m&&m["name"]){
dojo.debug("RpcService: Creating Method: this.",m.name,"()");
this[m.name]=this.generateMethod(m.name,m.parameters,m["url"]||m["serviceUrl"]||m["serviceURL"]);
if(dojo.lang.isFunction(this[m.name])){
dojo.debug("RpcService: Successfully created",m.name,"()");
}else{
dojo.debug("RpcService: Failed to create",m.name,"()");
}
}
},this);
}
this.serviceUrl=_706.serviceUrl||_706.serviceURL;
dojo.debug("RpcService: Dojo RpcService is ready for use.");
},connect:function(_708){
dojo.debug("RpcService: Attempting to load SMD document from:",_708);
dojo.io.bind({url:_708,mimetype:"text/json",load:dojo.lang.hitch(this,function(type,_70a,e){
return this.processSmd(_70a);
}),sync:true});
}});
dojo.provide("dojo.rpc.JsonService");
dojo.rpc.JsonService=function(args){
if(args){
if(dojo.lang.isString(args)){
this.connect(args);
}else{
if(args["smdUrl"]){
this.connect(args.smdUrl);
}
if(args["smdStr"]){
this.processSmd(dj_eval("("+args.smdStr+")"));
}
if(args["smdObj"]){
this.processSmd(args.smdObj);
}
if(args["serviceUrl"]){
this.serviceUrl=args.serviceUrl;
}
if(typeof args["strictArgChecks"]!="undefined"){
this.strictArgChecks=args.strictArgChecks;
}
}
}
};
dojo.inherits(dojo.rpc.JsonService,dojo.rpc.RpcService);
dojo.extend(dojo.rpc.JsonService,{bustCache:false,contentType:"application/json-rpc",lastSubmissionId:0,callRemote:function(_70d,_70e){
var _70f=new dojo.Deferred();
this.bind(_70d,_70e,_70f);
return _70f;
},bind:function(_710,_711,_712,url){
dojo.io.bind({url:url||this.serviceUrl,postContent:this.createRequest(_710,_711),method:"POST",contentType:this.contentType,mimetype:"text/json",load:this.resultCallback(_712),error:this.errorCallback(_712),preventCache:this.bustCache});
},createRequest:function(_714,_715){
var req={"params":_715,"method":_714,"id":++this.lastSubmissionId};
var data=dojo.json.serialize(req);
dojo.debug("JsonService: JSON-RPC Request: "+data);
return data;
},parseResults:function(obj){
if(!obj){
return;
}
if(obj["Result"]!=null){
return obj["Result"];
}else{
if(obj["result"]!=null){
return obj["result"];
}else{
if(obj["ResultSet"]){
return obj["ResultSet"];
}else{
return obj;
}
}
}
}});
dojo.provide("dojo.rpc.*");
dojo.provide("dojo.xml.Parse");
dojo.xml.Parse=function(){
var isIE=((dojo.render.html.capable)&&(dojo.render.html.ie));
function getTagName(node){
try{
return node.tagName.toLowerCase();
}
catch(e){
return "";
}
}
function getDojoTagName(node){
var _71c=getTagName(node);
if(!_71c){
return "";
}
if((dojo.widget)&&(dojo.widget.tags[_71c])){
return _71c;
}
var p=_71c.indexOf(":");
if(p>=0){
return _71c;
}
if(_71c.substr(0,5)=="dojo:"){
return _71c;
}
if(dojo.render.html.capable&&dojo.render.html.ie&&node.scopeName!="HTML"){
return node.scopeName.toLowerCase()+":"+_71c;
}
if(_71c.substr(0,4)=="dojo"){
return "dojo:"+_71c.substring(4);
}
var djt=node.getAttribute("dojoType")||node.getAttribute("dojotype");
if(djt){
if(djt.indexOf(":")<0){
djt="dojo:"+djt;
}
return djt.toLowerCase();
}
djt=node.getAttributeNS&&node.getAttributeNS(dojo.dom.dojoml,"type");
if(djt){
return "dojo:"+djt.toLowerCase();
}
try{
djt=node.getAttribute("dojo:type");
}
catch(e){
}
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((dj_global["djConfig"])&&(!djConfig["ignoreClassNames"])){
var _71f=node.className||node.getAttribute("class");
if((_71f)&&(_71f.indexOf)&&(_71f.indexOf("dojo-")!=-1)){
var _720=_71f.split(" ");
for(var x=0,c=_720.length;x<c;x++){
if(_720[x].slice(0,5)=="dojo-"){
return "dojo:"+_720[x].substr(5).toLowerCase();
}
}
}
}
return "";
}
this.parseElement=function(node,_724,_725,_726){
var _727=getTagName(node);
if(isIE&&_727.indexOf("/")==0){
return null;
}
try{
var attr=node.getAttribute("parseWidgets");
if(attr&&attr.toLowerCase()=="false"){
return {};
}
}
catch(e){
}
var _729=true;
if(_725){
var _72a=getDojoTagName(node);
_727=_72a||_727;
_729=Boolean(_72a);
}
var _72b={};
_72b[_727]=[];
var pos=_727.indexOf(":");
if(pos>0){
var ns=_727.substring(0,pos);
_72b["ns"]=ns;
if((dojo.ns)&&(!dojo.ns.allow(ns))){
_729=false;
}
}
if(_729){
var _72e=this.parseAttributes(node);
for(var attr in _72e){
if((!_72b[_727][attr])||(typeof _72b[_727][attr]!="array")){
_72b[_727][attr]=[];
}
_72b[_727][attr].push(_72e[attr]);
}
_72b[_727].nodeRef=node;
_72b.tagName=_727;
_72b.index=_726||0;
}
var _72f=0;
for(var i=0;i<node.childNodes.length;i++){
var tcn=node.childNodes.item(i);
switch(tcn.nodeType){
case dojo.dom.ELEMENT_NODE:
var ctn=getDojoTagName(tcn)||getTagName(tcn);
if(!_72b[ctn]){
_72b[ctn]=[];
}
_72b[ctn].push(this.parseElement(tcn,true,_725,_72f));
if((tcn.childNodes.length==1)&&(tcn.childNodes.item(0).nodeType==dojo.dom.TEXT_NODE)){
_72b[ctn][_72b[ctn].length-1].value=tcn.childNodes.item(0).nodeValue;
}
_72f++;
break;
case dojo.dom.TEXT_NODE:
if(node.childNodes.length==1){
_72b[_727].push({value:node.childNodes.item(0).nodeValue});
}
break;
default:
break;
}
}
return _72b;
};
this.parseAttributes=function(node){
var _734={};
var atts=node.attributes;
var _736,i=0;
while((_736=atts[i++])){
if(isIE){
if(!_736){
continue;
}
if((typeof _736=="object")&&(typeof _736.nodeValue=="undefined")||(_736.nodeValue==null)||(_736.nodeValue=="")){
continue;
}
}
var nn=_736.nodeName.split(":");
nn=(nn.length==2)?nn[1]:_736.nodeName;
_734[nn]={value:_736.nodeValue};
}
return _734;
};
};
dojo.provide("dojo.xml.*");
dojo.provide("dojo.undo.Manager");
dojo.undo.Manager=function(_739){
this.clear();
this._parent=_739;
};
dojo.extend(dojo.undo.Manager,{_parent:null,_undoStack:null,_redoStack:null,_currentManager:null,canUndo:false,canRedo:false,isUndoing:false,isRedoing:false,onUndo:function(_73a,item){
},onRedo:function(_73c,item){
},onUndoAny:function(_73e,item){
},onRedoAny:function(_740,item){
},_updateStatus:function(){
this.canUndo=this._undoStack.length>0;
this.canRedo=this._redoStack.length>0;
},clear:function(){
this._undoStack=[];
this._redoStack=[];
this._currentManager=this;
this.isUndoing=false;
this.isRedoing=false;
this._updateStatus();
},undo:function(){
if(!this.canUndo){
return false;
}
this.endAllTransactions();
this.isUndoing=true;
var top=this._undoStack.pop();
if(top instanceof dojo.undo.Manager){
top.undoAll();
}else{
top.undo();
}
if(top.redo){
this._redoStack.push(top);
}
this.isUndoing=false;
this._updateStatus();
this.onUndo(this,top);
if(!(top instanceof dojo.undo.Manager)){
this.getTop().onUndoAny(this,top);
}
return true;
},redo:function(){
if(!this.canRedo){
return false;
}
this.isRedoing=true;
var top=this._redoStack.pop();
if(top instanceof dojo.undo.Manager){
top.redoAll();
}else{
top.redo();
}
this._undoStack.push(top);
this.isRedoing=false;
this._updateStatus();
this.onRedo(this,top);
if(!(top instanceof dojo.undo.Manager)){
this.getTop().onRedoAny(this,top);
}
return true;
},undoAll:function(){
while(this._undoStack.length>0){
this.undo();
}
},redoAll:function(){
while(this._redoStack.length>0){
this.redo();
}
},push:function(undo,redo,_746){
if(!undo){
return;
}
if(this._currentManager==this){
this._undoStack.push({undo:undo,redo:redo,description:_746});
}else{
this._currentManager.push.apply(this._currentManager,arguments);
}
this._redoStack=[];
this._updateStatus();
},concat:function(_747){
if(!_747){
return;
}
if(this._currentManager==this){
for(var x=0;x<_747._undoStack.length;x++){
this._undoStack.push(_747._undoStack[x]);
}
if(_747._undoStack.length>0){
this._redoStack=[];
}
this._updateStatus();
}else{
this._currentManager.concat.apply(this._currentManager,arguments);
}
},beginTransaction:function(_749){
if(this._currentManager==this){
var mgr=new dojo.undo.Manager(this);
mgr.description=_749?_749:"";
this._undoStack.push(mgr);
this._currentManager=mgr;
return mgr;
}else{
this._currentManager=this._currentManager.beginTransaction.apply(this._currentManager,arguments);
}
},endTransaction:function(_74b){
if(this._currentManager==this){
if(this._parent){
this._parent._currentManager=this._parent;
if(this._undoStack.length==0||_74b){
var idx=dojo.lang.find(this._parent._undoStack,this);
if(idx>=0){
this._parent._undoStack.splice(idx,1);
if(_74b){
for(var x=0;x<this._undoStack.length;x++){
this._parent._undoStack.splice(idx++,0,this._undoStack[x]);
}
this._updateStatus();
}
}
}
return this._parent;
}
}else{
this._currentManager=this._currentManager.endTransaction.apply(this._currentManager,arguments);
}
},endAllTransactions:function(){
while(this._currentManager!=this){
this.endTransaction();
}
},getTop:function(){
if(this._parent){
return this._parent.getTop();
}else{
return this;
}
}});
dojo.provide("dojo.undo.*");
dojo.provide("dojo.crypto");
dojo.crypto.cipherModes={ECB:0,CBC:1,PCBC:2,CFB:3,OFB:4,CTR:5};
dojo.crypto.outputTypes={Base64:0,Hex:1,String:2,Raw:3};
dojo.provide("dojo.crypto.MD5");
dojo.crypto.MD5=new function(){
var _74e=8;
var mask=(1<<_74e)-1;
function toWord(s){
var wa=[];
for(var i=0;i<s.length*_74e;i+=_74e){
wa[i>>5]|=(s.charCodeAt(i/_74e)&mask)<<(i%32);
}
return wa;
}
function toString(wa){
var s=[];
for(var i=0;i<wa.length*32;i+=_74e){
s.push(String.fromCharCode((wa[i>>5]>>>(i%32))&mask));
}
return s.join("");
}
function toHex(wa){
var h="0123456789abcdef";
var s=[];
for(var i=0;i<wa.length*4;i++){
s.push(h.charAt((wa[i>>2]>>((i%4)*8+4))&15)+h.charAt((wa[i>>2]>>((i%4)*8))&15));
}
return s.join("");
}
function toBase64(wa){
var p="=";
var tab="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var s=[];
for(var i=0;i<wa.length*4;i+=3){
var t=(((wa[i>>2]>>8*(i%4))&255)<<16)|(((wa[i+1>>2]>>8*((i+1)%4))&255)<<8)|((wa[i+2>>2]>>8*((i+2)%4))&255);
for(var j=0;j<4;j++){
if(i*8+j*6>wa.length*32){
s.push(p);
}else{
s.push(tab.charAt((t>>6*(3-j))&63));
}
}
}
return s.join("");
}
function add(x,y){
var l=(x&65535)+(y&65535);
var m=(x>>16)+(y>>16)+(l>>16);
return (m<<16)|(l&65535);
}
function R(n,c){
return (n<<c)|(n>>>(32-c));
}
function C(q,a,b,x,s,t){
return add(R(add(add(a,q),add(x,t)),s),b);
}
function FF(a,b,c,d,x,s,t){
return C((b&c)|((~b)&d),a,b,x,s,t);
}
function GG(a,b,c,d,x,s,t){
return C((b&d)|(c&(~d)),a,b,x,s,t);
}
function HH(a,b,c,d,x,s,t){
return C(b^c^d,a,b,x,s,t);
}
function II(a,b,c,d,x,s,t){
return C(c^(b|(~d)),a,b,x,s,t);
}
function core(x,len){
x[len>>5]|=128<<((len)%32);
x[(((len+64)>>>9)<<4)+14]=len;
var a=1732584193;
var b=-271733879;
var c=-1732584194;
var d=271733878;
for(var i=0;i<x.length;i+=16){
var olda=a;
var oldb=b;
var oldc=c;
var oldd=d;
a=FF(a,b,c,d,x[i+0],7,-680876936);
d=FF(d,a,b,c,x[i+1],12,-389564586);
c=FF(c,d,a,b,x[i+2],17,606105819);
b=FF(b,c,d,a,x[i+3],22,-1044525330);
a=FF(a,b,c,d,x[i+4],7,-176418897);
d=FF(d,a,b,c,x[i+5],12,1200080426);
c=FF(c,d,a,b,x[i+6],17,-1473231341);
b=FF(b,c,d,a,x[i+7],22,-45705983);
a=FF(a,b,c,d,x[i+8],7,1770035416);
d=FF(d,a,b,c,x[i+9],12,-1958414417);
c=FF(c,d,a,b,x[i+10],17,-42063);
b=FF(b,c,d,a,x[i+11],22,-1990404162);
a=FF(a,b,c,d,x[i+12],7,1804603682);
d=FF(d,a,b,c,x[i+13],12,-40341101);
c=FF(c,d,a,b,x[i+14],17,-1502002290);
b=FF(b,c,d,a,x[i+15],22,1236535329);
a=GG(a,b,c,d,x[i+1],5,-165796510);
d=GG(d,a,b,c,x[i+6],9,-1069501632);
c=GG(c,d,a,b,x[i+11],14,643717713);
b=GG(b,c,d,a,x[i+0],20,-373897302);
a=GG(a,b,c,d,x[i+5],5,-701558691);
d=GG(d,a,b,c,x[i+10],9,38016083);
c=GG(c,d,a,b,x[i+15],14,-660478335);
b=GG(b,c,d,a,x[i+4],20,-405537848);
a=GG(a,b,c,d,x[i+9],5,568446438);
d=GG(d,a,b,c,x[i+14],9,-1019803690);
c=GG(c,d,a,b,x[i+3],14,-187363961);
b=GG(b,c,d,a,x[i+8],20,1163531501);
a=GG(a,b,c,d,x[i+13],5,-1444681467);
d=GG(d,a,b,c,x[i+2],9,-51403784);
c=GG(c,d,a,b,x[i+7],14,1735328473);
b=GG(b,c,d,a,x[i+12],20,-1926607734);
a=HH(a,b,c,d,x[i+5],4,-378558);
d=HH(d,a,b,c,x[i+8],11,-2022574463);
c=HH(c,d,a,b,x[i+11],16,1839030562);
b=HH(b,c,d,a,x[i+14],23,-35309556);
a=HH(a,b,c,d,x[i+1],4,-1530992060);
d=HH(d,a,b,c,x[i+4],11,1272893353);
c=HH(c,d,a,b,x[i+7],16,-155497632);
b=HH(b,c,d,a,x[i+10],23,-1094730640);
a=HH(a,b,c,d,x[i+13],4,681279174);
d=HH(d,a,b,c,x[i+0],11,-358537222);
c=HH(c,d,a,b,x[i+3],16,-722521979);
b=HH(b,c,d,a,x[i+6],23,76029189);
a=HH(a,b,c,d,x[i+9],4,-640364487);
d=HH(d,a,b,c,x[i+12],11,-421815835);
c=HH(c,d,a,b,x[i+15],16,530742520);
b=HH(b,c,d,a,x[i+2],23,-995338651);
a=II(a,b,c,d,x[i+0],6,-198630844);
d=II(d,a,b,c,x[i+7],10,1126891415);
c=II(c,d,a,b,x[i+14],15,-1416354905);
b=II(b,c,d,a,x[i+5],21,-57434055);
a=II(a,b,c,d,x[i+12],6,1700485571);
d=II(d,a,b,c,x[i+3],10,-1894986606);
c=II(c,d,a,b,x[i+10],15,-1051523);
b=II(b,c,d,a,x[i+1],21,-2054922799);
a=II(a,b,c,d,x[i+8],6,1873313359);
d=II(d,a,b,c,x[i+15],10,-30611744);
c=II(c,d,a,b,x[i+6],15,-1560198380);
b=II(b,c,d,a,x[i+13],21,1309151649);
a=II(a,b,c,d,x[i+4],6,-145523070);
d=II(d,a,b,c,x[i+11],10,-1120210379);
c=II(c,d,a,b,x[i+2],15,718787259);
b=II(b,c,d,a,x[i+9],21,-343485551);
a=add(a,olda);
b=add(b,oldb);
c=add(c,oldc);
d=add(d,oldd);
}
return [a,b,c,d];
}
function hmac(data,key){
var wa=toWord(key);
if(wa.length>16){
wa=core(wa,key.length*_74e);
}
var l=[],r=[];
for(var i=0;i<16;i++){
l[i]=wa[i]^909522486;
r[i]=wa[i]^1549556828;
}
var h=core(l.concat(toWord(data)),512+data.length*_74e);
return core(r.concat(h),640);
}
this.compute=function(data,_79c){
var out=_79c||dojo.crypto.outputTypes.Base64;
switch(out){
case dojo.crypto.outputTypes.Hex:
return toHex(core(toWord(data),data.length*_74e));
case dojo.crypto.outputTypes.String:
return toString(core(toWord(data),data.length*_74e));
default:
return toBase64(core(toWord(data),data.length*_74e));
}
};
this.getHMAC=function(data,key,_7a0){
var out=_7a0||dojo.crypto.outputTypes.Base64;
switch(out){
case dojo.crypto.outputTypes.Hex:
return toHex(hmac(data,key));
case dojo.crypto.outputTypes.String:
return toString(hmac(data,key));
default:
return toBase64(hmac(data,key));
}
};
}();
dojo.provide("dojo.crypto.*");
dojo.provide("dojo.collections.Collections");
dojo.collections.DictionaryEntry=function(k,v){
this.key=k;
this.value=v;
this.valueOf=function(){
return this.value;
};
this.toString=function(){
return String(this.value);
};
};
dojo.collections.Iterator=function(arr){
var a=arr;
var _7a6=0;
this.element=a[_7a6]||null;
this.atEnd=function(){
return (_7a6>=a.length);
};
this.get=function(){
if(this.atEnd()){
return null;
}
this.element=a[_7a6++];
return this.element;
};
this.map=function(fn,_7a8){
var s=_7a8||dj_global;
if(Array.map){
return Array.map(a,fn,s);
}else{
var arr=[];
for(var i=0;i<a.length;i++){
arr.push(fn.call(s,a[i]));
}
return arr;
}
};
this.reset=function(){
_7a6=0;
this.element=a[_7a6];
};
};
dojo.collections.DictionaryIterator=function(obj){
var a=[];
var _7ae={};
for(var p in obj){
if(!_7ae[p]){
a.push(obj[p]);
}
}
var _7b0=0;
this.element=a[_7b0]||null;
this.atEnd=function(){
return (_7b0>=a.length);
};
this.get=function(){
if(this.atEnd()){
return null;
}
this.element=a[_7b0++];
return this.element;
};
this.map=function(fn,_7b2){
var s=_7b2||dj_global;
if(Array.map){
return Array.map(a,fn,s);
}else{
var arr=[];
for(var i=0;i<a.length;i++){
arr.push(fn.call(s,a[i]));
}
return arr;
}
};
this.reset=function(){
_7b0=0;
this.element=a[_7b0];
};
};
dojo.provide("dojo.collections.ArrayList");
dojo.collections.ArrayList=function(arr){
var _7b7=[];
if(arr){
_7b7=_7b7.concat(arr);
}
this.count=_7b7.length;
this.add=function(obj){
_7b7.push(obj);
this.count=_7b7.length;
};
this.addRange=function(a){
if(a.getIterator){
var e=a.getIterator();
while(!e.atEnd()){
this.add(e.get());
}
this.count=_7b7.length;
}else{
for(var i=0;i<a.length;i++){
_7b7.push(a[i]);
}
this.count=_7b7.length;
}
};
this.clear=function(){
_7b7.splice(0,_7b7.length);
this.count=0;
};
this.clone=function(){
return new dojo.collections.ArrayList(_7b7);
};
this.contains=function(obj){
for(var i=0;i<_7b7.length;i++){
if(_7b7[i]==obj){
return true;
}
}
return false;
};
this.forEach=function(fn,_7bf){
var s=_7bf||dj_global;
if(Array.forEach){
Array.forEach(_7b7,fn,s);
}else{
for(var i=0;i<_7b7.length;i++){
fn.call(s,_7b7[i],i,_7b7);
}
}
};
this.getIterator=function(){
return new dojo.collections.Iterator(_7b7);
};
this.indexOf=function(obj){
for(var i=0;i<_7b7.length;i++){
if(_7b7[i]==obj){
return i;
}
}
return -1;
};
this.insert=function(i,obj){
_7b7.splice(i,0,obj);
this.count=_7b7.length;
};
this.item=function(i){
return _7b7[i];
};
this.remove=function(obj){
var i=this.indexOf(obj);
if(i>=0){
_7b7.splice(i,1);
}
this.count=_7b7.length;
};
this.removeAt=function(i){
_7b7.splice(i,1);
this.count=_7b7.length;
};
this.reverse=function(){
_7b7.reverse();
};
this.sort=function(fn){
if(fn){
_7b7.sort(fn);
}else{
_7b7.sort();
}
};
this.setByIndex=function(i,obj){
_7b7[i]=obj;
this.count=_7b7.length;
};
this.toArray=function(){
return [].concat(_7b7);
};
this.toString=function(_7cd){
return _7b7.join((_7cd||","));
};
};
dojo.provide("dojo.collections.Queue");
dojo.collections.Queue=function(arr){
var q=[];
if(arr){
q=q.concat(arr);
}
this.count=q.length;
this.clear=function(){
q=[];
this.count=q.length;
};
this.clone=function(){
return new dojo.collections.Queue(q);
};
this.contains=function(o){
for(var i=0;i<q.length;i++){
if(q[i]==o){
return true;
}
}
return false;
};
this.copyTo=function(arr,i){
arr.splice(i,0,q);
};
this.dequeue=function(){
var r=q.shift();
this.count=q.length;
return r;
};
this.enqueue=function(o){
this.count=q.push(o);
};
this.forEach=function(fn,_7d7){
var s=_7d7||dj_global;
if(Array.forEach){
Array.forEach(q,fn,s);
}else{
for(var i=0;i<q.length;i++){
fn.call(s,q[i],i,q);
}
}
};
this.getIterator=function(){
return new dojo.collections.Iterator(q);
};
this.peek=function(){
return q[0];
};
this.toArray=function(){
return [].concat(q);
};
};
dojo.provide("dojo.collections.Stack");
dojo.collections.Stack=function(arr){
var q=[];
if(arr){
q=q.concat(arr);
}
this.count=q.length;
this.clear=function(){
q=[];
this.count=q.length;
};
this.clone=function(){
return new dojo.collections.Stack(q);
};
this.contains=function(o){
for(var i=0;i<q.length;i++){
if(q[i]==o){
return true;
}
}
return false;
};
this.copyTo=function(arr,i){
arr.splice(i,0,q);
};
this.forEach=function(fn,_7e1){
var s=_7e1||dj_global;
if(Array.forEach){
Array.forEach(q,fn,s);
}else{
for(var i=0;i<q.length;i++){
fn.call(s,q[i],i,q);
}
}
};
this.getIterator=function(){
return new dojo.collections.Iterator(q);
};
this.peek=function(){
return q[(q.length-1)];
};
this.pop=function(){
var r=q.pop();
this.count=q.length;
return r;
};
this.push=function(o){
this.count=q.push(o);
};
this.toArray=function(){
return [].concat(q);
};
};
dojo.provide("dojo.dnd.DragAndDrop");
dojo.declare("dojo.dnd.DragSource",null,{type:"",onDragEnd:function(evt){
},onDragStart:function(evt){
},onSelected:function(evt){
},unregister:function(){
dojo.dnd.dragManager.unregisterDragSource(this);
},reregister:function(){
dojo.dnd.dragManager.registerDragSource(this);
}});
dojo.declare("dojo.dnd.DragObject",null,{type:"",register:function(){
var dm=dojo.dnd.dragManager;
if(dm["registerDragObject"]){
dm.registerDragObject(this);
}
},onDragStart:function(evt){
},onDragMove:function(evt){
},onDragOver:function(evt){
},onDragOut:function(evt){
},onDragEnd:function(evt){
},onDragLeave:dojo.lang.forward("onDragOut"),onDragEnter:dojo.lang.forward("onDragOver"),ondragout:dojo.lang.forward("onDragOut"),ondragover:dojo.lang.forward("onDragOver")});
dojo.declare("dojo.dnd.DropTarget",null,{acceptsType:function(type){
if(!dojo.lang.inArray(this.acceptedTypes,"*")){
if(!dojo.lang.inArray(this.acceptedTypes,type)){
return false;
}
}
return true;
},accepts:function(_7f0){
if(!dojo.lang.inArray(this.acceptedTypes,"*")){
for(var i=0;i<_7f0.length;i++){
if(!dojo.lang.inArray(this.acceptedTypes,_7f0[i].type)){
return false;
}
}
}
return true;
},unregister:function(){
dojo.dnd.dragManager.unregisterDropTarget(this);
},onDragOver:function(evt){
},onDragOut:function(evt){
},onDragMove:function(evt){
},onDropStart:function(evt){
},onDrop:function(evt){
},onDropEnd:function(){
}},function(){
this.acceptedTypes=[];
});
dojo.dnd.DragEvent=function(){
this.dragSource=null;
this.dragObject=null;
this.target=null;
this.eventStatus="success";
};
dojo.declare("dojo.dnd.DragManager",null,{selectedSources:[],dragObjects:[],dragSources:[],registerDragSource:function(_7f7){
},dropTargets:[],registerDropTarget:function(_7f8){
},lastDragTarget:null,currentDragTarget:null,onKeyDown:function(){
},onMouseOut:function(){
},onMouseMove:function(){
},onMouseUp:function(){
}});
dojo.provide("dojo.dnd.HtmlDragManager");
dojo.declare("dojo.dnd.HtmlDragManager",dojo.dnd.DragManager,{disabled:false,nestedTargets:false,mouseDownTimer:null,dsCounter:0,dsPrefix:"dojoDragSource",dropTargetDimensions:[],currentDropTarget:null,previousDropTarget:null,_dragTriggered:false,selectedSources:[],dragObjects:[],dragSources:[],currentX:null,currentY:null,lastX:null,lastY:null,mouseDownX:null,mouseDownY:null,threshold:7,dropAcceptable:false,cancelEvent:function(e){
e.stopPropagation();
e.preventDefault();
},registerDragSource:function(ds){
if(ds["domNode"]){
var dp=this.dsPrefix;
var _7fc=dp+"Idx_"+(this.dsCounter++);
ds.dragSourceId=_7fc;
this.dragSources[_7fc]=ds;
ds.domNode.setAttribute(dp,_7fc);
if(dojo.render.html.ie){
dojo.event.browser.addListener(ds.domNode,"ondragstart",this.cancelEvent);
}
}
},unregisterDragSource:function(ds){
if(ds["domNode"]){
var dp=this.dsPrefix;
var _7ff=ds.dragSourceId;
delete ds.dragSourceId;
delete this.dragSources[_7ff];
ds.domNode.setAttribute(dp,null);
if(dojo.render.html.ie){
dojo.event.browser.removeListener(ds.domNode,"ondragstart",this.cancelEvent);
}
}
},registerDropTarget:function(dt){
this.dropTargets.push(dt);
},unregisterDropTarget:function(dt){
var _802=dojo.lang.find(this.dropTargets,dt,true);
if(_802>=0){
this.dropTargets.splice(_802,1);
}
},getDragSource:function(e){
var tn=e.target;
if(tn===dojo.body()){
return;
}
var ta=dojo.html.getAttribute(tn,this.dsPrefix);
while((!ta)&&(tn)){
tn=tn.parentNode;
if((!tn)||(tn===dojo.body())){
return;
}
ta=dojo.html.getAttribute(tn,this.dsPrefix);
}
return this.dragSources[ta];
},onKeyDown:function(e){
},onMouseDown:function(e){
if(this.disabled){
return;
}
if(dojo.render.html.ie){
if(e.button!=1){
return;
}
}else{
if(e.which!=1){
return;
}
}
var _808=e.target.nodeType==dojo.html.TEXT_NODE?e.target.parentNode:e.target;
if(dojo.html.isTag(_808,"button","textarea","input","select","option")){
return;
}
var ds=this.getDragSource(e);
if(!ds){
return;
}
if(!dojo.lang.inArray(this.selectedSources,ds)){
this.selectedSources.push(ds);
ds.onSelected();
}
this.mouseDownX=e.pageX;
this.mouseDownY=e.pageY;
e.preventDefault();
dojo.event.connect(document,"onmousemove",this,"onMouseMove");
},onMouseUp:function(e,_80b){
if(this.selectedSources.length==0){
return;
}
this.mouseDownX=null;
this.mouseDownY=null;
this._dragTriggered=false;
e.dragSource=this.dragSource;
if((!e.shiftKey)&&(!e.ctrlKey)){
if(this.currentDropTarget){
this.currentDropTarget.onDropStart();
}
dojo.lang.forEach(this.dragObjects,function(_80c){
var ret=null;
if(!_80c){
return;
}
if(this.currentDropTarget){
e.dragObject=_80c;
var ce=this.currentDropTarget.domNode.childNodes;
if(ce.length>0){
e.dropTarget=ce[0];
while(e.dropTarget==_80c.domNode){
e.dropTarget=e.dropTarget.nextSibling;
}
}else{
e.dropTarget=this.currentDropTarget.domNode;
}
if(this.dropAcceptable){
ret=this.currentDropTarget.onDrop(e);
}else{
this.currentDropTarget.onDragOut(e);
}
}
e.dragStatus=this.dropAcceptable&&ret?"dropSuccess":"dropFailure";
dojo.lang.delayThese([function(){
try{
_80c.dragSource.onDragEnd(e);
}
catch(err){
var _80f={};
for(var i in e){
if(i=="type"){
_80f.type="mouseup";
continue;
}
_80f[i]=e[i];
}
_80c.dragSource.onDragEnd(_80f);
}
},function(){
_80c.onDragEnd(e);
}]);
},this);
this.selectedSources=[];
this.dragObjects=[];
this.dragSource=null;
if(this.currentDropTarget){
this.currentDropTarget.onDropEnd();
}
}else{
}
dojo.event.disconnect(document,"onmousemove",this,"onMouseMove");
this.currentDropTarget=null;
},onScroll:function(){
for(var i=0;i<this.dragObjects.length;i++){
if(this.dragObjects[i].updateDragOffset){
this.dragObjects[i].updateDragOffset();
}
}
if(this.dragObjects.length){
this.cacheTargetLocations();
}
},_dragStartDistance:function(x,y){
if((!this.mouseDownX)||(!this.mouseDownX)){
return;
}
var dx=Math.abs(x-this.mouseDownX);
var dx2=dx*dx;
var dy=Math.abs(y-this.mouseDownY);
var dy2=dy*dy;
return parseInt(Math.sqrt(dx2+dy2),10);
},cacheTargetLocations:function(){
dojo.profile.start("cacheTargetLocations");
this.dropTargetDimensions=[];
dojo.lang.forEach(this.dropTargets,function(_818){
var tn=_818.domNode;
if(!tn||!_818.accepts([this.dragSource])){
return;
}
var abs=dojo.html.getAbsolutePosition(tn,true);
var bb=dojo.html.getBorderBox(tn);
this.dropTargetDimensions.push([[abs.x,abs.y],[abs.x+bb.width,abs.y+bb.height],_818]);
},this);
dojo.profile.end("cacheTargetLocations");
},onMouseMove:function(e){
if((dojo.render.html.ie)&&(e.button!=1)){
this.currentDropTarget=null;
this.onMouseUp(e,true);
return;
}
if((this.selectedSources.length)&&(!this.dragObjects.length)){
var dx;
var dy;
if(!this._dragTriggered){
this._dragTriggered=(this._dragStartDistance(e.pageX,e.pageY)>this.threshold);
if(!this._dragTriggered){
return;
}
dx=e.pageX-this.mouseDownX;
dy=e.pageY-this.mouseDownY;
}
this.dragSource=this.selectedSources[0];
dojo.lang.forEach(this.selectedSources,function(_81f){
if(!_81f){
return;
}
var tdo=_81f.onDragStart(e);
if(tdo){
tdo.onDragStart(e);
tdo.dragOffset.y+=dy;
tdo.dragOffset.x+=dx;
tdo.dragSource=_81f;
this.dragObjects.push(tdo);
}
},this);
this.previousDropTarget=null;
this.cacheTargetLocations();
}
dojo.lang.forEach(this.dragObjects,function(_821){
if(_821){
_821.onDragMove(e);
}
});
if(this.currentDropTarget){
var c=dojo.html.toCoordinateObject(this.currentDropTarget.domNode,true);
var dtp=[[c.x,c.y],[c.x+c.width,c.y+c.height]];
}
if((!this.nestedTargets)&&(dtp)&&(this.isInsideBox(e,dtp))){
if(this.dropAcceptable){
this.currentDropTarget.onDragMove(e,this.dragObjects);
}
}else{
var _824=this.findBestTarget(e);
if(_824.target===null){
if(this.currentDropTarget){
this.currentDropTarget.onDragOut(e);
this.previousDropTarget=this.currentDropTarget;
this.currentDropTarget=null;
}
this.dropAcceptable=false;
return;
}
if(this.currentDropTarget!==_824.target){
if(this.currentDropTarget){
this.previousDropTarget=this.currentDropTarget;
this.currentDropTarget.onDragOut(e);
}
this.currentDropTarget=_824.target;
e.dragObjects=this.dragObjects;
this.dropAcceptable=this.currentDropTarget.onDragOver(e);
}else{
if(this.dropAcceptable){
this.currentDropTarget.onDragMove(e,this.dragObjects);
}
}
}
},findBestTarget:function(e){
var _826=this;
var _827=new Object();
_827.target=null;
_827.points=null;
dojo.lang.every(this.dropTargetDimensions,function(_828){
if(!_826.isInsideBox(e,_828)){
return true;
}
_827.target=_828[2];
_827.points=_828;
return Boolean(_826.nestedTargets);
});
return _827;
},isInsideBox:function(e,_82a){
if((e.pageX>_82a[0][0])&&(e.pageX<_82a[1][0])&&(e.pageY>_82a[0][1])&&(e.pageY<_82a[1][1])){
return true;
}
return false;
},onMouseOver:function(e){
},onMouseOut:function(e){
}});
dojo.dnd.dragManager=new dojo.dnd.HtmlDragManager();
(function(){
var d=document;
var dm=dojo.dnd.dragManager;
dojo.event.connect(d,"onkeydown",dm,"onKeyDown");
dojo.event.connect(d,"onmouseover",dm,"onMouseOver");
dojo.event.connect(d,"onmouseout",dm,"onMouseOut");
dojo.event.connect(d,"onmousedown",dm,"onMouseDown");
dojo.event.connect(d,"onmouseup",dm,"onMouseUp");
dojo.event.connect(window,"onscroll",dm,"onScroll");
})();
dojo.provide("dojo.html.selection");
dojo.html.selectionType={NONE:0,TEXT:1,CONTROL:2};
dojo.html.clearSelection=function(){
var _82f=dojo.global();
var _830=dojo.doc();
try{
if(_82f["getSelection"]){
if(dojo.render.html.safari){
_82f.getSelection().collapse();
}else{
_82f.getSelection().removeAllRanges();
}
}else{
if(_830.selection){
if(_830.selection.empty){
_830.selection.empty();
}else{
if(_830.selection.clear){
_830.selection.clear();
}
}
}
}
return true;
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.html.disableSelection=function(_831){
_831=dojo.byId(_831)||dojo.body();
var h=dojo.render.html;
if(h.mozilla){
_831.style.MozUserSelect="none";
}else{
if(h.safari){
_831.style.KhtmlUserSelect="none";
}else{
if(h.ie){
_831.unselectable="on";
}else{
return false;
}
}
}
return true;
};
dojo.html.enableSelection=function(_833){
_833=dojo.byId(_833)||dojo.body();
var h=dojo.render.html;
if(h.mozilla){
_833.style.MozUserSelect="";
}else{
if(h.safari){
_833.style.KhtmlUserSelect="";
}else{
if(h.ie){
_833.unselectable="off";
}else{
return false;
}
}
}
return true;
};
dojo.html.selectElement=function(_835){
dojo.deprecated("dojo.html.selectElement","replaced by dojo.html.selection.selectElementChildren",0.5);
};
dojo.html.selectInputText=function(_836){
var _837=dojo.global();
var _838=dojo.doc();
_836=dojo.byId(_836);
if(_838["selection"]&&dojo.body()["createTextRange"]){
var _839=_836.createTextRange();
_839.moveStart("character",0);
_839.moveEnd("character",_836.value.length);
_839.select();
}else{
if(_837["getSelection"]){
var _83a=_837.getSelection();
_836.setSelectionRange(0,_836.value.length);
}
}
_836.focus();
};
dojo.html.isSelectionCollapsed=function(){
dojo.deprecated("dojo.html.isSelectionCollapsed","replaced by dojo.html.selection.isCollapsed",0.5);
return dojo.html.selection.isCollapsed();
};
dojo.lang.mixin(dojo.html.selection,{getType:function(){
if(dojo.doc()["selection"]){
return dojo.html.selectionType[dojo.doc().selection.type.toUpperCase()];
}else{
var _83b=dojo.html.selectionType.TEXT;
var oSel;
try{
oSel=dojo.global().getSelection();
}
catch(e){
}
if(oSel&&oSel.rangeCount==1){
var _83d=oSel.getRangeAt(0);
if(_83d.startContainer==_83d.endContainer&&(_83d.endOffset-_83d.startOffset)==1&&_83d.startContainer.nodeType!=dojo.dom.TEXT_NODE){
_83b=dojo.html.selectionType.CONTROL;
}
}
return _83b;
}
},isCollapsed:function(){
var _83e=dojo.global();
var _83f=dojo.doc();
if(_83f["selection"]){
return _83f.selection.createRange().text=="";
}else{
if(_83e["getSelection"]){
var _840=_83e.getSelection();
if(dojo.lang.isString(_840)){
return _840=="";
}else{
return _840.isCollapsed||_840.toString()=="";
}
}
}
},getSelectedElement:function(){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
if(dojo.doc()["selection"]){
var _841=dojo.doc().selection.createRange();
if(_841&&_841.item){
return dojo.doc().selection.createRange().item(0);
}
}else{
var _842=dojo.global().getSelection();
return _842.anchorNode.childNodes[_842.anchorOffset];
}
}
},getParentElement:function(){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
var p=dojo.html.selection.getSelectedElement();
if(p){
return p.parentNode;
}
}else{
if(dojo.doc()["selection"]){
return dojo.doc().selection.createRange().parentElement();
}else{
var _844=dojo.global().getSelection();
if(_844){
var node=_844.anchorNode;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.parentNode;
}
return node;
}
}
}
},getSelectedText:function(){
if(dojo.doc()["selection"]){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
return null;
}
return dojo.doc().selection.createRange().text;
}else{
var _846=dojo.global().getSelection();
if(_846){
return _846.toString();
}
}
},getSelectedHtml:function(){
if(dojo.doc()["selection"]){
if(dojo.html.selection.getType()==dojo.html.selectionType.CONTROL){
return null;
}
return dojo.doc().selection.createRange().htmlText;
}else{
var _847=dojo.global().getSelection();
if(_847&&_847.rangeCount){
var frag=_847.getRangeAt(0).cloneContents();
var div=document.createElement("div");
div.appendChild(frag);
return div.innerHTML;
}
return null;
}
},hasAncestorElement:function(_84a){
return (dojo.html.selection.getAncestorElement.apply(this,arguments)!=null);
},getAncestorElement:function(_84b){
var node=dojo.html.selection.getSelectedElement()||dojo.html.selection.getParentElement();
while(node){
if(dojo.html.selection.isTag(node,arguments).length>0){
return node;
}
node=node.parentNode;
}
return null;
},isTag:function(node,tags){
if(node&&node.tagName){
for(var i=0;i<tags.length;i++){
if(node.tagName.toLowerCase()==String(tags[i]).toLowerCase()){
return String(tags[i]).toLowerCase();
}
}
}
return "";
},selectElement:function(_850){
var _851=dojo.global();
var _852=dojo.doc();
_850=dojo.byId(_850);
if(_852.selection&&dojo.body().createTextRange){
try{
var _853=dojo.body().createControlRange();
_853.addElement(_850);
_853.select();
}
catch(e){
dojo.html.selection.selectElementChildren(_850);
}
}else{
if(_851["getSelection"]){
var _854=_851.getSelection();
if(_854["removeAllRanges"]){
var _853=_852.createRange();
_853.selectNode(_850);
_854.removeAllRanges();
_854.addRange(_853);
}
}
}
},selectElementChildren:function(_855){
var _856=dojo.global();
var _857=dojo.doc();
_855=dojo.byId(_855);
if(_857.selection&&dojo.body().createTextRange){
var _858=dojo.body().createTextRange();
_858.moveToElementText(_855);
_858.select();
}else{
if(_856["getSelection"]){
var _859=_856.getSelection();
if(_859["setBaseAndExtent"]){
_859.setBaseAndExtent(_855,0,_855,_855.innerText.length-1);
}else{
if(_859["selectAllChildren"]){
_859.selectAllChildren(_855);
}
}
}
}
},getBookmark:function(){
var _85a;
var _85b=dojo.doc();
if(_85b["selection"]){
var _85c=_85b.selection.createRange();
_85a=_85c.getBookmark();
}else{
var _85d;
try{
_85d=dojo.global().getSelection();
}
catch(e){
}
if(_85d){
var _85c=_85d.getRangeAt(0);
_85a=_85c.cloneRange();
}else{
dojo.debug("No idea how to store the current selection for this browser!");
}
}
return _85a;
},moveToBookmark:function(_85e){
var _85f=dojo.doc();
if(_85f["selection"]){
var _860=_85f.selection.createRange();
_860.moveToBookmark(_85e);
_860.select();
}else{
var _861;
try{
_861=dojo.global().getSelection();
}
catch(e){
}
if(_861&&_861["removeAllRanges"]){
_861.removeAllRanges();
_861.addRange(_85e);
}else{
dojo.debug("No idea how to restore selection for this browser!");
}
}
},collapse:function(_862){
if(dojo.global()["getSelection"]){
var _863=dojo.global().getSelection();
if(_863.removeAllRanges){
if(_862){
_863.collapseToStart();
}else{
_863.collapseToEnd();
}
}else{
dojo.global().getSelection().collapse(_862);
}
}else{
if(dojo.doc().selection){
var _864=dojo.doc().selection.createRange();
_864.collapse(_862);
_864.select();
}
}
},remove:function(){
if(dojo.doc().selection){
var _865=dojo.doc().selection;
if(_865.type.toUpperCase()!="NONE"){
_865.clear();
}
return _865;
}else{
var _865=dojo.global().getSelection();
for(var i=0;i<_865.rangeCount;i++){
_865.getRangeAt(i).deleteContents();
}
return _865;
}
}});
dojo.provide("dojo.html.iframe");
dojo.html.iframeContentWindow=function(_867){
var win=dojo.html.getDocumentWindow(dojo.html.iframeContentDocument(_867))||dojo.html.iframeContentDocument(_867).__parent__||(_867.name&&document.frames[_867.name])||null;
return win;
};
dojo.html.iframeContentDocument=function(_869){
var doc=_869.contentDocument||((_869.contentWindow)&&(_869.contentWindow.document))||((_869.name)&&(document.frames[_869.name])&&(document.frames[_869.name].document))||null;
return doc;
};
dojo.html.BackgroundIframe=function(node){
if(dojo.render.html.ie55||dojo.render.html.ie60){
var html="<iframe src='javascript:false'"+" style='position: absolute; left: 0px; top: 0px; width: 100%; height: 100%;"+"z-index: -1; filter:Alpha(Opacity=\"0\");' "+">";
this.iframe=dojo.doc().createElement(html);
this.iframe.tabIndex=-1;
if(node){
node.appendChild(this.iframe);
this.domNode=node;
}else{
dojo.body().appendChild(this.iframe);
this.iframe.style.display="none";
}
}
};
dojo.lang.extend(dojo.html.BackgroundIframe,{iframe:null,onResized:function(){
if(this.iframe&&this.domNode&&this.domNode.parentNode){
var _86d=dojo.html.getMarginBox(this.domNode);
if(_86d.width==0||_86d.height==0){
dojo.lang.setTimeout(this,this.onResized,100);
return;
}
this.iframe.style.width=_86d.width+"px";
this.iframe.style.height=_86d.height+"px";
}
},size:function(node){
if(!this.iframe){
return;
}
var _86f=dojo.html.toCoordinateObject(node,true,dojo.html.boxSizing.BORDER_BOX);
with(this.iframe.style){
width=_86f.width+"px";
height=_86f.height+"px";
left=_86f.left+"px";
top=_86f.top+"px";
}
},setZIndex:function(node){
if(!this.iframe){
return;
}
if(dojo.dom.isNode(node)){
this.iframe.style.zIndex=dojo.html.getStyle(node,"z-index")-1;
}else{
if(!isNaN(node)){
this.iframe.style.zIndex=node;
}
}
},show:function(){
if(this.iframe){
this.iframe.style.display="block";
}
},hide:function(){
if(this.iframe){
this.iframe.style.display="none";
}
},remove:function(){
if(this.iframe){
dojo.html.removeNode(this.iframe,true);
delete this.iframe;
this.iframe=null;
}
}});
dojo.provide("dojo.dnd.HtmlDragAndDrop");
dojo.declare("dojo.dnd.HtmlDragSource",dojo.dnd.DragSource,{dragClass:"",onDragStart:function(){
var _871=new dojo.dnd.HtmlDragObject(this.dragObject,this.type);
if(this.dragClass){
_871.dragClass=this.dragClass;
}
if(this.constrainToContainer){
_871.constrainTo(this.constrainingContainer||this.domNode.parentNode);
}
return _871;
},setDragHandle:function(node){
node=dojo.byId(node);
dojo.dnd.dragManager.unregisterDragSource(this);
this.domNode=node;
dojo.dnd.dragManager.registerDragSource(this);
},setDragTarget:function(node){
this.dragObject=node;
},constrainTo:function(_874){
this.constrainToContainer=true;
if(_874){
this.constrainingContainer=_874;
}
},onSelected:function(){
for(var i=0;i<this.dragObjects.length;i++){
dojo.dnd.dragManager.selectedSources.push(new dojo.dnd.HtmlDragSource(this.dragObjects[i]));
}
},addDragObjects:function(el){
for(var i=0;i<arguments.length;i++){
this.dragObjects.push(dojo.byId(arguments[i]));
}
}},function(node,type){
node=dojo.byId(node);
this.dragObjects=[];
this.constrainToContainer=false;
if(node){
this.domNode=node;
this.dragObject=node;
this.type=(type)||(this.domNode.nodeName.toLowerCase());
dojo.dnd.DragSource.prototype.reregister.call(this);
}
});
dojo.declare("dojo.dnd.HtmlDragObject",dojo.dnd.DragObject,{dragClass:"",opacity:0.5,createIframe:true,disableX:false,disableY:false,createDragNode:function(){
var node=this.domNode.cloneNode(true);
if(this.dragClass){
dojo.html.addClass(node,this.dragClass);
}
if(this.opacity<1){
dojo.html.setOpacity(node,this.opacity);
}
var ltn=node.tagName.toLowerCase();
var isTr=(ltn=="tr");
if((isTr)||(ltn=="tbody")){
var doc=this.domNode.ownerDocument;
var _87e=doc.createElement("table");
if(isTr){
var _87f=doc.createElement("tbody");
_87e.appendChild(_87f);
_87f.appendChild(node);
}else{
_87e.appendChild(node);
}
var _880=((isTr)?this.domNode:this.domNode.firstChild);
var _881=((isTr)?node:node.firstChild);
var _882=tdp.childNodes;
var _883=_881.childNodes;
for(var i=0;i<_882.length;i++){
if((_883[i])&&(_883[i].style)){
_883[i].style.width=dojo.html.getContentBox(_882[i]).width+"px";
}
}
node=_87e;
}
if((dojo.render.html.ie55||dojo.render.html.ie60)&&this.createIframe){
with(node.style){
top="0px";
left="0px";
}
var _885=document.createElement("div");
_885.appendChild(node);
this.bgIframe=new dojo.html.BackgroundIframe(_885);
_885.appendChild(this.bgIframe.iframe);
node=_885;
}
node.style.zIndex=999;
return node;
},onDragStart:function(e){
dojo.html.clearSelection();
this.scrollOffset=dojo.html.getScroll().offset;
this.dragStartPosition=dojo.html.getAbsolutePosition(this.domNode,true);
this.dragOffset={y:this.dragStartPosition.y-e.pageY,x:this.dragStartPosition.x-e.pageX};
this.dragClone=this.createDragNode();
this.containingBlockPosition=this.domNode.offsetParent?dojo.html.getAbsolutePosition(this.domNode.offsetParent,true):{x:0,y:0};
if(this.constrainToContainer){
this.constraints=this.getConstraints();
}
with(this.dragClone.style){
position="absolute";
top=this.dragOffset.y+e.pageY+"px";
left=this.dragOffset.x+e.pageX+"px";
}
dojo.body().appendChild(this.dragClone);
dojo.event.topic.publish("dragStart",{source:this});
},getConstraints:function(){
if(this.constrainingContainer.nodeName.toLowerCase()=="body"){
var _887=dojo.html.getViewport();
var _888=_887.width;
var _889=_887.height;
var _88a=dojo.html.getScroll().offset;
var x=_88a.x;
var y=_88a.y;
}else{
var _88d=dojo.html.getContentBox(this.constrainingContainer);
_888=_88d.width;
_889=_88d.height;
x=this.containingBlockPosition.x+dojo.html.getPixelValue(this.constrainingContainer,"padding-left",true)+dojo.html.getBorderExtent(this.constrainingContainer,"left");
y=this.containingBlockPosition.y+dojo.html.getPixelValue(this.constrainingContainer,"padding-top",true)+dojo.html.getBorderExtent(this.constrainingContainer,"top");
}
var mb=dojo.html.getMarginBox(this.domNode);
return {minX:x,minY:y,maxX:x+_888-mb.width,maxY:y+_889-mb.height};
},updateDragOffset:function(){
var _88f=dojo.html.getScroll().offset;
if(_88f.y!=this.scrollOffset.y){
var diff=_88f.y-this.scrollOffset.y;
this.dragOffset.y+=diff;
this.scrollOffset.y=_88f.y;
}
if(_88f.x!=this.scrollOffset.x){
var diff=_88f.x-this.scrollOffset.x;
this.dragOffset.x+=diff;
this.scrollOffset.x=_88f.x;
}
},onDragMove:function(e){
this.updateDragOffset();
var x=this.dragOffset.x+e.pageX;
var y=this.dragOffset.y+e.pageY;
if(this.constrainToContainer){
if(x<this.constraints.minX){
x=this.constraints.minX;
}
if(y<this.constraints.minY){
y=this.constraints.minY;
}
if(x>this.constraints.maxX){
x=this.constraints.maxX;
}
if(y>this.constraints.maxY){
y=this.constraints.maxY;
}
}
this.setAbsolutePosition(x,y);
dojo.event.topic.publish("dragMove",{source:this});
},setAbsolutePosition:function(x,y){
if(!this.disableY){
this.dragClone.style.top=y+"px";
}
if(!this.disableX){
this.dragClone.style.left=x+"px";
}
},onDragEnd:function(e){
switch(e.dragStatus){
case "dropSuccess":
dojo.html.removeNode(this.dragClone);
this.dragClone=null;
break;
case "dropFailure":
var _897=dojo.html.getAbsolutePosition(this.dragClone,true);
var _898={left:this.dragStartPosition.x+1,top:this.dragStartPosition.y+1};
var anim=dojo.lfx.slideTo(this.dragClone,_898,300);
var _89a=this;
dojo.event.connect(anim,"onEnd",function(e){
dojo.html.removeNode(_89a.dragClone);
_89a.dragClone=null;
});
anim.play();
break;
}
dojo.event.topic.publish("dragEnd",{source:this});
},constrainTo:function(_89c){
this.constrainToContainer=true;
if(_89c){
this.constrainingContainer=_89c;
}else{
this.constrainingContainer=this.domNode.parentNode;
}
}},function(node,type){
this.domNode=dojo.byId(node);
this.type=type;
this.constrainToContainer=false;
this.dragSource=null;
dojo.dnd.DragObject.prototype.register.call(this);
});
dojo.declare("dojo.dnd.HtmlDropTarget",dojo.dnd.DropTarget,{vertical:false,onDragOver:function(e){
if(!this.accepts(e.dragObjects)){
return false;
}
this.childBoxes=[];
for(var i=0,_8a1;i<this.domNode.childNodes.length;i++){
_8a1=this.domNode.childNodes[i];
if(_8a1.nodeType!=dojo.html.ELEMENT_NODE){
continue;
}
var pos=dojo.html.getAbsolutePosition(_8a1,true);
var _8a3=dojo.html.getBorderBox(_8a1);
this.childBoxes.push({top:pos.y,bottom:pos.y+_8a3.height,left:pos.x,right:pos.x+_8a3.width,height:_8a3.height,width:_8a3.width,node:_8a1});
}
return true;
},_getNodeUnderMouse:function(e){
for(var i=0,_8a6;i<this.childBoxes.length;i++){
with(this.childBoxes[i]){
if(e.pageX>=left&&e.pageX<=right&&e.pageY>=top&&e.pageY<=bottom){
return i;
}
}
}
return -1;
},createDropIndicator:function(){
this.dropIndicator=document.createElement("div");
with(this.dropIndicator.style){
position="absolute";
zIndex=999;
if(this.vertical){
borderLeftWidth="1px";
borderLeftColor="black";
borderLeftStyle="solid";
height=dojo.html.getBorderBox(this.domNode).height+"px";
top=dojo.html.getAbsolutePosition(this.domNode,true).y+"px";
}else{
borderTopWidth="1px";
borderTopColor="black";
borderTopStyle="solid";
width=dojo.html.getBorderBox(this.domNode).width+"px";
left=dojo.html.getAbsolutePosition(this.domNode,true).x+"px";
}
}
},onDragMove:function(e,_8a8){
var i=this._getNodeUnderMouse(e);
if(!this.dropIndicator){
this.createDropIndicator();
}
var _8aa=this.vertical?dojo.html.gravity.WEST:dojo.html.gravity.NORTH;
var hide=false;
if(i<0){
if(this.childBoxes.length){
var _8ac=(dojo.html.gravity(this.childBoxes[0].node,e)&_8aa);
if(_8ac){
hide=true;
}
}else{
var _8ac=true;
}
}else{
var _8ad=this.childBoxes[i];
var _8ac=(dojo.html.gravity(_8ad.node,e)&_8aa);
if(_8ad.node===_8a8[0].dragSource.domNode){
hide=true;
}else{
var _8ae=_8ac?(i>0?this.childBoxes[i-1]:_8ad):(i<this.childBoxes.length-1?this.childBoxes[i+1]:_8ad);
if(_8ae.node===_8a8[0].dragSource.domNode){
hide=true;
}
}
}
if(hide){
this.dropIndicator.style.display="none";
return;
}else{
this.dropIndicator.style.display="";
}
this.placeIndicator(e,_8a8,i,_8ac);
if(!dojo.html.hasParent(this.dropIndicator)){
dojo.body().appendChild(this.dropIndicator);
}
},placeIndicator:function(e,_8b0,_8b1,_8b2){
var _8b3=this.vertical?"left":"top";
var _8b4;
if(_8b1<0){
if(this.childBoxes.length){
_8b4=_8b2?this.childBoxes[0]:this.childBoxes[this.childBoxes.length-1];
}else{
this.dropIndicator.style[_8b3]=dojo.html.getAbsolutePosition(this.domNode,true)[this.vertical?"x":"y"]+"px";
}
}else{
_8b4=this.childBoxes[_8b1];
}
if(_8b4){
this.dropIndicator.style[_8b3]=(_8b2?_8b4[_8b3]:_8b4[this.vertical?"right":"bottom"])+"px";
if(this.vertical){
this.dropIndicator.style.height=_8b4.height+"px";
this.dropIndicator.style.top=_8b4.top+"px";
}else{
this.dropIndicator.style.width=_8b4.width+"px";
this.dropIndicator.style.left=_8b4.left+"px";
}
}
},onDragOut:function(e){
if(this.dropIndicator){
dojo.html.removeNode(this.dropIndicator);
delete this.dropIndicator;
}
},onDrop:function(e){
this.onDragOut(e);
var i=this._getNodeUnderMouse(e);
var _8b8=this.vertical?dojo.html.gravity.WEST:dojo.html.gravity.NORTH;
if(i<0){
if(this.childBoxes.length){
if(dojo.html.gravity(this.childBoxes[0].node,e)&_8b8){
return this.insert(e,this.childBoxes[0].node,"before");
}else{
return this.insert(e,this.childBoxes[this.childBoxes.length-1].node,"after");
}
}
return this.insert(e,this.domNode,"append");
}
var _8b9=this.childBoxes[i];
if(dojo.html.gravity(_8b9.node,e)&_8b8){
return this.insert(e,_8b9.node,"before");
}else{
return this.insert(e,_8b9.node,"after");
}
},insert:function(e,_8bb,_8bc){
var node=e.dragObject.domNode;
if(_8bc=="before"){
return dojo.html.insertBefore(node,_8bb);
}else{
if(_8bc=="after"){
return dojo.html.insertAfter(node,_8bb);
}else{
if(_8bc=="append"){
_8bb.appendChild(node);
return true;
}
}
}
return false;
}},function(node,_8bf){
if(arguments.length==0){
return;
}
this.domNode=dojo.byId(node);
dojo.dnd.DropTarget.call(this);
if(_8bf&&dojo.lang.isString(_8bf)){
_8bf=[_8bf];
}
this.acceptedTypes=_8bf||[];
dojo.dnd.dragManager.registerDropTarget(this);
});
dojo.provide("dojo.dnd.*");
dojo.provide("dojo.ns");
dojo.ns={namespaces:{},failed:{},loading:{},loaded:{},register:function(name,_8c1,_8c2,_8c3){
if(!_8c3||!this.namespaces[name]){
this.namespaces[name]=new dojo.ns.Ns(name,_8c1,_8c2);
}
},allow:function(name){
if(this.failed[name]){
return false;
}
if((djConfig.excludeNamespace)&&(dojo.lang.inArray(djConfig.excludeNamespace,name))){
return false;
}
return ((name==this.dojo)||(!djConfig.includeNamespace)||(dojo.lang.inArray(djConfig.includeNamespace,name)));
},get:function(name){
return this.namespaces[name];
},require:function(name){
var ns=this.namespaces[name];
if((ns)&&(this.loaded[name])){
return ns;
}
if(!this.allow(name)){
return false;
}
if(this.loading[name]){
dojo.debug("dojo.namespace.require: re-entrant request to load namespace \""+name+"\" must fail.");
return false;
}
var req=dojo.require;
this.loading[name]=true;
try{
if(name=="dojo"){
req("dojo.namespaces.dojo");
}else{
if(!dojo.hostenv.moduleHasPrefix(name)){
dojo.registerModulePath(name,"../"+name);
}
req([name,"manifest"].join("."),false,true);
}
if(!this.namespaces[name]){
this.failed[name]=true;
}
}
finally{
this.loading[name]=false;
}
return this.namespaces[name];
}};
dojo.ns.Ns=function(name,_8ca,_8cb){
this.name=name;
this.module=_8ca;
this.resolver=_8cb;
this._loaded=[];
this._failed=[];
};
dojo.ns.Ns.prototype.resolve=function(name,_8cd,_8ce){
if(!this.resolver||djConfig["skipAutoRequire"]){
return false;
}
var _8cf=this.resolver(name,_8cd);
if((_8cf)&&(!this._loaded[_8cf])&&(!this._failed[_8cf])){
var req=dojo.require;
req(_8cf,false,true);
if(dojo.hostenv.findModule(_8cf,false)){
this._loaded[_8cf]=true;
}else{
if(!_8ce){
dojo.raise("dojo.ns.Ns.resolve: module '"+_8cf+"' not found after loading via namespace '"+this.name+"'");
}
this._failed[_8cf]=true;
}
}
return Boolean(this._loaded[_8cf]);
};
dojo.registerNamespace=function(name,_8d2,_8d3){
dojo.ns.register.apply(dojo.ns,arguments);
};
dojo.registerNamespaceResolver=function(name,_8d5){
var n=dojo.ns.namespaces[name];
if(n){
n.resolver=_8d5;
}
};
dojo.registerNamespaceManifest=function(_8d7,path,name,_8da,_8db){
dojo.registerModulePath(name,path);
dojo.registerNamespace(name,_8da,_8db);
};
dojo.registerNamespace("dojo","dojo.widget");
dojo.provide("dojo.widget.Manager");
dojo.widget.manager=new function(){
this.widgets=[];
this.widgetIds=[];
this.topWidgets={};
var _8dc={};
var _8dd=[];
this.getUniqueId=function(_8de){
var _8df;
do{
_8df=_8de+"_"+(_8dc[_8de]!=undefined?++_8dc[_8de]:_8dc[_8de]=0);
}while(this.getWidgetById(_8df));
return _8df;
};
this.add=function(_8e0){
this.widgets.push(_8e0);
if(!_8e0.extraArgs["id"]){
_8e0.extraArgs["id"]=_8e0.extraArgs["ID"];
}
if(_8e0.widgetId==""){
if(_8e0["id"]){
_8e0.widgetId=_8e0["id"];
}else{
if(_8e0.extraArgs["id"]){
_8e0.widgetId=_8e0.extraArgs["id"];
}else{
_8e0.widgetId=this.getUniqueId(_8e0.ns+"_"+_8e0.widgetType);
}
}
}
if(this.widgetIds[_8e0.widgetId]){
dojo.debug("widget ID collision on ID: "+_8e0.widgetId);
}
this.widgetIds[_8e0.widgetId]=_8e0;
};
this.destroyAll=function(){
for(var x=this.widgets.length-1;x>=0;x--){
try{
this.widgets[x].destroy(true);
delete this.widgets[x];
}
catch(e){
}
}
};
this.remove=function(_8e2){
if(dojo.lang.isNumber(_8e2)){
var tw=this.widgets[_8e2].widgetId;
delete this.widgetIds[tw];
this.widgets.splice(_8e2,1);
}else{
this.removeById(_8e2);
}
};
this.removeById=function(id){
if(!dojo.lang.isString(id)){
id=id["widgetId"];
if(!id){
dojo.debug("invalid widget or id passed to removeById");
return;
}
}
for(var i=0;i<this.widgets.length;i++){
if(this.widgets[i].widgetId==id){
this.remove(i);
break;
}
}
};
this.getWidgetById=function(id){
if(dojo.lang.isString(id)){
return this.widgetIds[id];
}
return id;
};
this.getWidgetsByType=function(type){
var lt=type.toLowerCase();
var _8e9=(type.indexOf(":")<0?function(x){
return x.widgetType.toLowerCase();
}:function(x){
return x.getNamespacedType();
});
var ret=[];
dojo.lang.forEach(this.widgets,function(x){
if(_8e9(x)==lt){
ret.push(x);
}
});
return ret;
};
this.getWidgetsByFilter=function(_8ee,_8ef){
var ret=[];
dojo.lang.every(this.widgets,function(x){
if(_8ee(x)){
ret.push(x);
if(_8ef){
return false;
}
}
return true;
});
return (_8ef?ret[0]:ret);
};
this.getAllWidgets=function(){
return this.widgets.concat();
};
this.getWidgetByNode=function(node){
var w=this.getAllWidgets();
node=dojo.byId(node);
for(var i=0;i<w.length;i++){
if(w[i].domNode==node){
return w[i];
}
}
return null;
};
this.byId=this.getWidgetById;
this.byType=this.getWidgetsByType;
this.byFilter=this.getWidgetsByFilter;
this.byNode=this.getWidgetByNode;
var _8f5={};
var _8f6=["dojo.widget"];
for(var i=0;i<_8f6.length;i++){
_8f6[_8f6[i]]=true;
}
this.registerWidgetPackage=function(_8f8){
if(!_8f6[_8f8]){
_8f6[_8f8]=true;
_8f6.push(_8f8);
}
};
this.getWidgetPackageList=function(){
return dojo.lang.map(_8f6,function(elt){
return (elt!==true?elt:undefined);
});
};
this.getImplementation=function(_8fa,_8fb,_8fc,ns){
var impl=this.getImplementationName(_8fa,ns);
if(impl){
var ret=_8fb?new impl(_8fb):new impl();
return ret;
}
};
function buildPrefixCache(){
for(var _900 in dojo.render){
if(dojo.render[_900]["capable"]===true){
var _901=dojo.render[_900].prefixes;
for(var i=0;i<_901.length;i++){
_8dd.push(_901[i].toLowerCase());
}
}
}
}
var _903=function(_904,_905){
if(!_905){
return null;
}
for(var i=0,l=_8dd.length,_908;i<=l;i++){
_908=(i<l?_905[_8dd[i]]:_905);
if(!_908){
continue;
}
for(var name in _908){
if(name.toLowerCase()==_904){
return _908[name];
}
}
}
return null;
};
var _90a=function(_90b,_90c){
var _90d=dojo.evalObjPath(_90c,false);
return (_90d?_903(_90b,_90d):null);
};
this.getImplementationName=function(_90e,ns){
var _910=_90e.toLowerCase();
ns=ns||"dojo";
var imps=_8f5[ns]||(_8f5[ns]={});
var impl=imps[_910];
if(impl){
return impl;
}
if(!_8dd.length){
buildPrefixCache();
}
var _913=dojo.ns.get(ns);
if(!_913){
dojo.ns.register(ns,ns+".widget");
_913=dojo.ns.get(ns);
}
if(_913){
_913.resolve(_90e);
}
impl=_90a(_910,_913.module);
if(impl){
return (imps[_910]=impl);
}
_913=dojo.ns.require(ns);
if((_913)&&(_913.resolver)){
_913.resolve(_90e);
impl=_90a(_910,_913.module);
if(impl){
return (imps[_910]=impl);
}
}
dojo.deprecated("dojo.widget.Manager.getImplementationName","Could not locate widget implementation for \""+_90e+"\" in \""+_913.module+"\" registered to namespace \""+_913.name+"\". "+"Developers must specify correct namespaces for all non-Dojo widgets","0.5");
for(var i=0;i<_8f6.length;i++){
impl=_90a(_910,_8f6[i]);
if(impl){
return (imps[_910]=impl);
}
}
throw new Error("Could not locate widget implementation for \""+_90e+"\" in \""+_913.module+"\" registered to namespace \""+_913.name+"\"");
};
this.resizing=false;
this.onWindowResized=function(){
if(this.resizing){
return;
}
try{
this.resizing=true;
for(var id in this.topWidgets){
var _916=this.topWidgets[id];
if(_916.checkSize){
_916.checkSize();
}
}
}
catch(e){
}
finally{
this.resizing=false;
}
};
if(typeof window!="undefined"){
dojo.addOnLoad(this,"onWindowResized");
dojo.event.connect(window,"onresize",this,"onWindowResized");
}
};
(function(){
var dw=dojo.widget;
var dwm=dw.manager;
var h=dojo.lang.curry(dojo.lang,"hitch",dwm);
var g=function(_91b,_91c){
dw[(_91c||_91b)]=h(_91b);
};
g("add","addWidget");
g("destroyAll","destroyAllWidgets");
g("remove","removeWidget");
g("removeById","removeWidgetById");
g("getWidgetById");
g("getWidgetById","byId");
g("getWidgetsByType");
g("getWidgetsByFilter");
g("getWidgetsByType","byType");
g("getWidgetsByFilter","byFilter");
g("getWidgetByNode","byNode");
dw.all=function(n){
var _91e=dwm.getAllWidgets.apply(dwm,arguments);
if(arguments.length>0){
return _91e[n];
}
return _91e;
};
g("registerWidgetPackage");
g("getImplementation","getWidgetImplementation");
g("getImplementationName","getWidgetImplementationName");
dw.widgets=dwm.widgets;
dw.widgetIds=dwm.widgetIds;
dw.root=dwm.root;
})();
dojo.provide("dojo.a11y");
dojo.a11y={imgPath:dojo.uri.dojoUri("src/widget/templates/images"),doAccessibleCheck:true,accessible:null,checkAccessible:function(){
if(this.accessible===null){
this.accessible=false;
if(this.doAccessibleCheck==true){
this.accessible=this.testAccessible();
}
}
return this.accessible;
},testAccessible:function(){
this.accessible=false;
if(dojo.render.html.ie||dojo.render.html.mozilla){
var div=document.createElement("div");
div.style.backgroundImage="url(\""+this.imgPath+"/tab_close.gif\")";
dojo.body().appendChild(div);
var _920=null;
if(window.getComputedStyle){
var _921=getComputedStyle(div,"");
_920=_921.getPropertyValue("background-image");
}else{
_920=div.currentStyle.backgroundImage;
}
var _922=false;
if(_920!=null&&(_920=="none"||_920=="url(invalid-url:)")){
this.accessible=true;
}
dojo.body().removeChild(div);
}
return this.accessible;
},setCheckAccessible:function(_923){
this.doAccessibleCheck=_923;
},setAccessibleMode:function(){
if(this.accessible===null){
if(this.checkAccessible()){
dojo.render.html.prefixes.unshift("a11y");
}
}
return this.accessible;
}};
dojo.provide("dojo.widget.Widget");
dojo.declare("dojo.widget.Widget",null,function(){
this.children=[];
this.extraArgs={};
},{parent:null,isTopLevel:false,disabled:false,isContainer:false,widgetId:"",widgetType:"Widget",ns:"dojo",getNamespacedType:function(){
return (this.ns?this.ns+":"+this.widgetType:this.widgetType).toLowerCase();
},toString:function(){
return "[Widget "+this.getNamespacedType()+", "+(this.widgetId||"NO ID")+"]";
},repr:function(){
return this.toString();
},enable:function(){
this.disabled=false;
},disable:function(){
this.disabled=true;
},onResized:function(){
this.notifyChildrenOfResize();
},notifyChildrenOfResize:function(){
for(var i=0;i<this.children.length;i++){
var _925=this.children[i];
if(_925.onResized){
_925.onResized();
}
}
},create:function(args,_927,_928,ns){
if(ns){
this.ns=ns;
}
this.satisfyPropertySets(args,_927,_928);
this.mixInProperties(args,_927,_928);
this.postMixInProperties(args,_927,_928);
dojo.widget.manager.add(this);
this.buildRendering(args,_927,_928);
this.initialize(args,_927,_928);
this.postInitialize(args,_927,_928);
this.postCreate(args,_927,_928);
return this;
},destroy:function(_92a){
if(this.parent){
this.parent.removeChild(this);
}
this.destroyChildren();
this.uninitialize();
this.destroyRendering(_92a);
dojo.widget.manager.removeById(this.widgetId);
},destroyChildren:function(){
var _92b;
var i=0;
while(this.children.length>i){
_92b=this.children[i];
if(_92b instanceof dojo.widget.Widget){
this.removeChild(_92b);
_92b.destroy();
continue;
}
i++;
}
},getChildrenOfType:function(type,_92e){
var ret=[];
var _930=dojo.lang.isFunction(type);
if(!_930){
type=type.toLowerCase();
}
for(var x=0;x<this.children.length;x++){
if(_930){
if(this.children[x] instanceof type){
ret.push(this.children[x]);
}
}else{
if(this.children[x].widgetType.toLowerCase()==type){
ret.push(this.children[x]);
}
}
if(_92e){
ret=ret.concat(this.children[x].getChildrenOfType(type,_92e));
}
}
return ret;
},getDescendants:function(){
var _932=[];
var _933=[this];
var elem;
while((elem=_933.pop())){
_932.push(elem);
if(elem.children){
dojo.lang.forEach(elem.children,function(elem){
_933.push(elem);
});
}
}
return _932;
},isFirstChild:function(){
return this===this.parent.children[0];
},isLastChild:function(){
return this===this.parent.children[this.parent.children.length-1];
},satisfyPropertySets:function(args){
return args;
},mixInProperties:function(args,frag){
if((args["fastMixIn"])||(frag["fastMixIn"])){
for(var x in args){
this[x]=args[x];
}
return;
}
var _93a;
var _93b=dojo.widget.lcArgsCache[this.widgetType];
if(_93b==null){
_93b={};
for(var y in this){
_93b[((new String(y)).toLowerCase())]=y;
}
dojo.widget.lcArgsCache[this.widgetType]=_93b;
}
var _93d={};
for(var x in args){
if(!this[x]){
var y=_93b[(new String(x)).toLowerCase()];
if(y){
args[y]=args[x];
x=y;
}
}
if(_93d[x]){
continue;
}
_93d[x]=true;
if((typeof this[x])!=(typeof _93a)){
if(typeof args[x]!="string"){
this[x]=args[x];
}else{
if(dojo.lang.isString(this[x])){
this[x]=args[x];
}else{
if(dojo.lang.isNumber(this[x])){
this[x]=new Number(args[x]);
}else{
if(dojo.lang.isBoolean(this[x])){
this[x]=(args[x].toLowerCase()=="false")?false:true;
}else{
if(dojo.lang.isFunction(this[x])){
if(args[x].search(/[^\w\.]+/i)==-1){
this[x]=dojo.evalObjPath(args[x],false);
}else{
var tn=dojo.lang.nameAnonFunc(new Function(args[x]),this);
dojo.event.kwConnect({srcObj:this,srcFunc:x,adviceObj:this,adviceFunc:tn});
}
}else{
if(dojo.lang.isArray(this[x])){
this[x]=args[x].split(";");
}else{
if(this[x] instanceof Date){
this[x]=new Date(Number(args[x]));
}else{
if(typeof this[x]=="object"){
if(this[x] instanceof dojo.uri.Uri){
this[x]=dojo.uri.dojoUri(args[x]);
}else{
var _93f=args[x].split(";");
for(var y=0;y<_93f.length;y++){
var si=_93f[y].indexOf(":");
if((si!=-1)&&(_93f[y].length>si)){
this[x][_93f[y].substr(0,si).replace(/^\s+|\s+$/g,"")]=_93f[y].substr(si+1);
}
}
}
}else{
this[x]=args[x];
}
}
}
}
}
}
}
}
}else{
this.extraArgs[x.toLowerCase()]=args[x];
}
}
},postMixInProperties:function(args,frag,_943){
},initialize:function(args,frag,_946){
return false;
},postInitialize:function(args,frag,_949){
return false;
},postCreate:function(args,frag,_94c){
return false;
},uninitialize:function(){
return false;
},buildRendering:function(args,frag,_94f){
dojo.unimplemented("dojo.widget.Widget.buildRendering, on "+this.toString()+", ");
return false;
},destroyRendering:function(){
dojo.unimplemented("dojo.widget.Widget.destroyRendering");
return false;
},addedTo:function(_950){
},addChild:function(_951){
dojo.unimplemented("dojo.widget.Widget.addChild");
return false;
},removeChild:function(_952){
for(var x=0;x<this.children.length;x++){
if(this.children[x]===_952){
this.children.splice(x,1);
_952.parent=null;
break;
}
}
return _952;
},getPreviousSibling:function(){
var idx=this.getParentIndex();
if(idx<=0){
return null;
}
return this.parent.children[idx-1];
},getSiblings:function(){
return this.parent.children;
},getParentIndex:function(){
return dojo.lang.indexOf(this.parent.children,this,true);
},getNextSibling:function(){
var idx=this.getParentIndex();
if(idx==this.parent.children.length-1){
return null;
}
if(idx<0){
return null;
}
return this.parent.children[idx+1];
}});
dojo.widget.lcArgsCache={};
dojo.widget.tags={};
dojo.widget.tags.addParseTreeHandler=function(type){
dojo.deprecated("addParseTreeHandler",". ParseTreeHandlers are now reserved for components. Any unfiltered DojoML tag without a ParseTreeHandler is assumed to be a widget","0.5");
};
dojo.widget.tags["dojo:propertyset"]=function(_957,_958,_959){
var _95a=_958.parseProperties(_957["dojo:propertyset"]);
};
dojo.widget.tags["dojo:connect"]=function(_95b,_95c,_95d){
var _95e=_95c.parseProperties(_95b["dojo:connect"]);
};
dojo.widget.buildWidgetFromParseTree=function(type,frag,_961,_962,_963,_964){
dojo.a11y.setAccessibleMode();
var _965=type.split(":");
_965=(_965.length==2)?_965[1]:type;
var _966=_964||_961.parseProperties(frag[frag["ns"]+":"+_965]);
var _967=dojo.widget.manager.getImplementation(_965,null,null,frag["ns"]);
if(!_967){
throw new Error("cannot find \""+type+"\" widget");
}else{
if(!_967.create){
throw new Error("\""+type+"\" widget object has no \"create\" method and does not appear to implement *Widget");
}
}
_966["dojoinsertionindex"]=_963;
var ret=_967.create(_966,frag,_962,frag["ns"]);
return ret;
};
dojo.widget.defineWidget=function(_969,_96a,_96b,init,_96d){
if(dojo.lang.isString(arguments[3])){
dojo.widget._defineWidget(arguments[0],arguments[3],arguments[1],arguments[4],arguments[2]);
}else{
var args=[arguments[0]],p=3;
if(dojo.lang.isString(arguments[1])){
args.push(arguments[1],arguments[2]);
}else{
args.push("",arguments[1]);
p=2;
}
if(dojo.lang.isFunction(arguments[p])){
args.push(arguments[p],arguments[p+1]);
}else{
args.push(null,arguments[p]);
}
dojo.widget._defineWidget.apply(this,args);
}
};
dojo.widget.defineWidget.renderers="html|svg|vml";
dojo.widget._defineWidget=function(_970,_971,_972,init,_974){
var _975=_970.split(".");
var type=_975.pop();
var regx="\\.("+(_971?_971+"|":"")+dojo.widget.defineWidget.renderers+")\\.";
var r=_970.search(new RegExp(regx));
_975=(r<0?_975.join("."):_970.substr(0,r));
dojo.widget.manager.registerWidgetPackage(_975);
var pos=_975.indexOf(".");
var _97a=(pos>-1)?_975.substring(0,pos):_975;
_974=(_974)||{};
_974.widgetType=type;
if((!init)&&(_974["classConstructor"])){
init=_974.classConstructor;
delete _974.classConstructor;
}
dojo.declare(_970,_972,init,_974);
};
dojo.provide("dojo.widget.Parse");
dojo.widget.Parse=function(_97b){
this.propertySetsList=[];
this.fragment=_97b;
this.createComponents=function(frag,_97d){
var _97e=[];
var _97f=false;
try{
if(frag&&frag.tagName&&(frag!=frag.nodeRef)){
var _980=dojo.widget.tags;
var tna=String(frag.tagName).split(";");
for(var x=0;x<tna.length;x++){
var ltn=tna[x].replace(/^\s+|\s+$/g,"").toLowerCase();
frag.tagName=ltn;
var ret;
if(_980[ltn]){
_97f=true;
ret=_980[ltn](frag,this,_97d,frag.index);
_97e.push(ret);
}else{
if(ltn.indexOf(":")==-1){
ltn="dojo:"+ltn;
}
ret=dojo.widget.buildWidgetFromParseTree(ltn,frag,this,_97d,frag.index);
if(ret){
_97f=true;
_97e.push(ret);
}
}
}
}
}
catch(e){
dojo.debug("dojo.widget.Parse: error:",e);
}
if(!_97f){
_97e=_97e.concat(this.createSubComponents(frag,_97d));
}
return _97e;
};
this.createSubComponents=function(_985,_986){
var frag,_988=[];
for(var item in _985){
frag=_985[item];
if(frag&&typeof frag=="object"&&(frag!=_985.nodeRef)&&(frag!=_985.tagName)&&(!dojo.dom.isNode(frag))){
_988=_988.concat(this.createComponents(frag,_986));
}
}
return _988;
};
this.parsePropertySets=function(_98a){
return [];
};
this.parseProperties=function(_98b){
var _98c={};
for(var item in _98b){
if((_98b[item]==_98b.tagName)||(_98b[item]==_98b.nodeRef)){
}else{
var frag=_98b[item];
if(frag.tagName&&dojo.widget.tags[frag.tagName.toLowerCase()]){
}else{
if(frag[0]&&frag[0].value!=""&&frag[0].value!=null){
try{
if(item.toLowerCase()=="dataprovider"){
var _98f=this;
this.getDataProvider(_98f,frag[0].value);
_98c.dataProvider=this.dataProvider;
}
_98c[item]=frag[0].value;
var _990=this.parseProperties(frag);
for(var _991 in _990){
_98c[_991]=_990[_991];
}
}
catch(e){
dojo.debug(e);
}
}
}
switch(item.toLowerCase()){
case "checked":
case "disabled":
if(typeof _98c[item]!="boolean"){
_98c[item]=true;
}
break;
}
}
}
return _98c;
};
this.getDataProvider=function(_992,_993){
dojo.io.bind({url:_993,load:function(type,_995){
if(type=="load"){
_992.dataProvider=_995;
}
},mimetype:"text/javascript",sync:true});
};
this.getPropertySetById=function(_996){
for(var x=0;x<this.propertySetsList.length;x++){
if(_996==this.propertySetsList[x]["id"][0].value){
return this.propertySetsList[x];
}
}
return "";
};
this.getPropertySetsByType=function(_998){
var _999=[];
for(var x=0;x<this.propertySetsList.length;x++){
var cpl=this.propertySetsList[x];
var cpcc=cpl.componentClass||cpl.componentType||null;
var _99d=this.propertySetsList[x]["id"][0].value;
if(cpcc&&(_99d==cpcc[0].value)){
_999.push(cpl);
}
}
return _999;
};
this.getPropertySets=function(_99e){
var ppl="dojo:propertyproviderlist";
var _9a0=[];
var _9a1=_99e.tagName;
if(_99e[ppl]){
var _9a2=_99e[ppl].value.split(" ");
for(var _9a3 in _9a2){
if((_9a3.indexOf("..")==-1)&&(_9a3.indexOf("://")==-1)){
var _9a4=this.getPropertySetById(_9a3);
if(_9a4!=""){
_9a0.push(_9a4);
}
}else{
}
}
}
return this.getPropertySetsByType(_9a1).concat(_9a0);
};
this.createComponentFromScript=function(_9a5,_9a6,_9a7,ns){
_9a7.fastMixIn=true;
var ltn=(ns||"dojo")+":"+_9a6.toLowerCase();
if(dojo.widget.tags[ltn]){
return [dojo.widget.tags[ltn](_9a7,this,null,null,_9a7)];
}
return [dojo.widget.buildWidgetFromParseTree(ltn,_9a7,this,null,null,_9a7)];
};
};
dojo.widget._parser_collection={"dojo":new dojo.widget.Parse()};
dojo.widget.getParser=function(name){
if(!name){
name="dojo";
}
if(!this._parser_collection[name]){
this._parser_collection[name]=new dojo.widget.Parse();
}
return this._parser_collection[name];
};
dojo.widget.createWidget=function(name,_9ac,_9ad,_9ae){
var _9af=false;
var _9b0=(typeof name=="string");
if(_9b0){
var pos=name.indexOf(":");
var ns=(pos>-1)?name.substring(0,pos):"dojo";
if(pos>-1){
name=name.substring(pos+1);
}
var _9b3=name.toLowerCase();
var _9b4=ns+":"+_9b3;
_9af=(dojo.byId(name)&&!dojo.widget.tags[_9b4]);
}
if((arguments.length==1)&&(_9af||!_9b0)){
var xp=new dojo.xml.Parse();
var tn=_9af?dojo.byId(name):name;
return dojo.widget.getParser().createComponents(xp.parseElement(tn,null,true))[0];
}
function fromScript(_9b7,name,_9b9,ns){
_9b9[_9b4]={dojotype:[{value:_9b3}],nodeRef:_9b7,fastMixIn:true};
_9b9.ns=ns;
return dojo.widget.getParser().createComponentFromScript(_9b7,name,_9b9,ns);
}
_9ac=_9ac||{};
var _9bb=false;
var tn=null;
var h=dojo.render.html.capable;
if(h){
tn=document.createElement("span");
}
if(!_9ad){
_9bb=true;
_9ad=tn;
if(h){
dojo.body().appendChild(_9ad);
}
}else{
if(_9ae){
dojo.dom.insertAtPosition(tn,_9ad,_9ae);
}else{
tn=_9ad;
}
}
var _9bd=fromScript(tn,name.toLowerCase(),_9ac,ns);
if((!_9bd)||(!_9bd[0])||(typeof _9bd[0].widgetType=="undefined")){
throw new Error("createWidget: Creation of \""+name+"\" widget failed.");
}
try{
if(_9bb&&_9bd[0].domNode.parentNode){
_9bd[0].domNode.parentNode.removeChild(_9bd[0].domNode);
}
}
catch(e){
dojo.debug(e);
}
return _9bd[0];
};
dojo.provide("dojo.widget.DomWidget");
dojo.widget._cssFiles={};
dojo.widget._cssStrings={};
dojo.widget._templateCache={};
dojo.widget.defaultStrings={dojoRoot:dojo.hostenv.getBaseScriptUri(),baseScriptUri:dojo.hostenv.getBaseScriptUri()};
dojo.widget.fillFromTemplateCache=function(obj,_9bf,_9c0,_9c1){
var _9c2=_9bf||obj.templatePath;
var _9c3=dojo.widget._templateCache;
if(!_9c2&&!obj["widgetType"]){
do{
var _9c4="__dummyTemplate__"+dojo.widget._templateCache.dummyCount++;
}while(_9c3[_9c4]);
obj.widgetType=_9c4;
}
var wt=_9c2?_9c2.toString():obj.widgetType;
var ts=_9c3[wt];
if(!ts){
_9c3[wt]={"string":null,"node":null};
if(_9c1){
ts={};
}else{
ts=_9c3[wt];
}
}
if((!obj.templateString)&&(!_9c1)){
obj.templateString=_9c0||ts["string"];
}
if((!obj.templateNode)&&(!_9c1)){
obj.templateNode=ts["node"];
}
if((!obj.templateNode)&&(!obj.templateString)&&(_9c2)){
var _9c7=dojo.hostenv.getText(_9c2);
if(_9c7){
_9c7=_9c7.replace(/^\s*<\?xml(\s)+version=[\'\"](\d)*.(\d)*[\'\"](\s)*\?>/im,"");
var _9c8=_9c7.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
if(_9c8){
_9c7=_9c8[1];
}
}else{
_9c7="";
}
obj.templateString=_9c7;
if(!_9c1){
_9c3[wt]["string"]=_9c7;
}
}
if((!ts["string"])&&(!_9c1)){
ts.string=obj.templateString;
}
};
dojo.widget._templateCache.dummyCount=0;
dojo.widget.attachProperties=["dojoAttachPoint","id"];
dojo.widget.eventAttachProperty="dojoAttachEvent";
dojo.widget.onBuildProperty="dojoOnBuild";
dojo.widget.waiNames=["waiRole","waiState"];
dojo.widget.wai={waiRole:{name:"waiRole","namespace":"http://www.w3.org/TR/xhtml2",alias:"x2",prefix:"wairole:"},waiState:{name:"waiState","namespace":"http://www.w3.org/2005/07/aaa",alias:"aaa",prefix:""},setAttr:function(node,ns,attr,_9cc){
if(dojo.render.html.ie){
node.setAttribute(this[ns].alias+":"+attr,this[ns].prefix+_9cc);
}else{
node.setAttributeNS(this[ns]["namespace"],attr,this[ns].prefix+_9cc);
}
},getAttr:function(node,ns,attr){
if(dojo.render.html.ie){
return node.getAttribute(this[ns].alias+":"+attr);
}else{
return node.getAttributeNS(this[ns]["namespace"],attr);
}
},removeAttr:function(node,ns,attr){
var _9d3=true;
if(dojo.render.html.ie){
_9d3=node.removeAttribute(this[ns].alias+":"+attr);
}else{
node.removeAttributeNS(this[ns]["namespace"],attr);
}
return _9d3;
}};
dojo.widget.attachTemplateNodes=function(_9d4,_9d5,_9d6){
var _9d7=dojo.dom.ELEMENT_NODE;
function trim(str){
return str.replace(/^\s+|\s+$/g,"");
}
if(!_9d4){
_9d4=_9d5.domNode;
}
if(_9d4.nodeType!=_9d7){
return;
}
var _9d9=_9d4.all||_9d4.getElementsByTagName("*");
var _9da=_9d5;
for(var x=-1;x<_9d9.length;x++){
var _9dc=(x==-1)?_9d4:_9d9[x];
var _9dd=[];
if(!_9d5.widgetsInTemplate||!_9dc.getAttribute("dojoType")){
for(var y=0;y<this.attachProperties.length;y++){
var _9df=_9dc.getAttribute(this.attachProperties[y]);
if(_9df){
_9dd=_9df.split(";");
for(var z=0;z<_9dd.length;z++){
if(dojo.lang.isArray(_9d5[_9dd[z]])){
_9d5[_9dd[z]].push(_9dc);
}else{
_9d5[_9dd[z]]=_9dc;
}
}
break;
}
}
var _9e1=_9dc.getAttribute(this.eventAttachProperty);
if(_9e1){
var evts=_9e1.split(";");
for(var y=0;y<evts.length;y++){
if((!evts[y])||(!evts[y].length)){
continue;
}
var _9e3=null;
var tevt=trim(evts[y]);
if(evts[y].indexOf(":")>=0){
var _9e5=tevt.split(":");
tevt=trim(_9e5[0]);
_9e3=trim(_9e5[1]);
}
if(!_9e3){
_9e3=tevt;
}
var tf=function(){
var ntf=new String(_9e3);
return function(evt){
if(_9da[ntf]){
_9da[ntf](dojo.event.browser.fixEvent(evt,this));
}
};
}();
dojo.event.browser.addListener(_9dc,tevt,tf,false,true);
}
}
for(var y=0;y<_9d6.length;y++){
var _9e9=_9dc.getAttribute(_9d6[y]);
if((_9e9)&&(_9e9.length)){
var _9e3=null;
var _9ea=_9d6[y].substr(4);
_9e3=trim(_9e9);
var _9eb=[_9e3];
if(_9e3.indexOf(";")>=0){
_9eb=dojo.lang.map(_9e3.split(";"),trim);
}
for(var z=0;z<_9eb.length;z++){
if(!_9eb[z].length){
continue;
}
var tf=function(){
var ntf=new String(_9eb[z]);
return function(evt){
if(_9da[ntf]){
_9da[ntf](dojo.event.browser.fixEvent(evt,this));
}
};
}();
dojo.event.browser.addListener(_9dc,_9ea,tf,false,true);
}
}
}
}
var _9ee=_9dc.getAttribute(this.templateProperty);
if(_9ee){
_9d5[_9ee]=_9dc;
}
dojo.lang.forEach(dojo.widget.waiNames,function(name){
var wai=dojo.widget.wai[name];
var val=_9dc.getAttribute(wai.name);
if(val){
if(val.indexOf("-")==-1){
dojo.widget.wai.setAttr(_9dc,wai.name,"role",val);
}else{
var _9f2=val.split("-");
dojo.widget.wai.setAttr(_9dc,wai.name,_9f2[0],_9f2[1]);
}
}
},this);
var _9f3=_9dc.getAttribute(this.onBuildProperty);
if(_9f3){
eval("var node = baseNode; var widget = targetObj; "+_9f3);
}
}
};
dojo.widget.getDojoEventsFromStr=function(str){
var re=/(dojoOn([a-z]+)(\s?))=/gi;
var evts=str?str.match(re)||[]:[];
var ret=[];
var lem={};
for(var x=0;x<evts.length;x++){
if(evts[x].length<1){
continue;
}
var cm=evts[x].replace(/\s/,"");
cm=(cm.slice(0,cm.length-1));
if(!lem[cm]){
lem[cm]=true;
ret.push(cm);
}
}
return ret;
};
dojo.declare("dojo.widget.DomWidget",dojo.widget.Widget,function(){
if((arguments.length>0)&&(typeof arguments[0]=="object")){
this.create(arguments[0]);
}
},{templateNode:null,templateString:null,templateCssString:null,preventClobber:false,domNode:null,containerNode:null,widgetsInTemplate:false,addChild:function(_9fb,_9fc,pos,ref,_9ff){
if(!this.isContainer){
dojo.debug("dojo.widget.DomWidget.addChild() attempted on non-container widget");
return null;
}else{
if(_9ff==undefined){
_9ff=this.children.length;
}
this.addWidgetAsDirectChild(_9fb,_9fc,pos,ref,_9ff);
this.registerChild(_9fb,_9ff);
}
return _9fb;
},addWidgetAsDirectChild:function(_a00,_a01,pos,ref,_a04){
if((!this.containerNode)&&(!_a01)){
this.containerNode=this.domNode;
}
var cn=(_a01)?_a01:this.containerNode;
if(!pos){
pos="after";
}
if(!ref){
if(!cn){
cn=dojo.body();
}
ref=cn.lastChild;
}
if(!_a04){
_a04=0;
}
_a00.domNode.setAttribute("dojoinsertionindex",_a04);
if(!ref){
cn.appendChild(_a00.domNode);
}else{
if(pos=="insertAtIndex"){
dojo.dom.insertAtIndex(_a00.domNode,ref.parentNode,_a04);
}else{
if((pos=="after")&&(ref===cn.lastChild)){
cn.appendChild(_a00.domNode);
}else{
dojo.dom.insertAtPosition(_a00.domNode,cn,pos);
}
}
}
},registerChild:function(_a06,_a07){
_a06.dojoInsertionIndex=_a07;
var idx=-1;
for(var i=0;i<this.children.length;i++){
if(this.children[i].dojoInsertionIndex<=_a07){
idx=i;
}
}
this.children.splice(idx+1,0,_a06);
_a06.parent=this;
_a06.addedTo(this,idx+1);
delete dojo.widget.manager.topWidgets[_a06.widgetId];
},removeChild:function(_a0a){
dojo.dom.removeNode(_a0a.domNode);
return dojo.widget.DomWidget.superclass.removeChild.call(this,_a0a);
},getFragNodeRef:function(frag){
if(!frag){
return null;
}
if(!frag[this.getNamespacedType()]){
dojo.raise("Error: no frag for widget type "+this.getNamespacedType()+", id "+this.widgetId+" (maybe a widget has set it's type incorrectly)");
}
return frag[this.getNamespacedType()]["nodeRef"];
},postInitialize:function(args,frag,_a0e){
var _a0f=this.getFragNodeRef(frag);
if(_a0e&&(_a0e.snarfChildDomOutput||!_a0f)){
_a0e.addWidgetAsDirectChild(this,"","insertAtIndex","",args["dojoinsertionindex"],_a0f);
}else{
if(_a0f){
if(this.domNode&&(this.domNode!==_a0f)){
this._sourceNodeRef=dojo.dom.replaceNode(_a0f,this.domNode);
}
}
}
if(_a0e){
_a0e.registerChild(this,args.dojoinsertionindex);
}else{
dojo.widget.manager.topWidgets[this.widgetId]=this;
}
if(this.widgetsInTemplate){
var _a10=new dojo.xml.Parse();
var _a11;
var _a12=this.domNode.getElementsByTagName("*");
for(var i=0;i<_a12.length;i++){
if(_a12[i].getAttribute("dojoAttachPoint")=="subContainerWidget"){
_a11=_a12[i];
}
if(_a12[i].getAttribute("dojoType")){
_a12[i].setAttribute("isSubWidget",true);
}
}
if(this.isContainer&&!this.containerNode){
if(_a11){
var src=this.getFragNodeRef(frag);
if(src){
dojo.dom.moveChildren(src,_a11);
frag["dojoDontFollow"]=true;
}
}else{
dojo.debug("No subContainerWidget node can be found in template file for widget "+this);
}
}
var _a15=_a10.parseElement(this.domNode,null,true);
dojo.widget.getParser().createSubComponents(_a15,this);
var _a16=[];
var _a17=[this];
var w;
while((w=_a17.pop())){
for(var i=0;i<w.children.length;i++){
var _a19=w.children[i];
if(_a19._processedSubWidgets||!_a19.extraArgs["issubwidget"]){
continue;
}
_a16.push(_a19);
if(_a19.isContainer){
_a17.push(_a19);
}
}
}
for(var i=0;i<_a16.length;i++){
var _a1a=_a16[i];
if(_a1a._processedSubWidgets){
dojo.debug("This should not happen: widget._processedSubWidgets is already true!");
return;
}
_a1a._processedSubWidgets=true;
if(_a1a.extraArgs["dojoattachevent"]){
var evts=_a1a.extraArgs["dojoattachevent"].split(";");
for(var j=0;j<evts.length;j++){
var _a1d=null;
var tevt=dojo.string.trim(evts[j]);
if(tevt.indexOf(":")>=0){
var _a1f=tevt.split(":");
tevt=dojo.string.trim(_a1f[0]);
_a1d=dojo.string.trim(_a1f[1]);
}
if(!_a1d){
_a1d=tevt;
}
if(dojo.lang.isFunction(_a1a[tevt])){
dojo.event.kwConnect({srcObj:_a1a,srcFunc:tevt,targetObj:this,targetFunc:_a1d});
}else{
alert(tevt+" is not a function in widget "+_a1a);
}
}
}
if(_a1a.extraArgs["dojoattachpoint"]){
this[_a1a.extraArgs["dojoattachpoint"]]=_a1a;
}
}
}
if(this.isContainer&&!frag["dojoDontFollow"]){
dojo.widget.getParser().createSubComponents(frag,this);
}
},buildRendering:function(args,frag){
var ts=dojo.widget._templateCache[this.widgetType];
if(args["templatecsspath"]){
args["templateCssPath"]=args["templatecsspath"];
}
var _a23=args["templateCssPath"]||this.templateCssPath;
if(_a23&&!dojo.widget._cssFiles[_a23.toString()]){
if((!this.templateCssString)&&(_a23)){
this.templateCssString=dojo.hostenv.getText(_a23);
this.templateCssPath=null;
}
dojo.widget._cssFiles[_a23.toString()]=true;
}
if((this["templateCssString"])&&(!dojo.widget._cssStrings[this.templateCssString])){
dojo.html.insertCssText(this.templateCssString,null,_a23);
dojo.widget._cssStrings[this.templateCssString]=true;
}
if((!this.preventClobber)&&((this.templatePath)||(this.templateNode)||((this["templateString"])&&(this.templateString.length))||((typeof ts!="undefined")&&((ts["string"])||(ts["node"]))))){
this.buildFromTemplate(args,frag);
}else{
this.domNode=this.getFragNodeRef(frag);
}
this.fillInTemplate(args,frag);
},buildFromTemplate:function(args,frag){
var _a26=false;
if(args["templatepath"]){
args["templatePath"]=args["templatepath"];
}
dojo.widget.fillFromTemplateCache(this,args["templatePath"],null,_a26);
var ts=dojo.widget._templateCache[this.templatePath?this.templatePath.toString():this.widgetType];
if((ts)&&(!_a26)){
if(!this.templateString.length){
this.templateString=ts["string"];
}
if(!this.templateNode){
this.templateNode=ts["node"];
}
}
var _a28=false;
var node=null;
var tstr=this.templateString;
if((!this.templateNode)&&(this.templateString)){
_a28=this.templateString.match(/\$\{([^\}]+)\}/g);
if(_a28){
var hash=this.strings||{};
for(var key in dojo.widget.defaultStrings){
if(dojo.lang.isUndefined(hash[key])){
hash[key]=dojo.widget.defaultStrings[key];
}
}
for(var i=0;i<_a28.length;i++){
var key=_a28[i];
key=key.substring(2,key.length-1);
var kval=(key.substring(0,5)=="this.")?dojo.lang.getObjPathValue(key.substring(5),this):hash[key];
var _a2f;
if((kval)||(dojo.lang.isString(kval))){
_a2f=new String((dojo.lang.isFunction(kval))?kval.call(this,key,this.templateString):kval);
while(_a2f.indexOf("\"")>-1){
_a2f=_a2f.replace("\"","&quot;");
}
tstr=tstr.replace(_a28[i],_a2f);
}
}
}else{
this.templateNode=this.createNodesFromText(this.templateString,true)[0];
if(!_a26){
ts.node=this.templateNode;
}
}
}
if((!this.templateNode)&&(!_a28)){
dojo.debug("DomWidget.buildFromTemplate: could not create template");
return false;
}else{
if(!_a28){
node=this.templateNode.cloneNode(true);
if(!node){
return false;
}
}else{
node=this.createNodesFromText(tstr,true)[0];
}
}
this.domNode=node;
this.attachTemplateNodes();
if(this.isContainer&&this.containerNode){
var src=this.getFragNodeRef(frag);
if(src){
dojo.dom.moveChildren(src,this.containerNode);
}
}
},attachTemplateNodes:function(_a31,_a32){
if(!_a31){
_a31=this.domNode;
}
if(!_a32){
_a32=this;
}
return dojo.widget.attachTemplateNodes(_a31,_a32,dojo.widget.getDojoEventsFromStr(this.templateString));
},fillInTemplate:function(){
},destroyRendering:function(){
try{
dojo.dom.destroyNode(this.domNode);
delete this.domNode;
}
catch(e){
}
if(this._sourceNodeRef){
try{
dojo.dom.destroyNode(this._sourceNodeRef);
}
catch(e){
}
}
},createNodesFromText:function(){
dojo.unimplemented("dojo.widget.DomWidget.createNodesFromText");
}});
dojo.provide("dojo.lfx.toggle");
dojo.lfx.toggle.plain={show:function(node,_a34,_a35,_a36){
dojo.html.show(node);
if(dojo.lang.isFunction(_a36)){
_a36();
}
},hide:function(node,_a38,_a39,_a3a){
dojo.html.hide(node);
if(dojo.lang.isFunction(_a3a)){
_a3a();
}
}};
dojo.lfx.toggle.fade={show:function(node,_a3c,_a3d,_a3e){
dojo.lfx.fadeShow(node,_a3c,_a3d,_a3e).play();
},hide:function(node,_a40,_a41,_a42){
dojo.lfx.fadeHide(node,_a40,_a41,_a42).play();
}};
dojo.lfx.toggle.wipe={show:function(node,_a44,_a45,_a46){
dojo.lfx.wipeIn(node,_a44,_a45,_a46).play();
},hide:function(node,_a48,_a49,_a4a){
dojo.lfx.wipeOut(node,_a48,_a49,_a4a).play();
}};
dojo.lfx.toggle.explode={show:function(node,_a4c,_a4d,_a4e,_a4f){
dojo.lfx.explode(_a4f||{x:0,y:0,width:0,height:0},node,_a4c,_a4d,_a4e).play();
},hide:function(node,_a51,_a52,_a53,_a54){
dojo.lfx.implode(node,_a54||{x:0,y:0,width:0,height:0},_a51,_a52,_a53).play();
}};
dojo.provide("dojo.widget.HtmlWidget");
dojo.declare("dojo.widget.HtmlWidget",dojo.widget.DomWidget,{templateCssPath:null,templatePath:null,lang:"",toggle:"plain",toggleDuration:150,initialize:function(args,frag){
},postMixInProperties:function(args,frag){
if(this.lang===""){
this.lang=null;
}
this.toggleObj=dojo.lfx.toggle[this.toggle.toLowerCase()]||dojo.lfx.toggle.plain;
},createNodesFromText:function(txt,wrap){
return dojo.html.createNodesFromText(txt,wrap);
},destroyRendering:function(_a5b){
try{
if(this.bgIframe){
this.bgIframe.remove();
delete this.bgIframe;
}
if(!_a5b&&this.domNode){
dojo.event.browser.clean(this.domNode);
}
dojo.widget.HtmlWidget.superclass.destroyRendering.call(this);
}
catch(e){
}
},isShowing:function(){
return dojo.html.isShowing(this.domNode);
},toggleShowing:function(){
if(this.isShowing()){
this.hide();
}else{
this.show();
}
},show:function(){
if(this.isShowing()){
return;
}
this.animationInProgress=true;
this.toggleObj.show(this.domNode,this.toggleDuration,null,dojo.lang.hitch(this,this.onShow),this.explodeSrc);
},onShow:function(){
this.animationInProgress=false;
this.checkSize();
},hide:function(){
if(!this.isShowing()){
return;
}
this.animationInProgress=true;
this.toggleObj.hide(this.domNode,this.toggleDuration,null,dojo.lang.hitch(this,this.onHide),this.explodeSrc);
},onHide:function(){
this.animationInProgress=false;
},_isResized:function(w,h){
if(!this.isShowing()){
return false;
}
var wh=dojo.html.getMarginBox(this.domNode);
var _a5f=w||wh.width;
var _a60=h||wh.height;
if(this.width==_a5f&&this.height==_a60){
return false;
}
this.width=_a5f;
this.height=_a60;
return true;
},checkSize:function(){
if(!this._isResized()){
return;
}
this.onResized();
},resizeTo:function(w,h){
dojo.html.setMarginBox(this.domNode,{width:w,height:h});
if(this.isShowing()){
this.onResized();
}
},resizeSoon:function(){
if(this.isShowing()){
dojo.lang.setTimeout(this,this.onResized,0);
}
},onResized:function(){
dojo.lang.forEach(this.children,function(_a63){
if(_a63.checkSize){
_a63.checkSize();
}
});
}});
dojo.provide("dojo.widget.*");
dojo.provide("dojo.math");
dojo.math.degToRad=function(x){
return (x*Math.PI)/180;
};
dojo.math.radToDeg=function(x){
return (x*180)/Math.PI;
};
dojo.math.factorial=function(n){
if(n<1){
return 0;
}
var _a67=1;
for(var i=1;i<=n;i++){
_a67*=i;
}
return _a67;
};
dojo.math.permutations=function(n,k){
if(n==0||k==0){
return 1;
}
return (dojo.math.factorial(n)/dojo.math.factorial(n-k));
};
dojo.math.combinations=function(n,r){
if(n==0||r==0){
return 1;
}
return (dojo.math.factorial(n)/(dojo.math.factorial(n-r)*dojo.math.factorial(r)));
};
dojo.math.bernstein=function(t,n,i){
return (dojo.math.combinations(n,i)*Math.pow(t,i)*Math.pow(1-t,n-i));
};
dojo.math.gaussianRandom=function(){
var k=2;
do{
var i=2*Math.random()-1;
var j=2*Math.random()-1;
k=i*i+j*j;
}while(k>=1);
k=Math.sqrt((-2*Math.log(k))/k);
return i*k;
};
dojo.math.mean=function(){
var _a73=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0;
for(var i=0;i<_a73.length;i++){
mean+=_a73[i];
}
return mean/_a73.length;
};
dojo.math.round=function(_a76,_a77){
if(!_a77){
var _a78=1;
}else{
var _a78=Math.pow(10,_a77);
}
return Math.round(_a76*_a78)/_a78;
};
dojo.math.sd=dojo.math.standardDeviation=function(){
var _a79=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
return Math.sqrt(dojo.math.variance(_a79));
};
dojo.math.variance=function(){
var _a7a=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0,_a7c=0;
for(var i=0;i<_a7a.length;i++){
mean+=_a7a[i];
_a7c+=Math.pow(_a7a[i],2);
}
return (_a7c/_a7a.length)-Math.pow(mean/_a7a.length,2);
};
dojo.math.range=function(a,b,step){
if(arguments.length<2){
b=a;
a=0;
}
if(arguments.length<3){
step=1;
}
var _a81=[];
if(step>0){
for(var i=a;i<b;i+=step){
_a81.push(i);
}
}else{
if(step<0){
for(var i=a;i>b;i+=step){
_a81.push(i);
}
}else{
throw new Error("dojo.math.range: step must be non-zero");
}
}
return _a81;
};
dojo.provide("dojo.math.curves");
dojo.math.curves={Line:function(_a83,end){
this.start=_a83;
this.end=end;
this.dimensions=_a83.length;
for(var i=0;i<_a83.length;i++){
_a83[i]=Number(_a83[i]);
}
for(var i=0;i<end.length;i++){
end[i]=Number(end[i]);
}
this.getValue=function(n){
var _a87=new Array(this.dimensions);
for(var i=0;i<this.dimensions;i++){
_a87[i]=((this.end[i]-this.start[i])*n)+this.start[i];
}
return _a87;
};
return this;
},Bezier:function(pnts){
this.getValue=function(step){
if(step>=1){
return this.p[this.p.length-1];
}
if(step<=0){
return this.p[0];
}
var _a8b=new Array(this.p[0].length);
for(var k=0;j<this.p[0].length;k++){
_a8b[k]=0;
}
for(var j=0;j<this.p[0].length;j++){
var C=0;
var D=0;
for(var i=0;i<this.p.length;i++){
C+=this.p[i][j]*this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,i);
}
for(var l=0;l<this.p.length;l++){
D+=this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,l);
}
_a8b[j]=C/D;
}
return _a8b;
};
this.p=pnts;
return this;
},CatmullRom:function(pnts,c){
this.getValue=function(step){
var _a95=step*(this.p.length-1);
var node=Math.floor(_a95);
var _a97=_a95-node;
var i0=node-1;
if(i0<0){
i0=0;
}
var i=node;
var i1=node+1;
if(i1>=this.p.length){
i1=this.p.length-1;
}
var i2=node+2;
if(i2>=this.p.length){
i2=this.p.length-1;
}
var u=_a97;
var u2=_a97*_a97;
var u3=_a97*_a97*_a97;
var _a9f=new Array(this.p[0].length);
for(var k=0;k<this.p[0].length;k++){
var x1=(-this.c*this.p[i0][k])+((2-this.c)*this.p[i][k])+((this.c-2)*this.p[i1][k])+(this.c*this.p[i2][k]);
var x2=(2*this.c*this.p[i0][k])+((this.c-3)*this.p[i][k])+((3-2*this.c)*this.p[i1][k])+(-this.c*this.p[i2][k]);
var x3=(-this.c*this.p[i0][k])+(this.c*this.p[i1][k]);
var x4=this.p[i][k];
_a9f[k]=x1*u3+x2*u2+x3*u+x4;
}
return _a9f;
};
if(!c){
this.c=0.7;
}else{
this.c=c;
}
this.p=pnts;
return this;
},Arc:function(_aa5,end,ccw){
var _aa8=dojo.math.points.midpoint(_aa5,end);
var _aa9=dojo.math.points.translate(dojo.math.points.invert(_aa8),_aa5);
var rad=Math.sqrt(Math.pow(_aa9[0],2)+Math.pow(_aa9[1],2));
var _aab=dojo.math.radToDeg(Math.atan(_aa9[1]/_aa9[0]));
if(_aa9[0]<0){
_aab-=90;
}else{
_aab+=90;
}
dojo.math.curves.CenteredArc.call(this,_aa8,rad,_aab,_aab+(ccw?-180:180));
},CenteredArc:function(_aac,_aad,_aae,end){
this.center=_aac;
this.radius=_aad;
this.start=_aae||0;
this.end=end;
this.getValue=function(n){
var _ab1=new Array(2);
var _ab2=dojo.math.degToRad(this.start+((this.end-this.start)*n));
_ab1[0]=this.center[0]+this.radius*Math.sin(_ab2);
_ab1[1]=this.center[1]-this.radius*Math.cos(_ab2);
return _ab1;
};
return this;
},Circle:function(_ab3,_ab4){
dojo.math.curves.CenteredArc.call(this,_ab3,_ab4,0,360);
return this;
},Path:function(){
var _ab5=[];
var _ab6=[];
var _ab7=[];
var _ab8=0;
this.add=function(_ab9,_aba){
if(_aba<0){
dojo.raise("dojo.math.curves.Path.add: weight cannot be less than 0");
}
_ab5.push(_ab9);
_ab6.push(_aba);
_ab8+=_aba;
computeRanges();
};
this.remove=function(_abb){
for(var i=0;i<_ab5.length;i++){
if(_ab5[i]==_abb){
_ab5.splice(i,1);
_ab8-=_ab6.splice(i,1)[0];
break;
}
}
computeRanges();
};
this.removeAll=function(){
_ab5=[];
_ab6=[];
_ab8=0;
};
this.getValue=function(n){
var _abe=false,_abf=0;
for(var i=0;i<_ab7.length;i++){
var r=_ab7[i];
if(n>=r[0]&&n<r[1]){
var subN=(n-r[0])/r[2];
_abf=_ab5[i].getValue(subN);
_abe=true;
break;
}
}
if(!_abe){
_abf=_ab5[_ab5.length-1].getValue(1);
}
for(var j=0;j<i;j++){
_abf=dojo.math.points.translate(_abf,_ab5[j].getValue(1));
}
return _abf;
};
function computeRanges(){
var _ac4=0;
for(var i=0;i<_ab6.length;i++){
var end=_ac4+_ab6[i]/_ab8;
var len=end-_ac4;
_ab7[i]=[_ac4,end,len];
_ac4=end;
}
}
return this;
}};
dojo.provide("dojo.math.points");
dojo.math.points={translate:function(a,b){
if(a.length!=b.length){
dojo.raise("dojo.math.translate: points not same size (a:["+a+"], b:["+b+"])");
}
var c=new Array(a.length);
for(var i=0;i<a.length;i++){
c[i]=a[i]+b[i];
}
return c;
},midpoint:function(a,b){
if(a.length!=b.length){
dojo.raise("dojo.math.midpoint: points not same size (a:["+a+"], b:["+b+"])");
}
var c=new Array(a.length);
for(var i=0;i<a.length;i++){
c[i]=(a[i]+b[i])/2;
}
return c;
},invert:function(a){
var b=new Array(a.length);
for(var i=0;i<a.length;i++){
b[i]=-a[i];
}
return b;
},distance:function(a,b){
return Math.sqrt(Math.pow(b[0]-a[0],2)+Math.pow(b[1]-a[1],2));
}};
dojo.provide("dojo.math.*");

