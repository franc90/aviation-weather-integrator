package pl.edu.agh.awi.downloader.weather;

import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;

import javax.xml.bind.JAXB;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractClient<T, U> {

    private Class<T> responseType;

    public AbstractClient(Class<T> responseType) {
        this.responseType = responseType;
    }

    public T getResponse(RequestParameters<U> parameters) {
        String formattedUrl = formatUrl(parameters);
        URL url = createUrl(formattedUrl);
        T response = JAXB.unmarshal(url, responseType);
        return response;
    }

    protected abstract String formatUrl(RequestParameters<U> parameters);

    private URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new MalformedUrlException("Wrong URL: " + url, e);
        }
    }

}
