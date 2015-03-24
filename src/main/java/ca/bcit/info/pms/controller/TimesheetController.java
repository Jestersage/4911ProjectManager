package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.service.TimesheetService;

@Named( "timesheetController" )
@RequestScoped
public class TimesheetController implements Serializable {

    @Inject
    private TimesheetService timeService;
}
