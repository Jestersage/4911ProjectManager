package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.inject.Inject;

import ca.bcit.info.pms.service.TimesheetService;

public class TimesheetController implements Serializable {

    @Inject
    private TimesheetService timeService;
}
