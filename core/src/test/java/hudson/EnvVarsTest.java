/*
 * The MIT License
 * 
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., Kohsuke Kawaguchi
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson;

import junit.framework.TestCase;

import java.util.Collections;

/**
 * @author Kohsuke Kawaguchi
 */
public class EnvVarsTest extends TestCase {
    /**
     * Makes sure that {@link EnvVars} behave in case-insensitive way.
     */
    public void test1() {
        EnvVars ev = new EnvVars(Collections.singletonMap("Path","A:B:C"));
        assertTrue(ev.containsKey("PATH"));
        assertEquals("A:B:C",ev.get("PATH"));
    }

    /**
     * Ensure calls to overrideAll(Map<String,String>) maintains current
     * behaviour of removing keys from the env vars when the provided value is
     * empty
     */
    public void testOverrideAllRemovesEmptyEntries() {
        EnvVars ev = new EnvVars(Collections.singletonMap("emptyString","value"));
        ev.overrideAll(Collections.singletonMap("emptyString",""));
        assertFalse(ev.containsKey("emptyString"));

        ev = new EnvVars(Collections.singletonMap("nullValue","value"));
        ev.overrideAll(Collections.singletonMap("nullValue",(String)null));
        assertFalse(ev.containsKey("nullValue"));
    }

    /**
     * Ensure calls to overrideAllRetainEmpty(Map<String,String>) does not
     * remove keys from the env vars when the provided value is empty
     */
    public void testOverrideAllRetainEmptyDoesNotRemovesEmptyEntries() {
        EnvVars ev = new EnvVars(Collections.singletonMap("emptyString","value"));
        ev.overrideAllRetainEmpty(Collections.singletonMap("emptyString",""));
        assertTrue(ev.containsKey("emptyString"));

        ev = new EnvVars(Collections.singletonMap("nullValue","value"));
        ev.overrideAllRetainEmpty(Collections.singletonMap("nullValue",(String)null));
        assertTrue(ev.containsKey("nullValue"));
    }
}
