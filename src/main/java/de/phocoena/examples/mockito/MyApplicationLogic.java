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

public class MyApplicationLogic {

    MyApplicationData myApplicationDataInstance;
    
    private Integer lowerBound; 
    private Integer upperBound; 

    public MyApplicationLogic( Integer lowerBound, Integer upperBound) 
    throws IllegalArgumentException 
    {
        myApplicationDataInstance = new MyApplicationData();
        this.setTolerance(lowerBound, upperBound);
    }
        
    public MyApplicationLogic( Integer lowerBound, Integer upperBound, MyApplicationData anApplicationDataInstance) 
    throws IllegalArgumentException 
    {
        myApplicationDataInstance = anApplicationDataInstance;
        this.setTolerance(lowerBound, upperBound);
    }
    
    final public void setTolerance( Integer lowerBound, Integer upperBound) 
    throws IllegalArgumentException 
    {
        if( lowerBound==null || upperBound==null) {
            throw new IllegalArgumentException("bounds must not be null");
        }
        if( lowerBound.intValue()==upperBound.intValue()) {
            throw new IllegalArgumentException("lower and upper bounds must not be the equal");
        }
        if( lowerBound > upperBound) {
            throw new IllegalArgumentException("lower bound must not be greate than upper bound");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public boolean isWithinTolerance( int value) {
        return value >= lowerBound && value <= upperBound;
    }

    public boolean isConstantDataDeliveredByFunctionWithinTolerance() {
        int value = myApplicationDataInstance.getConstantDataFunction();
        return isWithinTolerance(value);
    }
    
    public boolean  isSensorDataDeliveredByFunctionWithinTolerance() {
        int value = myApplicationDataInstance.getSensorDataFunction();
        return isWithinTolerance(value);
    }   

    public boolean isConstantDataDeliveredByProcedureWithinTolerance() {
        MyApplicationValueContainer myValueContainer = new MyApplicationValueContainer(0);
        myApplicationDataInstance.getConstantDataProcedure( myValueContainer);
        return isWithinTolerance(myValueContainer.getValue());
    }
    
    public boolean isSensorDataDeliveredByProcedureWithinTolerance() {
        MyApplicationValueContainer myValueContainer = new MyApplicationValueContainer(0);
        myApplicationDataInstance.getSensorDataProcedure( myValueContainer);
        return isWithinTolerance(myValueContainer.getValue());
    } 
    
}
