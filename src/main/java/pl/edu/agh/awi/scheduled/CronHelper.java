package pl.edu.agh.awi.scheduled;

public interface CronHelper {

    String TEST_CRON = "1/30 * * * * ?";

    String QUATERLY_CRON = "* 0/15 * * * ?";

    String METAR_CRON = "* 12 */1 * * *";

    String TAF_CRON = "* 22 */6 * * *";

    String AIRSIGMET_CRON = "* 47 */1 * * *";

}
