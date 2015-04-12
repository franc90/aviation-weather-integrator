package pl.edu.agh.awi.scheduled;

public interface CronHelper {

    String EVERY_QUARTER = "1/3 * * * * ?";

    String METAR_CRON = "* 12 */1 * * *";

    String TAF_CRON = "* 22 */6 * * *";

    String AIRSIGMET_CRON = "* 47 */1 * * *";

}
