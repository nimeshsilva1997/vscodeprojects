import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SilvaProject6 extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    int sqlNumber;
    String inputNumber;
    String partName, partDescription, partPrice = null;
    JTextField number, name, description, price;

    public SilvaProject6() throws ClassNotFoundException, SQLException {

        // Confirmation prompt
        int confirmation = JOptionPane.showOptionDialog(null,
                "Please read the compiler FIRST for instructions. If you wish to exit, press CANCEL.", "Confirmation",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (confirmation == JOptionPane.OK_OPTION) {
            // do something
            System.out.println("INSTRUCTIONS: ");
            System.out.println(
                    "- Enter 11111, 22222, or 33333 and press READ to view details for part numbers 11111, 22222, and 33333. ");
            System.out.println(
                    "- If you like, you can press update to update the price of the Cordless Drill from $35 to $250.");
            System.out.println("- Also if you like, you can press DELETE to delete the row that starts with 33333.");
            System.out.println(" * NOTE:");
            System.out.println(
                    " 	If you have already ran the program and exited eclipse, changes you made will COMMIT, ");
            System.out.println(
                    "		so the next time you load the program on eclipse, go to MySQL Workbench and drop the table.");
            System.out.println(
                    " 	If you experience any bugs or glitches (like incorrect price on Cordless Drill), go to the MySQL Workbench and redrop the table.");
            System.out.println("ENJOY!");
        } else if (confirmation == JOptionPane.CANCEL_OPTION) {

            // Close the connection
            System.out.println("Have a great day!");
            System.exit(0);
        }

        buildGUI(); // calls buildGUI method
    }

    public final void buildGUI() {

        // GUI formatting
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        getContentPane().add(panel1, "North");
        getContentPane().add(panel2, "West");
        getContentPane().add(panel3, "Center");
        getContentPane().add(panel4, "East");
        getContentPane().add(panel5, "South");

        JLabel partNumber = new JLabel("Part number ");
        panel1.add(partNumber);
        number = new JTextField("", 10);
        panel1.add(number);

        JLabel partNameLabel = new JLabel("Name ");
        panel1.add(partNameLabel);
        name = new JTextField("", 15);
        panel1.add(name);

        JLabel partNameDescription = new JLabel("Description ");
        panel2.add(partNameDescription);
        description = new JTextField("", 15);
        panel2.add(description);

        JLabel partNamePrice = new JLabel("Price");
        panel3.add(partNamePrice);
        price = new JTextField("", 15);
        panel3.add(price);

        JButton read = new JButton("Read");
        read.addActionListener(this);
        panel5.add(read);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton update = new JButton("Update");
        update.addActionListener(this);
        panel5.add(update);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton delete = new JButton("Delete");
        delete.addActionListener(this);
        panel5.add(delete);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void getPartName() throws SQLException, ClassNotFoundException { // getPartName method
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");
        // Establish a connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");

        System.out.println("Database connected");
        // Create a statement
        Statement statement = connection.createStatement();
        // Execute a statement
        ResultSet resultSet = statement.executeQuery("select * from Inventory WHERE partNumber = " + sqlNumber + ";");
        // Iterate through the result and print the student names
        while (resultSet.next()) {
            partName = resultSet.getString(2);
            partDescription = resultSet.getString(3);
            partPrice = resultSet.getString(4);
        }
        // Close the connection
        connection.close();
    }

    public void getPartUpdate() throws SQLException, ClassNotFoundException { // getPartUpdate method
        sqlNumber = 22222;
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");
        // Establish a connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
        connection.setAutoCommit(false);

        System.out.println("Database connected");
        // Create a statement
        Statement statement = connection.createStatement();
        // Execute a statement
        String command = "UPDATE Inventory" + " SET Price = '250'" + " WHERE partNumber = '22222'";
        int count = statement.executeUpdate(command);

        // statement.executeUpdate(sql);

        connection.rollback();

        // Close the connection
        connection.close();
    }

    public void getPartDelete() throws SQLException, ClassNotFoundException { // getPartDelete
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");
        // Establish a connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
        connection.setAutoCommit(false);

        System.out.println("Database connected");
        // Create a statement
        Statement statement = connection.createStatement();
        // Execute a statement
        String command = "delete from Inventory" + " WHERE description = 'York Brand'";
        int count = statement.executeUpdate(command);

        connection.rollback();
        // Close the connection
        connection.close();
    }

    @Override
    public void actionPerformed(ActionEvent action) { // actionPerformed method

        if (action.getActionCommand().equals("Read")) { // What happens if READ is selected
            System.out.println("Read complete.");
            try {
                inputNumber = number.getText();
                sqlNumber = Integer.parseInt(inputNumber);
                getPartName();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            name.setText(partName);
            description.setText(partDescription);
            price.setText(partPrice);

        } else if (action.getActionCommand().equals("Update")) { // What happens if UPDATE is selected
            try {
                inputNumber = number.getText();
                sqlNumber = Integer.parseInt(inputNumber);
                getPartUpdate();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            int inputUpdate = JOptionPane.showOptionDialog(null,
                    "WARNING!!! If you press UPDATE, the record for partNumber 22222 will be updated. Press OK if you wish to update the record."
                            + " Press Cancel if you wish to cancel",
                    "Update Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (inputUpdate == JOptionPane.OK_OPTION) {
                // do something
                int inputUpdateConfirm = JOptionPane.showOptionDialog(null,
                        "WARNING!!! Once you click OK, the price for Coordless Drill will be updated to $250 from $35. Do you wish to continue?",
                        "Update Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,
                        null);

                // If user selects UPDATE, the value of partNumber 22222 has a value of $35, and
                // will change to $250 after UPDATE is made
                if (inputUpdateConfirm == JOptionPane.OK_OPTION) {
                    // do something
                    System.out.println("Update complete.");
                    int input2 = JOptionPane.showOptionDialog(null,
                            "Update Complete!! Please enter 22222 then click READ to view the price of $250.",
                            "inputUpdateConfirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                            null, null);
                    if (input2 == JOptionPane.OK_OPTION) {
                        // do something
                    } else if (input2 == JOptionPane.CANCEL_OPTION) {
                        // Close the connection

                        System.out.println("Have a great day!");
                        System.exit(0);
                    }

                } else if (inputUpdateConfirm == JOptionPane.CANCEL_OPTION) {
                    // Close the connection

                    System.out.println("Have a great day!");
                    System.exit(0);
                }
            } else if (inputUpdate == JOptionPane.CANCEL_OPTION) {
                // Close the connection

                System.out.println("Have a great day!");
                System.exit(0);
            }

        } else if (action.getActionCommand().equals("Delete")) { // What happens if DELETE is selected
            try {
                inputNumber = number.getText();
                sqlNumber = Integer.parseInt(inputNumber);
                getPartDelete();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            int inputDelete = JOptionPane.showOptionDialog(null,
                    "WARNING! If you press DELETE, your record row for part number 33333 will be deleted. Do you wish to continue? ",
                    "Silva Project 5", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

            if (inputDelete == JOptionPane.OK_OPTION) {
                // do something

                int inputUpdateConfirm = JOptionPane.showOptionDialog(null,
                        "WARNING!!! Once you click OK, your record row for part number 33333 will be deleted. Do you wish to continue?",
                        "Update Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,
                        null);

                // If user selects DELETE, the value of partNumber 33333 row will be deleted
                if (inputUpdateConfirm == JOptionPane.OK_OPTION) {
                    // do something
                    int input2 = JOptionPane.showOptionDialog(null,
                            "Update Complete!! Please enter 33333 then click READ to view that the details for part number 33333 is gone.",
                            "inputUpdateConfirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                            null, null);
                    if (input2 == JOptionPane.OK_OPTION) {
                        // do something
                    } else if (input2 == JOptionPane.CANCEL_OPTION) {
                        // Close the connection

                        System.out.println("Have a great day!");
                        System.exit(0);
                    }
                }
                System.out.println("Delete complete.");
            } else if (inputDelete == JOptionPane.CANCEL_OPTION) {

                // Close the connection
                System.out.println("Have a great day!");
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException { // main method

        SilvaProject6 ex = new SilvaProject6();

        // the next statement correctly sizes your GUI
        ex.pack();
        ex.setVisible(true); // allows the GUI panel to be visible
    }
}
