package cis498;

public class Room {
    String buildingCode;
    String roomNumber;
    boolean computerized;
    int capacity;
    String maintenanceDay;
    int maintenanceStartTime;
    int maintenanceEndTime;

    public Room(String buildingCodeIn, String roomNumberIn, boolean computerizedIn, int capacityIn, String maintenanceDayIn, int maintenanceStartTimeIn, int maintenanceEndTimeIn) {
        buildingCode = buildingCodeIn;
        roomNumber = roomNumberIn;
        computerized = computerizedIn;
        capacity = capacityIn;
        maintenanceDay = maintenanceDayIn;
        maintenanceStartTime = maintenanceStartTimeIn;
        maintenanceEndTime = maintenanceEndTimeIn;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCodeIn) {
        buildingCode = buildingCodeIn;
    }
    
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String buildingIn) {
        building = buildingIn;
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
    
    
}
