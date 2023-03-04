# Spring performance tracking
AOP based library for performance tracking of spring web requests

## How to use
1. Clone repo
2. Publish to local repo
    ```
   ./gradlew publishToMavenLocal
   ```
3. Add dependency to your project
    ```
   implementation("com.cheypnow.web-request-tracing:core:1.0")
   ```
4. Add config
    ```
    @Configuration
    @EnableAspectJAutoProxy
    @Import(value = [SpringPerformanceTrackingAutoConfiguration::class])
    class PerformanceTrackingConfig {
    }
   ```
5. Add annotations to methods that should be tracked
   ```
   @PerformanceTracking
   fun test() {
   ...
   }
   ```
6. To use tracking data implement custom listener or use default `TrackingDataLogListener`:
    ```
    @Bean
    fun getTrackingDataLogProcessor() : TrackingDataOutputListener {
        return TrackingDataLogListener()
    }
   ```
## Tracking data
For each monitored endpoint, the library collects the running time of the entire endpoint, the running time, and the arguments of the monitored nested methods.

Below is a log produced by `TrackingDataLogListener`:
```
2023-03-04 21:58:35.763  INFO 54122 --- [nio-8080-exec-7] c.x.c.o.log.TrackingDataLogListener      : 
 228	/test
   224	Controller.endpoint
     224	TestServiceOne.test
        51	TestServiceTwo.testTwo
            arg2 = 2
            agr1 = JPWPUPD
       114	TestServiceTwo.testTwo
            arg2 = 4
            agr1 = HWEYXFZWD
          30	TestServiceThree.testThree
              strArg = AMGPCRKZC
          35	TestServiceThree.testThree
              strArg = YBPRSOBYV
```