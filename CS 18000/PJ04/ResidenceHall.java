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
public class ResidenceHall extends Object implements Leasable {
	private ArrayList<Student> currentResidents = new ArrayList<Student>();
	private ArrayList<Integer> availableDorms = new ArrayList<Integer>();
	private final String residentHallName;
	private final String streetName;

	public boolean isStudentInfoFormatting(String info) {
		try {
			Integer.parseInt(info.substring(0, info.indexOf(":")));
		} catch (NumberFormatException nfe) {
			return false;
		}
		if (!info.contains(":")) {
			return false;
		}
		String studentInfo = info.substring(info.indexOf(":") + 2);
		if (studentInfo.split(", ").length != 4) {
			return false;
		} else if (studentInfo.split(", ").length == 4) {
			try {
				Integer.parseInt(studentInfo.split(", ")[3]);
				Integer.parseInt(studentInfo.split(", ")[0].substring(2));
			} catch (NumberFormatException nfe) {
				return false;
			}
			if (Integer.parseInt(studentInfo.split(", ")[3]) < 2020) {
				return false;
			}
			if (studentInfo.split(", ")[1].split(" ").length != 2) {
				return false;
			}
			if (!studentInfo.split(", ")[2].contains("@")) {
				return false;
			}
		}
		return true;
	}

	public ResidenceHall(String filename) throws FileNotFoundException, InvalidStudentException {
		File f = new File(filename);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lines = new ArrayList<String>();
		try {
			String line = br.readLine();
			while (line != null) {
				lines.add(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Testing
		/*
		 * for (int i = 0; i < lines.size(); i++) { System.out.println(lines.get(i)); }
		 * System.out.println("Finished printing list!");
		 */
		// Testing
		
		if (lines.get(0).split(", ").length != 2) {
			throw new InvalidStudentException("The information in the file is in an invalid format!");
		}
		this.residentHallName = lines.get(0).split(", ")[0];
		this.streetName = lines.get(0).split(", ")[1];
		
		// Testing
		//System.out.println(this.residentHallName + " | " + this.streetName);
		// Testing
		
		for (int i = 1; i < lines.size(); i++) {
			
			// Testing 
			//System.out.println("Testing line: \n" + lines.get(i));
			// Testing
			
			if (lines.get(i).contains("Unknown")) {
				availableDorms.add(Integer.parseInt(lines.get(i).substring(0, lines.get(i).indexOf(":"))));
				
				// Testing
				//System.out.println(Integer.parseInt(lines.get(i).substring(0, lines.get(i).indexOf(":"))));
				// Testing
				
				continue;
			}
			if (!isStudentInfoFormatting(lines.get(i))) {
				throw new InvalidStudentException("The information in the file is in an invalid format!");
			} else {
				String studentInfo = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
				int studentRoom = Integer.parseInt(lines.get(i).substring(0, lines.get(i).indexOf(":")));
				Address newAddress = new Address(studentRoom, this.residentHallName, this.streetName);
				Student newStudent = new Student(studentInfo.split(", ")[0], studentInfo.split(", ")[1],
						studentInfo.split(", ")[2], Integer.parseInt(studentInfo.split(", ")[3]), newAddress);
				currentResidents.add(newStudent);
			}

		}
	}

	public int countResidents() {
		int numOfResidents = currentResidents.size();
		return numOfResidents;
	}

	public double getContractCost() {
		return 4860.00;
	}

	public int signContract(Student student) throws OccupiedRoomException {
		int roomNo = 0;
		if (availableDorms.isEmpty()) {
			throw new OccupiedRoomException("Error Occurred: There are no available rooms");
		}
		roomNo = availableDorms.get(0);
		Address newAddress = new Address(roomNo, this.residentHallName, this.streetName);
		Student newStudent = new Student(student.getPurdueId(), student.getName(), student.getEmail(),
				student.getGradYear(), newAddress);
		currentResidents.add(newStudent);
		availableDorms.remove(0);
		return roomNo;
	}

	public void signContract(Student student, int roomNo) throws OccupiedRoomException {
		boolean roomAvailability = false;
		if (availableDorms.isEmpty()) {
			throw new OccupiedRoomException("Error Occurred: Room Specified is not available");
		}
		for (int i = 0; i < availableDorms.size(); i++) {
			if (availableDorms.get(i) == roomNo) {
				roomAvailability = true;
				availableDorms.remove(i);
				Address newAddress = new Address(roomNo, this.residentHallName, this.streetName);
				Student newStudent = new Student(student.getPurdueId(), student.getName(), student.getEmail(),
						student.getGradYear(), newAddress);
				currentResidents.add(newStudent);
				return;
			}
			
		}
		if (!roomAvailability) {
			throw new OccupiedRoomException("Error Occurred: Room Specified is not available");
		}
		
	}

	public Student viewResident(int roomNo) throws StudentNotFoundException {
		for (int i = 0; i < currentResidents.size(); i++) {
			if (currentResidents.get(i).getAddress().getRoomNumber() == roomNo) {
				return currentResidents.get(i);
			}
		}
		throw new StudentNotFoundException("Error Occurred: No resident lives in " + Integer.toString(roomNo) + "!");
	}

	public int countFloormates(int floor) {
		int count = 0;
		String floorNo = Integer.toString(floor);
		for (int i = 0; i < currentResidents.size(); i++) {
			String roomNo = Integer.toString(currentResidents.get(i).getAddress().getRoomNumber());
			if (roomNo.substring(0, 1).equalsIgnoreCase(floorNo)) {
				count++;
			}
		}
		return count;
	}

	public ArrayList<Student> listResidents() {
		return currentResidents;
	}

	public int cancelContract(String purdueId) throws StudentNotFoundException {
		int roomNo = 0;
		for (int i = 0; i < currentResidents.size(); i++) {
			if (currentResidents.get(i).getPurdueId().equals(purdueId)) {
				roomNo = currentResidents.get(i).getAddress().getRoomNumber();
				availableDorms.add(roomNo);
				currentResidents.remove(i);
				return roomNo;
			}
		}
		throw new StudentNotFoundException("Error Occurred: Student not found");
	}

	public void saveResidentInfoToFile(String filename) {
		File f = new File(filename);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fos);

		int[] rooms = new int[countResidents() + availableDorms.size()];
		for (int i = 0; i < currentResidents.size(); i++) {
			rooms[i] = currentResidents.get(i).getAddress().getRoomNumber();
		}
		for (int i = countResidents(); i < (availableDorms.size() + countResidents()); i++) {
			rooms[i] = availableDorms.get(i - countResidents());
		}
		Arrays.sort(rooms);

		ArrayList<String> residentInfo = new ArrayList<String>();
		for (int i = 0; i < rooms.length; i++) {
			boolean roomFound = false;
			for (int j = 0; j < availableDorms.size(); j++) {
				if (rooms[i] == availableDorms.get(j)) {
					roomFound = true;
					String info = String.format("%d: Unknown", rooms[i]);
					residentInfo.add(info);
					break;
				}
			}
			if (!roomFound) {
				for (int j = 0; j < currentResidents.size(); j++) {
					if (rooms[i] == currentResidents.get(j).getAddress().getRoomNumber()) {
						roomFound = true;
						String info = String.format("%d: %s, %s, %s, %d", rooms[i],
								currentResidents.get(j).getPurdueId(), currentResidents.get(j).getName(),
								currentResidents.get(j).getEmail(), currentResidents.get(j).getGradYear());
						residentInfo.add(info);
						break;
					}
				}
			}
		}
		

		pw.write(String.format("%s, %s\n", this.residentHallName, this.streetName));
		for (int i = 0; i < residentInfo.size(); i++) {
			
			//Testing
			System.out.println(residentInfo.get(i));
			//Testing
			
			pw.write(residentInfo.get(i) + "\n");
		}
		pw.flush();
	}
}
