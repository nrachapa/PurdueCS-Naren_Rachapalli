package PJ04;

import java.io.FileNotFoundException;

public class TestingPFour {

	public static void main(String[] args) throws FileNotFoundException, InvalidStudentException, OccupiedRoomException, StudentNotFoundException {
		String residenceHall = "/Users/rain09/Workspace/Eclipse-ws/Tester/src/PJ04/residenceHall.txt";
		ResidenceHall rh = new ResidenceHall(residenceHall);
		for (int i = 0; i < rh.listResidents().size(); i++) {
			System.out.println(rh.listResidents().get(i).toString());
		}
		
		System.out.println();
		System.out.println("-------------------------------Residence Hall Test 1------------------------------------");
		System.out.println();
		
		Student s1 = new Student("0032405708", "Naren Rachapalli", "nrachapa@purdue.edu", 2024, new Address(833, "Hillenbrand Hall", "1301 Third Street"));
		rh.signContract(s1);
		for (int i = 0; i < rh.listResidents().size(); i++) {
			System.out.println(rh.listResidents().get(i).toString());
		}
		
		System.out.println();
		System.out.println("-------------------------------Residence Hall Test 2------------------------------------");
		System.out.println();
		
		rh.cancelContract(s1.getPurdueId());
		for (int i = 0; i < rh.listResidents().size(); i++) {
			System.out.println(rh.listResidents().get(i).toString());
		}
		
		System.out.println();
		System.out.println("-------------------------------Residence Hall Test 3------------------------------------");
		System.out.println();
		
		rh.signContract(s1, 404);
		for (int i = 0; i < rh.listResidents().size(); i++) {
			System.out.println(rh.listResidents().get(i).toString());
		}
		
		System.out.println();
		System.out.println("-------------------------------Residence Hall Test 4------------------------------------");
		System.out.println();
		
		System.out.println(rh.countResidents());
		String changeFile = "/Users/rain09/Workspace/Eclipse-ws/Tester/src/PJ04/ChangeMe.txt";
		rh.saveResidentInfoToFile(changeFile);
		
		System.out.println();
		System.out.println("-------------------------------Rooms To Rent Test 1------------------------------------");
		System.out.println();
		
		String roomStructure = "/Users/rain09/Workspace/Eclipse-ws/Tester/src/PJ04/roomStructure.txt";
		String tenantInfo = "/Users/rain09/Workspace/Eclipse-ws/Tester/src/PJ04/fewApartments.txt";
		RoomsToRent rtr = new RoomsToRent(roomStructure, tenantInfo);
		
		System.out.println(Double.toString(rtr.getMonthlyCost()));
		
		System.out.println();
		System.out.println("-------------------------------Rooms To Rent Test 2------------------------------------");
		System.out.println();
		
		rtr.addTenant(s1);
		for (int i = 0; i < rtr.listTenants().size(); i++) {
			System.out.println(rtr.listTenants().get(i));
		}
		
		System.out.println();
		System.out.println("-------------------------------Rooms To Rent Test 3------------------------------------");
		System.out.println();
		
		rtr.removeTenant(s1.getPurdueId());
		for (int i = 0; i < rtr.listTenants().size(); i++) {
			System.out.println(rtr.listTenants().get(i));
		}
		
		System.out.println();
		System.out.println("-------------------------------Rooms To Rent Test 4------------------------------------");
		System.out.println();
		
		rtr.addTenant(s1, 101);
		for (int i = 0; i < rtr.listTenants().size(); i++) {
			System.out.println(rtr.listTenants().get(i));
		}
		
		System.out.println();
		System.out.println("-------------------------------Rooms To Rent Test 5------------------------------------");
		System.out.println();
		
		System.out.println("Change File!");
		rtr.saveTenantInfoToFile("/Users/rain09/Workspace/Eclipse-ws/Tester/src/PJ04/ChangeMeTwo.txt");
		
		System.out.println();
		System.out.println("-------------------------------Rooms To Rent Test 6------------------------------------");
		System.out.println();
	}

}
