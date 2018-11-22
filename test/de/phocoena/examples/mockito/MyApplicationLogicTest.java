/*
MIT License

Copyright (c) 2018 phocoena

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package de.phocoena.examples.mockito;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

public class MyApplicationLogicTest {

    @Mock
    MyApplicationData myApplicationDataMockInstance; 
	
    @Rule 
    public MockitoRule mockitoRule = MockitoJUnit.rule(); 

    MyApplicationLogic engine;
    MyApplication application;
    
    @Before
    public void init() throws Exception {
        engine = new MyApplicationLogic( 1, 2);
    }
    
    @After
    public void cleanup() throws Exception {
        engine = null;
    }

    @Test
    public void testsWithChangingApplicationDataFunction() {
        when(myApplicationDataMockInstance.getSensorDataFunction()).thenReturn(1,10,100,0);
        engine = new MyApplicationLogic( 1, 2, myApplicationDataMockInstance);
        engine.setTolerance(1, 10);
        assertEquals("testsWithChangingApplicationData 1 1 10",    engine.isSensorDataWithinToleranceWithFunctionCall(), true);
        assertEquals("testsWithChangingApplicationData 1 10 10",   engine.isSensorDataWithinToleranceWithFunctionCall(), true);
        assertEquals("testsWithChangingApplicationData 1 1000 10", engine.isSensorDataWithinToleranceWithFunctionCall(), false);
        assertEquals("testsWithChangingApplicationData 1 0 10",    engine.isSensorDataWithinToleranceWithFunctionCall(), false);
    }
    
    @Test
    public void testsWithChangingApplicationDataProcedure() {
      
        List<Integer> myList = new ArrayList<>();
        myList.add(1);
        myList.add(10);
        myList.add(100);
        
        doAnswer((Answer<Object>) (InvocationOnMock invocation) -> {
          Object[] args = invocation.getArguments();
          if( myList.isEmpty()) {
            ((MyApplicationValueContainer)args[0]).setValue(0);
            return null;
          }
          ((MyApplicationValueContainer)args[0]).setValue((myList.get(0)));
          myList.remove(0);
          return null; // void method, so return null
        }).when( myApplicationDataMockInstance).getSensorDataProcedure(ArgumentMatchers.any());
        
        engine = new MyApplicationLogic( 1, 2, myApplicationDataMockInstance);
        engine.setTolerance(1, 11);
        
        assertEquals("testsWithChangingApplicationData 1 1 1", engine.isSensorDataWithinToleranceWithProcedureCall(), true);
        assertEquals("testsWithChangingApplicationData 1 10 10", engine.isSensorDataWithinToleranceWithProcedureCall(), true);
        assertEquals("testsWithChangingApplicationData 1 100 10", engine.isSensorDataWithinToleranceWithProcedureCall(), false);
        assertEquals("testsWithChangingApplicationData 1 0 10", engine.isSensorDataWithinToleranceWithProcedureCall(), false);
        
    }
    
}
