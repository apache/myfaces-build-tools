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
package org.apache.myfaces.custom.effect;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlOutputTextTagBase;

/**
 * The tag for the fade, it wont have any attributes just a plain tag, which
 * does the inclusion of the fader
 *
 * 6/10/2005 The fade now is altered into a general effects tag which will work
 * like that
 *
 * <x:effects squish="true" fade="true" fadeColor="#afafaf">content</x:effects>
 * <x:effects fade="true"/>
 *
 * @author Werner Punz werpu@gmx.at
 * @version $Revision$ $Date$
 */

public class EffectTag extends HtmlOutputTextTagBase {
    //DivTag
    public static final String	TAG_PARAM_FADECOLOR		= "fadeColor";

    public static final String	TAG_PARAM_FADE			= "fade";

    public static final String  TAG_PARAM_DURATION      = "duration";
    
    public static final String	TAG_PARAM_SQUISH		= "squish";

    public static final String	TAG_PARAM_SCALE			= "scale";

    public static final String	TAG_PARAM_SCALE_SIZE	= "scaleSize";

    public static final String	TAG_PARAM_PUFF			= "puff";

    public static final String  TAG_PARAM_PULSATE 		= "pulsate";

    /**
     * only one param, to keep it as simple as possible it will hover out at
     * 100% and the default hover in is 150% there is no real need for a
     * different hover out than 100% that I can see for now but feel free to add
     * one if you need it
     */

    String						_fadeColor				= "";

    String                      _duration               = "500";
    
    String						_fade					= "false";

    String						_puff					= "false";

    String						_scale					= "false";

    String						_scaleSize				= "150";

    String						_squish					= "false";

    String 						_pulsate				= "false";

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        super.setStringProperty(component, TAG_PARAM_FADECOLOR, _fadeColor);
        super.setIntegerProperty(component, TAG_PARAM_SCALE_SIZE, _scaleSize);
        super.setBooleanProperty(component, TAG_PARAM_FADE, _fade);


        super.setBooleanProperty(component, TAG_PARAM_PUFF, _puff);
        super.setBooleanProperty(component, TAG_PARAM_SCALE, _scale);
        super.setBooleanProperty(component, TAG_PARAM_SQUISH, _squish);
        super.setBooleanProperty(component, TAG_PARAM_PULSATE, _pulsate);
    
        super.setIntegerProperty(component, TAG_PARAM_DURATION, _duration);
    }

    /**
     * release method
     */
    public void release() {
        super.release();
        _fadeColor = null;
        _fade = null;
        _puff = null;
        _scale = null;
        _squish = null;
        _scaleSize = null;
        _pulsate = null;
        _duration = null;
    }

    public String getComponentType() {
        return Effect.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return EffectRenderer.RENDERER_TYPE;
    }

    /**
     * Setter for the fade color now in place
     *
     * @param fadeColor
     */
    public void setFadeColor(String fadeColor) {
        _fadeColor = fadeColor;
    }

    /**
     * enable the fading effect
     *
     * @param fade
     */
    public void setFade(String fade) {
        _fade = fade;
    }

    /**
     * enable the scaleing effect
     *
     * @param incoming
     */
    public void setscale(String incoming) {
        _scale = incoming;
    }

    /**
     * enable the puffing effect
     *
     * @param puff
     */
    public void setPuff(String puff) {
        _puff = puff;
    }

    /**
     * enable the squishing effect
     *
     * @param sqish
     */
    public void setSquish(String sqish) {
        _squish = sqish;
    }

    /**
     * Scale size for the hover out adjustment of the scale effect
     *
     * @param size
     */
    public void setScaleSize(String size) {
        _scaleSize = size;
    }

    public void setPulsate(String pulsate) {
        _pulsate = pulsate;
    }

    public void setDuration(String effectDuration)
    {
       _duration = effectDuration;
    }
}
