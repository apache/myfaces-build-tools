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

if (!imageLoops) {
    var imageLoops = new Object();
}

getImageLoop = function(clientId) {
    return imageLoops[clientId];
}

function ImageLoop(clientId, images, delay, minDelay, maxDelay, transitionTime) {

    this._clientId = clientId;
    this._clientIdImg1 = clientId + ":IMG1";
    this._clientIdImg2 = clientId + ":IMG2";
    this._images = images;
    this._numImagesLoaded = 0;
    this._index = 0;
    
    this._imgState = [ this._clientIdImg1, this._clientIdImg2 ];
    dojo.html.hide(dojo.byId(this._imgState[0]));
    dojo.html.hide(dojo.byId(this._imgState[1]));

    this._timer = null;
    this._speedModificationFactor = 2;
    
    this._delay = delay;
    this._originalDelay = delay;
    if (minDelay > delay) {
        dojo.debug("Minimum delay may not be greater than delay");
        this._minDelay = delay;
    } else {
        this._minDelay = minDelay;
    }
    if (maxDelay < delay) {
        dojo.debug("Maximum delay may not be less than delay"); 
        this._maxDelay = maxDelay;
    } else {
        this._maxDelay = maxDelay;
    }
    if (transitionTime <= 0) {
        this._transitionTime = 1;
    } else {
        this._transitionTime = transitionTime;
    }
    
    this._animsCombined = null;
    
    if (imageLoops[clientId]) {
        imageLoops[clientId].stop();
    }
    imageLoops[clientId] = this;
    
    this._preload();
}

ImageLoop.prototype.start = function() {
    if (this._timer === null && this._numImagesLoaded === this._images.length && this._images.length > 1) {
        dojo.debug("Starting loop");
        this._timer = -1;
        this._loopImages();
    }
}

ImageLoop.prototype.stop = function() {
    if (this._timer !== null) {
        dojo.debug("Stopping loop.");
        dojo.lang.clearTimeout(this._timer);
        this._timer = null;
    }
}
    
ImageLoop.prototype.forward = function() {
    if (this._numImagesLoaded === this._images.length && this._images.length > 1) {
        this.stop();
        if (this._animsCombined !== null) {
            this._animsCombined.stop();
        }
        this._index = (this._index+1) % this._images.length;
        var url = this._images[this._index];
        this._showImage(url, 1);
    }
}

ImageLoop.prototype.back = function() {
    if (this._numImagesLoaded === this._images.length && this._images.length > 1) {
        this.stop();
        if (this._animsCombined !== null) {
            this._animsCombined.stop();
        }
        this._index = (this._images.length + this._index - 1) % this._images.length;
        var url = this._images[this._index];
        this._showImage(url, 1);
    }
}

ImageLoop.prototype.accelerate = function() {
    var newDelay = this._delay / this._speedModificationFactor;
    if (newDelay < this._minDelay) {
        this._delay = this._minDelay;
        dojo.debug("Minimum delay reached.");
    } else {
        this._delay = newDelay;
    }
}

ImageLoop.prototype.decelerate = function() {
    var newDelay = this._delay * this._speedModificationFactor;
    if (newDelay > this._maxDelay) {
        this._delay = this._maxDelay;
        dojo.debug("Maximum delay reached.");
    } else {
        this._delay = newDelay;
    }
}

ImageLoop.prototype.reset = function() {
    dojo.debug("Delay reset.");
    this._delay = this._originalDelay;
}

ImageLoop.prototype.getImageCount = function() {
    return this._images.length;
}

ImageLoop.prototype.setImageIndex = function(index) {
    if (this._numImagesLoaded === this._images.length) {
        if (index >= this._images.length) {
            throw RangeError; 
        }
        this.stop();
        if (this._animsCombined !== null) {
            this._animsCombined.stop();
        }
        this._index = index;
        var url = this._images[this._index];
        this._showImage(url, 1);
    }
}

ImageLoop.prototype._imagePreloaded = function(evt) {
    this._numImagesLoaded++;
    dojo.debug("Image " + this._numImagesLoaded + "/" + this._images.length + " loaded [" + evt.currentTarget.src + "]");
    if (this._numImagesLoaded === this._images.length) {
        dojo.debug("Finished loading: " + this._numImagesLoaded + " images loaded.");
        // Set index to last image. Loop will start with first image.
        this._index = this._images.length - 1;
        this.start();
    }
}

ImageLoop.prototype._preload = function() {
    dojo.debug("Pre-loading " + this._images.length + " images.");
    for (var i=0; i<this._images.length; i++) {
        var image = new Image();
        dojo.event.connect(image, "onload", this, "_imagePreloaded");
        image.src = this._images[i];
    }
}

ImageLoop.prototype._loopImages = function() {
    this._index++;
    this._index = this._index % this._images.length;
    var url = this._images[this._index];
    this._showImage(url, this._transitionTime);
}

ImageLoop.prototype._showImageOnEnd = function() {
    this._animsCombined = null;
    if (this._timer !== null) {
        this._timer = dojo.lang.setTimeout(this, "_loopImages", this._delay);
    }
}

ImageLoop.prototype._showImage = function(imgUrl, transitionTime) {
    var imgShow = dojo.byId(this._imgState[0]);
    var imgHide = dojo.byId(this._imgState[1]);
    // Switch image id's for next loop.
    this._imgState[0] = imgHide.id;
    this._imgState[1] = imgShow.id;
    imgShow.src = imgUrl;
    
    var anims = [];
    var animImgShow = dojo.lfx.html.fadeShow(imgShow, transitionTime);
    anims.push(animImgShow);
    var animImgHide = dojo.lfx.html.fadeHide(imgHide, transitionTime);
    anims.push(animImgHide);
    this._animsCombined = dojo.lfx.combine(anims);
    dojo.event.connect(this._animsCombined, "onEnd", this, "_showImageOnEnd");
    this._animsCombined.play();
}

