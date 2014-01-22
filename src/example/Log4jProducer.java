package  example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author �޻�
 * @since 2012-2-27 ����5:54:34
 */

public class Log4jProducer {
    static final Log log = LogFactory.getLog("testLog");


    public static void main(final String[] args) {
        log.info("just a test");
    }
}
