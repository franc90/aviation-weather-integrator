package pl.edu.agh.awi.downloader.weather.parameters;

public class RequestParameters<T> {

    private final T value;

    private final int hoursBeforeNow;

    private RequestParameters(T value, int hoursBeforeNow) {
        this.value = value;
        this.hoursBeforeNow = hoursBeforeNow;
    }

    public T getValue() {
        return value;
    }

    public int getHoursBeforeNow() {
        return hoursBeforeNow;
    }

    public static class RequestParametersBuilder<T> {

        private T value;

        private int hoursBeforeNow;

        public RequestParametersBuilder<T> setValue(T value) {
            this.value = value;
            return this;
        }

        public RequestParametersBuilder<T> setHoursBeforeNow(int hoursBeforeNow) {
            this.hoursBeforeNow = hoursBeforeNow;
            return this;
        }

        public RequestParameters<T> build() {
            return new RequestParameters<>(value, hoursBeforeNow);
        }
    }

}
