package ca.bcit.info.pms.model;

/**
 * Status of a project or work package.
 */
public enum Status {
    /** Created. Waiting for estimated and planned costs information. */
    CREATED,

    /**
     * Open for charge. Estimated and planned costs information ready.
     * For work package ONLY.
     */
    OPEN,

    /**
     * Closed for charge, for the moment, can be opened again.
     * For work package ONLY.
     */
    CLOSED,

    /** Completed, not expected to get any further charges. */
    COMPLETE;
}
