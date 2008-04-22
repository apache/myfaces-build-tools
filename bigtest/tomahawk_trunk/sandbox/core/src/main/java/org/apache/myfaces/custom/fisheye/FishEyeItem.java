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

package org.apache.myfaces.custom.fisheye;

/**
 * @author Thomas Spiegl
 */
public class FishEyeItem {
    private String _caption;
    private String _iconSrc;
    private String _target;

    public FishEyeItem(String caption, String iconSrc) {
        _caption = caption;
        _iconSrc = iconSrc;
    }

    public FishEyeItem(String caption, String iconSrc, String target) {
        _caption = caption;
        _iconSrc = iconSrc;
        _target = target;
    }

    public String getCaption() {
        return _caption;
    }

    public void setCaption(String caption) {
        _caption = caption;
    }

    public String getIconSrc() {
        return _iconSrc;
    }

    public void setIconSrc(String iconSrc) {
        _iconSrc = iconSrc;
    }

    public String getTarget() {
        return _target;
    }

    public void setTarget(String target) {
        _target = target;
    }
}
