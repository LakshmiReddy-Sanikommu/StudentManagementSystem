package com.sms;

import org.springframework.stereotype.Service;

@Service
public class AssignmentService {
    public String assignToAll() { return "Assigning assignment to all..."; }
    public String changeAssignmentForAll() { return "Changing assignment for all..."; }
    public String deleteAssignmentForAll() { return "Deleting assignment for all..."; }
    public String assignToOne() { return "Assigning assignment to one..."; }
    public String changeAssignmentForOne() { return "Changing assignment for one..."; }
    public String deleteAssignmentForOne() { return "Deleting assignment for one..."; }
    public String checkSubmittedAssignments() { return "Checking submitted assignments..."; }
}
