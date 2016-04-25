package cis498;

public class Room {
    String buildingCode;
    String roomNumber;
    boolean computerized;
    int capacity;
    String maintenanceDay;
    int maintenanceStartTime;  
    int maintenanceEndTime;    
    Section[][] timeSlots;     // array of all possible exam timeslots, which will store a scheduled exam object

    public Room(String buildingCodeIn, String roomNumberIn, boolean computerizedIn, int capacityIn, String maintenanceDayIn, 
                int maintenanceStartTimeIn, int maintenanceEndTimeIn, boolean weekendOk) {
        
        buildingCode = buildingCodeIn;
        roomNumber = roomNumberIn;
        computerized = computerizedIn;
        capacity = capacityIn;
        maintenanceDay = maintenanceDayIn;
        maintenanceStartTime = maintenanceStartTimeIn;
        maintenanceEndTime = maintenanceEndTimeIn;
       
        if(weekendOk){
            //room is available on Saturday, so we'll need 5 days x 5 timeslots
            timeSlots= new Section[5][5];
        }else{
            //room is not available on Saturday, so we'll only need 4 days x 5 timeslots
            timeSlots= new Section[4][5];
        }
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCodeIn) {
        buildingCode = buildingCodeIn;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumberIn) {
        roomNumber = roomNumberIn;
    }

    public boolean isComputerized() {
        return computerized;
    }

    public void setComputerized(boolean computerizedIn) {
        computerized = computerizedIn;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacityIn) {
        capacity = capacityIn;
    }

    public String getMaintenanceDay() {
        return maintenanceDay;
    }

    public void setMaintenanceDay(String maintenanceDayIn) {
        maintenanceDay = maintenanceDayIn;
    }

    public int getMaintenanceStartTime() {
        return maintenanceStartTime;
    }

    public void setMaintenanceStartTime(int maintenanceStartTimeIn) {
        maintenanceStartTime = maintenanceStartTimeIn;
    }

    public int getMaintenanceEndTime() {
        return maintenanceEndTime;
    }

    public void setMaintenanceEndTime(int maintenanceEndTimeIn) {
        maintenanceEndTime = maintenanceEndTimeIn;
    }   
    
    public void scheduleExam(Section courseSection, int day, int timeslot){
        if(courseSection!=null && day>=0 && day<5 && timeslot>=0 && timeslot<5){
            timeSlots[day][timeslot] = courseSection;
        }
    }
    
    public Section getScheduledSection(int day, int timeslot){
        if(day>=0 && day<5 && timeslot>=0 && timeslot<5){
            return timeSlots[day][timeslot];
        }else{
            return null;
        }
    }
}