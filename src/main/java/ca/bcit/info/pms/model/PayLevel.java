package ca.bcit.info.pms.model;

/**
 * PayLevel names.
 */
public enum PayLevel {
    P1("P1"),
    P2("P2"),
    P3("P3"),
    P4("P4"),
    P5("P5"),
    P6("P6"),
    JS("JS"),
    SS("SS"),
    DS("DS");

    private String label;

    private PayLevel(final String name) {
        label = name;
    }

    public String getLabel() {
        return label;
    }
}
