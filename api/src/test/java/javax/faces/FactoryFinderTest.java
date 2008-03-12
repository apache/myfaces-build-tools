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

package javax.faces;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.apache.myfaces.mock.api.Mock2ApplicationFactory;
import org.apache.myfaces.mock.api.MockApplicationFactory;

public class FactoryFinderTest extends TestCase {

  public static void main(String[] args) {
    junit.textui.TestRunner.run(FactoryFinderTest.class);

  }

  public FactoryFinderTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    // this needs to be called *before* the first Test test is run, 
    // as there may be left over FactoryFinder configurations from
    // that previous tests that may interfere with the first test here. 
    FactoryFinder.releaseFactories(); 
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    // call this again so there is no possibility of messing up tests that will
    // run after this one
    FactoryFinder.releaseFactories();
    releaseRegisteredFactoryNames();
  }

  private void releaseRegisteredFactoryNames() throws Exception {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Map _registeredFactoryNames = getRegisteredFactoryNames();
    _registeredFactoryNames.remove(classLoader);
  }

  private List registeredFactoryNames(String factoryName) throws Exception {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Map _registeredFactoryNames = getRegisteredFactoryNames();
    Map map = (Map) _registeredFactoryNames.get(classLoader);
    return (List) map.get(factoryName);
  }

  /*
   * This method allows us access to the _registeredFactoryNames field so we can
   * test the content of that map during the running of this test.
   * 
   * @return Returns the _registeredFactoryNames Map from the FactoryFinder
   * class. @throws NoSuchFieldException @throws IllegalAccessException
   */
  private Map getRegisteredFactoryNames() throws NoSuchFieldException,
      IllegalAccessException {
    Class factoryFinderClass = FactoryFinder.class;
    Field fields[] = factoryFinderClass.getDeclaredFields();
    Field field = null;
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getName().equals("_registeredFactoryNames")) {
        field = fields[i];
        field.setAccessible(true);
        break;
      }
    }
    Map _registeredFactoryNames = (Map) field.get(null);
    return _registeredFactoryNames;
  }

  /*
   * Test method for 'javax.faces.FactoryFinder.getFactory(String)'
   */
  public void testGetFactory() throws Exception {
    // no catch because if this fails the test fails, i.e. not trying to test
    // setFactory here
    FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
        MockApplicationFactory.class.getName());
    try {
      Object factory = FactoryFinder
          .getFactory(FactoryFinder.APPLICATION_FACTORY);
      assertNotNull(factory);
      assertTrue(factory.getClass().equals(MockApplicationFactory.class));
    } catch (IllegalStateException e) {
      fail("Should not throw an illegal state exception");
    }
  }

  /*
   * Test method for 'javax.faces.FactoryFinder.getFactory(String)'
   */
  public void testGetFactoryTwice() throws Exception {
    // this test just makes sure that things work when the get has been called
    // more than once
    FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
        MockApplicationFactory.class.getName());
    try {
      Object factory1 = FactoryFinder
          .getFactory(FactoryFinder.APPLICATION_FACTORY);
      assertNotNull(factory1);
      assertTrue(factory1.getClass().equals(MockApplicationFactory.class));
      Object factory2 = FactoryFinder
          .getFactory(FactoryFinder.APPLICATION_FACTORY);
      assertNotNull(factory2);
      assertTrue(factory2.getClass().equals(MockApplicationFactory.class));
      assertEquals(factory1, factory2);
    } catch (IllegalStateException e) {
      fail("Should not throw an illegal state exception");
    }
  }

  /*
   * Test method for 'javax.faces.FactoryFinder.getFactory(String)'
   */
  public void testGetFactoryNoFactory() throws Exception {
    // no catch because if this fails the test fails, i.e. not trying to test
    // setFactory here
    FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
        MockApplicationFactory.class.getName());
    try {
      FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
      fail("Should have thrown an illegal state exception");
    } catch (IllegalArgumentException e) {
      assertNotNull(e.getMessage());
    }
  }

  /*
   * No configuration test, this should throw and deliver a useful message Test
   * method for 'javax.faces.FactoryFinder.getFactory(String)'
   */
  public void testGetFactoryNoConfiguration() throws Exception {
    try {
      FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
      fail("Should have thrown an illegal state exception");
    } catch (IllegalStateException e) {
      assertNotNull(e.getMessage());
      assertTrue(e.getMessage().startsWith(
          "No Factories configured for this Application"));
    }
  }

  /*
   * Bogus factory name test Test method for
   * 'javax.faces.FactoryFinder.setFactory(String, String)'
   */
  public void testSetFactoryBogusName() {
    try {
      FactoryFinder.setFactory("BogusFactoryName", MockApplicationFactory.class
          .getName());
      fail("Should have thrown an illegal argument exception");
    } catch (IllegalArgumentException e) {
      assertNotNull(e.getMessage());
    }
  }

  /*
   * Test method for 'javax.faces.FactoryFinder.setFactory(String, String)'
   */
  public void testSetFactory() throws Exception {
    try {
      FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
          MockApplicationFactory.class.getName());
      assertTrue(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(MockApplicationFactory.class.getName()));
    } catch (IllegalArgumentException e) {
      fail("Should not throw an illegal argument exception");
    }
  }

  /*
   * If a factory has ever been handed out then setFactory is not supposed to
   * change the factory layout. This test checks to see if that is true. Test
   * method for 'javax.faces.FactoryFinder.setFactory(String, String)'
   */
  public void testSetFactoryNoEffect() throws Exception {
    try {
      FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
          MockApplicationFactory.class.getName());
      assertTrue(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(MockApplicationFactory.class.getName()));
      assertFalse(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(Mock2ApplicationFactory.class.getName()));
      // getFactory should cause setFactory to stop changing the
      // registered classes
      FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
      // this should essentially be a no-op
      FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
          Mock2ApplicationFactory.class.getName());
      assertFalse(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(Mock2ApplicationFactory.class.getName()));
      assertTrue(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(MockApplicationFactory.class.getName()));
    } catch (IllegalArgumentException e) {
      fail("Should not throw an illegal argument exception");
    }
  }

  /*
   * Adding factories should add the class name to the list of avalable class
   * names Test method for 'javax.faces.FactoryFinder.setFactory(String,
   * String)'
   */
  public void testSetFactoryAdditiveClassNames() throws Exception {
    try {
      FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
          MockApplicationFactory.class.getName());
      assertTrue(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(MockApplicationFactory.class.getName()));
      assertFalse(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(Mock2ApplicationFactory.class.getName()));
      FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
          Mock2ApplicationFactory.class.getName());
      assertTrue(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(Mock2ApplicationFactory.class.getName()));
      assertTrue(registeredFactoryNames(FactoryFinder.APPLICATION_FACTORY)
          .contains(MockApplicationFactory.class.getName()));
    } catch (IllegalArgumentException e) {
      fail("Should not throw an illegal argument exception");
    }
  }

  /*
   * Test method for 'javax.faces.FactoryFinder.releaseFactories()'
   */
  public void testReleaseFactories() {

  }

}
