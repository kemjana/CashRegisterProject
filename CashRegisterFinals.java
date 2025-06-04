import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashRegisterFinals {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<String[]> users = new ArrayList<>();
    static String currentUser = "";

    // Signup prompt ---------------------------------------------------------------------------
    public static void signup() {
        System.out.println("=============================== SIGN UP ================================");

        while (true) {
            System.out.print("Enter username (alphanumeric, 5-15 chars): ");
            String username = sc.nextLine();

            if (!username.matches("^[A-Za-z0-9]{5,15}$")) {
                System.out.println("      ***Invalid username! Must be 5-15 alphanumeric characters.***      ");
                System.out.println("");
                continue;
            }

            System.out.print("Enter password (1 uppercase, 1 number, 8-20 chars): ");
            String password = sc.nextLine();

            if (!password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
                System.out.println("                   ***Invalid password! Try again.***                   ");
                System.out.println("");
                continue;
            }

            users.add(new String[]{username, password});
            System.out.println("                       *** Signup successful! ***                       ");
            System.out.println("");
            break;
        }
    }

    // Login prompt -----------------------------------------------------------------------------
    public static boolean login() {
        System.out.println("=============================== LOG IN =================================");

        while (true) {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            for (String[] user : users) {
                if (user[0].equals(username) && user[1].equals(password)) {
                    currentUser = username;
                    System.out.println("                        *** Login successful! ***                        ");
                    System.out.println("");
                    return true;
                }
            }

            System.out.println("             ***Invalid username or password. Try again.***             ");
            System.out.println("");
        }
    }

    // View Menu ----------------------------------------------------------------------
    public static void viewMenu() {
        System.out.println("================================= MENU ==================================");
        System.out.println("         |                  A. CHICKEN INASAL                   |");
        System.out.println("         |   ID        NAME                             PRICE   |");
        System.out.println("         |   PM1       PAA/SPICY PAA (1 rice)           P99     |");
        System.out.println("         |   PM2       PETCHO (1 rice)                  P111    |");
        System.out.println("         |                    B. HALO HALO                      |");
        System.out.println("         |   ID        NAME                             PRICE   |");
        System.out.println("         |   HH1       PINOY HALO-HALO (small)          P53     |");
        System.out.println("         |   HH2       PINOY HALO-HALO (regular)        P73     |");
        System.out.println("         |   CL1       CREMA DE LECHE  (small)          P62     |");
        System.out.println("         |   CL2       CREMA DE LECHE  (regular)        P82     |");
        System.out.println("         |                     C. INUMIN                        |");
        System.out.println("         |   ID        NAME                             PRICE   |");
        System.out.println("         |   SG1       SAGO'T GULAMAN (small)           P32     |");
        System.out.println("         |   SD1       SOFTDRINK (regular)              P32     |");
        System.out.println("         |   SD2       SOFTDRINK (large)                P42     |");
        System.out.println("         |   IT1       ICED TEA (regular)               P34     |");
        System.out.println("         |   IT2       ICED TEA (large)                 P44     |");
        System.out.println("         |                  D. MERIENDA ATBP.                   |");
        System.out.println("         |   ID        NAME                             PRICE   |");
        System.out.println("         |   PB1       PALABOK                          P69     |");
        System.out.println("         |   LT1       LUMPIANG TONGUE (2pc.)           P45     |");
        System.out.println("         |   LF1       LECHE FLAN (large)               P29     |");
        System.out.println("");
    }

    // Price Mapping ----------------------------------------------------------------------
    static HashMap<String, Integer> priceMap = new HashMap<>();

    static {
        priceMap.put("PM1", 99);
        priceMap.put("PM2", 111);
        priceMap.put("HH1", 53);
        priceMap.put("HH2", 73);
        priceMap.put("CL1", 62);
        priceMap.put("CL2", 82);
        priceMap.put("SG1", 32);
        priceMap.put("SD1", 32);
        priceMap.put("SD2", 42);
        priceMap.put("IT1", 34);
        priceMap.put("IT2", 44);
        priceMap.put("PB1", 69);
        priceMap.put("LT1", 45);
        priceMap.put("LF1", 29);
    }

    // Order Food ----------------------------------------------------------------------
    public static void orderFood(ArrayList<String> orderList, ArrayList<Integer> quantityList, ArrayList<Integer> totalList) {
        System.out.println("============================= ORDER FOOD ================================");
        viewMenu();
       
        String addOrder = "";
        do {
            System.out.print("Enter your order ID: ");
            String orderChoice = sc.nextLine().toUpperCase();

            if (!priceMap.containsKey(orderChoice) || !orderChoice.matches("^[A-Za-z]{2}\\d{1,2}$")) {
                System.out.println("            ***Invalid item! Please enter a valid item ID.***            ");
                System.out.println("");
                continue;
            }

            int orderQuantity = 0;
            while (true) {
                System.out.print("Quantity: ");
                try {
                    orderQuantity = Integer.parseInt(sc.nextLine());
                    if (orderQuantity <= 0) {
                        System.out.println("                 ***Quantity must be greater than 0!***                 "); // added try-catch feature kineme
                        System.out.println("");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("            ***Invalid input! Please enter a valid number.***            ");
                    System.out.println("");
                }
            }

            int price = priceMap.get(orderChoice);
            int total = price * orderQuantity;

            orderList.add(orderChoice);
            quantityList.add(orderQuantity);
            totalList.add(total);

            System.out.println("                      ***Added: " + orderChoice + " x" + orderQuantity + " (P" + total + ")***                    ");
            System.out.print("Do you want to add another order? (Y/N): ");
            addOrder = sc.nextLine().toUpperCase();
        } while (addOrder.equals("Y"));
    }

    // View Cart ----------------------------------------------------------------------
    public static void viewCart(ArrayList<String> orderList, ArrayList<Integer> quantityList, ArrayList<Integer> totalList) {
        System.out.println("============================== YOUR CART ================================");

        if (orderList.isEmpty()) {
            System.out.println("                        ***Your cart is empty!***                        ");
            System.out.println("");
        } else {
            int grandTotal = 0;
            for (int i = 0; i < orderList.size(); i++) {
                System.out.println((i + 1) + ". " + orderList.get(i) + " x" + quantityList.get(i) + " - P" + totalList.get(i));
                grandTotal += totalList.get(i);
            }
            System.out.println("=========================================================================");
            System.out.println("                           TOTAL AMOUNT: P" + grandTotal);
        }
            System.out.println("=========================================================================");
    }

    // Edit Cart ----------------------------------------------------------------------
    public static void editCart(ArrayList<String> orderList, ArrayList<Integer> quantityList, ArrayList<Integer> totalList) {
        System.out.println("============================== EDIT CART ================================");
        
        viewCart(orderList, quantityList, totalList);

        int editOrderID = -1;
        while (true) {
            System.out.print("Enter order ID to edit: ");
            try {
                editOrderID = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("       ***Invalid input! Please enter a valid numeric order ID.***       "); // added try-catch feature kineme
                System.out.println("");
            }
        }

        if (editOrderID > 0 && editOrderID <= orderList.size()) {
            int index = editOrderID - 1;

            int newQuantity;
            while (true) {
                System.out.print("Enter new quantity for " + orderList.get(index) + "(Enter '-1' to remove order): ");
                try {
                    newQuantity = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("       ***Invalid input! Please enter a valid numeric quantity.***       "); // eto ren added try-catch feature
                    System.out.println("");
                }
            }

            if (newQuantity <= 0) {
                orderList.remove(index);
                quantityList.remove(index);
                totalList.remove(index);
                System.out.println("                     ***Order removed from cart.***                     ");
                System.out.println("");
            } else {
                int newTotal = newQuantity * priceMap.get(orderList.get(index));
                quantityList.set(index, newQuantity);
                totalList.set(index, newTotal);
                System.out.println("                          ***Order updated!***                          ");
                System.out.println("");
            }
        } else {
            System.out.println("                       ***Invalid order number!***                       ");
            System.out.println(""); 
        }
    }

    // Pay Order ----------------------------------------------------------------------
    public static void payOrder(ArrayList<String> orderList, ArrayList<Integer> quantityList, ArrayList<Integer> totalList) {
        System.out.println("============================== PAY ORDER ================================");

        int totalAmount = 0;
        for (int total : totalList) {
            totalAmount += total;
        }

        System.out.println("=========================================================================");
        System.out.println("                           TOTAL AMOUNT: P" + totalAmount);
        System.out.println("=========================================================================");

        int payment;
        while (true) {
            System.out.print("Enter payment amount: P");
            try {
                payment = Integer.parseInt(sc.nextLine());
                if (payment < totalAmount) {
                    System.out.println("        ***Insufficient payment! Please enter a valid amount.***        ");
                    System.out.println("");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("    ***Invalid input! Please enter a valid numeric payment amount.***    "); // added try-catch feature kineme
            }
        }
        System.out.println("                        ***Payment Successful!***                        ");
        System.out.println("=========================================================================");
        System.out.println("                                CHANGE: P" + (payment - totalAmount));
        System.out.println("=========================================================================");
        System.out.println("                   ***Check your receipt for details.***                ");
        System.out.println("");

        transactionLogger(orderList, quantityList, totalList);

        // orderList.clear();
        // quantityList.clear();
        // totalList.clear();
    }

    // Receipt ----------------------------------------------------------------------
    public static void transactionLogger(ArrayList<String> orderList, ArrayList<Integer> quantityList, ArrayList<Integer> totalList) {
        try (FileWriter writer = new FileWriter("transactions.txt", true)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            writer.write("=============== TRANSACTION ===============\n");
            writer.write("                DATE & TIME                \n");
            writer.write("            " +now.format(formatter) + "\n");
            writer.write("    ___________________________________    \n");
            writer.write("                  CASHIER                  \n");
            writer.write("                 " +currentUser + "\n");
            writer.write("    ___________________________________    \n");
            writer.write("                   ITEMS                   \n");

                if (orderList.isEmpty()) {
                    writer.write("          ***No items ordered.***          \n");
                    writer.write("===========================================\n");
                    return;
                }

            int grandTotal = 0;
            for (int i = 0; i < orderList.size(); i++) {
                writer.write("              - " + orderList.get(i) + " x" + quantityList.get(i) + " = P" + totalList.get(i) + "\n");
                grandTotal += totalList.get(i);
            }
            
            writer.write("    ___________________________________    \n");
            writer.write("                   TOTAL                   \n");
            writer.write("                   P" + grandTotal + "\n");
            writer.write("    ___________________________________    \n");
            writer.write("   ***Thank you for using our system!***   \n");
            writer.write("===========================================\n");
        } catch (IOException e) {
            System.out.println("***Error logging transaction: " + e.getMessage() + "***"); // added try-catch here
        }
    }

    public static void main(String[] args) {
        System.out.println("=============== WELCOME TO MANG INASAR ORDERING SYSTEM! ===============");
        System.out.println("");
        signup();
        if (!login()) return;

        String newTransaction;
        do {
            ArrayList<String> orderList = new ArrayList<>();
            ArrayList<Integer> quantityList = new ArrayList<>();
            ArrayList<Integer> totalList = new ArrayList<>();

            boolean exitMenu = false;
            while (!exitMenu) {
                System.out.println("1. View Menu");
                System.out.println("2. Order Food");
                System.out.println("3. View Cart");
                System.out.println("4. Edit Cart");
                System.out.println("5. Pay Order");
                System.out.println("6. Exit Transaction");

                int choice = 0;
                while (choice < 1 || choice > 6) {
                    System.out.print("Enter your choice: ");
                    try {
                        choice = Integer.parseInt(sc.nextLine());
                        if (choice < 1 || choice > 6) {
                            System.out.println("***Invalid choice! Please enter a number between 1 and 6.***");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("***Invalid input! Please enter a valid number between 1 and 6.***");
                    }
                }

                switch (choice) {
                    case 1:
                        viewMenu();
                        break;
                    case 2:
                        orderFood(orderList, quantityList, totalList);
                        break;
                    case 3:
                        viewCart(orderList, quantityList, totalList);
                        break;
                    case 4:
                        editCart(orderList, quantityList, totalList);
                        break;
                    case 5:
                        payOrder(orderList, quantityList, totalList);
                        break;
                    case 6:
                        System.out.println("***Thank you! Exiting transaction...");
                        exitMenu = true;
                        break;
                }

                if (choice == 5) break;
            }

            System.out.print("Do you want to start another transaction? (Y/N): ");
            newTransaction = sc.nextLine().toUpperCase();
        } while (newTransaction.equals("Y"));

        System.out.println("==========================================================================");
        System.out.println("             THANK YOU FOR USING MANG INASAR ORDERING SYSTEM!             ");
        System.out.println("==========================================================================");
    }
}