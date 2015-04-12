package pl.edu.agh.awi.persistence.model;


import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ModelBuilderTest {


    public static final Date DATE_PROPERTY = new Date();
    public static final Double DOUBLE_PROPERTY = Double.valueOf("12.3");
    public static final int INTEGER_PROPERTY = 13;
    public static final String STRING_PROPERTY = "Some string";

    @Test
    public void shouldBuildValidModel() {
        SomeModel expectedModel = createExpectedModel();
        SomeModel model = ModelBuilder.build(SomeModel::new,
                m -> {
                    m.setDateProperty(DATE_PROPERTY);
                    m.setStringProperty(STRING_PROPERTY);
                    m.setDoubleProperty(DOUBLE_PROPERTY);
                    m.setIntegerProperty(INTEGER_PROPERTY);
                });
        assertEquals(expectedModel, model);
    }

    private SomeModel createExpectedModel() {
        SomeModel model = new SomeModel();
        model.setDateProperty(DATE_PROPERTY);
        model.setDoubleProperty(DOUBLE_PROPERTY);
        model.setIntegerProperty(INTEGER_PROPERTY);
        model.setStringProperty(STRING_PROPERTY);
        return model;
    }


    public class SomeModel {

        private String stringProperty;

        private Integer integerProperty;

        private Double doubleProperty;

        private Date dateProperty;

        public String getStringProperty() {
            return stringProperty;
        }

        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public Integer getIntegerProperty() {
            return integerProperty;
        }

        public void setIntegerProperty(Integer integerProperty) {
            this.integerProperty = integerProperty;
        }

        public Double getDoubleProperty() {
            return doubleProperty;
        }

        public void setDoubleProperty(Double doubleProperty) {
            this.doubleProperty = doubleProperty;
        }

        public Date getDateProperty() {
            return dateProperty;
        }

        public void setDateProperty(Date dateProperty) {
            this.dateProperty = dateProperty;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SomeModel someModel = (SomeModel) o;

            if (stringProperty != null ? !stringProperty.equals(someModel.stringProperty) : someModel.stringProperty != null)
                return false;
            if (integerProperty != null ? !integerProperty.equals(someModel.integerProperty) : someModel.integerProperty != null)
                return false;
            if (doubleProperty != null ? !doubleProperty.equals(someModel.doubleProperty) : someModel.doubleProperty != null)
                return false;
            return !(dateProperty != null ? !dateProperty.equals(someModel.dateProperty) : someModel.dateProperty != null);

        }

        @Override
        public int hashCode() {
            int result = stringProperty != null ? stringProperty.hashCode() : 0;
            result = 31 * result + (integerProperty != null ? integerProperty.hashCode() : 0);
            result = 31 * result + (doubleProperty != null ? doubleProperty.hashCode() : 0);
            result = 31 * result + (dateProperty != null ? dateProperty.hashCode() : 0);
            return result;
        }
    }
}
