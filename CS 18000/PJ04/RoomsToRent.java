package PJ04;

import java.util.*;
import java.io.*;

/**
 *
 * Project 4 - StudentNotFoundException
 * 
 * 
 * @author Naren Rachapalli, lab sec 008
 * @version October 31, 2020
 */
public class RoomsToRent extends Object {
	private ArrayList<Integer> availableRooms = new ArrayList<Integer>();
	private final String buildingName;
	private ArrayList<Student> currentTenants = new ArrayList<Student>();
	private final String streetName;

	public boolean StudentInfoFormatting(String info) {
		if (info.split(", ").length != 5) {
			return false;
		} else if (info.split(", ").length == 5) {
			try {
				Integer.parseInt(info.split(", ")[3]);
				Integer.parseInt(info.split(", ")[4]);
				Integer.parseInt(info.split(", ")[0].substring(2));
			} catch (NumberFormatException nfe) {
				return false;
			}
			if (Integer.parseInt(info.split(", ")[3]) < 2020) {
				return false;
			}
			if (info.split(", ")[1].split(" ").length != 2) {
				return false;
			}
			if (!info.split(", ")[2].contains("@")) {
				return false;
			}
		}
		return true;
	}

	public RoomsToRent(String roomStructureFileName, String tenantInfoFileName)
			throws FileNotFoundException, InvalidStudentException {
		File rsf = new File(roomStructureFileName);
		File tif = new File(tenantInfoFileName);
		FileReader rsfFileReader = new FileReader(rsf);
		FileReader tFileReader = new FileReader(tif);
		BufferedReader rsfReader = new BufferedReader(rsfFileReader);
		BufferedReader tifReader = new BufferedReader(tFileReader);
		ArrayList<String> tenantsFile = new ArrayList<String>();
		ArrayList<String> roomFile = new ArrayList<String>();
		ArrayList<Integer> roomsOccupied = new ArrayList<Integer>();

		try {
			String line = rsfReader.readLine();
			while (line != null) {
				roomFile.add(line);
				line = rsfReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String line = tifReader.readLine();
			while (line != null) {
				tenantsFile.add(line);
				line = tifReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (tenantsFile.get(0).split(", ").length != 2) {
			throw new InvalidStudentException("The information in the file is in an invalid format!");
		}
		this.buildingName = tenantsFile.get(0).split(", ")[0];
		this.streetName = tenantsFile.get(0).split(", ")[1];
		for (int i = 1; i < tenantsFile.size(); i++) {
			if (!StudentInfoFormatting(tenantsFile.get(i))) {
				throw new InvalidStudentException("Error Occurred: Improper Formatting!");
			}
			String[] student = tenantsFile.get(i).split(", ");
			String puid = student[0];
			String name = student[1];
			String email = student[2];
			int gradYear = Integer.parseInt(student[3]);
			int room = Integer.parseInt(student[4]);
			roomsOccupied.add(room);
			Student newStudent = new Student(puid, name, email, gradYear,
					new Address(room, this.buildingName, this.streetName));
			
			// Testing
			System.out.println(newStudent.toString());
			// Testing 
			
			currentTenants.add(newStudent);
		}
		for (int i = 0; i < roomFile.size(); i++) {
			for (int j = 0; j < roomFile.get(i).split(", ").length; j++) {
				try {
					Integer.parseInt(roomFile.get(i).split(", ")[j]);
				} catch (NumberFormatException nfe) {
					throw new InvalidStudentException("Error Occurred: Improper Formatting!");
				}
				boolean notAvailableRooms = false;
				for (int k = 0; k < roomsOccupied.size(); k++) {
					if (Integer.parseInt(roomFile.get(i).split(", ")[j]) == roomsOccupied.get(k)) {
						notAvailableRooms = true;
					}
				}
				if (!notAvailableRooms) {
					availableRooms.add(Integer.parseInt(roomFile.get(i).split(", ")[j]));
				}
			}
		}
	}

	public int getAvailability() {
		return availableRooms.size();
	}

	public double getMonthlyCost() {
		return 800.00;
	}
	
	public Student locateTenant(String puid) throws StudentNotFoundException {
		for (int i = 0; i < this.currentTenants.size(); i++) {
			if (this.currentTenants.get(i).getPurdueId().equals(puid)) {
				return this.currentTenants.get(i);
			}
		}
		throw new StudentNotFoundException("Error Occurred: Student doesn't exist!");
	}

	public void addTenant(Student student, int roomNo) throws OccupiedRoomException {
		for (int i = 0; i < currentTenants.size(); i++) {
			if (currentTenants.get(i).getAddress().getRoomNumber() == roomNo) {
				throw new OccupiedRoomException("Error Occurred: Room is occupied by another student!");
			}
		}
		Student newStudent = new Student(student.getPurdueId(), student.getName(), student.getEmail(),
				student.getGradYear(), new Address(roomNo, this.buildingName, this.streetName));
		currentTenants.add(newStudent);
		int roomNoIndex = -1;
		for (int j = 0; j < availableRooms.size(); j++) {
			if (availableRooms.get(j) == roomNo) {
				availableRooms.remove(j);
			}
		}
	}

	public int addTenant(Student student) throws OccupiedRoomException {
		if (availableRooms.isEmpty()) {
			throw new OccupiedRoomException("Error Occurred: All rooms are filled!");
		}
		int roomNo = availableRooms.get(0);
		Student newStudent = new Student(student.getPurdueId(), student.getName(), student.getEmail(),
				student.getGradYear(), new Address(roomNo, this.buildingName, this.streetName));
		currentTenants.add(newStudent);
		availableRooms.remove(0);
		return roomNo;
	}

	public ArrayList<Student> listTenants() {
		return currentTenants;
	}

	public int removeTenant(String purdueId) throws StudentNotFoundException {
		int roomNo = 0;
		for (int i = 0; i < currentTenants.size(); i++) {
			if (currentTenants.get(i).getPurdueId().equals(purdueId)) {
				roomNo = currentTenants.get(i).getAddress().getRoomNumber();
				availableRooms.add(roomNo);
				currentTenants.remove(i);
				return roomNo;
			}
		}
		throw new StudentNotFoundException("Error Occurred: Student not found");
	}

	public void saveTenantInfoToFile(String filename) {
		File f = new File(filename);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fos);
		ArrayList<String> residentInfo = new ArrayList<String>();
		for (int j = 0; j < currentTenants.size(); j++) {
			String info = String.format("%s, %s, %s, %d, %d", currentTenants.get(j).getPurdueId(),
					currentTenants.get(j).getName(), currentTenants.get(j).getEmail(),
					currentTenants.get(j).getGradYear(), currentTenants.get(j).getAddress().getRoomNumber());
			residentInfo.add(info);
		}
		pw.println(String.format("%s, %s", this.buildingName, this.streetName));
		for (int i = 0; i < residentInfo.size(); i++) {
			pw.println(residentInfo.get(i));
		}
		pw.flush();
	}
}
