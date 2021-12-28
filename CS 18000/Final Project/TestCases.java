import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Tests Cases for CS180 Project 5
 * Adapted from public test cases from Homework 9, used HW07 test cases for reference
 *
 */
public class TestCases {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * Tests Cases for CS180 Project 5
     * Adapted from public test cases from Homework 9
     *
     * <p>Purdue University -- CS18000 -- Fall 2020</p>
     *
     * @author Purdue CS, Tejas George
     * @version December 5, 2020
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @Test(timeout = 1000)
        public void testServerClass() {
            Class<?> clazz;
            int modifiers;
            Class<?>[] superinterfaces;
            Class<?> superclass;

            clazz = Server.class;

            modifiers = clazz.getModifiers();
            superinterfaces = clazz.getInterfaces();
            superclass = clazz.getSuperclass();


            // Tests for Class
            assertTrue("Ensure that 'Server' exists and is public", Modifier.isPublic(modifiers));
            assertEquals("Ensure that 'Server' extends Thread", superclass, Thread.class);
            assertEquals("Ensure that `Server` class does NOT implement any interfaces!",
                    superinterfaces.length, 0);
        }

        @Test(timeout = 1000)
        public void testServerFields() {
            Class<?> clazz;

            clazz = Server.class;

            try {
                assertTrue("Ensure 'accounts' is private and static",
                        Modifier.isPrivate(clazz.getDeclaredField("accounts").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("accounts").getModifiers()));
                assertTrue("Ensure 'socket' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("socket").getModifiers()));
                assertTrue("Ensure 'serverSocket' is private and static",
                        Modifier.isPrivate(clazz.getDeclaredField("serverSocket").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("serverSocket").getModifiers()));
                assertTrue("Ensure 'PORT_NUMBER' is private, static, and final",
                        Modifier.isPrivate(clazz.getDeclaredField("PORT_NUMBER").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("PORT_NUMBER").getModifiers()) &&
                                Modifier.isFinal(clazz.getDeclaredField("PORT_NUMBER").getModifiers()));
                assertTrue("Ensure 'CSV_STORAGE' is private, static, and final",
                        Modifier.isPrivate(clazz.getDeclaredField("CSV_STORAGE").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("CSV_STORAGE").getModifiers()) &&
                                Modifier.isFinal(clazz.getDeclaredField("CSV_STORAGE").getModifiers()));
                assertTrue("Ensure 'SYNCHRO' is private, static, and final",
                        Modifier.isPrivate(clazz.getDeclaredField("SYNCHRO").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("SYNCHRO").getModifiers()) &&
                                Modifier.isFinal(clazz.getDeclaredField("SYNCHRO").getModifiers()));
                assertTrue("Ensure 'numThreads' is private and static",
                        Modifier.isPrivate(clazz.getDeclaredField("numThreads").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("numThreads").getModifiers()));
                assertTrue("Ensure 'changes' is private and static",
                        Modifier.isPrivate(clazz.getDeclaredField("changes").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("changes").getModifiers()));
                assertTrue("Ensure 'threadsChanged' is private and static",
                        Modifier.isPrivate(clazz.getDeclaredField("threadsChanged").getModifiers()) &&
                                Modifier.isStatic(clazz.getDeclaredField("threadsChanged").getModifiers()));
                assertTrue("Ensure 'sent' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("sent").getModifiers()));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        @Test(timeout = 1000)
        @SuppressWarnings("unchecked")
        public void testServerMethods() {

            // Constructor
            try {
                Constructor<Server> constructor = Server.class.getConstructor(Socket.class);
                Socket s = new Socket();
                Server server = new Server(s);

                Field socket = server.getClass().getDeclaredField("socket");
                socket.setAccessible(true);

                Field sent = server.getClass().getDeclaredField("sent");
                sent.setAccessible(true);

                assertTrue("Ensure the Constructor is public", Modifier.isPublic(constructor.getModifiers()));
                assertTrue("Make sure the Constructor assigns the proper value to Socket and initializes sent",
                        socket.get(server).equals(s) && sent.get(server) == null);
            } catch (NoSuchMethodException e) {
                System.out.println("Make sure the Constructor accepts a Socket object");
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // writeToFile proper declaration
            try {
                Method writeToFile = Server.class.getDeclaredMethod("writeToFile", String.class);
                assertTrue("Ensure writeToFile is public and void",
                        Modifier.isPublic(writeToFile.getModifiers()) &&
                                writeToFile.getReturnType().equals(void.class));
            } catch (NoSuchMethodException exception) {
                exception.printStackTrace();
            }

            // readFromFile proper declaration
            try {
                Method readFromFile = Server.class.getDeclaredMethod("readFromFile", String.class);
                assertTrue("Ensure readFromFile is public, static, and void",
                        Modifier.isPublic(readFromFile.getModifiers()) &&
                                readFromFile.getReturnType().equals(void.class) &&
                                Modifier.isStatic(readFromFile.getModifiers()));
            } catch (NoSuchMethodException exception) {
                exception.printStackTrace();
            }

            // writeToFile/read from file correct input
            try {
                Server server = new Server(new Socket());
                Field accounts = server.getClass().getDeclaredField("accounts");
                accounts.setAccessible(true);

                HashMap<String, Account> map = (HashMap<String, Account>) accounts.get(server);

                server.writeToFile("tester.csv");

                Server.readFromFile("tester.csv");

                assertEquals("Make sure the readFromFile and writeToFile methods properly read/write the data",
                        accounts.get(server), map);
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // writeToFile incorrect input
            try {
                Server server = new Server(new Socket());
                Field accounts = server.getClass().getDeclaredField("accounts");
                accounts.setAccessible(true);

                server.writeToFile("test");

                assertTrue("Make sure the writeToFile method verifies filename",
                        getOutput().startsWith("Invalid filename!"));
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            }

            // readFromFile incorrect input
            try {
                Server server = new Server(new Socket());
                Field accounts = server.getClass().getDeclaredField("accounts");
                accounts.setAccessible(true);

                Server.readFromFile("test");

                assertTrue("Make sure the readFromFile method verifies filename",
                        getOutput().startsWith("Invalid filename!"));
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            }

            // editAccounts proper declaration
            try {
                Method editAccounts = Server.class.getDeclaredMethod("editAccounts", Account.class);
                assertTrue("Ensure editAccounts is public, synchronized, and void",
                        Modifier.isPublic(editAccounts.getModifiers()) &&
                                editAccounts.getReturnType().equals(void.class) &&
                                Modifier.isSynchronized(editAccounts.getModifiers()));
            } catch (NoSuchMethodException exception) {
                exception.printStackTrace();
            }

            // editAccounts proper input - deletion
            try {
                Server server = new Server(new Socket());
                Field accounts = server.getClass().getDeclaredField("accounts");
                accounts.setAccessible(true);

                Server.readFromFile("test.csv");

                HashMap<String, Account> map = (HashMap<String, Account>) accounts.get(server);
                map.remove("account2");

                Account delete = new Account("account2", "pass2");

                server.editAccounts(delete);

                assertEquals("Make sure the editAccounts method properly deletes an Account",
                        accounts.get(server), map);
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // editAccounts proper input - modify
            try {
                Server server = new Server(new Socket());
                Field accounts = server.getClass().getDeclaredField("accounts");
                accounts.setAccessible(true);

                Server.readFromFile("test.csv");

                HashMap<String, Account> map = (HashMap<String, Account>) accounts.get(server);
                map.remove("account2");

                Account modify = new Account("account2", "modified password");
                map.put(modify.getUserName(), modify);

                server.editAccounts(modify);

                assertEquals("Make sure the editAccounts method properly edits an Account",
                        accounts.get(server), map);
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // editAccounts proper input - add
            try {
                Server server = new Server(new Socket());
                Field accounts = server.getClass().getDeclaredField("accounts");
                accounts.setAccessible(true);

                Server.readFromFile("test.csv");

                HashMap<String, Account> map = (HashMap<String, Account>) accounts.get(server);

                Account add = new Account("account2", "modified password");
                map.put(add.getUserName(), add);

                server.editAccounts(add);

                assertEquals("Make sure the editAccounts method properly adds an Account",
                        accounts.get(server), map);
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // clientConnected proper declaration
            try {
                Method clientConnected = Server.class.getDeclaredMethod("clientConnected");
                assertTrue("Ensure clientConnected is private, synchronized, and void",
                        Modifier.isPrivate(clientConnected.getModifiers()) &&
                                clientConnected.getReturnType().equals(void.class) &&
                                Modifier.isSynchronized(clientConnected.getModifiers()));
            } catch (NoSuchMethodException exception) {
                exception.printStackTrace();
            }

            // clientConnected expected output
            try {
                Server server = new Server(new Socket());
                Field numThreads = server.getClass().getDeclaredField("numThreads");
                numThreads.setAccessible(true);
                Method connected = Server.class.getDeclaredMethod("clientConnected");
                connected.setAccessible(true);

                int first = (int) numThreads.get(server);
                connected.invoke(server);

                assertEquals("Verify clientConnected functions as expected",
                        ++first, (int) numThreads.get(server));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // clientDisconnected proper declaration
            try {
                Method clientDisconnected = Server.class.getDeclaredMethod("clientDisconnected");
                assertTrue("Ensure clientDisconnected is private, synchronized, and void",
                        Modifier.isPrivate(clientDisconnected.getModifiers()) &&
                                clientDisconnected.getReturnType().equals(void.class) &&
                                Modifier.isSynchronized(clientDisconnected.getModifiers()));
            } catch (NoSuchMethodException exception) {
                exception.printStackTrace();
            }

            // clientDisconnected expected output
            try {
                Server server = new Server(new Socket());
                Field numThreads = server.getClass().getDeclaredField("numThreads");
                numThreads.setAccessible(true);
                Method connected = Server.class.getDeclaredMethod("clientDisconnected");
                connected.setAccessible(true);

                int first = (int) numThreads.get(server);
                connected.invoke(server);

                assertEquals("Verify clientDisconnected functions as expected",
                        --first, (int) numThreads.get(server));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Test(timeout = 1000)
        public void testClientClass() {
            Class<?> clazz;
            int modifiers;
            Class<?>[] superinterfaces;
            Class<?> superclass;

            clazz = Client.class;

            modifiers = clazz.getModifiers();
            superinterfaces = clazz.getInterfaces();
            superclass = clazz.getSuperclass();


            // Tests for Class
            assertTrue("Ensure that 'Client' exists and is public", Modifier.isPublic(modifiers));
            assertEquals("Ensure that 'Client' extends JFrame", superclass, JFrame.class);
            assertEquals("Ensure that `Client` class implements Runnable", superinterfaces[0], Runnable.class);
            assertEquals("Ensure that `Client` class implements Runnable", superinterfaces.length, 1);
        }

        @Test(timeout = 1000)   // only one field in Client has any modifier
        public void testClientFields() {
            Class<?> clazz = Client.class;

            try {
                assertTrue("Ensure accounts is private",
                        Modifier.isPrivate(clazz.getDeclaredField("accounts").getModifiers()));
                assertEquals("Ensure socket exists",
                        clazz.getDeclaredField("socket").getModifiers(), 0);
                assertEquals("Ensure outputStream exists",
                        clazz.getDeclaredField("outputStream").getModifiers(), 0);
                assertEquals("Ensure inputStream exists",
                        clazz.getDeclaredField("inputStream").getModifiers(), 0);
                assertEquals("Ensure connected exists",
                        clazz.getDeclaredField("connected").getModifiers(), 0);
                assertTrue("Ensure signedInAccount is private",
                        Modifier.isPrivate(clazz.getDeclaredField("signedInAccount").getModifiers()));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        @Test(timeout = 1000)
        public void testClientMethods() {
            // Constructor and connectToServer - connectToServer does all the Constructor's work?
            try {
                Constructor<Client> constructor = Client.class.getConstructor(String.class, int.class);
                Client client = new Client("localhost", 4242);
                Method connectToServer = Client.class.getDeclaredMethod("connectToServer", String.class, int.class);

                Field socket = client.getClass().getDeclaredField("socket");
                socket.setAccessible(true);
                Field connected = client.getClass().getDeclaredField("connected");
                connected.setAccessible(true);
                Field outputStream = client.getClass().getDeclaredField("outputStream");
                outputStream.setAccessible(true);
                Field inputStream = client.getClass().getDeclaredField("inputStream");
                inputStream.setAccessible(true);

                assertTrue("Ensure the Constructor is public", Modifier.isPublic(constructor.getModifiers()));
                assertTrue("Ensure connectToServer is public and void",
                        Modifier.isPublic(connectToServer.getModifiers()) &&
                                connectToServer.getReturnType().equals(void.class));
            } catch (NoSuchMethodException e) {
                System.out.println("Make sure the Constructor accepts a Socket object");
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // sendObject
            try {
                Method sendObject = Client.class.getDeclaredMethod("sendObject", Object.class);

                assertTrue("Ensure sendObject is public and void",
                        Modifier.isPublic(sendObject.getModifiers()) &&
                                sendObject.getReturnType().equals(void.class));
            } catch (NoSuchMethodException exception) {
                System.out.println("Ensure all methods are declared");
            }

            // sendAccount
            try {
                Method sendAccount = Client.class.getDeclaredMethod("sendAccount", Account.class);

                assertTrue("Ensure sendAccount is public and void",
                        Modifier.isPublic(sendAccount.getModifiers()) &&
                                sendAccount.getReturnType().equals(void.class));
            } catch (NoSuchMethodException exception) {
                System.out.println("Ensure all methods are declared");
            }

            // receiveObject
            try {
                Method receiveObject = Client.class.getDeclaredMethod("receiveObject");

                assertTrue("Ensure receiveObject is public and void",
                        Modifier.isPublic(receiveObject.getModifiers()));
            } catch (NoSuchMethodException exception) {
                System.out.println("Ensure all methods are declared");
            }

            // receiveAccount
            try {
                Method receiveAccount = Client.class.getDeclaredMethod("receiveAccount");

                assertTrue("Ensure receiveAccount is public and void",
                        Modifier.isPublic(receiveAccount.getModifiers()));
            } catch (NoSuchMethodException exception) {
                System.out.println("Ensure all methods are declared");
            }

            // updateAccounts
            try {
                Method updateAccounts = Client.class.getDeclaredMethod("updateAccounts");

                assertTrue("Ensure updateAccounts is public",
                        Modifier.isPublic(updateAccounts.getModifiers()) &&
                                updateAccounts.getReturnType().equals(void.class));
            } catch (NoSuchMethodException exception) {
                System.out.println("Ensure all methods are declared");
            }

            // endConnection
            try {
                Method endConnection = Client.class.getDeclaredMethod("endConnection");

                assertTrue("Ensure endConnection is public and void",
                        Modifier.isPublic(endConnection.getModifiers()) &&
                                endConnection.getReturnType().equals(void.class));
            } catch (NoSuchMethodException exception) {
                System.out.println("Ensure all methods are declared");
            }
        }

        @Test(timeout = 1000)
        public void testAccountClass() {
            Class<?> clazz;
            int modifiers;
            Class<?>[] superinterfaces;
            Class<?> superclass;

            clazz = Account.class;

            modifiers = clazz.getModifiers();
            superinterfaces = clazz.getInterfaces();
            superclass = clazz.getSuperclass();


            // Tests for Class
            assertTrue("Ensure that `Account` class is public!", Modifier.isPublic(modifiers));
            assertFalse("Ensure that `Account` class is NOT abstract!", Modifier.isAbstract(modifiers));
            assertEquals("Ensure that `Account` class implements the Serializable interface!",
                    superinterfaces.length, 1);
            assertEquals("Ensure that `Account` class implements the Serializable interface!",
                    superinterfaces[0], Serializable.class);
            assertEquals("Ensure  that `Account` class does NOT extend any other class!",
                    superclass, Object.class);
        }

        @Test(timeout = 1000)
        public void testAccountFields() {
            Class<?> clazz = Account.class;

            try {
                assertTrue("Ensure 'userName' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("userName").getModifiers()));
                assertTrue("Ensure 'password' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("password").getModifiers()));
                assertTrue("Ensure 'profileList' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("profileList").getModifiers()));
                assertTrue("Ensure 'accountList' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("accountList").getModifiers()));
            } catch (NoSuchFieldException e) {
                System.out.println("Ensure all fields were declared");
            }
        }

        @Test(timeout = 1000)
        @SuppressWarnings("unchecked")
        public void testAccountMethods() {
            // Constructor
            try {
                Constructor<Account> constructor = Account.class.getConstructor(String.class, String.class);
                Account account = new Account("user", "pass");

                Field user = account.getClass().getDeclaredField("userName");
                user.setAccessible(true);
                Field pass = account.getClass().getDeclaredField("password");
                pass.setAccessible(true);

                assertTrue("Ensure the Constructor is public", Modifier.isPublic(constructor.getModifiers()));
                assertTrue("Make sure the Constructor assigns the proper value to username and password",
                        user.get(account).equals("user") && pass.get(account).equals("pass"));
            } catch (NoSuchMethodException e) {
                System.out.println("Make sure the Constructor accepts a userName and Password String");
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // getUserName
            try {
                Method getUserName = Account.class.getDeclaredMethod("getUserName");
                Account account = new Account("user", "pass");

                Field user = account.getClass().getDeclaredField("userName");
                user.setAccessible(true);

                assertTrue("Ensure getUserName is public and returns String",
                        Modifier.isPublic(getUserName.getModifiers()) &&
                                getUserName.getReturnType().equals(String.class));
                assertEquals("Ensure getUserName returns the username", user.get(account), account.getUserName());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getPassword
            try {
                Method getPassword = Account.class.getDeclaredMethod("getPassword");
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("password");
                field.setAccessible(true);

                assertTrue("Ensure getPassword is public and returns String",
                        Modifier.isPublic(getPassword.getModifiers()) &&
                                getPassword.getReturnType().equals(String.class));
                assertEquals("Ensure getPassword returns the password", field.get(account), account.getPassword());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getAccountList
            try {
                Method getAccountList = Account.class.getDeclaredMethod("getAccountList");
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("accountList");
                field.setAccessible(true);

                assertTrue("Ensure getAccountList is public and returns ArrayList",
                        Modifier.isPublic(getAccountList.getModifiers()) &&
                                getAccountList.getReturnType().equals(ArrayList.class));
                assertEquals("Ensure getAccountList returns the accountList",
                        field.get(account), account.getAccountList());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getProfileList
            try {
                Method getProfileList = Account.class.getDeclaredMethod("getProfileList");
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("profileList");
                field.setAccessible(true);

                assertTrue("Ensure getProfileList is public and returns ArrayList",
                        Modifier.isPublic(getProfileList.getModifiers()) &&
                                getProfileList.getReturnType().equals(ArrayList.class));
                assertEquals("Ensure getAccountList returns the profileList",
                        field.get(account), account.getProfileList());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setUserName
            try {
                Method setUserName = Account.class.getDeclaredMethod("setUserName", String.class);
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("userName");
                field.setAccessible(true);

                account.setUserName("newUsername");

                assertTrue("Ensure setUserName is public and void",
                        Modifier.isPublic(setUserName.getModifiers()) &&
                                setUserName.getReturnType().equals(void.class));
                assertEquals("Ensure setUserName sets the userName properly",
                        field.get(account), "newUsername");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setPassword
            try {
                Method setPassword = Account.class.getDeclaredMethod("setPassword", String.class);
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("password");
                field.setAccessible(true);

                account.setPassword("newPassword");

                assertTrue("Ensure setPassword is public and void",
                        Modifier.isPublic(setPassword.getModifiers()) &&
                                setPassword.getReturnType().equals(void.class));
                assertEquals("Ensure setPassword sets the password properly",
                        field.get(account), "newPassword");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // addNewProfile
            try {
                Method addNewProfile = Account.class.getDeclaredMethod("addNewProfile", Profile.class);
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("profileList");
                field.setAccessible(true);

                Profile profile = new Profile("prof", "pass", "con", "int", "abo");

                account.addNewProfile(profile);

                assertTrue("Ensure addNewProfile is public and void",
                        Modifier.isPublic(addNewProfile.getModifiers()) &&
                                addNewProfile.getReturnType().equals(void.class));
                assertEquals("Ensure addNewProfile adds the profile properly",
                        ((ArrayList<Profile>) field.get(account)).get(0), profile);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // deleteProfile
            try {
                Method deleteProfile = Account.class.getDeclaredMethod("deleteProfile", Profile.class);
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("profileList");
                field.setAccessible(true);

                Profile profile = new Profile("prof", "pass", "con", "int", "abo");

                account.addNewProfile(profile);

                account.deleteProfile(profile);

                assertTrue("Ensure deleteProfile is public and void",
                        Modifier.isPublic(deleteProfile.getModifiers()) &&
                                deleteProfile.getReturnType().equals(void.class));
                assertEquals("Ensure deleteProfile removes the profile properly",
                        ((ArrayList<Profile>) field.get(account)).size(), 0);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // addNewAccount
            try {
                Method addNewAccount = Account.class.getDeclaredMethod("addNewAccount", Account.class);
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("accountList");
                field.setAccessible(true);

                Account account1 = new Account("user", "pass");

                account.addNewAccount(account1);

                assertTrue("Ensure addNewProfile is public and void",
                        Modifier.isPublic(addNewAccount.getModifiers()) &&
                                addNewAccount.getReturnType().equals(void.class));
                assertEquals("Ensure addNewAccount adds the account properly",
                        ((ArrayList<Account>) field.get(account)).get(0), account1);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // deleteAccount
            try {
                Method deleteAccount = Account.class.getDeclaredMethod("deleteAccount", Account.class);
                Account account = new Account("user", "pass");

                Field field = account.getClass().getDeclaredField("accountList");
                field.setAccessible(true);

                Account account1 = new Account("user", "pass");

                account.addNewAccount(account1);

                account.deleteAccount(account1);

                assertTrue("Ensure deleteAccount is public and void",
                        Modifier.isPublic(deleteAccount.getModifiers()) &&
                                deleteAccount.getReturnType().equals(void.class));
                assertEquals("Ensure deleteAccount removes the account properly",
                        ((ArrayList<Account>) field.get(account)).size(), 0);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // toCSV
            try {
                Method toCSV = Account.class.getDeclaredMethod("toCSV");
                Account account = new Account("user", "pass");

                assertTrue("Ensure toCSV is public and returns String",
                        Modifier.isPublic(toCSV.getModifiers()) &&
                                toCSV.getReturnType().equals(String.class));
                assertEquals("Ensure toCSV returns the csv formatted Account", "user,pass", account.toCSV());
            } catch (NoSuchMethodException exception) {
                exception.printStackTrace();
            }
        }

        @Test(timeout = 1000)
        public void testProfileClass() {
            Class<?> clazz;
            int modifiers;
            Class<?>[] superinterfaces;
            Class<?> superclass;

            clazz = Profile.class;

            modifiers = clazz.getModifiers();
            superinterfaces = clazz.getInterfaces();
            superclass = clazz.getSuperclass();


            // Tests for Class
            assertTrue("Ensure that `Profile` class is public!", Modifier.isPublic(modifiers));
            assertFalse("Ensure that `Profile` class is NOT abstract!", Modifier.isAbstract(modifiers));
            assertEquals("Ensure that `Profile` class implements the Serializable interface!",
                    superinterfaces.length, 1);
            assertEquals("Ensure that `Profile` class implements the Serializable interface!",
                    superinterfaces[0], Serializable.class);
            assertEquals("Ensure  that `Profile` class does NOT extend any other class!", superclass, Object.class);
        }

        @Test(timeout = 1000)
        public void testProfileFields() {
            Class<?> clazz = Profile.class;

            try {
                assertTrue("Ensure 'userName' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("userName").getModifiers()));
                assertTrue("Ensure 'password' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("password").getModifiers()));
                assertTrue("Ensure 'isOnline' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("isOnline").getModifiers()));
                assertTrue("Ensure 'friendRequests' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("friendRequests").getModifiers()));
                assertTrue("Ensure 'contactInfo' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("contactInfo").getModifiers()));
                assertTrue("Ensure 'interests' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("interests").getModifiers()));
                assertTrue("Ensure 'aboutMe' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("aboutMe").getModifiers()));
                assertTrue("Ensure 'friendsList' is private",
                        Modifier.isPrivate(clazz.getDeclaredField("friendsList").getModifiers()));
            } catch (NoSuchFieldException e) {
                System.out.println("Ensure all fields were declared");
            }
        }

        @Test(timeout = 1000)
        @SuppressWarnings("unchecked")
        public void testProfileMethods() {
            // Constructor
            try {
                Constructor<Profile> constructor;
                constructor = Profile.class.getConstructor(String.class, String.class,
                        String.class, String.class, String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field user = profile.getClass().getDeclaredField("userName");
                user.setAccessible(true);
                Field pass = profile.getClass().getDeclaredField("password");
                pass.setAccessible(true);
                Field cont = profile.getClass().getDeclaredField("contactInfo");
                cont.setAccessible(true);
                Field inte = profile.getClass().getDeclaredField("interests");
                inte.setAccessible(true);
                Field abou = profile.getClass().getDeclaredField("aboutMe");
                abou.setAccessible(true);

                assertTrue("Ensure the Constructor is public", Modifier.isPublic(constructor.getModifiers()));
                assertTrue("Make sure the Constructor assigns the proper value to all variables",
                        user.get(profile).equals("user") && pass.get(profile).equals("pass") &&
                                cont.get(profile).equals("con") && inte.get(profile).equals("int") &&
                                abou.get(profile).equals("about"));
            } catch (NoSuchMethodException e) {
                System.out.println("Make sure the Constructor accepts a userName, Password, contactInfo, " +
                        "interests, and aboutMe String");
            } catch (NoSuchFieldException e) {
                System.out.println("Make sure the fields are properly declared");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // getUserName
            try {
                Method getUserName = Profile.class.getDeclaredMethod("getUserName");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field user = profile.getClass().getDeclaredField("userName");
                user.setAccessible(true);

                assertTrue("Ensure getUserName is public and returns String",
                        Modifier.isPublic(getUserName.getModifiers()) &&
                                getUserName.getReturnType().equals(String.class));
                assertEquals("Ensure getUserName returns the username", user.get(profile), profile.getUserName());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getPassword
            try {
                Method getPassword = Profile.class.getDeclaredMethod("getPassword");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("password");
                field.setAccessible(true);

                assertTrue("Ensure getPassword is public and returns String",
                        Modifier.isPublic(getPassword.getModifiers()) &&
                                getPassword.getReturnType().equals(String.class));
                assertEquals("Ensure getPassword returns the password", field.get(profile), profile.getPassword());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getFriendRequests
            try {
                Method getFriendRequests = Profile.class.getDeclaredMethod("getFriendRequests");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("friendRequests");
                field.setAccessible(true);

                assertTrue("Ensure getFriendRequests is public and returns boolean",
                        Modifier.isPublic(getFriendRequests.getModifiers()) &&
                                getFriendRequests.getReturnType().equals(boolean.class));
                assertEquals("Ensure getFriendRequests returns the friend request T/F",
                        field.get(profile), profile.getFriendRequests());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getContactInfo
            try {
                Method getContactInfo = Profile.class.getDeclaredMethod("getContactInfo");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("contactInfo");
                field.setAccessible(true);

                assertTrue("Ensure getContactInfo is public and returns String",
                        Modifier.isPublic(getContactInfo.getModifiers()) &&
                                getContactInfo.getReturnType().equals(String.class));
                assertEquals("Ensure getFriendRequests returns contactInfo",
                        field.get(profile), profile.getContactInfo());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getInterests
            try {
                Method getInterests = Profile.class.getDeclaredMethod("getInterests");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("interests");
                field.setAccessible(true);

                assertTrue("Ensure getInterests is public and returns String",
                        Modifier.isPublic(getInterests.getModifiers()) &&
                                getInterests.getReturnType().equals(String.class));
                assertEquals("Ensure getInterests returns interests",
                        field.get(profile), profile.getInterests());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // aboutMe
            try {
                Method aboutMe = Profile.class.getDeclaredMethod("aboutMe");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("aboutMe");
                field.setAccessible(true);

                assertTrue("Ensure aboutMe is public and returns String",
                        Modifier.isPublic(aboutMe.getModifiers()) &&
                                aboutMe.getReturnType().equals(String.class));
                assertEquals("Ensure aboutMe returns aboutMe",
                        field.get(profile), profile.aboutMe());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getOnlineStatus
            try {
                Method getOnlineStatus = Profile.class.getDeclaredMethod("getOnlineStatus");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("isOnline");
                field.setAccessible(true);

                assertTrue("Ensure getOnlineStatus is public and returns boolean",
                        Modifier.isPublic(getOnlineStatus.getModifiers()) &&
                                getOnlineStatus.getReturnType().equals(boolean.class));
                assertEquals("Ensure getOnlineStatus returns online T/F",
                        field.get(profile), profile.getOnlineStatus());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // getFriendsList
            try {
                Method getFriendsList = Profile.class.getDeclaredMethod("getFriendsList");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("friendsList");
                field.setAccessible(true);

                assertTrue("Ensure getFriendsList is public and returns ArrayList",
                        Modifier.isPublic(getFriendsList.getModifiers()) &&
                                getFriendsList.getReturnType().equals(ArrayList.class));
                assertEquals("Ensure getFriendsList returns friendsList",
                        field.get(profile), profile.getFriendsList());
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setUserName
            try {
                Method setUserName = Profile.class.getDeclaredMethod("setUserName", String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("userName");
                field.setAccessible(true);

                profile.setUserName("newUsername");

                assertTrue("Ensure setUserName is public and void",
                        Modifier.isPublic(setUserName.getModifiers()) &&
                                setUserName.getReturnType().equals(void.class));
                assertEquals("Ensure setUserName sets the userName properly",
                        field.get(profile), "newUsername");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setPassword
            try {
                Method setPassword = Profile.class.getDeclaredMethod("setPassword", String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("password");
                field.setAccessible(true);

                profile.setPassword("newPassword");

                assertTrue("Ensure setPassword is public and void",
                        Modifier.isPublic(setPassword.getModifiers()) &&
                                setPassword.getReturnType().equals(void.class));
                assertEquals("Ensure setPassword sets the password properly",
                        field.get(profile), "newPassword");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setFriendRequest
            try {
                Method setFriendRequest = Profile.class.getDeclaredMethod("setFriendRequest", boolean.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("friendRequests");
                field.setAccessible(true);

                profile.setFriendRequest(true);

                assertTrue("Ensure setFriendRequest is public and void",
                        Modifier.isPublic(setFriendRequest.getModifiers()) &&
                                setFriendRequest.getReturnType().equals(void.class));
                assertEquals("Ensure setFriendRequest sets the friendRequests properly",
                        field.get(profile), true);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setContactInfo
            try {
                Method setContactInfo = Profile.class.getDeclaredMethod("setContactInfo", String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("contactInfo");
                field.setAccessible(true);

                profile.setContactInfo("newCon");

                assertTrue("Ensure setContactInfo is public and void",
                        Modifier.isPublic(setContactInfo.getModifiers()) &&
                                setContactInfo.getReturnType().equals(void.class));
                assertEquals("Ensure setContactInfo sets the contactInfo properly",
                        field.get(profile), "newCon");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setInterests
            try {
                Method setInterests = Profile.class.getDeclaredMethod("setInterests", String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("interests");
                field.setAccessible(true);

                profile.setInterests("newInt");

                assertTrue("Ensure setInterests is public and void",
                        Modifier.isPublic(setInterests.getModifiers()) &&
                                setInterests.getReturnType().equals(void.class));
                assertEquals("Ensure setInterests sets the interests properly",
                        field.get(profile), "newInt");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setAboutMe
            try {
                Method setAboutMe = Profile.class.getDeclaredMethod("setAboutMe", String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("aboutMe");
                field.setAccessible(true);

                profile.setAboutMe("newAbout");

                assertTrue("Ensure setAboutMe is public and void",
                        Modifier.isPublic(setAboutMe.getModifiers()) &&
                                setAboutMe.getReturnType().equals(void.class));
                assertEquals("Ensure setAboutMe sets the aboutMe properly",
                        field.get(profile), "newAbout");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // setOnlineStatus
            try {
                Method setOnlineStatus = Profile.class.getDeclaredMethod("setOnlineStatus", boolean.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("isOnline");
                field.setAccessible(true);

                profile.setOnlineStatus(true);

                assertTrue("Ensure setOnlineStatus is public and void",
                        Modifier.isPublic(setOnlineStatus.getModifiers()) &&
                                setOnlineStatus.getReturnType().equals(void.class));
                assertEquals("Ensure setOnlineStatus sets the isOnline properly",
                        field.get(profile), true);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // addFriend
            try {
                Method addFriend = Profile.class.getDeclaredMethod("addFriend", String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("friendsList");
                field.setAccessible(true);

                profile.addFriend("friend");

                assertTrue("Ensure addFriend is public and void",
                        Modifier.isPublic(addFriend.getModifiers()) &&
                                addFriend.getReturnType().equals(void.class));
                assertEquals("Ensure addFriend adds the friend properly",
                        ((ArrayList<String>) field.get(profile)).get(0), "friend");
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // removeFriend
            try {
                Method removeFriend = Profile.class.getDeclaredMethod("removeFriend", String.class);
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                Field field = profile.getClass().getDeclaredField("friendsList");
                field.setAccessible(true);

                profile.addFriend("friend");

                profile.removeFriend("friend");

                assertTrue("Ensure removeFriend is public and void",
                        Modifier.isPublic(removeFriend.getModifiers()) &&
                                removeFriend.getReturnType().equals(void.class));
                assertEquals("Ensure removeFriend removes the friend properly",
                        ((ArrayList<String>) field.get(profile)).size(), 0);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            // toCSV
            try {
                Method toCSV = Profile.class.getDeclaredMethod("toCSV");
                Profile profile = new Profile("user", "pass",
                        "con", "int", "about");

                assertTrue("Ensure toCSV is public and returns String",
                        Modifier.isPublic(toCSV.getModifiers()) &&
                                toCSV.getReturnType().equals(String.class));
                assertEquals("Ensure toCSV returns the csv formatted Account",
                        "user,pass,con,int,about,false,[]", profile.toCSV());
            } catch (NoSuchMethodException exception) {
                exception.printStackTrace();
            }
        }
    }
}
