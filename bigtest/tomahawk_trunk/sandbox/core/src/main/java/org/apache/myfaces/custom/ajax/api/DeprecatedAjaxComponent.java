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
package org.apache.myfaces.custom.ajax.api;

/**Before the creation of the ppr-architecture, there was a similar effort done - this is deprecated now
 * and should be refactored to use the new architecture.
 *
 * Rule of thumb: only use AjaxComponent if you want to render something different on a partial request
 * if rendering stays the same, use the normal component architecture.
 *
 * @version $Revision: $ $Date: $
 *          <p/>
 * @deprecated
 */
public interface DeprecatedAjaxComponent extends AjaxComponent {
}
