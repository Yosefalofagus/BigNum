import java.io.FileReader;
import java.util.Scanner;

public class BigNumArithmetic {
    public static String removeLeadingZeros(String expression) {
        //split is a String method that breaks up a string based on a regular expression
        String[] values = expression.split(" ");

        //loop through every value
        boolean zeros;
        String temp;
        for (int i = 0; i < values.length; i++) {
            //operators naturally have no leading zeros
            if (!values[i].equals("+") && !values[i].equals("-") && !values[i].equals("*")) {
                zeros = true;
                temp = "";
            /*
            if zeros is true, then we are still in the section
            of all zeros that needs to be trimmed. If zeros is
            not true, then we are onto the rest of the numbers
            and they are all concatenated onto a temp string
            that gets put back into the array
             */
                for (int j = 0; j < values[i].length(); j++) {
                    if (zeros) {
                        if (values[i].charAt(j) != 48) {
                            zeros = false;
                        }
                    } if (!zeros) {
                        temp += values[i].charAt(j);
                    }
                }

                //if the length is zero, then the number was all zeros
                if (temp.length() == 0) {
                    temp = "0"; //string is changed to just a single zero
                }

                values[i] = temp;
            }
        }
        //puts the values back in a string
        expression = "";
        for (String i : values) {
            expression += i;
            expression += " ";
        }
        //removes the last space that was added with the previous loop
        expression = expression.substring(0, expression.length() - 1);

        return expression;
    }
    public static String removeSpaces(String expression) {
        String tempExpression = "";
        //This adds the characters to a temp string
        //one by one, until it hits the spaces.
        //it then adds one space, and ignores
        //the others.
        for (int i = 0; i < expression.length(); i++) {
            if ((expression.charAt(i) >= 48 && expression.charAt(i) <= 57) ||
                    expression.charAt(i) == 43 ||
                    expression.charAt(i) == 42 ||
                    expression.charAt(i) == 45) {
                tempExpression += expression.substring(i, i + 1);
                if (expression.charAt(i) == 43 ||
                expression.charAt(i) == 42 ||
                expression.charAt(i) == 45) {
                    tempExpression += " ";
                }
            } else if (i != 0) {
                if (expression.charAt(i - 1) >= 48 && expression.charAt(i - 1) <= 57) {
                    tempExpression += " ";
                }
            }
        }
        //removes the final space added
        if (tempExpression.substring(tempExpression.length() - 1).equals(" ")) {
            tempExpression = tempExpression.substring(0, tempExpression.length() - 1);
        }
        return tempExpression;
    }
    //this is for converting between strings and LLists.
    //My LList represents numbers going from left to right,
    //highest order to lowest order.
    //(123 = 1 -> 2 -> 3)
    public static LList stringToLList(String str) {
        LList number = new LList();

        for (int i = 0; i < str.length(); i++) {
             number.append(str.substring(i, i + 1));
        }

        return number;
    }

    public static String llistToString(LList list) {

        String str = "";
        while (!list.isEmpty()) {
            list.moveToStart();
            str += list.remove();
        }
        return str;
    }

    public static LList addition(LList a, LList b) {
        LList sum = new LList();
        //this makes the two lists the same length
        if (a.length() > b.length()) {
            int index = a.length() - b.length();
             for (int i = 0; i < index; i++) {
                b.moveToStart();
                b.insert("0");
            }
        } else if (b.length() > a.length()){
            int index = b.length() - a.length();
            for (int i = 0; i < index; i++) {
                a.moveToStart();
                a.insert("0");
            }
        }

        Integer carry = 0;
        //going from right to left,
        //the numbers are added, with a carry
        //variable. the carry is set to the higher
        //order digit of the number as a 2 digit number
        // and the tempSum is the lower order bit.
        //tempsum is then inserted into the sum llist.
        //if there is a carry at the end, it is inserted.
        for (int i = a.length() - 1; i >= 0; i--) {
            sum.moveToStart();
            a.moveToPos(i);
            b.moveToPos(i);
            Integer tempSum = Integer.parseInt(a.remove().toString()) + Integer.parseInt(b.remove().toString()) + carry;

            carry = tempSum/10;

            if (tempSum > 9) {
                tempSum %= 10;
            }

            sum.insert(tempSum.toString());
        }
        if (carry != 0) {
            sum.moveToStart();
            sum.insert(carry);
        }
        return sum;
    }
    //this figures out which number has less digits
    //and uses that as the bottom number. it then does
    //the same loop as addition, except it multiplies, and
    //after each inner loop, it moves over one digit on the
    //bottom and adds an extra zero to end of the number
    //being added.
    public static LList multiplication(LList a, LList b) {
        LList big;
        LList small;

        if (a.length() > b.length()) {
            big = a;
            small = b;
        } else {
            big = b;
            small = a;
        }

        Integer carry;
        Integer product = 0;

        LList productOne = new LList();
        LList productTwo = new LList();

        for (int i = small.length() - 1; i >= 0; i--) {
            carry = 0;

            small.moveToPos(i);
            for (int j = big.length() - 1; j >= 0; j--) {
                big.moveToPos(j);
                product = Integer.parseInt(small.getValue().toString()) * Integer.parseInt(big.getValue().toString()) + carry;

                carry = product / 10;

                if (product > 9) {
                    product %= 10;
                }

                productOne.moveToStart();
                productOne.insert(product.toString());
            }

            if (carry > 0) {
                productOne.moveToStart();
                productOne.insert(carry);
            }

            productTwo = addition(productTwo, productOne);

            productOne.clear();
            for (int k = small.length() - i; k > 0; k--) {
                productOne.moveToStart();
                productOne.insert("0");
            }
        }
        return productTwo;
    }
    //first we find which number's value is greater with either
    //the number of digits, or if it is the same number of digits,
    //use compareTo. then we add zeros to the smaller number to make
    //it match the length of the larger one.
    //then we use the same algorithm as addition,
    //but instead we subtract everything.
    //the carry is also replaced with a borrow
    // which is 1 if the number at the end of the loop is
    //negative. The number adds 10. that is basically
    //"borrowing" a 10 from the next number.
    public static LList subtraction(LList a, LList b) {
        LList big = a;
        LList small = b;

        if (a.length() > b.length()) {
            big = a;
            small = b;
        } else if (b.length() > a.length()){
            big = b;
            small = a;
        } else {
            for (int i = 0; i < a.length(); i++) {
                a.moveToPos(i);
                b.moveToPos(i);
                if (Integer.parseInt(a.getValue().toString()) > Integer.parseInt(b.getValue().toString())) {
                    i = a.length();
                    big = a;
                    small = b;
                } else {
                    i = a.length();
                    big = b;
                    small = a;
                }
            }
        }

        int index = big.length() - small.length();
        for (int i = 0; i < index; i++) {
            small.moveToStart();
            small.insert("0");
        }

        int borrow = 0;

        LList ans = new LList();

        for (int i = big.length() - 1; i >= 0; i--) {
            big.moveToPos(i);
            small.moveToPos(i);

            Integer diff = Integer.parseInt(big.getValue().toString()) - Integer.parseInt(small.getValue().toString()) - borrow;

            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            ans.moveToStart();
            ans.insert(diff.toString());
        }

        return ans;
    }
    //This pushes numbers to the stack. when
    //it hits an operator, it checks if there are two
    //numbers in the stack already. if there is not,
    //then it returns empty string. if there is, it
    //pulls out two numbers, does the operation, then
    //puts that number in the stack.
    //if at the end there is one value in the stack,
    //then it returns that value. otherwise, it returns
    //empty string.
    public static String evaluateExpression(String expression) {
        expression = removeSpaces(expression);
        expression = removeLeadingZeros(expression);

        LStack expressionStack = new LStack();

        for (String value : expression.split(" ")) {
            if (value.equals("+")) {
                if (expressionStack.length() >= 2) {
                    expressionStack.push(addition((LList) expressionStack.pop(), (LList) expressionStack.pop()));
                } else {
                    return "";
                }
            } else if (value.equals("-")) {
                if (expressionStack.length() >= 2) {
                    expressionStack.push(subtraction((LList) expressionStack.pop(), (LList) expressionStack.pop()));
                } else {
                    return "";
                }
            } else if (value.equals("*")) {
                if (expressionStack.length() >= 2) {
                    expressionStack.push(multiplication((LList) expressionStack.pop(), (LList) expressionStack.pop()));
                } else {
                    return "";
                }
            } else {
                expressionStack.push(stringToLList(value));
            }
        }

        if (expressionStack.length() == 1) {
            return removeLeadingZeros(llistToString((LList) expressionStack.pop()));
        } else {
            return "";
        }
    }
    public static void main(String[] args) {
        String line;

        /*
        everything is contained within a try statement so that
        if there is no file name given, or the file is not found,
        it will print "file not found" and exit the program.
         */
        try {
            FileReader input = new FileReader(args[0]);
            Scanner in = new Scanner(input);
            /*
            this is the main loop that reads the file line by line
            and evaluates expressions. It checks if the line has
            an expression first though.
             */
            while (in.hasNext()) {
                line = in.nextLine();
                if(line.length() != 0) {
                    System.out.print(BigNumArithmetic.removeSpaces(line));
                    System.out.print(" = ");
                    System.out.print(BigNumArithmetic.evaluateExpression(line));
                    System.out.println();
                }
            }
        } catch (Exception fileNotFound) {
            System.out.println("file not found");
            fileNotFound.printStackTrace();
        }
    }
}