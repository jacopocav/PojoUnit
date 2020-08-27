package testutil;

public class MethodsTestClass {
    private Integer rightProperty;
    public Integer getRightProperty() {
        return rightProperty;
    }
    public void setRightProperty(Integer rightProperty) {
        this.rightProperty = rightProperty;
    }


    private boolean rightBooleanProperty;
    public boolean isRightBooleanProperty() {
        return rightBooleanProperty;
    }
    public void setRightBooleanProperty(boolean rightBooleanProperty) {
        this.rightBooleanProperty = rightBooleanProperty;
    }
    public MethodsTestClass setFluentBooleanProperty(boolean rightBooleanProperty) {
        this.rightBooleanProperty = rightBooleanProperty;
        return this;
    }

    private Object notAProperty;
    public Integer notAGetter() {
        return null;
    }
    public void notASetter(boolean boolValue) {}

    private String wrongProperty;
    public Long getWrongProperty() {
        return null;
    }
    public void setWrongProperty(int value) {}

    private Character wrongBooleanProperty;
    public Character isWrongBooleanProperty() {
        return wrongBooleanProperty;
    }

    public void setNotUnary() {}
    public Object getNotNullary(Object parameter) {return null;}
    public boolean hasWrongName() {return false;}
    public String setWrongReturnType(int parameter) {return null;}

}
