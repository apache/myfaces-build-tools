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
package javax.faces.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.shale.test.base.AbstractJsfTestCase;

import junit.framework.Test;

public class UISelectManyTest extends AbstractJsfTestCase {

  public UISelectManyTest(String name) {
    super(name);
  }

  public static Test suite() {
      return null; // keep this method or maven won't run it
  }
  
  public void testValidateRequiredNull() {

    facesContext.getViewRoot().setLocale(_TEST_LOCALE);

    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    selectMany.setRequired(true);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue(new Integer(1));
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue(new Integer(2));
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue(new Integer(3));
    children.add(three);

    selectMany.validateValue(facesContext, null);

    assertFalse(selectMany.isValid());
  }

  public void testValidateRequiredEmptyList() {
    
    facesContext.getViewRoot().setLocale(_TEST_LOCALE);

    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    selectMany.setRequired(true);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue(new Integer(1));
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue(new Integer(2));
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue(new Integer(3));
    children.add(three);

    selectMany.validateValue(facesContext, Collections.EMPTY_LIST);

    assertFalse(selectMany.isValid());
  }

  public void testValidateIntArray() {
    
	facesContext.getViewRoot().setLocale(_TEST_LOCALE);
	  
    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue(new Integer(1));
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue(new Integer(2));
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue(new Integer(3));
    children.add(three);

    selectMany.validateValue(facesContext, new int[] { 2, 3 });

    assertTrue(selectMany.isValid());
  }

  public void testValidateStringArray() {

	facesContext.getViewRoot().setLocale(_TEST_LOCALE);

    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue("1");
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue("2");
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue("3");
    children.add(three);

    selectMany.validateValue(facesContext, new String[] { "2", "3" });

    assertTrue(selectMany.isValid());
  }

  public void testValidateStringList() {

	facesContext.getViewRoot().setLocale(_TEST_LOCALE);
	  
    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue("1");
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue("2");
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue("3");
    children.add(three);

    selectMany.validateValue(facesContext, Arrays.asList(new String[] { "2", "3" }));

    assertTrue(selectMany.isValid());
  }

  static private final Locale _TEST_LOCALE = new Locale("xx", "TEST");
}
