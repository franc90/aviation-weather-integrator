package pl.edu.agh.awi.scheduler.helper;

public interface CronHelper {

    String TEST_CRON = "0/30 * * * * ?";

    String FLIGHT_CRON = "* 0/15 * 1 * ?";

    String FLIGHT_DETAIL_CRON = "* 5/15 * 1 * ?";

    String METAR_CRON =  "* 12 */1 * * *";

    String TAF_CRON = "* 26 */6 * * *";

    String AIRSIGMET_CRON = "* 48 */1 * * *";

}
