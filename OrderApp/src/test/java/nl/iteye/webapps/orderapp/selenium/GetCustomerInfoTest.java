/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.webapps.orderapp.selenium;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

/**
 *
 * @author akoelewijn
 */
public class GetCustomerInfoTest extends SeleneseTestCase {

    public void setUp() throws Exception {
        setUp("http://localhost:8080/", "*chrome");
    }

    public void testUntitled() throws Exception {
        selenium.open("/OrderApp-1.0-SNAPSHOT/faces/index.xhtml");
        selenium.click("j_idt7:j_idt11");
        selenium.waitForPageToLoad("30000");
    }
}
