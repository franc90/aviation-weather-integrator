package pl.edu.agh.awi.scheduler.helper;

public interface CronHelper {

    String TEST_CRON = "0/30 * * * * ?";

    String QUATERLY_CRON = "* 0/15 * * * ?";

    String METAR_CRON = "* 7 */1 * * *";

    String TAF_CRON = "* 22 */6 * * *";

    String AIRSIGMET_CRON = "* 52 */1 * * *";

}
