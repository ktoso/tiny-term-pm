package pl.project13.tinytermpm.marshalling;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

public class JAXBUtil {

    JAXBUtil() {
    }

    static public <T> T unmarshal(Class<T> clazz, String response) throws JAXBException {
        Unmarshaller unmarshaller = createUnmarshaller(clazz);

        T unmarshal = unmarshal(clazz, response, unmarshaller);
        return unmarshal;
    }

    static public <T> String marshal(T objectToMarshal) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(objectToMarshal.getClass());
            Marshaller marshaller = ctx.createMarshaller();
            StringWriter writer = new StringWriter(500);

            marshaller.marshal(objectToMarshal, writer);

            String marshaled = writer.toString();
            return marshaled;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    static private <T> T unmarshal(Class<T> clazz, String response, Unmarshaller unmarshaller) throws JAXBException {
        ByteArrayInputStream is = new ByteArrayInputStream(response.getBytes());
        Object unmarshalled = unmarshaller.unmarshal(is);
        return clazz.cast(unmarshalled);
    }

    static private <T> Unmarshaller createUnmarshaller(Class<T> clazz) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(clazz);
        return ctx.createUnmarshaller();
    }
}
