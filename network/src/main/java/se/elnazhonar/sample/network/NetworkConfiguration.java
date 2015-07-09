package se.elnazhonar.sample.network;

/**
 * The idea is to put network configuration info, such as base url, user agent,... in one place
 * Its better to setup this configuration with gradle's product flavor builds,if the app target
 * different endpoints
 *
 */
public class NetworkConfiguration {

    public static String BASE_URL = "https://api.lifesum.com/v1/";
}
