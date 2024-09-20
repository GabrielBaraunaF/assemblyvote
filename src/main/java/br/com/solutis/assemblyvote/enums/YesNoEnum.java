package br.com.solutis.assemblyvote.enums;

public enum YesNoEnum {
    YES("Y"),
    NO("N");

    private final String value;

    YesNoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String value) {
        for (YesNoEnum yesNoEnum : YesNoEnum.values()) {
            if (yesNoEnum.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static YesNoEnum fromValue(String value) {
        for (YesNoEnum yesNoEnum : YesNoEnum.values()) {
            if (yesNoEnum.getValue().equalsIgnoreCase(value)) {
                return yesNoEnum;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}
