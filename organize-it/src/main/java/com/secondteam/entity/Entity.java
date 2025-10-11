package entity;

public class Entity {
    private String field1;
    private String field2;
    private Integer field3;

    private Entity(Builder builder) {
        this.field1 = builder.field1;
        this.field2 = builder.field2;
        this.field3 = builder.field3;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public Integer getField3() {
        return field3;
    }

    public static class Builder {
        private String field1;
        private String field2;
        private Integer field3;

        public Builder setField1(String field1) {
            if (field1 == null || field1.trim().isEmpty()) {
                throw new IllegalArgumentException("Field1 cannot be null or empty");
            }
            this.field1 = field1;
            return this;
        }

        public Builder setField2(String field2) {
            if (field2 == null || field2.trim().isEmpty()) {
                throw new IllegalArgumentException("Field2 cannot be null or empty");
            }
            this.field2 = field2;
            return this;
        }

        public Builder setField3(String field3Str) {
            try {
                this.field3 = Integer.parseInt(field3Str);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Field3 must be a valid integer");
            }
            return this;
        }

        public Entity build() {
            if (field1 == null || field2 == null || field3 == null) {
                throw new IllegalStateException("All fields must be initialized");
            }
            return new Entity(this);
        }
    }

    @Override
    public String toString() {
        return "Entity{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3=" + field3 +
                '}';
    }
}