package pl.edu.agh.awi.downloader.weather;

import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;

import javax.xml.bind.JAXB;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractClient<T> {

    private Class<T> type;

    public AbstractClient(Class<T> type) {
        this.type = type;
    }

    protected URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new MalformedUrlException("Wrong URL: " + url, e);
        }
    }

    protected T unmarshallResponse(URL url) {
        T response = JAXB.unmarshal(url, type);
        return response;
    }

}
