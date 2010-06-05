package nl.iteye.process.bulkaddresschangeprocess;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import nl.iteye.process.bulkaddresschangeprocess.model.BulkAddressChangeRequest;
import org.apache.camel.Converter;
import org.apache.xmlbeans.impl.common.ReaderInputStream;

/**
 *
 * @author andrej
 */
@Converter
public class AddressChangeConverter {

    @Converter
    public static InputStream toInputStream(BulkAddressChangeRequest bacr) throws JAXBException, UnsupportedEncodingException {
        //InputStream is;
        JAXBContext jaxbContext = JAXBContext.newInstance(bacr.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", true);
        final StringWriter xmlWriter = new StringWriter();
        marshaller.marshal(bacr, xmlWriter);
        //log.info("xml document: " + xmlWriter);
        StringReader sr = new StringReader(xmlWriter.toString());

        return new ReaderInputStream(sr,"UTF-8");
    }
}
