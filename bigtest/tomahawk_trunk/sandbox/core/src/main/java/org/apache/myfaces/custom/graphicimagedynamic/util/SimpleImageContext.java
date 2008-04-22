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

package org.apache.myfaces.custom.graphicimagedynamic.util;

import java.util.Map;

public class SimpleImageContext implements ImageContext {
	
	private final Map _params;
	private final Integer _width;
	private final Integer _height;

	public SimpleImageContext(Map params, Integer width, Integer height) {
		_params = params;
		_width = width;
		_height = height;
	}

	public Map getParamters() {
		return _params;
	}

	public Integer getWidth() {
		return _width;
	}

	public Integer getHeight() {
		return _height;
	}
}

