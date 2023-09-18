import java.util.ArrayList;
import java.util.Scanner;

class Main {
    
    //Variables for objects
    String productName;
    String productKey;
    boolean productType;
    double price;
    double amount;

    String expenseName;
    int expenseLength;
    double expense;
    
    /*
     * Function which constructs object
     * pre: none
     * post: object is created with certain attributes (for adding inventory)
     */
    public Main(String productName, String productKey, boolean productType, double price, double amount) {
        this.productName = productName;
        this.productKey = productKey;
        this.productType = productType;
        this.price = price;
        this.amount = amount;
    }


  
    /*
     * Function which constructs object
     * pre: none
     * post: object is created with certain (for adding expense)
     */
    public Main(String expenseName, int expenseLength, double expense) {
        this.expenseName = expenseName;
        this.expenseLength = expenseLength;
        this.expense = expense;
    }

  
    /*
     * Function repeats until user inputs double variable
     * pre: none
     * post: function returns double
     */
    public static double get_num () {

        Scanner sc = new Scanner(System.in);
        double num = 0;
        while(true) { //loops until loop is broken

            //Input - User inputs values until they input a value compatible with double variables
            try { // try-catch block tries to take user input and store it in a double
                num  = sc.nextDouble(); 
                break; // if successful, loop breaks
            } catch (Exception e) { // if fails, continue loop
                sc.next();
            }
        }

        return num; //return number
    }



  
    /*
     * Function prints out existing inventory from objects in ArraylIST
     * pre: none
     * post: prints all inventory and associated information 
     */
    public static void print_inventory(ArrayList<Main> inventory) {
        System.out.println("\033[H\033[2J"); //clears screen
        for(Main a : inventory) { // for each object in inventory ArrayList

            //Output - Output attributes of elements previously put into ArrayList
            
            System.out.println("Product Name: " + a.productName);
            System.out.println("Product Key: " + a.productKey);
            System.out.print("Product Type: ");

            if (a.productType == true) { //if product type is true
                System.out.println("by item");
            } else { //if product type is false
                System.out.println("by kilo");
            }

            System.out.println("Product Price (per item or kilo) " + a.price);
            System.out.println("Amount in Inventory: " + a.amount + "" + "\n");
        }
        
    }



  
    /*
     * Function prints all expenses in ArrayList, and the amount of gains in day. Calculates and outputs day's balance
     * pre: none
     * post: calculate and output daily gains and spendings
     */
    public static double day(ArrayList<Main> expenses, double gains, double balance) {

        System.out.println("Gains: +$" + gains + "\n");
        
        //Process - Calculate daily balance by adding gains from day
        balance += gains;

        ArrayList <Main> remove = new ArrayList<>();
        for(Main a : expenses) { //for all objects in ArrayList

            //Output - Print all expenses for day
            System.out.println(a.expenseName + " (" + a.expenseLength + " days left): -$" + a.expense);
            
            //Process - take away expense value from daily balance, decrease length of expense left
            balance -= a.expense;
            a.expenseLength --;

            //Process - if the expense length is over, take it off of daily expenses
            if (a.expenseLength < 1) {
                remove.add(a); //if expense length is over, add it to remove list
            }

        }

        for (Main a : remove) { // for all elements in remove arraylist
            expenses.remove(a); //remove element from expenses arraylist
        }


        //Output - Print daily balance
        System.out.println( "\n" + "Balance: $" + (int) (balance * 100)/100.0);
        return balance; // return calculated balance to be stored in the main method
    }



  
    /*
     * Add product to inventory after user input
     * pre: none
     * post: create a new object to return to main class
     */
    public static Main add_inventory() {
        Scanner sc = new Scanner(System.in);

        //Input - User inputs information about new inventory
        System.out.print("Product Name: ");
        String productN = sc.nextLine();
        System.out.print("Product Key: ");
        String productK = sc.nextLine().toUpperCase();
        System.out.print("Product Type (sold by ITEM or KILO): ");
        boolean productT = true;

        String productt = sc.nextLine().toUpperCase();
            
        while(!productt.equals("ITEM") && !productt.equals("KILO")) { // loops until user inputs either item or kilo
            productt = sc.nextLine().toUpperCase();
        }

        if(productt.equals("ITEM")) { //if user inputs item, boolean will store as true
            productT = true;
        } else if (productt.equals("KILO")) { //if user inputs kilo, boolean will store as false
            productT = false;
        }

        System.out.print("Price (per item or kg) (in $): ");
        double productP = get_num(); //execute get_num function
        System.out.print("# or kg of product in inventory: ");
        double amou = get_num(); //execute get_num function

        //create new object and return to main method       
        return (new Main(productN, productK, productT, productP, amou));
    }


  
    /*
     * Loop through cashier function, which has user input product keys and amount to create a reciept
     * pre: none
     * post: calculate customer's total payment, return gains from order to main function
     */
    public static double cashier(ArrayList<Main> inventory) {
        Scanner sc = new Scanner(System.in);
        String code = "";
        double gain = 0;
        String message = "";
        //Input - user either inputs commands, or adds item by entering keyword and amount
        while (!code.equals("DONE")) { //block of code will loop until user indicates they are done
            code = sc.nextLine().toUpperCase(); // user inputs keyword of product
            
            if(code.equals("DONE")) { //if user inputs DONE 
                break; // break out of loop
            } else if(code.equals("SHOW INV")) { //if user inputs SHOW INV
                print_inventory(inventory); //print inventory using function
                code = sc.nextLine().toUpperCase(); //prompt user again

                if(code.equals("DONE")) { //if code is DONE 
                    break; // break out of loop
                }
                
            }
            
            double amount = get_num(); //get number of items or kgs purchased
            
            
            int index = 0;

            //Process - Using user input, see if product is able to be bought, then add to list

            while ((!(inventory.get(index).productKey).equals(code)) && (index < (inventory.size() - 1))) { // linear search until user input matches with inventory code
                index ++;

            }

            if((inventory.get(index).amount <  amount)) { //if customer tries to order more than the store has
                System.out.println("INSUFFICIENT INVENTORY!");
              
            } else if ((inventory.get(index)).productKey.equals(code)) { //if linear search was successful

                //add name of product, amount bought, and price to output message
                message += (inventory.get(index).productName + " x " + amount + " --- $" + ((int)(amount * inventory.get(index).price * 100)/100.0) + "\n");
                
                //add cost of item to gains
                gain += (int)(amount * inventory.get(index).price * 100)/100.0;
                inventory.get(index).amount -= amount; //remove amount of inventory taken through order

                if (inventory.get(index).amount <= 0) { //if inventory runs out, remove from arraylist
                    inventory.remove(index);
                }

                //Output - output information from order to user
                System.out.println("\033[H\033[2J");
                System.out.println(message);

            } 
            
        }

        //Output - Output order informaton, subtotal, tax, and total to user 
        System.out.println("\033[H\033[2J");
        System.out.println(message);
        System.out.println("_____________________________");
        System.out.println("SUBTOTAL: $" + gain + "\n");
        System.out.println("TAX: $" + ((int) (gain * 0.13 * 100)/100.0) + "\n");
        System.out.println("TOTAL: $" + ((int) (gain * 1.13 * 100)/100.0));

        return gain; //return amount of money gained
    }


  


  
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String input = "";
        double gain = 0;
        double balance = 0;

        ArrayList<Main> inventory = new ArrayList<>();
        ArrayList<Main> expenses = new ArrayList<>();



        System.out.println("Welcome to CashCountant, the centralized money manager for your small business."
                           + " Input (CMD) for the list of commands.");
      
    
        //continue program until user indicates they want to end it (using keyword END)
        //if the user inputs wrong keyword, block of code loops
        while(!input.toUpperCase().equals("END")) {

            //Input - user inputs keyword for functions, which is stored in input variable
            System.out.print("> ");
            input = sc.nextLine().toUpperCase(); //input user's value


            //Process - else-if block is used to process user input, checking if valid keyword was inputted
            if(input.equals("CMD")) { //if user's input is CMD (show all function keywords)
                System.out.println("\033[H\033[2J"); //clear screen

                //Output - Ouput all functions which the user can use 
                System.out.println("CMD --> Print all Command Codes");
                System.out.println("P INV --> Print Inventory");
                System.out.println("DAY --> Print Balance (end of day)");
                System.out.println("A INV --> Add Inventory");
                System.out.println("A EXP --> Add Expenses");
                System.out.println("CASH --> CASHIER");
                System.out.println("END --> End program.");
                System.out.println("(CASHIER COMMANDS) \n-- SHOW INV (show inventory) \n--> DONE (finish session)" 
                                        + "\n--> PRODUCT KEYWORD then NUMBER/WEIGHT (inputing new item to cash)");
                
            } 
            
            
            else if(input.equals("DAY")) { // if user's input is DAY (calculating balance once day is over)
                System.out.println("\033[H\033[2J"); // clear screen
                balance = day(expenses, gain, balance); // execute day function and store its return in balance variable
                gain = 0; // reset gains
               
                
            } 
            
            
            else if(input.equals("A INV")) { // if user's input is A INV (adding items to inventory)
                System.out.println("\033[H\033[2J"); // clear screen

                //Input - User inputs the number of times they want to add items to the inventory
                System.out.print("# of items: ");
                int times = (int) get_num(); // execute and store value of get_num function (function that loops until valid input is given)

                for(int i = 0; i < times; i++) { // repeat block of code # of times user wanted
                    inventory.add(add_inventory()); // store add_inventory's return value in inventory ArrayList

                    //Input - User inputs cost of their new inventory, which is stored in an object within the expenses ArrayList
                    System.out.print("Inventory Cost (in dollars): " );
                    expenses.add(new Main("Inventory", 1, get_num())); // create new object and store in expenses ArrayList
     
                    System.out.print("\n" );
                }
                
            } 
            
            
            
            else if(input.equals("A EXP")) { // if user's input is A EXP (adding expenses)
                System.out.println("\033[H\033[2J"); // clear screen
                
                //Input - User inputs how many times they want to add expenses
                System.out.print("# of expenses: ");
                int times = (int) get_num();

                for(int i = 0; i < times; i++) {
                    
                    //Input - User inputs the expense name, expense length, and cost of expense, which is stored in an object
                    System.out.print("Expense Name: ");
                    String expenseN = sc.nextLine();
                    System.out.print("Expense Length (in days): ");
                    int expenseL = (int) get_num(); //execute get_num function (function that loops until valid input is given)
                    System.out.print("Expense (in dollars): ");
                    double expen = get_num()/expenseL; //daily expense is total expense divided by expense length 

                    System.out.println();


                    expenses.add(new Main(expenseN, expenseL, expen)); // create new object and store in expenses ArrayList
      
                }
                
            } 
            
            
            else if(input.equals("CASH")) { // if user's input is CASH (cashier functionality)
                System.out.println("\033[H\033[2J"); // clear screen

                //Process - return value from cashier function is added to gains from day
                gain += cashier(inventory); //execute cashier function (with inventory ArrayList as parameter), add return to what gain already has
                
            }  
            
            
            else if (input.equals("P INV")) {
                //Output - Print out existing inventory
                print_inventory(inventory);
            }

      
    }

    
  }
  
}
