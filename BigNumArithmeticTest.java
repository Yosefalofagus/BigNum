import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BigNumArithmeticTest {
    /*
    This tests the main method with addition problems provided by PC
     */
    @Test
    public void BigNumEvaluateAddition_2_5_plus_output_7() {
        assertEquals("7", BigNumArithmetic.evaluateExpression("2 5 +"));
    }

    @Test
    public void BigNumEvaluateAddition_0002_20000_plus_output_20002() {
        assertEquals("20002", BigNumArithmetic.evaluateExpression("0002 20000 +"));
    }

    @Test
    public void BigNumValuateAddition_REALLYLARGE_True() {
        assertEquals("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", BigNumArithmetic.evaluateExpression("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999 1 +"));
    }

    @Test
    public void BigNumEvaluateMultiplication_34_17_output_578() {
        assertEquals("578", BigNumArithmetic.evaluateExpression("34 17 *"));
    }
    @Test
    public void BigNumEvaluateMultiplication_17_34_output_578() {
        assertEquals("578", BigNumArithmetic.evaluateExpression("17 34 *"));
    }

    @Test
    public void BigNumEvaluateMultiplication_99999999999_99999999999999_output_9999999999899900000000001() {
        assertEquals("9999999999899900000000001", BigNumArithmetic.evaluateExpression("99999999999 99999999999999 *"));
    }

    @Test
    public void BigNumRemoveLeadingZeros_000_output_0() {
        assertEquals("0", BigNumArithmetic.removeLeadingZeros("000"));
    }
    @Test
    public void BigNumEvaluateMultiplication_5_5_5_5_5_output_3125() {
        assertEquals("3125", BigNumArithmetic.evaluateExpression("5 5 5 5 5 * * * *"));
    }

    @Test
    public void BigNumEvaluateSubtraction_37_25_output_12() {
        assertEquals("12", BigNumArithmetic.evaluateExpression("37 25 -"));
    }

    @Test
    public void BigNumEvaluateSubtraction_25_37_output_12() {
        assertEquals("12", BigNumArithmetic.evaluateExpression("25 37 -"));
    }

    @Test
    public void BigNumTooManyOperators() {
        assertEquals("", BigNumArithmetic.evaluateExpression("2 2 + +"));
    }

    @Test
    public void BigNumMultiplication_5000_10_output_50000() {
        assertEquals("50000", BigNumArithmetic.evaluateExpression("5000 10 *"));
    }
    @Test
    public void BigNumMultiplication_123456789_987654321_output_121932631112635269() {
        assertEquals("121932631112635269", BigNumArithmetic.evaluateExpression("123456789 987654321 *"));
    }

    @Test
    public void LListTest() {
        LList list = new LList();
        list.append(0);
        list.insert(0);
        list.isEmpty();
        list.isAtEnd();
        list.prev();
        list.moveToEnd();
        list.remove();
        list.get(0);
        LStack stack = new LStack();
        stack.topValue();
        stack.clear();
        assertTrue(list.contains(0));
    }
}
