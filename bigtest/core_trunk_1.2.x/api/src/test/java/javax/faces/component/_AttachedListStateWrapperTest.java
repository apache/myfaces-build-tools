/*
 * Copyright 2004-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.faces.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class _AttachedListStateWrapperTest extends TestCase {

  public static void main(String[] args) {
    junit.textui.TestRunner.run(_AttachedListStateWrapperTest.class);
  }

  public _AttachedListStateWrapperTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /*
   * Test method for 'javax.faces.component._AttachedListStateWrapper._AttachedListStateWrapper(List)'
   */
  public void test_AttachedListStateWrapper() {
    List foo = new ArrayList();
    _AttachedListStateWrapper subject = new _AttachedListStateWrapper(foo);
    assertNotNull(subject.getWrappedStateList());
    assertTrue(subject.getWrappedStateList() == foo);
  }

  public void testSerialize() throws Exception {
    String foo = "foo";
    List list = new ArrayList();
    list.add(foo);
    _AttachedListStateWrapper subject = new _AttachedListStateWrapper(list);
    ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(subject);
    oos.flush();
    baos.flush();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bais);
    _AttachedListStateWrapper blorg = (_AttachedListStateWrapper)ois.readObject();
    assertEquals(blorg.getWrappedStateList(), subject.getWrappedStateList());
    oos.close();
    ois.close();
  }
  
}
